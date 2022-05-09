package by.dak.furman.nifi;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.gui.cuttingApp.CSawyer;
import by.dak.cutting.cut.gui.cuttingApp.IndividualSawyer;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Map;

import static by.dak.cutting.configuration.Constants.TIME_FOR_STRIP;

public class CuttingModelCalculate {
    public static final Logger logger = LogManager.getLogger(CuttingModelCalculate.class);

    public interface func {
        Function<CuttingModel, Function<TextureBoardDefPair, Function<Consumer<Strips>, CSawyer>>> sawyer = model -> pair -> consumer -> {
            CSawyer sawyer = new CSawyer();
            sawyer.setForceBestFit(true);
            sawyer.setForceMinimumRatio(0.2f);
            sawyer.setForceMigrationRatio(0.5f);
            sawyer.setCutSettings(model.getCutSettings(pair));
            sawyer.setNewSolutionListener(strips -> {
                try {
                    consumer.accept(strips);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            });
            return sawyer;
        };

        Function<MainFacade, Function<AOrder, CuttingModel>> model = context -> order -> {
            CuttingModel model = context.getStripsFacade().loadCuttingModel(order).load();
            Map<TextureBoardDefPair, CutSettings> settings = context.getStripsFacade().loadCutSettings(model.getOrder(), model.getPairs());
            model.setSettingsMap(settings);
            return model;
        };

        Function<MainFacade, Consumer<AOrder>> delete = context -> order -> {
            context.getStripsFacade().deleteAll(order);
            context.getReportFacade().removeAll(order);
        };

        Function<MainFacade, Function<CuttingModel, Function<TextureBoardDefPair, Consumer<Strips>>>> set_strips = mf -> model -> pair -> strips -> {
            synchronized (model) {
                model.putStrips(pair, strips);
            }
        };

        Function<CSawyer, Function<CuttingModel, Function<TextureBoardDefPair, CSawyer>>> wait_for_solution = sawyer -> model -> pair -> {
            long time = 0;
            while (model.getStrips(pair) == null || time < (model.getStrips(pair).getSegmentsCount() * TIME_FOR_STRIP))
                try {
                    time++;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            return sawyer;
        };

        Function<String, Observable<MainFacade>> main_facade = profile ->
                Observable.just(profile.equals("prod") ? SpringConfiguration.prod.get() : SpringConfiguration.profile.apply(profile).get())
                .map(SpringConfiguration::getMainFacade)
                .doOnNext(mf -> logger.info("context initialized"));

        Function<MainFacade, Function<CuttingModel, Single<CuttingModel>>> calculate = mf -> model -> Observable.fromIterable(model.getPairs())
                .flatMap(pair ->
                        Observable.just(func.sawyer.apply(model).apply(pair).apply(func.set_strips.apply(mf).apply(model).apply(pair)))
                                .doOnNext(IndividualSawyer::start)
                                .map(s -> func.wait_for_solution.apply(s).apply(model).apply(pair))
                                .doOnNext(CSawyer::stop)
                                .doOnNext(cs -> logger.info("pair {}:{} proceed", pair.getBoardDef().getName(), pair.getTexture().getName()))
                ).reduce(model, (m,s) -> m);
    }

    public static void main(String[] args) {

        Observable<CuttingModel> observable = Observable.just(123353550L)
                .flatMap(id -> func.main_facade.apply("home")
                        .observeOn(Schedulers.io())
                        .flatMap(mf -> Observable.just(mf.getOrderFacade().findBy(id))
                                .doOnNext(func.delete.apply(mf))
                                .map(func.model.apply(mf))
                                .flatMap(model -> func.calculate.apply(mf).apply(model).toObservable())
                                .doOnNext(model -> mf.getStripsFacade().saveAll(model))
                        )
                );
        observable.blockingForEach(s -> logger.info(s.toString()));
    }


}
