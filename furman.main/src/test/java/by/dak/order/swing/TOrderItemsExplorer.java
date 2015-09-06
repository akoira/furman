package by.dak.order.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 15.03.11
 * Time: 13:46
 */
public class TOrderItemsExplorer
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        OrderItemsExplorer orderItemsExplorer = new OrderItemsExplorer();
        Order order = FacadeContext.getOrderFacade().findUniqueByField("name", "T8935");
        orderItemsExplorer.setOrder(order);
        TestUtils.showFrame(orderItemsExplorer, "orderItemsExplorer");
    }
}
