package by.dak.cutting.afacade.report;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.afacade.AFacade;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.report.jasper.common.data.FurnitureCommonData;
import by.dak.report.jasper.common.data.converter.FurnitureConverter;
import by.dak.utils.GenericUtils;
import by.dak.utils.convert.Converter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

import java.text.DecimalFormat;
import java.util.List;

/**
 * User: akoyro
 * Date: 10.08.2010
 * Time: 23:02:07
 */
public abstract class AFacadeFurnitureDataConverter<F extends AFacade> implements Converter<List<F>, CommonDatas<CommonData>>
{
    private final MainFacade mainFacade;
    private final Dailysheet dailysheet;
    private Class<F> elementClass = GenericUtils.getParameterClass(getClass(), 0);

    private CommonDataType commonDataType;
    private AOrder order;

    public AFacadeFurnitureDataConverter(CommonDataType commonDataType, AOrder order, MainFacade mainFacade)
    {
        this.commonDataType = commonDataType;
        this.order = order;
        this.mainFacade = mainFacade;
        this.dailysheet = MainFacade.dailysheet.apply(mainFacade).apply(order);
    }

    @Override
    public CommonDatas<CommonData> convert(List<F> source)
    {
        CommonDatas<CommonData> datas = new CommonDatas<CommonData>(this.commonDataType, order);
        for (final F facade : source)
        {
            SearchFilter searchFilter = SearchFilter.instanceSingle();
            searchFilter.eq(PriceEntity.PROPERTY_priceAware, facade.getProfileType());
            searchFilter.eq(PriceEntity.PROPERTY_priced, facade.getProfileColor());

            FurnitureCommonData commonData = new FurnitureCommonData();
            commonData.setSize(new DecimalFormat("0").format(facade.getLength()) + "x" + new DecimalFormat("0").format(facade.getWidth()));
            commonData.setName(StringValueAnnotationProcessor.getProcessor().convert(facade.getProfileColor()));
            commonData.setService(StringValueAnnotationProcessor.getProcessor().convert(facade.getProfileType()));
            commonData.setSizeAsDouble(1);
            commonData.setCount(facade.getAmount().doubleValue());
            fillPriceBy(facade, commonData);
            commonData.setUnit(StringValueAnnotationProcessor.getProcessor().convert(Unit.piece));
            datas.add(commonData);
        }
        return datas;
    }

    private void fillPriceBy(F facade, CommonData commonData)
    {
        List<FurnitureLink> links = FacadeContext.getFurnitureLinkFacade().loadAllBy(facade);
        FurnitureConverter converter = new FurnitureConverter(false, commonDataType, order, mainFacade);
        commonData.setPrice(converter.convert(links).getCommonCost());
        commonData.setDialerPrice(converter.convert(links).getDialerCommonCost());
    }


    public Class<F> getElementClass()
    {
        return elementClass;
    }

    public void setElementClass(Class<F> elementClass)
    {
        this.elementClass = elementClass;
    }
}
