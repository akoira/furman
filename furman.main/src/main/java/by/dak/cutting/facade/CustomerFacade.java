package by.dak.cutting.facade;

import by.dak.lang.Period;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.customer.CustomerAccount;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CustomerFacade extends BaseFacade<Customer>
{
    Customer findById(long id, boolean lock);

    Customer getCurrentCustomer();

    List<CustomerAccount> loadCustomerAccounts(Period period);
}
