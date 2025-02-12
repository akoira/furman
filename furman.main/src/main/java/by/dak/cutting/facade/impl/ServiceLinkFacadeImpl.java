package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.ServiceLinkFacade;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.NamedQueryDefinition;
import by.dak.persistence.NamedQueryParameter;
import by.dak.persistence.dao.ServiceLinkDao;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.facade.CommonDataFacade;
import org.hibernate.transform.AliasToBeanResultTransformer;

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

    @Override
    public List<ServiceLink> loadAllBy(StatisticFilter statisticFilter, String serviceType) {
        NamedQueryDefinition definition = statisticFilter.getNamedQueryDefinition();
        definition.setNameQuery("statServices");
        definition.getParameterList().add(NamedQueryParameter.getObjectParameter("serviceType", serviceType));
        definition.setResultTransformer(new AliasToBeanResultTransformer(ServiceLink.class));
        return getDao().findAllBy(definition);
    }
}
