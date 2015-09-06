package by.dak.cutting.swing.order;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 17.07.2010
 * Time: 15:12:46
 */
public class TOrderItemsTab
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        OrderItemsTab orderItemsTab = new OrderItemsTab();
        Order order = FacadeContext.getOrderFacade().findBy(707400L);
        orderItemsTab.setValue(order);
        TestUtils.showFrame(orderItemsTab, "OrderItemsTab");
    }

}
