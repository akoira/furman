package by.dak.cutting.facade;

import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CashIncomeFacade extends BaseFacade<CashIncome> {
    List<CashIncome> findAllBy(Customer customer);
}
