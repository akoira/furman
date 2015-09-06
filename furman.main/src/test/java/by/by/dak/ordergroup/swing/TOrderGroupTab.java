package by.by.dak.ordergroup.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.ordergroup.OrderGroup;
import by.dak.ordergroup.swing.OrderGroupTab;
import by.dak.persistence.FacadeContext;
import by.dak.test.TestUtils;

import java.util.List;

/**
 * User: akoyro
 * Date: 15.01.2011
 * Time: 15:12:40
 */
public class TOrderGroupTab
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        OrderGroupTab orderGroupTab = new OrderGroupTab();
        orderGroupTab.init();


        OrderGroup orderGroup = FacadeContext.getOrderGroupFacade().createOrderGroup();

        List<OrderGroup> orderGroups = FacadeContext.getOrderGroupFacade().findAllByField(OrderGroup.PROPERTY_name, orderGroup.getName());
        if (orderGroups.size() > 0)
        {
            orderGroup.setName(orderGroup.getName() + "-1");
        }

        orderGroupTab.setValue(orderGroup);

        TestUtils.showFrame(orderGroupTab, orderGroupTab.getTitle());
    }
}
