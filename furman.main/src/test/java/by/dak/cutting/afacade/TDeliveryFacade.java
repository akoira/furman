package by.dak.cutting.afacade;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Delivery;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 01.02.2010
 * Time: 15:58:49
 * To change this template use File | Settings | File Templates.
 */
public class TDeliveryFacade
{
    public static void main(String[] args)
    {
        new SpringConfiguration();

        Delivery delivery = new Delivery();
        delivery.setProvider(FacadeContext.getProviderFacade().loadAll().get(0));
        delivery.setDeliveryDate(new Date(System.currentTimeMillis()));

        FacadeContext.getDeliveryFacade().save(delivery);
    }
}
