/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.persistence.dao.impl;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;

/**
 *
 * @author admin
 */
public class OrderDaoImplTest
{
    public static void main(String[] args)
    {
        SpringConfiguration sc = new SpringConfiguration();

        System.out.println(FacadeContext.getOrderFacade().getLastNumberBy(0l, 1000l));
        System.out.println(FacadeContext.getOrderFacade().getLastNumberBy(1000l, 2000l));
        System.out.println(FacadeContext.getOrderFacade().getLastNumberBy(3000l, Long.MAX_VALUE));
        

    }

}
