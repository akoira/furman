package by.dak.furman.nifi;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Order;
import by.dak.plastic.jasper.data.DSPPlasticCuttedReportDataCreator;
import by.dak.report.ReportType;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.ReportGeneratorImpl;
import by.dak.report.jasper.common.data.CommonDataCreator;
import by.dak.report.jasper.common.data.CommonReportData;
import by.dak.report.jasper.cutting.data.CuttedReportDataCreator;
import by.dak.report.model.impl.ReportsModelImpl;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ReportsCalculate {
    public static final Logger logger = LogManager.getLogger(ReportsCalculate.class);

    public interface func {
        Observable<MainFacade> main_facade = CuttingModelCalculate.func.main_facade;

        Function<Context, Function<ReportType, JasperPrint>> jasper_print = c -> type -> {
            switch (type) {
                case doorsingle:
                case doorscommon:
                case common:
                case production_common:
                case cutting:
                case glueing:
                case cutoff:
                case milling:
                case store:
                case cutting_dsp_plastic:
                    try {
                        return ReportGeneratorImpl.getInstance().generate(c.reportData.get(type));
                    } catch (JRException e) {
                        throw new IllegalArgumentException(e);
                    }
                case zfacade:
                case agtfacade:
                    try {
                        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(c.reportData.get(type).getJasperReportPath());
                        return JasperFillManager.fillReport(jasperReport, c.reportData.get(type).getAdditionalParams(), c.mainFacade.getJDBCConnection());
                    } catch (Throwable e) {
                        throw new IllegalArgumentException(e);
                    }
                default:
                    throw new IllegalArgumentException();
            }
        };


        Function<MainFacade, Function<AOrder, CuttingModel>> cutting_model = mf ->
                order -> mf.getStripsFacade().loadCuttingModel(order).load();

        Function<AOrder, Function<CuttingModel, ReportsModelImpl>> reports_model = order -> cuttingModel -> {
            ReportsModelImpl rm = new ReportsModelImpl();
            rm.setOrder(order);
            rm.setCuttingModel(cuttingModel);
            return rm;
        };

        Function<MainFacade, Function<AOrder, Function<CuttingModel, CommonReportData>>> common_report_data = mf -> o -> cm ->
                new CommonDataCreator(cm).create();

        Function<Context,
                Function<ReportType, Object>> report_object = c -> type -> {
            switch (type) {
                case common:
                case production_common:
                    return (CommonReportData) common_report_data.apply(c.mainFacade).apply(c.order).apply(c.cuttingModel);
                case cutting:
                    return new CuttedReportDataCreator(c.cuttingModel).create();
                case glueing:
                case cutoff:
                case milling:
                case store:
                case zfacade:
                case agtfacade:
                    return (AOrder) c.order;
                case cutting_dsp_plastic:
                    return new DSPPlasticCuttedReportDataCreator(c.mainFacade.getDspPlasticStripsFacade()
                            .loadCuttingModel(c.order).load()).create();
                default:
                    throw new IllegalArgumentException();
            }
        };

        Function<MainFacade, Function<ReportType, Function<Object, JReportData>>> report_data = mf -> type -> object ->
                mf.reportCreatorFactory.createReportDataCreator(object, type).create();

        Function<Context, Context> delete = c -> {
            c.mainFacade.getCommonDataFacade().deleteAll(c.order);
            c.mainFacade.getReportFacade().removeAll(c.order);
            return c;
        };

        Function<Context, Single<Map<ReportType, Object>>> report_objects = c -> Observable.fromIterable(c.reportsModel.getReportTypes()).reduce(new HashMap<ReportType, Object>(), (map, t) -> {
            map.put(t, func.report_object.apply(c).apply(t));
            return map;
        });

        Function<Context, Single<Map<ReportType, JReportData>>> report_datas = c -> Observable.fromIterable(c.reportsModel.getReportTypes())
                .reduce(new HashMap<ReportType, JReportData>(), (map, t) -> {
                    map.put(t, func.report_data.apply(c.mainFacade).apply(t).apply(c.reportObjects.get(t)));
                    return map;
                });

        Function<Context, Single<Map<ReportType, JasperPrint>>> jasper_prints = c -> Observable.fromIterable(c.reportsModel.getReportTypes())
                .reduce(new HashMap<ReportType, JasperPrint>(), (map, t) -> {
                    map.put(t, func.jasper_print.apply(c).apply(t));
                    return map;
                });

        Function<Context, Single<ReportsModelImpl>> fill_model = c -> Observable.fromIterable(c.reportsModel.getReportTypes())
                .reduce(c.reportsModel, (model, t) -> {
                    model.setReportData(t, c.reportData.get(t));
                    model.setJasperPrint(t, c.jasperPrints.get(t));
                    return model;
                });

        Function<Context, Single<ReportsModelImpl>> save = c -> Observable.fromIterable(c.reportsModel.getReportTypes())
                .reduce(c.reportsModel, (model, t) -> {
                    c.mainFacade.getReportFacade().saveReport(c.order, t, c.reportsModel.getJasperPrint(t));
                    return model;
                });

    }


    public static void main(String[] args) throws IOException {
        CuttingApp.loadTTF();
        Observable<ReportsModelImpl> observable = Observable.just(Context.context(Long.parseLong(args[0])))
                .observeOn(Schedulers.io())
                .map(c -> c.mainFacade(func.main_facade.blockingFirst()))
                .map(c -> c.order(c.mainFacade.getOrderFacade().findBy(c.orderId)))
                .doOnNext(c -> logger.info("order loaded"))
                .doOnNext(func.delete::apply)
                .doOnNext(c -> logger.info("cleared all"))
                .map(c -> c.cuttingModel(func.cutting_model.apply(c.mainFacade).apply(c.order)))
                .doOnNext(c -> logger.info("cutting model loaded"))
                .map(c -> c.reportsModel(func.reports_model.apply(c.order).apply(c.cuttingModel)))
                .map(c -> c.reportObjects(func.report_objects.apply(c).blockingGet()))
                .doOnNext(c -> logger.info("report objects loaded"))
                .map(c -> c.reportData(func.report_datas.apply(c).blockingGet()))
                .doOnNext(c -> logger.info("report data loaded"))
                .map(c -> c.jasperPrints(func.jasper_prints.apply(c).blockingGet()))
                .doOnNext(c -> logger.info("jasper prints  loaded"))
                .doOnNext(c -> func.fill_model.apply(c).blockingGet())
                .doOnNext(c -> logger.info("model filled"))
                .doOnNext(c -> func.save.apply(c).blockingGet())
                .doOnNext(c -> logger.info("model saved"))
                .map(c -> c.reportsModel);
        observable.blockingForEach(System.out::println);
    }
}
