package by.dak.persistence.entities.predefined;

import by.dak.persistence.convert.StoreElementStatus2StringConverter;
import by.dak.utils.convert.StringValue;

/**
 * User: akoyro
 * Date: 13.08.2009
 * Time: 17:52:34
 */
@StringValue(converterClass = StoreElementStatus2StringConverter.class)
public enum StoreElementStatus
{
    exist,//есть в наличии
    order,//необходимо заказывать
    used,//использованны
    temp,//временный, используется для храннения временных остатков.
}
