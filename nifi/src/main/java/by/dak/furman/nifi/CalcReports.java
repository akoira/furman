package by.dak.furman.nifi;

import by.dak.common.Funcs;
import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.report.model.impl.ReportsModelImpl;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.expression.ExpressionLanguageScope;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.*;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.apache.nifi.annotation.behavior.InputRequirement.Requirement.INPUT_REQUIRED;

@Tags({"furman", "Reports"})
@CapabilityDescription("Recalculate reports for order")
@InputRequirement(INPUT_REQUIRED)
public class CalcReports extends AbstractProcessor {
    public static final Relationship REL_SUCCESS = new Relationship.Builder()
            .name("success")
            .description("FlowFiles for which all content was collected.")
            .build();

    public static final Relationship REL_FAILURE = new Relationship.Builder()
            .name("failure")
            .description("In case a FlowFile generates an error during the ssh session, " +
                    "it will be routed to this relationship")
            .build();

    public static final PropertyDescriptor ORDER_ID = new PropertyDescriptor.Builder()
            .name("order.id")
            .displayName("order id")
            .description("order id")
            .expressionLanguageSupported(ExpressionLanguageScope.FLOWFILE_ATTRIBUTES)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .defaultValue("${order.id}")
            .required(true)
            .build();

    public static final PropertyDescriptor PROFILE = new PropertyDescriptor.Builder()
            .name("profile")
            .displayName("profile")
            .description("profile")
            .expressionLanguageSupported(ExpressionLanguageScope.FLOWFILE_ATTRIBUTES)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .defaultValue("${profile}")
            .required(true)
            .build();

    public static final List<PropertyDescriptor> properties = new LinkedList<>();

    static {
        properties.add(ORDER_ID);
        properties.add(PROFILE);
    }

    public static final Set<Relationship> relationships = new LinkedHashSet<>();

    static {
        relationships.add(REL_SUCCESS);
        relationships.add(REL_FAILURE);
    }

    @Override
    protected final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return properties;
    }

    @Override
    public final Set<Relationship> getRelationships() {
        return relationships;
    }

    @Override
    public void onTrigger(ProcessContext context, ProcessSession session) throws ProcessException {
        FlowFile file = session.get();

        Long orderId = context.getProperty(ORDER_ID).evaluateAttributeExpressions(file).asLong();
        String profile = context.getProperty(PROFILE).evaluateAttributeExpressions(file).getValue();
        if (profile != null)
            System.setProperty(CuttingApp.FURMAN_PROFILE, profile);

        Observable<ReportsModelImpl> observable = Observable.just(Context.context(orderId))
                .observeOn(Schedulers.io())
                .map(c -> c.mainFacade(ReportsCalculate.func.main_facade.apply(profile).blockingFirst()))
                .map(c -> c.order(c.mainFacade.getOrderFacade().findBy(c.orderId)))
                .doOnNext(c -> getLogger().info("order loaded"))
                .doOnNext(ReportsCalculate.func.delete::apply)
                .doOnNext(c -> getLogger().info("cleared all"))
                .map(c -> c.cuttingModel(ReportsCalculate.func.cutting_model.apply(c.mainFacade).apply(c.order)))
                .doOnNext(c -> getLogger().info("cutting model loaded"))
                .map(c -> c.reportsModel(ReportsCalculate.func.reports_model.apply(c.order).apply(c.cuttingModel)))
                .map(c -> c.reportObjects(ReportsCalculate.func.report_objects.apply(c).blockingGet()))
                .doOnNext(c -> getLogger().info("report objects loaded"))
                .map(c -> c.reportData(ReportsCalculate.func.report_datas.apply(c).blockingGet()))
                .doOnNext(c -> getLogger().info("report data loaded"))
                .map(c -> c.jasperPrints(ReportsCalculate.func.jasper_prints.apply(c).blockingGet()))
                .doOnNext(c -> getLogger().info("jasper prints  loaded"))
                .doOnNext(c -> ReportsCalculate.func.fill_model.apply(c).blockingGet())
                .doOnNext(c -> getLogger().info("model filled"))
                .doOnNext(c -> ReportsCalculate.func.save.apply(c).blockingGet())
                .doOnNext(c -> getLogger().info("model saved"))
                .map(c -> c.reportsModel);

        observable.blockingSubscribe(rm -> {
                },
                throwable -> {
                    getLogger().error(throwable.getMessage(), throwable);
                    session.putAttribute(file, "exception", ExceptionUtils.getStackTrace(throwable));
                    session.transfer(file, REL_FAILURE);
                },
                () -> session.transfer(file, REL_SUCCESS));
    }

    @Override
    protected void init(ProcessorInitializationContext context) {
        Funcs.init_fonts.run();
    }
}
