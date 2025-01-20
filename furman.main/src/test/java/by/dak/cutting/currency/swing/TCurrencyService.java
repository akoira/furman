package by.dak.cutting.currency.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.currency.CurrencyService;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;
import org.junit.Test;

import java.util.List;

public class TCurrencyService {

    @Test
    public void currencyService_factorTest() {
        new SpringConfiguration();
        CurrencyService currencyService = new CurrencyService();

        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(88);
        List<CashIncome> cashIncomeList = FacadeContext.getCashIncomeFacade().findAllBy(customer);
    }

    @Test
    public void currencyService_getSumTest() {
        new SpringConfiguration();
        CurrencyService currencyService = new CurrencyService();

        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(27);
        List<CashIncome> cashIncomeList = FacadeContext.getCashIncomeFacade().findAllBy(customer);
    }
}
