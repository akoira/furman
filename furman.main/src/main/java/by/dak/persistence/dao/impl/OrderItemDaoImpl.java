package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.OrderItemDao;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.OrderItem;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author admin
 * @version 0.1 18.10.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class OrderItemDaoImpl extends GenericDaoImpl<OrderItem> implements OrderItemDao
{
    public OrderItem createOrderItem(String name)
    {
        OrderItem orderItem = new OrderItem();
        orderItem.setName(name);
        getSession().persist(orderItem);
        return orderItem;
    }

    public void deleteOrderItem(OrderItem orderItem)
    {
        getSession().delete(orderItem);
    }

    @Override
    public List<OrderItem> findBy(AOrder order)
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(OrderItem.class);
        criteria.add(Restrictions.eq("order", order));
        return (List<OrderItem>) criteria.getExecutableCriteria(getSession()).list();
    }
}
