package by.dak.additional.report;

import by.dak.additional.Additional;
import by.dak.persistence.entities.AOrder;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.utils.convert.Converter;

import java.util.List;

/**
 * User: akoyro
 * Date: 19.06.2010
 * Time: 19:26:41
 */
public class AdditionalsConverter implements Converter<List<Additional>, CommonDatas<CommonData>>
{
    private boolean usePriceDealer;
    private AOrder order;


    public AdditionalsConverter(AOrder order)
    {

        this.order = order;
    }

    public CommonDatas<CommonData> convert(List<Additional> additionals)
    {
        CommonDatas<CommonData> commonDatas = new CommonDatas<CommonData>(CommonDataType.additional, order);
        for (Additional additional : additionals)
        {
            CommonData commonData = new CommonData();
            commonData.setName(additional.getName());
            commonData.setService(additional.getType());
            commonData.increase(additional.getSize());
            commonData.setPrice(additional.getPrice());
            commonData.setDialerPrice(additional.getPrice());
            commonDatas.add(commonData);
        }
        return commonDatas;
    }

}

