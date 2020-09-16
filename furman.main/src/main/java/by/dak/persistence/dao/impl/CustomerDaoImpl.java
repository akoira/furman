package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.CustomerDao;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.PersistenceEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author admin
 * @version 0.1 18.10.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class CustomerDaoImpl extends GenericDaoImpl<Customer> implements CustomerDao
{

    @Override
    public List<Customer> findAllSortedBy(String fieldName, boolean asc)
    {
        Criteria execCriteria = createCriteria(getPersistentClass());
        execCriteria.add(Restrictions.eq(PersistenceEntity.PROPERTY_deleted, false));
        return execCriteria.addOrder(asc ? Order.asc(fieldName) : Order.desc(fieldName)).list();
    }


    @Override
    protected Criteria createCriteria(Class clazz) {
        Criteria criteria = getSession().createCriteria(clazz);
        return criteria;
    }
}
