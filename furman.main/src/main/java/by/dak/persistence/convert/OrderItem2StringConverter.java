package by.dak.persistence.convert;

import by.dak.persistence.entities.OrderItem;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * User: akoyro
 * Date: 19.08.2009
 * Time: 16:32:15
 */
public class OrderItem2StringConverter implements EntityToStringConverter<OrderItem>
{
    @Override
    public String convert(OrderItem entity)
    {
        return entity.getName();
    }
}