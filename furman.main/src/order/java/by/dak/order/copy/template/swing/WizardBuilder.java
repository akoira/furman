package by.dak.order.copy.template.swing;

import org.jdesktop.application.ApplicationContext;

import java.util.Arrays;

/**
 * User: akoyro
 * Date: 11/24/13
 * Time: 8:37 PM
 */
public class WizardBuilder
{
    private ApplicationContext context;

    public AddTemplateWizardProvider build()
    {
        IStep[] steps = buildSteps();

        WizardContext wizardContext = new WizardContext();
        wizardContext.setSteps(Arrays.asList(steps));
        wizardContext.setTitle(context.getResourceMap(WizardBuilder.class).getString("title"));
        wizardContext.init();

        AddTemplateWizardProvider provider = new AddTemplateWizardProvider(wizardContext);
        return provider;
    }

    private IStep[] buildSteps()
    {
        SelectTemplate selectTemplate = new SelectTemplate();
        selectTemplate.setContext(context);
        selectTemplate.init();

        ReplaceMaterial replaceMaterial = new ReplaceMaterial();
        replaceMaterial.setContext(context);
        replaceMaterial.init();

        PrepareFacade prepareFacade = new PrepareFacade();
        prepareFacade.setContext(context);
        prepareFacade.init();

        return new IStep[]{selectTemplate, replaceMaterial, prepareFacade};
    }

    public ApplicationContext getContext()
    {
        return context;
    }

    public void setContext(ApplicationContext context)
    {
        this.context = context;
    }
}
