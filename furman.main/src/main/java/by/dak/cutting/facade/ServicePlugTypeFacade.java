package by.dak.cutting.facade;

import by.dak.persistence.entities.ServicePlugType;
import by.dak.persistence.entities.predefined.Unit;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ServicePlugTypeFacade extends PriceAwareFacade<ServicePlugType> {
    ServicePlugType findByUnit(Unit unit);
}
