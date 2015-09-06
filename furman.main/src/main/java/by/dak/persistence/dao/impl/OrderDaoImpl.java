package by.dak.persistence.dao.impl;

import by.dak.persistence.FinderException;
import by.dak.persistence.dao.OrderDao;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderStatus;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.*;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * @author admin
 * @version 0.1 18.10.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class OrderDaoImpl extends GenericDaoImpl<Order> implements OrderDao
{
    /* this method is only used in test */

    @Override
    public Order findOrderByNumber(Long orderNumber) throws FinderException
    {
        List<Order> orders = findByCriteria(eq(PROP_ORDER_NUMBER, orderNumber));
        assert orders.size() == 1;
        if (orders.isEmpty())
        {
            throw new FinderException("Order with number = " + orderNumber + " was not found.");
        }
        else
        {
            return orders.get(0);
        }
    }

//    @Override
//    public Date getReadyDateByLastOrder()
//    {
//        Query query = getSession().getNamedQuery("readyDateByLastOrder");
//        Order orderEntity = (Order)query.uniqueResult();
//        if( orderEntity != null && orderEntity.getReadyDate()!=null)
//        {
//            return orderEntity.getReadyDate();
//        }
//        return null;
//
//    }

    @Override
    public Long getLastOrderNumber()
    {
        Query query = getSession().getNamedQuery("lastOrderNumber");
        Number maxOrderNumber = (Number) query.uniqueResult();
        if (maxOrderNumber != null)
        {
            return maxOrderNumber.longValue();
        }
        return 0L;
    }

    @Override
    public List<Order> findAllArchive()
    {
        Query query = getSession().getNamedQuery("allArchive");
        return query.list();
    }

    @Override
    public Long getLastNumberBy(Long from, Long till)
    {
        Criteria criteria = createCriteria(getPersistentClass());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
        java.sql.Date fromT = new java.sql.Date(cal.getTime().getTime());

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        java.sql.Date tillT = new java.sql.Date(cal.getTime().getTime());
        criteria.setProjection(Projections.max(Order.PROPERTY_orderNumber));
        Criteria cDS = criteria.createCriteria(Order.PROPERTY_createdDailySheet);
        cDS.add(Restrictions.between("date", fromT, tillT));
        return (Long) findUniqueByCriteria(criteria, Restrictions.between(Order.PROPERTY_orderNumber, from, till));
    }

    @Override
    public List<Date> getDateBy(Customer customer)
    {
        Query query = getSession().getNamedQuery("createdDateGroup");

        query.setParameter("customer", customer);

        List<Date> dates = new ArrayList<Date>();

        List<Object[]> list = query.list();

        for (Object[] row : list)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, (Integer) row[0] - 1);
            calendar.set(Calendar.YEAR, (Integer) row[1]);

            dates.add(calendar.getTime());
        }

        return dates;
    }

    @Override
    public List<Order> findAllByStatus(OrderStatus orderStatus)
    {
        return getSession().getNamedQuery("allByStatus").setParameter("status", orderStatus).list();
    }
}