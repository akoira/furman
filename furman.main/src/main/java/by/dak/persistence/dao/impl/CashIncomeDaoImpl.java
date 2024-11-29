package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.CashIncomeDao;
import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;

import java.util.List;

public class CashIncomeDaoImpl extends GenericDaoImpl<CashIncome> implements CashIncomeDao {

    @Override
    public List<CashIncome> getAllIncomesBy(Customer customer, List<Long> reasons) {
        return getSession().getNamedQuery("allByCustomerReasons")
                .setParameter("customer", customer)
                .setParameterList("reasons", reasons)
                .list();
    }
}
