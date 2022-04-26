package by.dak.furman.nifi;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Func {
    public static final Logger logger = LogManager.getLogger(Func.class);

    Observable<MainFacade> main_facade = Observable.just(SpringConfiguration.home.get())
            .map(SpringConfiguration::getMainFacade)
            .doOnNext(mf -> logger.info("context initialized"));

    Function<MainFacade, Consumer<AOrder>> delete = context -> order -> {
        context.getStripsFacade().deleteAll(order);
        context.getReportFacade().removeAll(order);
    };
}
