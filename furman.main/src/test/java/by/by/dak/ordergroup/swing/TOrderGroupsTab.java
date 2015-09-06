package by.by.dak.ordergroup.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.ordergroup.swing.OrderGroupsTab;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 14.01.2011
 * Time: 13:58:02
 */
public class TOrderGroupsTab
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        OrderGroupsTab orderGroupsTab = new OrderGroupsTab();
        TestUtils.showFrame(orderGroupsTab, "OrderGroupsTab");
    }
}
