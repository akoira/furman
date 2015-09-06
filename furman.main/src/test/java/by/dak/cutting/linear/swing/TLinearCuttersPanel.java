package by.dak.cutting.linear.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 17.02.11
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */
public class TLinearCuttersPanel
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        List<Order> orders = new ArrayList<Order>();
        Order order = FacadeContext.getOrderFacade().findUniqueByField(Order.PROPERTY_name, "TEST2");
//        Order order1 = FacadeContext.getOrderFacade().findUniqueByField(Order.PROPERTY_name, "TEST_NEW_CUTTING2");
        orders.add(order);
//        orders.add(order1);
        for (Order o : orders)
        {
//            FacadeContext.getFurnitureFacade().detachFrom(o);
        }
        OrderGroup orderGroup = FacadeContext.getOrderGroupFacade().findUniqueByField("name", "TEST2");

//        LinearCuttingModelCreator modelCreator = new LinearCuttingModelCreator();
//        LinearCuttingModel model = modelCreator.createCuttingModel(orderGroup);
//        LinearCuttersPanel linearCuttersPanel = new LinearCuttersPanel();
//        linearCuttersPanel.setCuttingModel(model);
//        TestUtils.showFrame(linearCuttersPanel, "");
    }
}
