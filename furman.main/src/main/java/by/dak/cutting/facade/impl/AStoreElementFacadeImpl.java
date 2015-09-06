package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.AStoreElementFacade;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.StoreElementStatus;

import java.util.Arrays;
import java.util.List;

/**
 * User: akoyro
 * Date: 22.11.2009
 * Time: 0:53:11
 */
public abstract class AStoreElementFacadeImpl<E extends AStoreElement> extends BaseFacadeImpl<E> implements AStoreElementFacade<E>
{
    @Override
    public E cancelOrdered(E ordered)
    {
        if (ordered.getAmount() < 1)
            return null;
        List<E> list = findByOrdered(ordered);
        boolean next = false;
        if (list.size() > 0)
        {
            E e = list.get(0);
            if (e.getAmount() <= ordered.getAmount())
            {
                //заказанных больше или равно чем заказываемых
                next = e.getAmount() < ordered.getAmount();
                ordered.setAmount(ordered.getAmount() - e.getAmount());
            }
            else
            {
                //заказанных меньше чем заказываемых
                E cloned = (E) e.clone();
                e.setAmount(e.getAmount() - ordered.getAmount());
                save(e);

                cloned.setAmount(ordered.getAmount());
                e = cloned;

                ordered.setAmount(0);
            }
            e.setStatus(StoreElementStatus.used);
            e.setProvider(ordered.getProvider());
            e.setDelivery(ordered.getDelivery());
            save(e);
        }
        if (next)
        {
            cancelOrdered(ordered);
        }
        return null;
    }

    protected abstract List<E> findByOrdered(E ordered);


    public static SearchFilter getSearchFilterBy(String orderPath, StatisticFilter statisticFilter)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        if (statisticFilter.getStart() != null)
            searchFilter.ge(orderPath + ".readyDate", statisticFilter.getStart());
        if (statisticFilter.getEnd() != null)
            searchFilter.le(orderPath + ".readyDate", statisticFilter.getEnd());
        if (!Order.isNull(statisticFilter.getOrder()))
            searchFilter.eq(orderPath, statisticFilter.getOrder());
        else
        {
            if (!Customer.isNull(statisticFilter.getCustomer()))
                searchFilter.eq(orderPath + ".customer", statisticFilter.getCustomer());
        }
        searchFilter.in(orderPath + ".status", Arrays.asList(OrderStatus.notEditables()));
//        searchFilter.eq(Furniture.PROPERTY_status, StoreElementStatus.used);
        return searchFilter;
    }

    /**
     * Сортирует по убыванию стоимости
     */
    public E getMaxPricedBy(PriceAware furnitureType, Priced furnitureCode, StoreElementStatus status)
    {
        SearchFilter searchFilter = filterMaxPrice(furnitureType, furnitureCode, status);
        return getFirstBy(searchFilter);
    }

    protected SearchFilter filterMaxPrice(PriceAware furnitureType, Priced furnitureCode, StoreElementStatus status)
    {
        SearchFilter searchFilter = SearchFilter.instanceSingle();
        searchFilter.eq(Furniture.PROPERTY_priceAware, furnitureType);
        searchFilter.eq(Furniture.PROPERTY_priced, furnitureCode);
        searchFilter.eq(Furniture.PROPERTY_status, status);
        searchFilter.addDescOrder(Furniture.PROPERTY_price);
        return searchFilter;
    }

}
