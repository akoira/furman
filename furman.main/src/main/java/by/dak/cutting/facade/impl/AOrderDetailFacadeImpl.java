package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.AOrderDetailFacade;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.persistence.dao.AOrderDetailDao;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.AOrderDetail;
import by.dak.persistence.entities.OrderItem;

import java.util.List;

/**
 * User: akoyro
 * Date: 21.07.2010
 * Time: 19:37:29
 */
public abstract class AOrderDetailFacadeImpl<T extends AOrderDetail> extends BaseFacadeImpl<T> implements AOrderDetailFacade<T>
{
    @Override
    public void deleteAllBy(OrderItem orderItem)
    {
        ((AOrderDetailDao) dao).deleteAllBy(orderItem);
    }

    @Override
    public List<T> loadAllBy(OrderItem orderItem)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq("orderItem", orderItem);
        return loadAll(searchFilter);
    }

    @Override
    public List<T> loadAllBy(AOrder order)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq("orderItem.order", order);
        return loadAll(searchFilter);
    }

}
