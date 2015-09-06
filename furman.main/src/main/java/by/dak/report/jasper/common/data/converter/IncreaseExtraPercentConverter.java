package by.dak.report.jasper.common.data.converter;

import by.dak.report.jasper.common.data.CommonData;
import by.dak.utils.convert.Converter;

/**
 * @author Denis Koyro
 * @version 0.1 29.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class IncreaseExtraPercentConverter implements Converter<CommonData, CommonData>
{
    public CommonData convert(CommonData source)
    {
        double count = source.getCount();
        source.increase(count / 10d);
        return source;
    }
}
