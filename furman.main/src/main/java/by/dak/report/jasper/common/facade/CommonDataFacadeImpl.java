package by.dak.report.jasper.common.facade;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.NamedQueryDefinition;
import by.dak.persistence.NamedQueryParameter;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Order;
import by.dak.report.jasper.common.dao.CommonDataDao;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.List;

/**
 * User: akoyro
 * Date: 24.11.2010
 * Time: 14:46:23
 */
public class CommonDataFacadeImpl extends BaseFacadeImpl<CommonData> implements CommonDataFacade
{
    @Override
    public void saveAll(CommonDatas<CommonData> commonDatas)
    {
        for (CommonData commonData : commonDatas)
        {
            save(commonData);
        }
    }

    @Override
    public CommonDatas<CommonData> loadAll(Order order, CommonDataType commonDataType)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(CommonData.PROPERTY_commonDataType, commonDataType);
        searchFilter.eq(CommonData.PROPERTY_order, order);
        CommonDatas<CommonData> commonDatas = new CommonDatas<CommonData>(commonDataType, order);
        commonDatas.addAll(loadAll(searchFilter));
        return commonDatas;
    }

    @Override
    public void deleteAll(AOrder order)
    {
        ((CommonDataDao) getDao()).deleteAllBy(order);
    }

    public List<Statistic> getCommanDataMap(StatisticFilter statisticFilter, CommonDataType commonDataType)
    {
        NamedQueryDefinition definition = statisticFilter.getNamedQueryDefinition();
        definition.setNameQuery("statisticsServices");
        definition.getParameterList().add(NamedQueryParameter.getObjectParameter("commonDataType", commonDataType));
        definition.setResultTransformer(new AliasToBeanResultTransformer(Statistic.class));
        return getDao().findAllBy(definition);
    }

}
