package by.dak.swing.wizard;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardObserver;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: akoyro
 * Date: 20.06.2009
 * Time: 15:53:44
 */
public abstract class DWizardController<P extends DWizardPanelProvider, M, S extends WizardStep>
{
    private P provider;
    private M model;
    private S lastSelectedStep;
    private GenericWizardObserver genericWizardObserver = new GenericWizardObserver();
    private WizardSessionController wizardSessionController = new WizardSessionController(this);
    private StepChangedController stepChangedController = new StepChangedController();
    private ApplicationContext context;

    @Deprecated
    public DWizardController()
    {
        this(Application.getInstance().getContext());
    }

    public DWizardController(ApplicationContext context)
    {
        this.context = context;
        wizardSessionController.setSessionStorage(context.getSessionStorage());
        genericWizardObserver.addWizardObserver(wizardSessionController);
        genericWizardObserver.addWizardObserver(stepChangedController);
    }


    public P getProvider()
    {
        return provider;
    }

    public void setProvider(P provider)
    {
        this.provider = provider;
        provider.addWizardFinishedListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                if (lastSelectedStep != null)
                {
                    reloadProperties(false, getWizardSessionController().getComponentBy(getIdBy(lastSelectedStep)));
                }
            }
        });
    }


    public M getModel()
    {
        return model;
    }

    public void setModel(M model)
    {
        this.model = model;
    }

    public WizardSessionController getWizardSessionController()
    {
        return wizardSessionController;
    }

    public void setWizardSessionController(WizardSessionController wizardSessionController)
    {
        this.wizardSessionController = wizardSessionController;
    }

    public GenericWizardObserver getGenericWizardObserver()
    {
        return genericWizardObserver;
    }

    public S getLastSelectedStep()
    {
        return lastSelectedStep;
    }

    /**
     * Использовать этот метод для настройки шага перед его открытием
     *
     * @param currentStep
     */
    protected abstract void adjustCurrentStep(S currentStep);

    /**
     * Использовать этот метод для настройки шага перед текущим
     *
     * @param currentStep
     */
    protected abstract void adjustLastStep(S currentStep);

    protected abstract S getStepBy(String id);

    protected abstract String getIdBy(S step);

    public class StepChangedController implements WizardObserver
    {
        @Override
        public void stepsChanged(Wizard wizard)
        {
        }

        @Override
        public void navigabilityChanged(Wizard wizard)
        {
        }

        @Override
        public void selectionChanged(Wizard wizard)
        {

            final S step = getStepBy(wizard.getCurrentStep());

            final S lastStep = getLastSelectedStep();

            adjustLastStep(step);

            adjustCurrentStep(step);

            Runnable runnable = new Runnable()
            {
                @Override
                public void run()
                {
                    if (lastStep != null)
                    {
                        reloadProperties(false, getWizardSessionController().getComponentBy(getIdBy(lastStep)));
                    }
                    reloadProperties(true, getWizardSessionController().getComponentBy(getIdBy(step)));
                }
            };
            SwingUtilities.invokeLater(runnable);
            lastSelectedStep = step;
        }
    }

    private void reloadProperties(boolean load, JComponent jComponent)
    {
        try
        {
            if (load)
            {
                Application.getInstance().getContext().getSessionStorage().restore(jComponent, jComponent.getName() + ".xml");
            }
            else
            {
                Application.getInstance().getContext().getSessionStorage().save(jComponent, jComponent.getName() + ".xml");
            }
        }
        catch (Exception e)
        {
            Logger.getLogger(DWizardController.class.getName()).log(Level.SEVERE, "c.getParent() == null", e);
        }
    }


}
