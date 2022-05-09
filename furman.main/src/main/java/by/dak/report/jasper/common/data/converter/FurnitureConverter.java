package by.dak.report.jasper.common.data.converter;

import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.PriceEntity;
import by.dak.report.jasper.ReportUtils;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.report.jasper.common.data.FurnitureCommonData;
import by.dak.utils.convert.Converter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 */

public class FurnitureConverter implements Converter<List<FurnitureLink>, List<CommonData>> {
    private boolean usePriceDealer;
    private boolean useOrderItemCount = true;
    private CommonDataType commonDataType;
    private AOrder order;
    private final Dailysheet dailysheet;
    private final MainFacade mainFacade;

    private SortedMap<String, CommonDatas<CommonData>> commonDatas = new TreeMap<String, CommonDatas<CommonData>>(new StringComparator());

    //private CommonDatas<CommonData> commonDatas;

    public FurnitureConverter(boolean useOrderItemCount, CommonDataType commonDataType, AOrder order, MainFacade mainFacade) {
        this.useOrderItemCount = useOrderItemCount;
        this.commonDataType = commonDataType;
        this.order = order;
        this.mainFacade = mainFacade;
        this.dailysheet = MainFacade.dailysheet.apply(mainFacade).apply(order);
    }

    public FurnitureConverter(CommonDataType commonDataType, AOrder order, MainFacade mainFacade) {
        this(true, commonDataType, order, mainFacade);
    }

    @Override
    public CommonDatas<CommonData> convert(List<FurnitureLink> source) {
        commonDatas.clear();
        for (FurnitureLink link : source) {

            FurnitureCommonData commonData = FurnitureCommonData.valueOf(link);

            CommonDatas<CommonData> commonDatas = getCommonDatasBy(commonData);
            switch (link.getFurnitureType().getUnit()) {
                case squareMetre:
                case piece:
                case gramme:
                    //case jar760g:
                    commonData.setSizeAsDouble(link.getSize());
                    commonData.setCount(getAmountBy(link).doubleValue());
                    PriceEntity price = mainFacade.getPriceFacade().findUniqueBy(link.getFurnitureType(), link.getFurnitureCode());
                    ReportUtils.fillPrice(commonData, price, order, mainFacade);
                    commonData.setUnit(StringValueAnnotationProcessor.getProcessor().convert(link.getFurnitureType().getUnit()));
                    commonDatas.add(commonData);
                    break;
                case linearMetre:
                case linearMiliMetre:
                case linearSantiMetre:
                    commonData.setSizeAsDouble(link.getSize());
                    commonData.setCount(getAmountBy(link).doubleValue());
                    commonData.setUnit(StringValueAnnotationProcessor.getProcessor().convert(link.getFurnitureType().getUnit()));
                    int index = commonDatas.lastIndexOf(commonData);
                    if (index > -1) {
                        commonData = (FurnitureCommonData) commonDatas.get(index);
                        commonData.increaseCount(getAmountBy(link).doubleValue());
                    } else {
                        price = mainFacade.getPriceFacade().findUniqueBy(link.getFurnitureType(), link.getFurnitureCode());
                        ReportUtils.fillPrice(commonData, price, order, mainFacade);
                        commonDatas.add(commonData);
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        return sort();
    }

    private CommonDatas<CommonData> getCommonDatasBy(FurnitureCommonData commonData) {
        CommonDatas<CommonData> commonDatas = this.commonDatas.get(commonData.getService());
        if (commonDatas == null) {
            commonDatas = new CommonDatas<CommonData>(commonDataType, order);
            this.commonDatas.put(commonData.getService(), commonDatas);
        }
        return commonDatas;
    }

    private Integer getAmountBy(FurnitureLink link) {
        return link.getAmount() * (useOrderItemCount ? link.getOrderItem().getAmount() : 1);
    }

    private CommonDatas<CommonData> sort() {
        CommonDatas<CommonData> sorted = new CommonDatas<CommonData>(commonDataType, order);
        for (String service : commonDatas.keySet()) {
            CommonDatas<CommonData> materials = commonDatas.get(service);
            Collections.sort(materials);
            materials.get(materials.size() - 1).markAsLast();
            sorted.addAll(materials);
        }
        return sorted;
    }

}
