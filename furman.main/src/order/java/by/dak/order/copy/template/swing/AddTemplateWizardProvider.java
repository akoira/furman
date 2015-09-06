package by.dak.order.copy.template.swing;

import org.netbeans.spi.wizard.WizardController;
import org.netbeans.spi.wizard.WizardPanelProvider;

import javax.swing.*;
import java.util.Map;

/**
 * User: akoyro
 * Date: 11/22/13
 * Time: 8:04 PM
 */
public class AddTemplateWizardProvider extends WizardPanelProvider
{
    private WizardContext context;

    public AddTemplateWizardProvider(WizardContext context)
    {
        super(context.getTitle(), context.getNames(), context.getDescriptions());
        this.context = context;
    }

    @Override
    protected JComponent createPanel(WizardController wizardController, String s, Map map)
    {
        return context.getStepBy(s).getView();
    }
}