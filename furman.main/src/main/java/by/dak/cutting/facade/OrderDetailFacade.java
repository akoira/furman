package by.dak.cutting.facade;

import by.dak.persistence.entities.AOrderDetail;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderDetailFacade extends AOrderDetailFacade<AOrderDetail>
{
}