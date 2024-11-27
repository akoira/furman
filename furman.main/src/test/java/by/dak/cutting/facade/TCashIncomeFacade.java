package by.dak.cutting.facade;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public void loadCashIncomesByCustomerTest__customerId_159951() {
        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(27);

        List<CashIncome> cashIncomeList = FacadeContext.getCashIncomeFacade().findAllBy(customer);
        System.out.println(cashIncomeList);
    }

    @Test
    public void loadCashIncomesByCustomerTest__filteredByReasonId() {
        List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
        Customer customer = customers.get(27);

        List<Long> reasonIds = new ArrayList<>(Arrays.asList(1L, 2L, 12L, 13L));

        List<CashIncome> cashIncomeList = FacadeContext.getCashIncomeFacade().findAllBy(customer);
        List<CashIncome> filteredCashIncomeList = cashIncomeList.stream()
                .filter(c -> reasonIds.contains(c.getReasonId()))
                .collect(Collectors.toList());

        System.out.println(filteredCashIncomeList);
    }
}
