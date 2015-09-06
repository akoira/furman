package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.statistics.ServiceStatistics;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Service;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.facade.CommonDataFacade;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdaterProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 24.11.2010
 * Time: 13:35:50
 */
public class ServiceTypeNode extends AStatisticsNode implements ListUpdaterProvider<ServiceStatistics>
{
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
                    for (CommonDataFacade.Statistic statistic : list)
                    {
                        ServiceStatistics serviceStatistics = new ServiceStatistics();
                        ServiceType serviceType = (ServiceType) getUserObject();
                        serviceStatistics.setCode(FacadeContext.getServiceFacade().findUniqueByField(Service.PROPERTY_serviceType, serviceType));
                        serviceStatistics.setType((PriceAware) FacadeContext.getFacadeBy(serviceType.getMaterialType().priceAwareClass()).findUniqueByField("name", statistic.getName()));
                        serviceStatistics.setSize(statistic.getAmount());
                        serviceStatistics.setPrice(FacadeContext.getPriceFacade().findUniqueBy(serviceStatistics.getType(), serviceStatistics.getCode()));
                        cacheStatistics.add(serviceStatistics);
                    }
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


}
