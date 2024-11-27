package by.dak.cutting.facade;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Discounts;
import by.dak.persistence.entities.Order;
import org.junit.Test;

import java.util.List;

public class TOrderFacade {
    @Test
    public void findAllByCustomerStatusesDateTest__customerId_159951() {
        new SpringConfiguration();

        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(128);

        List<Order> orders = FacadeContext.getOrderFacade().findAllByCustomerStatusesDate(customer);
        System.out.println(orders);
    }

}
