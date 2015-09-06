package by.dak.persistence.dao.impl;

import by.dak.cutting.SpringConfiguration;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 30.03.2010
 * Time: 17:37:31
 * To change this template use File | Settings | File Templates.
 */
public class TOrderGroupFacade
{
    public static void main(String[] args)
    {
        new SpringConfiguration();

        OrderGroup orderGroup = new OrderGroup();
        orderGroup.setName("orderGroup1");

        FacadeContext.getOrderGroupFacade().save(orderGroup);

        System.out.println(FacadeContext.getOrderGroupFacade().loadAll().get(0).getName());
    }
}
