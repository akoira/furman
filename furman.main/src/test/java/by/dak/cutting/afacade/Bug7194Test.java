package by.dak.cutting.afacade;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.Order;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: Apr 14, 2009
 * Time: 1:39:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bug7194Test
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();

        createOrders();
        List<Order> list = FacadeContext.getOrderFacade().loadAllArchive();
        System.out.println(list);

    }

    private static void createOrders()
    {
        Dailysheet dailysheet = new Dailysheet();
        dailysheet.setEmployee(FacadeContext.getEmployeeFacade().loadAll().get(0));
        for (int month = 0; month < 12; month++)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, month);
            dailysheet.setDate(new Date(calendar.getTime().getTime()));
            FacadeContext.getDailysheetFacade().save(dailysheet);

            for (int orderNumber = 1; orderNumber < 20; orderNumber++)
            {
                Order order = new Order();
                order.setOrderNumber((long) orderNumber);
                order.setCreatedDailySheet(dailysheet);
                order.setName("Order" + orderNumber);
                order.setCustomer(FacadeContext.getCustomerFacade().loadAll().get(0));

                FacadeContext.getOrderFacade().save(order);
            }
        }
    }
}
