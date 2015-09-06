package by.dak.cutting.facade;

import by.dak.cutting.SpringConfiguration;
import by.dak.lang.Period;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.customer.CustomerAccount;

import java.util.List;

/**
 * User: akoyro
 * Date: 04.05.11
 * Time: 15:37
 */
public class TCustomerFacade
{
    public static void main(String[] args)
    {
        new SpringConfiguration();

        List<CustomerAccount> customerAccountList = FacadeContext.getCustomerFacade().loadCustomerAccounts(Period.all);
        System.out.println(customerAccountList);
    }
}
