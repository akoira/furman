package by.dak.cutting.swing.dictionaries.service;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.store.modules.ServicePanel;
import by.dak.swing.wizard.DWizardPanelProvider;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;

import static by.dak.cutting.swing.dictionaries.service.ServiceWizardController.Step.prices;
import static by.dak.cutting.swing.dictionaries.service.ServiceWizardController.Step.service;

/**
 * User: akoyro
 * Date: 20.06.2009
 * Time: 15:44:54
 */
public class ServiceWizardPanelProvider extends DWizardPanelProvider<ServiceWizardController.Step>
{
    private static final ResourceMap resourceMap = CuttingApp.getApplication().getContext().getResourceMap(ServiceWizardPanelProvider.class);


    private ServicePanel servicePanel = new ServicePanel();
    private ServicePricesPanel servicePricesPanel = new ServicePricesPanel();

    private ServiceWizardPanelProvider(String title, String[] steps, String[] descriptions)
    {
        super(title, steps, descriptions);
    }

    public static ServiceWizardPanelProvider createInstance()
    {
        return new ServiceWizardPanelProvider(resourceMap.getString("title"),
                new String[]{
                        service.name(), prices.name()
                },
                new String[]{
                        resourceMap.getString(service.name()),
                        resourceMap.getString(prices.name()),
                });
    }


    @Override
    public JPanel getComponentBy(ServiceWizardController.Step step)
    {
        switch (step)
        {
            case service:
                return servicePanel;
            case prices:
                return servicePricesPanel;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public ServiceWizardController.Step valueOf(String id)
    {
        return ServiceWizardController.Step.valueOf(id);
    }

    public ServicePanel getServicePanel()
    {
        return servicePanel;
    }


    public ServicePricesPanel getServicePricesPanel()
    {
        return servicePricesPanel;
    }


}
