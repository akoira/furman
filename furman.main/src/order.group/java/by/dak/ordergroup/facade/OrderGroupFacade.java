package by.dak.ordergroup.facade;

import by.dak.cutting.facade.BaseFacade;
import by.dak.ordergroup.OrderGroup;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderGroupFacade extends BaseFacade<OrderGroup>
{
    public OrderGroup createOrderGroup();
}
