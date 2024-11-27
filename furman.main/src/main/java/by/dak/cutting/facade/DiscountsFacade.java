package by.dak.cutting.facade;

import by.dak.persistence.entities.Discounts;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface DiscountsFacade extends BaseFacade<Discounts> {
}
