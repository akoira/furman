package by.dak.cutting.facade;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.*;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TOrderFacade {
    @Test
    public void findAllByCustomerStatusesDateTest__customerId_159951() throws ParseException {
        new SpringConfiguration();

        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(27);

        String inputString = "01-01-2019";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dateFrom = dateFormat.parse(inputString);

        List<OrderStatus> statuses = new ArrayList<>(Arrays.asList(OrderStatus.design, OrderStatus.production, OrderStatus.webDesign));

        List<Order> orders = FacadeContext.getOrderFacade().findAllByCustomerStatusesDate(customer, dateFrom, statuses);
        System.out.println(orders);
    }

}
