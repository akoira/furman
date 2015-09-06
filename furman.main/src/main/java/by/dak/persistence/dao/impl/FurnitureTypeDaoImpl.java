package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.FurnitureTypeDao;
import by.dak.persistence.entities.types.FurnitureType;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 02.01.2010
 * Time: 15:28:23
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureTypeDaoImpl extends GenericDaoImpl<FurnitureType> implements FurnitureTypeDao
{
    @Override
    public List<FurnitureType> findChildTypesBy(String keyword)
    {
        Query query = getSession().getNamedQuery("childTypesByKeyword");
        query.setString("keyword", keyword);
        return query.list();
    }
}
