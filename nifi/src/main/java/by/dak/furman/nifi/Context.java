package by.dak.furman.nifi;

import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.Order;
import by.dak.report.ReportType;
import by.dak.report.jasper.JReportData;
import by.dak.report.model.impl.ReportsModelImpl;
import net.sf.jasperreports.engine.JasperPrint;

import java.util.Map;

public class Context {
    public final Long orderId;
    public final MainFacade mainFacade;
    public final Order order;

    public final CuttingModel cuttingModel;

    public final ReportsModelImpl reportsModel;

    public final Map<ReportType, Object> reportObjects;

    public final Map<ReportType, JReportData> reportData;

    public final Map<ReportType, JasperPrint> jasperPrints;

    private Context(Long orderId, MainFacade mainFacade, Order order,
                    CuttingModel cuttingModel, ReportsModelImpl reportsModel,
                    Map<ReportType, Object> reportObjects,
                    Map<ReportType, JReportData> reportData,
                    Map<ReportType, JasperPrint> jasperPrints) {
        this.orderId = orderId;
        this.mainFacade = mainFacade;
        this.order = order;
        this.cuttingModel = cuttingModel;
        this.reportsModel = reportsModel;
        this.reportObjects = reportObjects;
        this.reportData = reportData;
        this.jasperPrints = jasperPrints;
    }

    public Context orderId(Long orderId) {
        return new Context(orderId, mainFacade, order, cuttingModel,
                reportsModel, reportObjects, reportData, jasperPrints);
    }

    public Context mainFacade(MainFacade mainFacade) {
        return new Context(orderId, mainFacade, order, cuttingModel,
                reportsModel, reportObjects, reportData, jasperPrints);
    }

    public Context order(Order order) {
        return new Context(orderId, mainFacade, order, cuttingModel,
                reportsModel, reportObjects, reportData, jasperPrints);
    }

    public Context cuttingModel(CuttingModel cuttingModel) {
        return new Context(orderId, mainFacade, order, cuttingModel,
                reportsModel, reportObjects, reportData, jasperPrints);
    }

    public Context reportsModel(ReportsModelImpl reportsModel) {
        return new Context(orderId, mainFacade, order, cuttingModel,
                reportsModel, reportObjects, reportData, jasperPrints);
    }

    public Context reportObjects(Map<ReportType, Object> reportObjects) {
        return new Context(orderId, mainFacade, order, cuttingModel,
                reportsModel, reportObjects, reportData, jasperPrints);
    }

    public Context reportData(Map<ReportType, JReportData> reportData) {
        return new Context(orderId, mainFacade, order, cuttingModel,
                reportsModel, reportObjects, reportData, jasperPrints);
    }

    public Context jasperPrints(Map<ReportType, JasperPrint> jasperPrints) {
        return new Context(orderId, mainFacade, order, cuttingModel,
                reportsModel, reportObjects, reportData, jasperPrints);
    }


    public static Context context(Long orderId) {
        return new Context(orderId, null, null,
                null, null,
                null, null, null);
    }
}
