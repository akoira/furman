package by.dak.cutting.facade;

import by.dak.persistence.entities.Delivery;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DeliveryFacade extends BaseFacade<Delivery>
{
}
