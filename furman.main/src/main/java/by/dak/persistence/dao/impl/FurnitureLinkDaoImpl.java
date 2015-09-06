package by.dak.persistence.dao.impl;

import by.dak.cutting.linear.FurnitureTypeCodePair;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.dao.FurnitureLinkDao;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.predefined.Unit;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.03.2010
 * Time: 11:25:03
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureLinkDaoImpl extends AOrderDetailDaoImpl<FurnitureLink> implements FurnitureLinkDao
{
    @Override
    public List<FurnitureLink> findAllBy(OrderItem orderItem)
    {
        return getSession().createCriteria(FurnitureLink.class).add(Restrictions.eq("orderItem", orderItem)).list();
    }

    @Override
    public List<Object[]> loadUniquePairsBy(OrderGroup orderGroup, Unit unit)
    {
        Query query = getSession().getNamedQuery("loadUniquePairsByOrderGroup");
        query.setEntity("orderGroup", orderGroup);
        query.setString("unit", unit.name());
        return query.list();
    }

    @Override
    public Long getCountBy(OrderGroup orderGroup, FurnitureTypeCodePair pair)
    {
        Query query = getSession().getNamedQuery("sumDetailsByOrderGroup");
        query.setEntity("orderGroup", orderGroup);
        query.setEntity("furnitureType", pair.getFurnitureType());
        query.setEntity("furnitureCode", pair.getFurnitureCode());

        return (Long) query.list().get(0);
    }
}
