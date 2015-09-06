package by.dak.ordergroup.converter;

import by.dak.ordergroup.OrderGroup;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 30.03.2010
 * Time: 17:01:17
 * To change this template use File | Settings | File Templates.
 */
public class OrderGroup2StringConverter implements EntityToStringConverter<OrderGroup>
{
    @Override
    public String convert(OrderGroup entity)
    {
        return entity.getName();
    }
}
