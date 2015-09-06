package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.FurnitureDao;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 27.01.2010
 * Time: 16:55:23
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureDaoImpl extends AStoreElementDaoImpl<Furniture> implements FurnitureDao
{
    @Override
    public List<Furniture> loadAllByOrder(Order order)
    {
        return getSession().createCriteria(Furniture.class).add(Restrictions.eq("order", order)).list();
    }
}
