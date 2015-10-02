package by.dak.swing.wizard;

import org.jdesktop.application.SessionStorage;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardObserver;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Контроллер используется для сохранение и востановление панелей wizard.
 */
public class WizardSessionController implements WizardObserver
{
    private SessionStorage sessionStorage;
    private String previosStep;
    private DWizardController wizardController;

    public WizardSessionController(DWizardController wizardController)
    {
        this.wizardController = wizardController;
    }

    protected JComponent getComponentBy(String id)
    {
        return wizardController.getProvider().getComponentBy(wizardController.getStepBy(id));
    }

    public SessionStorage getSessionStorage()
    {
        return sessionStorage;
    }

    public void setSessionStorage(SessionStorage sessionStorage)
    {
        this.sessionStorage = sessionStorage;
    }


    private void save(JComponent component)
    {
        try
        {
            getSessionStorage().save(component, component.getName() + ".xml");
        } catch (Exception e)
        {
            Logger.getLogger(WizardSessionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void load(JComponent component)
    {
        try
        {
            getSessionStorage().restore(component, component.getName() + ".xml");
        } catch (Exception e)
        {
            Logger.getLogger(WizardSessionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @Override
    public void selectionChanged(Wizard wizard)
    {
        String currentStep = wizard.getCurrentStep();
        if (currentStep != null)
        {
            final JComponent currentComponent = getComponentBy(currentStep);
            Runnable runnable = new Runnable()
            {
                @Override
                public void run()
                {
                    load(currentComponent);
                }
            };
            SwingUtilities.invokeLater(runnable);
        }

        if (previosStep != null)
        {
            final JComponent previosComponent = getComponentBy(previosStep);
            save(previosComponent);
        }

        previosStep = currentStep;
    }

    @Override
    public void stepsChanged(Wizard wizard)
    {
    }

    @Override
    public void navigabilityChanged(Wizard wizard)
    {
    }

}
