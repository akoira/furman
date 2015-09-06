package by.dak.additional.swing;

import by.dak.persistence.entities.OrderItem;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 19.06.2010
 * Time: 16:41:12
 */
public class TAdditionalsTab
{
    public static void main(String[] args)
    {
        AdditionalsTab tab = new AdditionalsTab();
        OrderItem orderItem = new OrderItem();
        orderItem.setName("Test item");
        tab.setValue(orderItem);
        TestUtils.showFrame(tab, "test");
    }
}