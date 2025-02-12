package by.dak.report.jasper.common.data.converter;

import by.dak.persistence.MainFacade;
import by.dak.persistence.convert.ServiceType2StringConverter;
import by.dak.persistence.entities.*;
import by.dak.report.jasper.ReportUtils;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.report.jasper.common.data.ServiceCommonData;
import by.dak.report.jasper.common.facade.CommonDataFacade;
import by.dak.utils.convert.Converter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

import java.util.*;

/**
 *
 */

public class ServiceConverter implements Converter<List<ServiceLink>, List<CommonData>> {
    private boolean usePriceDealer;
    private boolean useOrderItemCount = true;
    private AOrder order;
    private final Dailysheet dailysheet;
    private final MainFacade mainFacade;
    private static final ServiceType2StringConverter serviceType2StringConverter = new ServiceType2StringConverter();

    private SortedMap<String, CommonDatas<CommonData>> commonDatas = new TreeMap<String, CommonDatas<CommonData>>(new StringComparator());

    //private CommonDatas<CommonData> commonDatas;

    public ServiceConverter(boolean useOrderItemCount,AOrder order, MainFacade mainFacade) {
        this.useOrderItemCount = useOrderItemCount;
        this.order = order;
        this.mainFacade = mainFacade;
        this.dailysheet = MainFacade.dailysheet.apply(mainFacade).apply(order);
    }

    public ServiceConverter(AOrder order, MainFacade mainFacade) {
        this(true, order, mainFacade);
    }

    @Override
    public CommonDatas<CommonData> convert(List<ServiceLink> source) {
        commonDatas.clear();
        for (ServiceLink link : source) {

            ServiceCommonData commonData = ServiceCommonData.valueOf(link);

            CommonDatas<CommonData> commonDatas = getCommonDatasBy(commonData);
            switch (link.getPriceAware().getUnit()) {
                case squareMetre:
                case piece:
                case gramme:
                    //case jar760g:
                    commonData.setSizeAsDouble(link.getSize());
                    commonData.setCount(getAmountBy(link).doubleValue());
                    PriceEntity price = mainFacade.getPriceFacade().findUniqueBy(link.getPriceAware(), link.getPriced());
                    ReportUtils.fillPrice(commonData, price, order, mainFacade);
                    commonData.setUnit(StringValueAnnotationProcessor.getProcessor().convert(link.getPriceAware().getUnit()));
                    commonDatas.add(commonData);
                    break;
                case linearMetre:
                case linearMiliMetre:
                case linearSantiMetre:
                    commonData.setSizeAsDouble(link.getSize());
                    commonData.setCount(getAmountBy(link).doubleValue());
                    commonData.setUnit(StringValueAnnotationProcessor.getProcessor().convert(link.getPriceAware().getUnit()));
                    int index = commonDatas.lastIndexOf(commonData);
                    if (index > -1) {
                        commonData = (ServiceCommonData) commonDatas.get(index);
                        commonData.increaseCount(getAmountBy(link).doubleValue());
                    } else {
                        price = mainFacade.getPriceFacade().findUniqueBy(link.getPriceAware(), link.getPriced());
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

    private CommonDatas<CommonData> getCommonDatasBy(ServiceCommonData commonData) {
        CommonDatas<CommonData> commonDatas = this.commonDatas.get(commonData.getService());
        if (commonDatas == null) {
            commonDatas = new CommonDatas<CommonData>(CommonDataType.additionalService, order);
            this.commonDatas.put(commonData.getService(), commonDatas);
        }
        return commonDatas;
    }

    private Integer getAmountBy(ServiceLink link) {
        return link.getAmount() * (useOrderItemCount ? link.getOrderItem().getAmount() : 1);
    }

    private CommonDatas<CommonData> sort() {
        CommonDatas<CommonData> sorted = new CommonDatas<CommonData>(CommonDataType.additionalService, order);
        for (String service : commonDatas.keySet()) {
            CommonDatas<CommonData> materials = commonDatas.get(service);
            Collections.sort(materials);
            materials.get(materials.size() - 1).markAsLast();
            sorted.addAll(materials);
        }
        return sorted;
    }

    public static List<CommonDataFacade.Statistic> convertToStatistic(List<ServiceLink> source) {
        List<CommonDataFacade.Statistic> statistics = new ArrayList<>();

        for (ServiceLink link : source) {
            CommonDataFacade.Statistic statistic = new CommonDataFacade.Statistic();
            statistic.setService(serviceType2StringConverter.convert(link.getPriced().getServiceType()));
            statistic.setName(link.getName());
            statistic.setAmount(link.getSize());

            statistics.add(statistic);
        }
        return statistics;
    }

}
