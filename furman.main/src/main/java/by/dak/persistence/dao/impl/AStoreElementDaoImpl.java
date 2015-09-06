package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.AStoreElementDao;
import by.dak.persistence.entities.AStoreElement;
import by.dak.persistence.entities.Delivery;
import by.dak.persistence.entities.Provider;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import org.hibernate.Criteria;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * User: akoyro
 * Date: 22.11.2009
 * Time: 21:18:49
 */
public abstract class AStoreElementDaoImpl<E extends AStoreElement> extends GenericDaoImpl<E> implements AStoreElementDao<E>
{
    @Override
    public void updateDeliveryProviderTo(Delivery delivery, Provider provider)
    {
        //todo
//
//        Query query=getSession().createQuery("update Furniture set provider=:provider where delivery=:delivery");
//        query.setParameter("provider",provider);
//        query.setParameter("delivery",delivery);
//
//        query.executeUpdate();
    }

    @Override
    public void updateDeliveryStatusTo(Delivery delivery, StoreElementStatus status)
    {

        //todo должно бфть переделано в HQL
        Criteria execCriteria = createCriteria(getPersistentClass());
        execCriteria.add(eq("delivery", delivery));
        execCriteria.add(eq("status", StoreElementStatus.order));
        List<E> elements = execCriteria.list();
        for (E element : elements)
        {
            element.setStatus(status);
            update(element);
        }
    }
}
