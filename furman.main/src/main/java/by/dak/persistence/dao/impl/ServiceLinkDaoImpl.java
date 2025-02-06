package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.ServiceLinkDao;
import by.dak.persistence.entities.ServiceLink;
import by.dak.persistence.entities.OrderItem;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ServiceLinkDaoImpl extends AOrderDetailDaoImpl<ServiceLink> implements ServiceLinkDao
{
    @Override
    public List<ServiceLink> findAllBy(OrderItem orderItem)
    {
        return getSession().createCriteria(ServiceLink.class).add(Restrictions.eq("orderItem", orderItem)).list();
    }
}
