package by.dak.persistence.convert;

import by.dak.persistence.entities.Order;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

/**
 * User: akoyro
 * Date: 19.08.2009
 * Time: 16:32:15
 */
public class Order2StringConverter implements EntityToStringConverter<Order>
{
    @Override
    public String convert(Order entity)
    {
        if (entity == Order.NULL_Order)
        {
            return Order.NULL_Order.getName();
        }
        else
        {
            return new StringBuffer(StringValueAnnotationProcessor.getProcessor().convert(entity.getNumber()))
                    .append(" ")
                    .append(entity.getName())
                    .toString();
        }
    }
}