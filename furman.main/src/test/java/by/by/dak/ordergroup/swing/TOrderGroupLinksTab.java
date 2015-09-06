package by.by.dak.ordergroup.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.ordergroup.OrderGroup;
import by.dak.ordergroup.swing.OrdersTab;
import by.dak.persistence.FacadeContext;
import by.dak.test.TestUtils;

import java.util.List;

/**
 * User: akoyro
 * Date: 15.01.2011
 * Time: 18:21:46
 */
public class TOrderGroupLinksTab
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        OrdersTab ordersTab = new OrdersTab();

        OrderGroup orderGroup = getOrderGroup();

        ordersTab.setValue(orderGroup);

        TestUtils.showFrame(ordersTab, ordersTab.getTitle());
    }

    public static OrderGroup getOrderGroup()
    {
        List<OrderGroup> orderGroups = FacadeContext.getOrderGroupFacade().loadAll();

        OrderGroup orderGroup = orderGroups.size() < 1 ? FacadeContext.getOrderGroupFacade().createOrderGroup() : orderGroups.get(0);
        return orderGroup;
    }
}
