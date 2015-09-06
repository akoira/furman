package by.dak.cutting.swing.dictionaries.delivery;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.store.modules.BaseOrderPanel;
import by.dak.cutting.swing.store.modules.DeliveryPanel;
import by.dak.cutting.swing.store.modules.DeliveryTypePanel;
import by.dak.persistence.FacadeContext;
import by.dak.swing.wizard.DWizardPanelProvider;
import org.jdesktop.application.ResourceMap;
import org.netbeans.spi.wizard.WizardException;

import javax.swing.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 01.02.2010
 * Time: 16:16:31
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryWizardPanelProvider extends DWizardPanelProvider<DeliveryController.Step>
{
    private static final ResourceMap resourceMap = CuttingApp.getApplication().getContext().getResourceMap(DeliveryWizardPanelProvider.class);

    private DeliveryPanel deliveryPanel = null;
    private DeliveryTypePanel deliveryTypePanel = null;
    private BaseOrderPanel orderPanel = null;

    protected DeliveryWizardPanelProvider(String title, String[] steps, String[] descriptions)
    {
        super(title, steps, descriptions);
    }

    @Override
    protected Object finish(Map settings) throws WizardException
    {
        if (getOrderPanel().getTableCtrl().getListUpdater().getList().size() == 0)
        {
            FacadeContext.getDeliveryFacade().delete(getDeliveryPanel().getValue());
        }

        return super.finish(settings);
    }

    @Override
    public JPanel getComponentBy(DeliveryController.Step step)
    {
        switch (step)
        {
            case delivery:
                return getDeliveryPanel();
            case type:
                return getDeliveryTypePanel();
            case order:
                return getOrderPanel();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public DeliveryController.Step valueOf(String id)
    {
        return DeliveryController.Step.valueOf(id);
    }

    public static DeliveryWizardPanelProvider createInstance()
    {
        return new DeliveryWizardPanelProvider(resourceMap.getString("title"),
                new String[]{
                        DeliveryController.Step.delivery.name(),
                        DeliveryController.Step.type.name(),
                        DeliveryController.Step.order.name()
                },
                new String[]{
                        resourceMap.getString(DeliveryController.Step.delivery.name()),
                        resourceMap.getString(DeliveryController.Step.type.name()),
                        resourceMap.getString(DeliveryController.Step.order.name())
                });
    }

    public DeliveryPanel getDeliveryPanel()
    {
        if (deliveryPanel == null)
        {
            deliveryPanel = new DeliveryPanel();
        }

        return deliveryPanel;
    }


    public DeliveryTypePanel getDeliveryTypePanel()
    {
        if (deliveryTypePanel == null)
        {
            deliveryTypePanel = new DeliveryTypePanel();
        }

        return deliveryTypePanel;
    }

    public BaseOrderPanel getOrderPanel()
    {
        if (orderPanel == null)
        {
            orderPanel = new BaseOrderPanel();
            orderPanel.getTableCtrl().setShowFilterHeader(false);
            orderPanel.init();
        }
        return orderPanel;
    }

}
