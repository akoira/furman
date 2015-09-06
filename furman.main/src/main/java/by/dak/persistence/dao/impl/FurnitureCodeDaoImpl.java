package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.FurnitureCodeDao;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.types.FurnitureCode;
import org.hibernate.SQLQuery;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 02.01.2010
 * Time: 15:26:31
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureCodeDaoImpl extends GenericDaoImpl<FurnitureCode> implements FurnitureCodeDao
{
    @Override
    public List<FurnitureCode> findBy(PriceAware priceAware)
    {
        SQLQuery q = getSession().createSQLQuery("select distinct f.*  from FURNITURE_CODE f inner join PRICE p on p.PRICED_ID = f.ID where p.PRICEAWARE_ID = :ID order by f.name");
        q.setLong("ID", priceAware.getId());
        q.addEntity("furnitureCode", FurnitureCode.class);
        return q.list();
    }
}
