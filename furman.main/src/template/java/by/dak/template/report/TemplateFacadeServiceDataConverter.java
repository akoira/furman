package by.dak.template.report;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.afacade.report.AFacadeServiceDataConverter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Service;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.report.jasper.ReportUtils;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.template.TemplateFacade;

import java.util.List;

/**
 * User: akoyro
 * Date: 10.08.2010
 * Time: 23:02:07
 */
public class TemplateFacadeServiceDataConverter extends AFacadeServiceDataConverter<TemplateFacade>
{
    public TemplateFacadeServiceDataConverter(AOrder order, MainFacade mainFacade)
    {
        super(order, mainFacade);
    }

    @Override
    protected ServiceType getServiceType()
    {
        return ServiceType.drilling;
    }

    @Override
    public CommonDatas<CommonData> convert(List<TemplateFacade> source)
    {
        //can be null
        Service service = FacadeContext.getServiceFacade().findUniqueByField(Service.PROPERTY_serviceType, getServiceType());
        CommonDatas<CommonData> datas = new CommonDatas<CommonData>(CommonDataType.valueOf(getServiceType()), getOrder());

        BoardDef def = FacadeContext.getBoardDefFacade().findDefaultBoardDef();
        for (TemplateFacade facade : source)
        {
            if (facade.getDrilling() != null)
            {
                SearchFilter searchFilter = SearchFilter.instanceSingle();
                searchFilter.eq(PriceEntity.PROPERTY_priceAware, def);
                searchFilter.eq(PriceEntity.PROPERTY_priced, service);
                CommonData commonData = CommonData.valueOf(getServiceType(), def);
                int i = datas.indexOf(commonData);
                commonData = i > -1 ? datas.remove(i) : commonData;
                commonData.increase(facade.getAmount().doubleValue() * facade.getDrilling());

                List<PriceEntity> prices = FacadeContext.getPriceFacade().loadAll(searchFilter);
                if (prices.size() > 0)
                    ReportUtils.fillPrice(commonData, prices.get(0), order, mainFacade);
                datas.add(commonData);
            }
        }
        return datas;
    }
}
