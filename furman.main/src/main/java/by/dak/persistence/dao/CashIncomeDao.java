package by.dak.persistence.dao;

import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashIncomeDao extends GenericDao<CashIncome> {

    List<CashIncome> getAllIncomesBy(Customer customer, List<Long> reasons);
}
