package by.dak.cutting;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Customer;
import org.junit.Test;

import java.util.List;

import static by.dak.cutting.facade.impl.helper.CustomerLimitChecker.isCustomerLimitReached;

public class TCustomerLimitChecker {
    @Test
    public void isCustomerLimitReached__test() {
        new SpringConfiguration();

        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(128);

        boolean isArrear = isCustomerLimitReached(customer);

        System.out.println(isArrear);
    }
}
