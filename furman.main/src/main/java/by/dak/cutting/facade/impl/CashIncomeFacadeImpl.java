package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.CashIncomeFacade;
import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;

import java.util.List;

public class CashIncomeFacadeImpl extends BaseFacadeImpl<CashIncome> implements CashIncomeFacade {

    @Override
    public List<CashIncome> findAllBy(Customer customer) {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(CashIncome.PROPERTY_customer, customer);

        return super.loadAll(filter);
    }
}
