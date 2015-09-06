package by.dak.cutting.swing.dictionaries.service;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Service;
import by.dak.swing.wizard.DWizardController;
import by.dak.swing.wizard.WizardStep;

/**
 * User: akoyro
 * Date: 20.06.2009
 * Time: 15:52:11
 */
public class ServiceWizardController extends DWizardController<ServiceWizardPanelProvider, ServiceModel, ServiceWizardController.Step>
{
    public ServiceWizardController(Service service)
    {
        ServiceModel model = new ServiceModel();
        model.setService(service);
        setProvider(ServiceWizardPanelProvider.createInstance());
        setModel(model);
    }

    @Override
    protected void adjustCurrentStep(Step currentStep)
    {
        switch (currentStep)
        {
            case service:
                getProvider().getServicePanel().setValue(getModel().getService());
                break;
            case prices:
                getProvider().getServicePricesPanel().setValue(getModel().getService());
                getProvider().getServicePricesPanel().setPrices(FacadeContext.getPriceFacade().findAllBy(getModel().getService()));
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    protected void adjustLastStep(Step currentStep)
    {
        if (getLastSelectedStep() != null)
        {
            switch (getLastSelectedStep())
            {
                case service:
                    getProvider().getServicePanel().save();
                    break;
                case prices:
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        else
        {

        }
    }

    @Override
    protected ServiceWizardController.Step getStepBy(String id)
    {
        return Step.valueOf(id);
    }

    @Override
    protected String getIdBy(Step step)
    {
        return step.name();
    }

    public static enum Step implements WizardStep
    {
        service,
        prices
    }
}
