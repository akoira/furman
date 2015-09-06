package by.dak.report.jasper.common.facade;

import by.dak.cutting.facade.BaseFacade;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Order;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface CommonDataFacade extends BaseFacade<CommonData>
{
    public void saveAll(CommonDatas<CommonData> commonDatas);

    public CommonDatas<CommonData> loadAll(Order order, CommonDataType commonDataType);

    public void deleteAll(AOrder order);

    public List<Statistic> getCommanDataMap(StatisticFilter statisticFilter, CommonDataType commonDataType);

    public static class Statistic
    {
        private String service;
        private String name;
        private Double amount;

        public String getService()
        {
            return service;
        }

        public void setService(String service)
        {
            this.service = service;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public Double getAmount()
        {
            return amount;
        }

        public void setAmount(Double amount)
        {
            this.amount = amount;
        }
    }

}
