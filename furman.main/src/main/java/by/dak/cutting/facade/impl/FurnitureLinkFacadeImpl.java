package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.FurnitureLinkFacade;
import by.dak.cutting.linear.FurnitureTypeCodePair;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.dao.FurnitureLinkDao;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.03.2010
 * Time: 11:27:10
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureLinkFacadeImpl extends AOrderDetailFacadeImpl<FurnitureLink> implements FurnitureLinkFacade
{
    @Override
    public List<FurnitureLink> loadAllBy(OrderItem orderItem)
    {
        return ((FurnitureLinkDao) dao).findAllBy(orderItem);
    }

    @Override
    public List<FurnitureLink> loadAllBy(AOrder order)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq("orderItem.order", order);
        //        searchFilter.eq(AOrderDetail.PROPERTY_discriminator, FurnitureLink.class.getSimpleName());
        return loadAll(searchFilter);
    }

    public List<FurnitureLink> loadAllBy(AOrder order, List<OrderItemType> types)
    {
        return loadAllBy(order, types, FurnitureLink.class.getSimpleName());
    }

    @Override
    public List<FurnitureLink> loadAllBy(AOrder order, List<OrderItemType> types, String discriminator)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq("orderItem.order", order);
        searchFilter.in("orderItem.type", types);
        searchFilter.eq(AOrderDetail.PROPERTY_discriminator, discriminator);
        return loadAll(searchFilter);
    }


    @Override
    public List<FurnitureLink> findAllBy(Furniture child)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(FurnitureLink.PROPERTY_children + "." + FurnitureLink2FurnitureLink.PROPERTY_child, child);
        return loadAll(searchFilter);
    }

    @Override
    public List<FurnitureLink> loadAllBy(OrderGroup orderGroup, FurnitureTypeCodePair pair, Unit unit)
    {
        List<FurnitureLink> furnitureLinks = new ArrayList<FurnitureLink>();
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(FurnitureLink.PROPERTY_orderItem + "." + OrderItem.PROPERTY_order + "." + Order.PROPERTY_orderGroup, orderGroup);
        searchFilter.eq(FurnitureLink.PROPERTY_priceAware + "." + FurnitureType.PROPERTY_unit, unit);
        searchFilter.eq(FurnitureLink.PROPERTY_priced, pair.getFurnitureCode());
        searchFilter.eq(FurnitureLink.PROPERTY_priceAware, pair.getFurnitureType());
        furnitureLinks.addAll(loadAll(searchFilter));

        return furnitureLinks;
    }

    @Override
    public int getCountBy(OrderGroup orderGroup, FurnitureTypeCodePair pair)
    {
        int result = ((FurnitureLinkDao) dao).getCountBy(orderGroup, pair).intValue();

        return result;
    }

    @Override
    public List<FurnitureTypeCodePair> loadUniquePairsBy(OrderGroup orderGroup, Unit unit)
    {
        List<Object[]> pairs = ((FurnitureLinkDao) getDao()).loadUniquePairsBy(orderGroup, unit);
        List<FurnitureTypeCodePair> result = new ArrayList<FurnitureTypeCodePair>();
        for (Object[] objects : pairs)
        {
            result.add(new FurnitureTypeCodePair((FurnitureType) objects[0], (FurnitureCode) objects[1]));

        }
        return result;
    }
}
