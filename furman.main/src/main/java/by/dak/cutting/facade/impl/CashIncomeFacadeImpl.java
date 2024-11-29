package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.CashIncomeFacade;
import by.dak.persistence.dao.CashIncomeDao;
import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CashIncomeFacadeImpl extends BaseFacadeImpl<CashIncome> implements CashIncomeFacade {
   private final List<Long> REASONS_FOR_ARREAR = new ArrayList<>(Arrays.asList(1L, 2L, 12L, 13L));


    @Override
    public List<CashIncome> findAllBy(Customer customer) {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(CashIncome.PROPERTY_customer, customer);

        return super.loadAll(filter);
    }

    @Override
    public List<CashIncome> getAllIncomesBy(Customer customer, List<Long> reasons) {
        return ((CashIncomeDao) dao).getAllIncomesBy(customer, reasons);
    }

    @Override
    public List<CashIncome> getAllIncomesForArrear(Customer customer) {
        return getAllIncomesBy(customer, REASONS_FOR_ARREAR);
    }
}
