package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.statistics.ServiceStatistics;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Service;
import by.dak.persistence.entities.ServiceLink;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.converter.ServiceConverter;
import by.dak.report.jasper.common.facade.CommonDataFacade;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdaterProvider;

import java.util.*;
import java.util.stream.Collectors;

/**
 * User: akoyro
 * Date: 24.11.2010
 * Time: 13:35:50
 */
public class ServiceTypeNode extends AStatisticsNode implements ListUpdaterProvider<ServiceStatistics>
{
    public static final String BRACKETED_DATA = "\\s*\\([^)]*\\)";
    private List<ServiceStatistics> cacheStatistics = new ArrayList<ServiceStatistics>();
    

    protected ServiceTypeNode(StatisticFilter filter, ServiceType serviceType)
    {
        super(filter);
        setUserObject(serviceType);
    }

    @Override
    protected void initChildren()
    {
//        SearchFilter searchFilter = SearchFilter.instanceUnbound();
//        if (!Order.isNull(getFilter().getOrder()))
//        {
//            searchFilter.eq(CommonData.PROPERTY_order, getFilter().getOrder());
//        }
//        else
//        {
//            if (!Customer.isNull(getFilter().getCustomer()))
//            {
//                searchFilter.eq(CommonData.PROPERTY_order + "." + Order.PROPERTY_customer, getFilter().getCustomer());
//            }
//        }
//
//        searchFilter.ge(CommonData.PROPERTY_order + "." + Order.PROPERTY_readyDate, getFilter().getStart());
//        searchFilter.le(CommonData.PROPERTY_order + "." + Order.PROPERTY_readyDate, getFilter().getEnd());
//        searchFilter.eq(CommonData.PROPERTY_commonDataType, CommonDataType.valueOf((ServiceType) getUserObject()));
//        List<CommonData> commonDatas = FacadeContext.getCommonDataFacade().loadAll(searchFilter);
//        for (CommonData commonData : commonDatas)
//        {
//
//        }
    }


    @Override
    public by.dak.swing.table.ListUpdater<ServiceStatistics> getListUpdater()
    {
        AListUpdater<ServiceStatistics> listUpdater = new ListUpdater<ServiceStatistics>()
        {

            @Override
            public void update()
            {
                if (cacheStatistics.size() == 0)
                {
                    List<CommonDataFacade.Statistic> list = FacadeContext.getCommonDataFacade().getCommanDataMap(getFilter(), CommonDataType.valueOf((ServiceType) getUserObject()));
                    List<CommonDataFacade.Statistic> changedlist = list.stream()
                            .map(s -> {
                                s.setName(s.getName().replaceAll(BRACKETED_DATA, "").trim());
                                return s;
                            }).collect(Collectors.toList());

                    List<ServiceLink> additionalServices = FacadeContext.getServiceLinkFacade().loadAllBy(getFilter(), ((ServiceType) getUserObject()).name());

                    if (!additionalServices.isEmpty()) {
                        changedlist.addAll(ServiceConverter.convertToStatistic(additionalServices));
                        changedlist = groupStatisticData(changedlist);
                    }

                    changedlist.forEach(statistic -> {
                        ServiceStatistics serviceStatistics = new ServiceStatistics();
                        ServiceType serviceType = (ServiceType) getUserObject();
                        serviceStatistics.setCode(FacadeContext.getServiceFacade().findUniqueByField(Service.PROPERTY_serviceType, serviceType));
                        serviceStatistics.setType((PriceAware) FacadeContext.getFacadeBy(serviceType.getMaterialType().priceAwareClass()).findUniqueByField("name", statistic.getName()));
                        serviceStatistics.setSize(statistic.getAmount());
                        serviceStatistics.setPrice(FacadeContext.getPriceFacade().findUniqueBy(serviceStatistics.getType(), serviceStatistics.getCode()));
                        cacheStatistics.add(serviceStatistics);
                    });
                }
                getList().clear();
                getList().addAll(cacheStatistics);
            }

            @Override
            public String[] getVisibleProperties()
            {
                return new String[]{"type", "size", "price.currencyType", "price.price", "price.priceDealer", "total", "totalDealer"};
            }
        };

        listUpdater.setSearchFilter(SearchFilter.instanceUnbound());
        return listUpdater;
    }

    private static List<CommonDataFacade.Statistic> groupStatisticData(List<CommonDataFacade.Statistic> changedlist) {
        return changedlist.stream()
                .collect(Collectors.groupingBy(
                        stat -> new AbstractMap.SimpleEntry<>(stat.getService(), stat.getName()),
                        LinkedHashMap::new,
                        Collectors.summingDouble(CommonDataFacade.Statistic::getAmount)
                )).entrySet().stream()
                .map(entry -> {
                    CommonDataFacade.Statistic stat = new CommonDataFacade.Statistic();
                    stat.setService(entry.getKey().getKey());
                    stat.setName(entry.getKey().getValue());
                    stat.setAmount(entry.getValue());
                    return stat;
                }).collect(Collectors.toList());
    }
}
