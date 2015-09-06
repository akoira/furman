package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.AOrderDetailDao;
import by.dak.persistence.entities.AOrderDetail;
import by.dak.persistence.entities.OrderItem;
import org.hibernate.Query;

/**
 * User: akoyro
 * Date: 21.07.2010
 * Time: 19:42:09
 */
public class AOrderDetailDaoImpl<T extends AOrderDetail> extends GenericDaoImpl<T> implements AOrderDetailDao<T>
{
    @Override
    public void deleteAllBy(OrderItem orderItem)
    {
        Query query = getSession().getNamedQuery("deleteByOrderItem");
        query.setEntity("orderItem", orderItem);
        query.executeUpdate();
    }
}
