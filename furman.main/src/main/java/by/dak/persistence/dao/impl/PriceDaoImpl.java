package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.PriceDao;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.Service;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * User: akoyro
 * Date: 28.04.2009
 * Time: 23:35:50
 */
public class PriceDaoImpl extends GenericDaoImpl<PriceEntity> implements PriceDao
{


    @Override
    public List<PriceEntity> findAllBy(Service service)
    {
        SQLQuery q = getSession().createSQLQuery("select distinct p.*  from PRICE p inner join SERVICE s on p.PRICED_ID = s.ID " +
                "where s.ID = :serviceID");
        q.setLong("serviceID", service.getId());
        q.addEntity("price", PriceEntity.class);
        return q.list();
    }


    @Override
    public PriceEntity findUniqueBy(PriceAware priceAware, Priced priced)
    {
        Criteria criteria = getSession().createCriteria(PriceEntity.class);
        criteria.add(Restrictions.eq(PriceEntity.PROPERTY_priceAware, priceAware));
        criteria.add(Restrictions.eq(PriceEntity.PROPERTY_priced, priced));
        criteria.setCacheable(true);
        return (PriceEntity) criteria.uniqueResult();
    }

}
