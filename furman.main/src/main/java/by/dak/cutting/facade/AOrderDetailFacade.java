package by.dak.cutting.facade;

import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.AOrderDetail;
import by.dak.persistence.entities.OrderItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: akoyro
 * Date: 21.07.2010
 * Time: 19:36:14
 */
@Transactional
public interface AOrderDetailFacade<T extends AOrderDetail> extends BaseFacade<T>
{
    public void deleteAllBy(OrderItem orderItem);

    public List<T> loadAllBy(AOrder order);

    public List<T> loadAllBy(OrderItem orderItem);
}
