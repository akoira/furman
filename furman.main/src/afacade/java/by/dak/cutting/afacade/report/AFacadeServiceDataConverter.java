package by.dak.cutting.afacade.report;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.afacade.AFacade;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Service;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.report.jasper.ReportUtils;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.utils.GenericUtils;
import by.dak.utils.convert.Converter;

import java.util.List;

/**
 * User: akoyro
 * Date: 10.08.2010
 * Time: 23:02:07
 */
public abstract class AFacadeServiceDataConverter<F extends AFacade> implements Converter<List<F>, CommonDatas<CommonData>>
{

    private Class<F> elementClass = GenericUtils.getParameterClass(getClass(), 0);
    public final AOrder order;
    public final MainFacade mainFacade;
    public final Dailysheet dailysheet;


    public AFacadeServiceDataConverter(AOrder order, MainFacade mainFacade)
    {
        this.order = order;
        this.mainFacade = mainFacade;
        this.dailysheet = MainFacade.dailysheet.apply(mainFacade).apply(this.order);
    }


    protected abstract ServiceType getServiceType();

    @Override
    public CommonDatas<CommonData> convert(List<F> source)
    {
        //can be null
        Service service = FacadeContext.getServiceFacade().findUniqueByField(Service.PROPERTY_serviceType, getServiceType());
        CommonDatas<CommonData> datas = new CommonDatas<CommonData>(CommonDataType.valueOf(getServiceType()), order);
        for (F facade : source)
        {
            SearchFilter searchFilter = SearchFilter.instanceSingle();
            searchFilter.eq(PriceEntity.PROPERTY_priceAware, facade.getProfileType());
            searchFilter.eq(PriceEntity.PROPERTY_priced, service);
            CommonData commonData = CommonData.valueOf(getServiceType(), facade.getProfileType());
            int i = datas.indexOf(commonData);
            commonData = i > -1 ? datas.remove(i) : commonData;
            commonData.increase(facade.getAmount().doubleValue());

            List<PriceEntity> prices = FacadeContext.getPriceFacade().loadAll(searchFilter);
            if (prices.size() > 0)
            {
                ReportUtils.fillPrice(commonData, prices.get(0), dailysheet);
            }
            datas.add(commonData);
        }
        return datas;
    }

    public Class<F> getElementClass()
    {
        return elementClass;
    }

    public void setElementClass(Class<F> elementClass)
    {
        this.elementClass = elementClass;
    }

    public AOrder getOrder()
    {
        return order;
    }
}
