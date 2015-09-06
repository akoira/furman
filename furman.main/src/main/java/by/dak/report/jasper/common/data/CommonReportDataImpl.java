package by.dak.report.jasper.common.data;

import by.dak.persistence.entities.AOrder;

import java.util.*;

/**
 * @author akoyro
 * @version 0.1 04.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class CommonReportDataImpl implements CommonReportData
{
    private AOrder order;

    private Map<CommonDataType, CommonDatas> commonDatasMap = new HashMap<CommonDataType, CommonDatas>();


    public CommonReportDataImpl(AOrder order)
    {
        this.order = order;

    }

    @Override
    public AOrder getOrder()
    {
        return order;
    }

    public void setCommonDatas(CommonDatas commonDatas)
    {
        setCommonDatas(commonDatas.getCommonDataType(), commonDatas);
    }

    public void setCommonDatas(CommonDataType commonDataType, CommonDatas commonDatas)
    {
        commonDatas.setCommonDataType(commonDataType);
        commonDatasMap.put(commonDataType, commonDatas);
    }

    @Override
    public CommonDatas getCommonDatas(CommonDataType commonDataType)
    {
        return commonDatasMap.get(commonDataType);
    }


    public static List<CommonData> convertForDialer(List<CommonData> commonDatas)
    {
        ArrayList<CommonData> result = new ArrayList<CommonData>();
        if (commonDatas != null)
        {
            for (CommonData commonData : commonDatas)
            {
                result.add(commonData.cloneForDialer());
            }
        }
        return result;
    }

    @Override
    public List<CommonData> getServicesData()
    {
        return getBy(CommonDataType.serviceTypes());
    }

    private List<CommonData> getBy(CommonDataType[] commonDataTypes)
    {
        List<CommonData> result = new ArrayList<CommonData>();
        for (CommonDataType commonDataType : commonDataTypes)
        {
            CommonDatas commonDatas = getCommonDatas(commonDataType);
            if (commonDatas != null)
            {
                result.addAll(commonDatas);
            }
        }
        return result;
    }

    @Override
    public List<CommonData> getMaterialsData()
    {
        return getBy(CommonDataType.materialTypes());
    }

    @Override
    public List<CommonData> getFurnitureData()
    {
        List<CommonData> commonDatas = getBy(new CommonDataType[]{CommonDataType.furniture, CommonDataType.facadeFurniture});
        commonDatas = addEmtyFurnitureCommonData(commonDatas);
        return commonDatas;
    }

    private List<CommonData> addEmtyFurnitureCommonData(List<CommonData> commonDatas)
    {
        if (commonDatas == null || commonDatas.size() < 1)
        {
            CommonData commonData = new FurnitureCommonData();
            commonDatas = Collections.singletonList(commonData);
        }
        return commonDatas;
    }


    public List<CommonData> getDialerFurnitureData()
    {
        List<CommonData> commonDatas = getBy(new CommonDataType[]{CommonDataType.furniture, CommonDataType.agtfacade, CommonDataType.zfacade});
        commonDatas = addEmtyFurnitureCommonData(commonDatas);
        return commonDatas;
    }

    public Double getCommonCost()
    {
        double result = 0l;

        CommonDataType[] types = CommonDataType.productionTypes();
        for (CommonDataType type : types)
        {
            CommonDatas commonDatas = getCommonDatas(type);
            if (commonDatas != null)
            {
                result += commonDatas.getCommonCost();
            }
        }
        return result;
    }

    public Double getDialerCommonCost()
    {
        double result = 0l;

        CommonDataType[] types = CommonDataType.dialerTypes();
        for (CommonDataType type : types)
        {
            CommonDatas commonDatas = getCommonDatas(type);
            if (commonDatas != null)
            {
                result += commonDatas.getDialerCommonCost();
            }

        }
        return result;
    }

}

