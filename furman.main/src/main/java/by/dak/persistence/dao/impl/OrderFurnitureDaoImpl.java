package by.dak.persistence.dao.impl;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.dao.OrderFurnitureDao;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.TextureEntity;
import org.hibernate.Query;

import java.util.List;

/**
 * @author admin
 * @version 0.1 18.10.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class OrderFurnitureDaoImpl extends AOrderDetailDaoImpl<OrderFurniture> implements OrderFurnitureDao
{
    public void deleteSecond(OrderFurniture first)
    {
        Query q = getSession().getNamedQuery("deleteSecond");
        q.setEntity("fist", first);
        q.executeUpdate();
    }

    public List<OrderFurniture> findBy(AOrder order, TextureEntity texture, BoardDef boardDef)
    {
        Query q = getSession().getNamedQuery("orderFurnituresByPair");
        q.setLong("order", order.getId());
        q.setLong("texture", texture.getId());
        q.setLong("boardDef", boardDef.getId());
        return q.list();
    }

    public List<Object[]> findUniquePairDefText(AOrder order)
    {
        Query q = getSession().getNamedQuery("uniquePairsByOrder");
        q.setEntity("order", order);
        return q.list();
    }

    @Override
    public List<OrderFurniture> findWithCutoffBy(AOrder order)
    {
        Query q = getSession().getNamedQuery("furnituresWithCutoff");
        q.setEntity("order", order);
        return q.list();
    }

    @Override
    public List<OrderFurniture> findWithMillingBy(AOrder order)
    {
        Query q = getSession().getNamedQuery("furnituresWithMilling");
        q.setEntity("order", order);
        return q.list();
    }

    @Override
    public List<OrderFurniture> findOrderedWithGlueing(AOrder order, Boolean primary)
    {
        Query q = getSession().getNamedQuery("findOrderedWithGlueing");
        q.setEntity("order", order);
        q.setBoolean("primary", primary);
        return q.list();
    }

    @Override
    public List<OrderFurniture> findOrderedComplex(AOrder order, Boolean primary)
    {
        Query q = getSession().getNamedQuery("findOrderedComplex");
        q.setEntity("order", order);
        q.setBoolean("primary", primary);
        return q.list();
    }

    @Override
    public List<OrderFurniture> findOrderedBySize(AOrder order, TextureBoardDefPair pair, int maxCount)
    {
        Query q = getSession().getNamedQuery("findOrderedBySize");
        if (maxCount > -1)
        {
            q.setMaxResults(maxCount);
        }
        q.setEntity("order", order);
        q.setEntity("boardDef", pair.getBoardDef());
        q.setEntity("texture", pair.getTexture());
        return q.list();
    }

    @Override
    public Long getCountBy(AOrder order, TextureBoardDefPair pair)
    {
        Query q = getSession().getNamedQuery("sumOrderFurnitures");
        q.setEntity("order", order);
        q.setEntity("boardDef", pair.getBoardDef());
        q.setEntity("texture", pair.getTexture());
        Long count = (Long) q.list().get(0);
        return count;
    }
}
