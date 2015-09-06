package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.store.tabs.DeliveryTab;
import by.dak.persistence.entities.Delivery;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 01.02.2010
 * Time: 17:17:37
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryPanel extends AEntityEditorPanel<Delivery>
{
    private DeliveryTab deliveryTab;

    public DeliveryPanel()
    {
        setShowOkCancel(false);
    }

    @Override
    protected void addTabs()
    {
        deliveryTab = new DeliveryTab();
        addTab(getResourceMap().getString("tab.delivery.title"), deliveryTab);
    }
}
