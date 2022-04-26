package by.dak.furman.nifi;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.cut.CuttingModel;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.expression.ExpressionLanguageScope;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.apache.nifi.annotation.behavior.InputRequirement.Requirement.INPUT_REQUIRED;

@Tags({"furman", "CuttingModel"})
@CapabilityDescription("Recalculate cutting model for order")
@InputRequirement(INPUT_REQUIRED)
public final class CalcCuttingModel extends AbstractProcessor {
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

        Observable<CuttingModel> observable = Observable.just(orderId)
                .flatMap(id -> CuttingModelCalculate.func.main_facade
                        .observeOn(Schedulers.io())
                        .flatMap(mf -> Observable.just(mf.getOrderFacade().findBy(id))
                                .doOnNext(CuttingModelCalculate.func.delete.apply(mf))
                                .map(CuttingModelCalculate.func.model.apply(mf))
                                .flatMap(model -> CuttingModelCalculate.func.calculate.apply(mf).apply(model).toObservable())
                                .doOnNext(model -> mf.getStripsFacade().saveAll(model))
                        )
                );
        observable.blockingSubscribe(cm -> {
                },
                throwable -> {
                    getLogger().error(throwable.getMessage(), throwable);
                    session.putAttribute(file, "exception", ExceptionUtils.getStackTrace(throwable));
                    session.transfer(file, REL_FAILURE);
                },
                () -> session.transfer(file, REL_SUCCESS));
    }
}
