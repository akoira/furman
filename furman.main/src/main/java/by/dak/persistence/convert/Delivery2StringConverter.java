package by.dak.persistence.convert;

import by.dak.persistence.entities.Delivery;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 01.02.2010
 * Time: 15:49:03
 * To change this template use File | Settings | File Templates.
 */
public class Delivery2StringConverter implements EntityToStringConverter<Delivery>
{
    @Override
    public String convert(Delivery entity)
    {
        return entity.getDeliveryDate() + "/" + entity.getProvider().getName();
    }
}
