package by.dak.cutting.facade;

import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.predefined.OrderItemType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author akoyro
 * @version 0.1 28.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
@Transactional
public interface OrderItemFacade extends BaseFacade<OrderItem>
{
    public List<OrderItem> loadBy(AOrder order);

    List<OrderItem> findBy(AOrder order, OrderItemType type);

}
