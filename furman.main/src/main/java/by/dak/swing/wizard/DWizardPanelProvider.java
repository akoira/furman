package by.dak.swing.wizard;

import by.dak.cutting.swing.DModPanel;
import org.netbeans.spi.wizard.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

/**
 * User: akoyro
 * Date: 20.06.2009
 * Time: 15:46:56
 */
public abstract class DWizardPanelProvider<S extends WizardStep> extends WizardPanelProvider
{
    public static final String PROPERTY_wizardFinished = "wizardFinished";
    public static final String PROPERTY_wizardCanceled = "wizardCanceled";

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    protected DWizardPanelProvider(String title, String[] steps, String[] descriptions)
    {
        super(title, steps, descriptions);
    }

    public abstract JPanel getComponentBy(S step);

    public abstract S valueOf(String id);

    @Override
    protected JComponent createPanel(WizardController controller, String id, Map settings)
    {
        S step = valueOf(id);
        return createWizardPage(step, getComponentBy(step));
    }


    protected WizardPage createWizardPage(S step, final JPanel panel)
    {
        WizardPage wizardPage = new WizardPage(step.name())
        {
            @Override
            public WizardPanelNavResult allowBack(String stepName, Map settings, Wizard wizard)
            {
                return super.allowBack(stepName, settings, wizard);
            }

            @Override
            public WizardPanelNavResult allowFinish(String stepName, Map settings, Wizard wizard)
            {
                if (panel instanceof DModPanel)
                {
                    return ((DModPanel) panel).validateGUI() ? WizardPanelNavResult.PROCEED : WizardPanelNavResult.REMAIN_ON_PAGE;
                }
                return super.allowFinish(stepName, settings, wizard);
            }

            @Override
            public WizardPanelNavResult allowNext(String stepName, Map settings, Wizard wizard)
            {
                if (panel instanceof DModPanel)
                {
                    return ((DModPanel) panel).validateGUI() ? WizardPanelNavResult.PROCEED : WizardPanelNavResult.REMAIN_ON_PAGE;
                }
                return super.allowNext(stepName, settings, wizard);
            }
        };
        wizardPage.setName(step.name());
        wizardPage.setLayout(new BorderLayout());
        wizardPage.add(panel, BorderLayout.CENTER);
        return wizardPage;
    }

    @Override
    public boolean cancel(Map settings)
    {
        boolean result = super.cancel(settings);
        propertyChangeSupport.firePropertyChange(PROPERTY_wizardCanceled, null, settings);
        return result;
    }

    @Override
    protected Object finish(Map settings) throws WizardException
    {
        Object result = super.finish(settings);
        propertyChangeSupport.firePropertyChange(PROPERTY_wizardFinished, null, settings);
        return result;
    }

    public synchronized void addWizardFinishedListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(PROPERTY_wizardFinished, listener);
    }

    public synchronized void removeWizardFinishedListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(PROPERTY_wizardFinished, listener);
    }
}
