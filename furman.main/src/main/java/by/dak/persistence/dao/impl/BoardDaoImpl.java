package by.dak.persistence.dao.impl;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.dao.BoardDao;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

public class BoardDaoImpl extends AStoreElementDaoImpl<Board> implements BoardDao
{

    @Override
    public List<Board> findMinFreeRestBoardsBy(Dimension detailSize, TextureBoardDefPair pair, int maxCount)
    {
        Criteria execCriteria = createCriteria(getPersistentClass());
        execCriteria.add(isNull("order"));
        execCriteria.add(eq(Board.PROPERTY_priceAware, pair.getBoardDef()));
        execCriteria.add(eq(Board.PROPERTY_priced, pair.getTexture()));
        execCriteria.add(ge("length", new Long(detailSize.getWidth())));
        execCriteria.add(ge("width", new Long(detailSize.getHeight())));
        //только остатки, целые листы берем потом
        execCriteria.add(or(
                lt("length", new Long(pair.getBoardDef().getDefaultLength())),
                lt("width", new Long(pair.getBoardDef().getDefaultWidth())))
        );
        execCriteria.add(ge("amount", 1));
        execCriteria.add(eq("status", StoreElementStatus.exist));
        execCriteria.addOrder(org.hibernate.criterion.Order.asc("length"));
        execCriteria.addOrder(org.hibernate.criterion.Order.asc("width"));
        execCriteria.addOrder(org.hibernate.criterion.Order.desc("amount"));
        execCriteria.addOrder(org.hibernate.criterion.Order.desc("price"));
        execCriteria.setMaxResults(maxCount);
        return execCriteria.list();
    }


    @Override
    public void deleteOrderBoards(Order order)
    {
        Query query = getSession().getNamedQuery("deleteOrderBoardsByOrder");
        query.setEntity("order", order);
        query.setString("status", StoreElementStatus.order.name());
        query.executeUpdate();
    }

    @Override
    protected Criteria createCriteria(Board template)
    {
        Criteria criteria = super.createCriteria(template);
        if (template.getBoardDef() != null)
        {
            criteria.createCriteria(Board.PROPERTY_priceAware).add(Example.create(template.getBoardDef()));
        }

        if (template.getTexture() != null)
        {
            criteria.createCriteria(Board.PROPERTY_priced).add(Example.create(template.getTexture()));
        }
        return criteria;
    }
}
