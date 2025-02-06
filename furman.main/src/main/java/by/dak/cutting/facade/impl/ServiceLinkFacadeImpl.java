package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.ServiceLinkFacade;
import by.dak.persistence.dao.ServiceLinkDao;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.OrderItemType;
import java.util.List;

public class ServiceLinkFacadeImpl extends AOrderDetailFacadeImpl<ServiceLink> implements ServiceLinkFacade
{
    @Override
    public List<ServiceLink> loadAllBy(OrderItem orderItem)
    {
        return ((ServiceLinkDao) dao).findAllBy(orderItem);
    }

    @Override
    public List<ServiceLink> loadAllBy(AOrder order)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq("orderItem.order", order);
        //        searchFilter.eq(AOrderDetail.PROPERTY_discriminator, ServiceLink.class.getSimpleName());
        return loadAll(searchFilter);
    }

    public List<ServiceLink> loadAllBy(AOrder order, List<OrderItemType> types)
    {
        return loadAllBy(order, types, ServiceLink.class.getSimpleName());
    }

    @Override
    public List<ServiceLink> loadAllBy(AOrder order, List<OrderItemType> types, String discriminator)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq("orderItem.order", order);
        searchFilter.in("orderItem.type", types);
        searchFilter.eq(AOrderDetail.PROPERTY_discriminator, discriminator);
        return loadAll(searchFilter);
    }
}
