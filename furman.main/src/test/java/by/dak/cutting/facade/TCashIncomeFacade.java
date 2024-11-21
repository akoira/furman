package by.dak.cutting.facade;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TCashIncomeFacade {
    public static void main(String[] args) {
        new SpringConfiguration();

        List<CashIncome> cashIncomeList = FacadeContext.getCashIncomeFacade().loadAll();
        System.out.println(cashIncomeList);
    }

    @Before
    public void before() {
        new SpringConfiguration();
    }

    @Test
    public void loadAllCashIncomesTest() {
        List<CashIncome> cashIncomeList = FacadeContext.getCashIncomeFacade().loadAll();
        System.out.println(cashIncomeList);
    }

    @Test
    @Transactional
    public void loadCashIncomesByCustomerTest__customerId_159951() {
        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(27);

        List<CashIncome> cashIncomeList = FacadeContext.getCashIncomeFacade().findAllBy(customer);
        System.out.println(cashIncomeList);
    }
}
