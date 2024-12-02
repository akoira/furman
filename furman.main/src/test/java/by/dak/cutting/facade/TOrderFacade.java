package by.dak.cutting.facade;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.facade.impl.OrderFacadeImpl;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.*;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static by.dak.utils.convert.TimeUtils.parseDateFromString;

public class TOrderFacade {
    @Test
    public void findAllByCustomerStatusesDateTest__customerId_159951() throws ParseException {
        new SpringConfiguration();

        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(27);

        Date dateFrom = parseDateFromString("01-01-2019");

        List<OrderStatus> statuses = new ArrayList<>(Arrays.asList(OrderStatus.design, OrderStatus.production, OrderStatus.webDesign));

        List<Order> orders = FacadeContext.getOrderFacade().findAllByCustomerStatusesDate(customer, dateFrom, statuses);
        System.out.println(orders);
    }

    @Test
    public void getAllForArrear__customerId_159951() throws ParseException {
        new SpringConfiguration();

        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(27);

        Date dateFrom = parseDateFromString("01-01-2019");

        List<OrderStatus> statuses = new ArrayList<>(Arrays.asList(OrderStatus.design, OrderStatus.production, OrderStatus.webDesign));

        List<OrderFacadeImpl.OrderDto> orders = FacadeContext.getOrderFacade().getAllForArrear(customer, dateFrom, statuses);

        Double ordersSum = orders.stream().mapToDouble(OrderFacadeImpl.OrderDto::getTotalCost).sum();

        System.out.println(ordersSum);
    }

}
