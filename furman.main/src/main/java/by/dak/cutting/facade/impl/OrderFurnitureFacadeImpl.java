package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.OrderFurnitureFacade;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.dao.OrderFurnitureDao;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.AOrderDetail;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.OrderItem;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 29.01.2009
 * Time: 15:50:43
 * To change this template use File | Settings | File Templates.
 */
public class OrderFurnitureFacadeImpl extends AOrderBoardDetailFacadeImpl<OrderFurniture> implements OrderFurnitureFacade
{
    public static SearchFilter getSearchFilterBy(AOrder order)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq("orderItem.order", order);
        //searchFilter.eq(AOrderDetail.PROPERTY_discriminator, OrderFurniture.class.getSimpleName());
        return searchFilter;
    }

    public static SearchFilter getSearchFilterBy(OrderItem orderItem)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq("orderItem", orderItem);
        searchFilter.eq(AOrderDetail.PROPERTY_discriminator, OrderFurniture.class.getSimpleName());
        return searchFilter;
    }


    @Override
    public void save(OrderFurniture entity)
    {
        super.save(entity);
        if (!entity.isComplex() && entity.isPrimary()
                && entity.getId() != null && entity.getId() > 0)
        {
            //todo удаляем second при смене типов возможно есть более правельный способ
            ((OrderFurnitureDao) dao).deleteSecond(entity);
        }
    }

    @Override
    public List<OrderFurniture> findOrderedWithGlueing(AOrder order, Boolean primary)
    {
        List<OrderFurniture> result = ((OrderFurnitureDao) dao).findOrderedWithGlueing(order, primary);

        return result;
    }


    @Override
    public List<OrderFurniture> findWithCutoffBy(AOrder order)
    {
        return ((OrderFurnitureDao) dao).findWithCutoffBy(order);
    }


    /**
     * @param order
     * @return !!!only primary details!!!
     */
    @Override
    public List<OrderFurniture> findWithMillingBy(AOrder order)
    {
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.eq(OrderFurniture.PROPERTY_orderItem + "." + OrderItem.PROPERTY_order, order);
        searchFilter.eq(OrderFurniture.PROPERTY_primary, true);
        searchFilter.isNotNull(OrderFurniture.PROPERTY_milling);
        return loadAll(searchFilter);
    }

    @Override
    public List<OrderFurniture> findOrderedComplex(AOrder order, Boolean primary)
    {
        return ((OrderFurnitureDao) dao).findOrderedComplex(order, primary);
    }

    @Override
    public List<OrderFurniture> loadOrderedByNumber(AOrder order)
    {
        SearchFilter searchFilter = getSearchFilterBy(order);
        searchFilter.addAscOrder("number");
        return loadAll(searchFilter);
    }

    @Override
    public List<OrderFurniture> loadOrderedByNumber(OrderItem orderItem)
    {
        SearchFilter searchFilter = getSearchFilterBy(orderItem);
        searchFilter.addAscOrder("number");
        return loadAll(searchFilter);
    }

    @Override
    public List<OrderFurniture> loadOrderedBySize(AOrder order, TextureBoardDefPair pair, int maxCount)
    {
        return ((OrderFurnitureDao) dao).findOrderedBySize(order, pair, maxCount);
    }

    @Override
    public List<OrderFurniture> loadAllBy(AOrder order)
    {
        SearchFilter searchFilter = getSearchFilterBy(order);
        searchFilter.addAscOrder("number");
        return loadAll(searchFilter);
    }

    @Override
    public int getCountBy(AOrder order, TextureBoardDefPair pair)
    {
//        SearchFilter searchFilter = SearchFilter.instanceUnbound();
//        searchFilter.eq("orderItem.order",order);
//        searchFilter.eq(OrderFurniture.PROPERTY_priceAware,pair.getBoardDef());
//        searchFilter.eq(OrderFurniture.PROPERTY_priced,pair.getTexture());
//        return getCountBy(searchFilter,OrderFurniture.PROPERTY_amount);
        return ((OrderFurnitureDao) getDao()).getCountBy(order, pair).intValue();
    }




    @Override
    public int getCountBy(AOrder order, List<Long> boardDefIds)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(order, OrderFurniture.PROPERTY_orderItem, OrderItem.PROPERTY_order);
        filter.in(OrderFurniture.PROPERTY_priceAware + "." + OrderFurniture.PROPERTY_id, boardDefIds);
        return getCount(filter);
    }

}
