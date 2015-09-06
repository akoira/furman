package by.dak.cutting.facade.impl;

import by.dak.additional.Additional;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.OrderItemFacade;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.dao.OrderItemDao;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.predefined.OrderItemType;

import java.util.List;

/**
 * @author akoyro
 * @version 0.1 28.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class OrderItemFacadeImpl extends BaseFacadeImpl<OrderItem> implements OrderItemFacade
{
    @Override
    public List<OrderItem> findBy(AOrder order, OrderItemType type)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(OrderItem.PROPERTY_order, order);
        searchFilter.eq(OrderItem.PROPERTY_type, type);
        return loadAll(searchFilter);
    }

    @Override
    public List<OrderItem> loadBy(AOrder order)
    {
        return ((OrderItemDao) dao).findBy(order);
    }

    @Override
    public void delete(OrderItem entity)
    {
        List<OrderFurniture> orderFurnitures = FacadeContext.getOrderFurnitureFacade().loadOrderedByNumber(entity);
        for (OrderFurniture furniture : orderFurnitures)
        {
            if (furniture.isPrimary())
            {
                FacadeContext.getOrderFurnitureFacade().delete(furniture);
            }
        }
        List<FurnitureLink> furnitureLinks = FacadeContext.getFurnitureLinkFacade().loadAllBy(entity);

        for (FurnitureLink furnitureLink : furnitureLinks)
        {
            FacadeContext.getFurnitureLinkFacade().delete(furnitureLink);
        }

        List<Additional> additionals = FacadeContext.getAdditionalFacade().loadAllBy(entity);

        for (Additional additional : additionals)
        {
            FacadeContext.getAdditionalFacade().delete(additional);
        }
        super.delete(entity.getId());
    }
}
