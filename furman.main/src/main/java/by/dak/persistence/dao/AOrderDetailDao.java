package by.dak.persistence.dao;

import by.dak.persistence.entities.AOrderDetail;
import by.dak.persistence.entities.OrderItem;
import org.springframework.stereotype.Repository;

/**
 * User: akoyro
 * Date: 21.07.2010
 * Time: 19:42:24
 */
@Repository
public interface AOrderDetailDao<T extends AOrderDetail> extends GenericDao<T>
{
    public void deleteAllBy(OrderItem orderItem);
}
