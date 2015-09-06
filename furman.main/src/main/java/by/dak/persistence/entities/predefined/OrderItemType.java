package by.dak.persistence.entities.predefined;

import by.dak.persistence.convert.OrderItemType2StringConverter;
import by.dak.utils.convert.StringValue;

/**
 * User: akoyro
 * Date: 20.07.2010
 * Time: 21:32:03
 */
@StringValue(converterClass = OrderItemType2StringConverter.class)
public enum OrderItemType
{
    first, //первый в заказе может быть только один
    common, // копия первого из другого заказа может быть много
    zfacade, // zfacade может быть много
    agtfacade, //agtfacade может быть много
    templateFacade, //templateFacade can be more then one
    plastic
}
