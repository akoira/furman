package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.FurnitureCodeFacade;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.types.FurnitureCode;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 02.01.2010
 * Time: 15:34:06
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureCodeFacadeImpl extends BaseFacadeImpl<FurnitureCode> implements FurnitureCodeFacade
{
    public static final SearchFilter getFilterPriceAware(PriceAware priceAware)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq("prices.priceAware", priceAware);
        filter.addAscOrder("name");
        return filter;
    }

    @Override
    public List<FurnitureCode> findBy(PriceAware priceAware)
    {
        return loadAll(getFilterPriceAware(priceAware));
    }

    @Override
    public List<FurnitureCode> loadAll()
    {
        return loadAll(SearchFilter.instanceUnbound());
    }

    @Override
    public List<FurnitureCode> loadAll(SearchFilter filter)
    {
        filter.addAscOrder(FurnitureCode.PROPERTY_name);
        return super.loadAll(filter);
    }
}
