package by.dak.cutting.facade;

import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
public interface CashIncomeFacade extends BaseFacade<CashIncome> {
    List<CashIncome> findAllBy(Customer customer);
    List<CashIncome> getAllIncomesBy(Customer customer, List<Long> reasons);
    List<CashIncome> getAllIncomesForArrear(Customer customer);
}
