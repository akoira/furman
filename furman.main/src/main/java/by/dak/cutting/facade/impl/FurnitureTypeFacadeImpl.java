package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.FurnitureTypeFacade;
import by.dak.persistence.dao.FurnitureTypeDao;
import by.dak.persistence.entities.types.FurnitureType;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 02.01.2010
 * Time: 15:36:04
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureTypeFacadeImpl extends BaseFacadeImpl<FurnitureType> implements FurnitureTypeFacade
{
    @Override
    public List<FurnitureType> findChildTypesBy(String keyword)
    {
        return ((FurnitureTypeDao) getDao()).findChildTypesBy(keyword);
    }

    @Override
    public List<FurnitureType> loadAll()
    {
        return loadAll(SearchFilter.instanceUnbound());
    }

    @Override
    public List<FurnitureType> loadAll(SearchFilter filter)
    {
        filter.addAscOrder(FurnitureType.PROPERTY_name);
        return super.loadAll(filter);
    }
}
