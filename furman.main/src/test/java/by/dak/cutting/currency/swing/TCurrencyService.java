package by.dak.cutting.currency.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.currency.CurrencyService;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TCurrencyService {

    @Test
    public void currencyServiceTest() {
        new SpringConfiguration();
        CurrencyService currencyService = new CurrencyService();

        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(88);
        List<CashIncome> cashIncomeList = FacadeContext.getCashIncomeFacade().findAllBy(customer);

        List<CashIncome> convertedCashIncomes = currencyService.convertAllIncomes(cashIncomeList);
        assertEquals(0, BigDecimal.valueOf(446.8740).compareTo(convertedCashIncomes.get(0).getAmount()));

    }
}
