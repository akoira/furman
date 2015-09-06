package by.dak.cutting.facade;

import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Attachment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: akoyro
 * Date: 19.03.11
 * Time: 12:34
 */
@Transactional
public interface AOrderFacade<E extends AOrder> extends BaseFacade<E>
{
    public String parseOrderNumber(E order);

    public E initNewOrderEntity(String namePrefix);

    /**
     * recalculate properties dependences
     *
     * @param entity
     */
    public void recalculate(E entity);

    List<E> loadAllBy(OrderGroup value);

    List<Attachment> getAttachment(E order);
}
