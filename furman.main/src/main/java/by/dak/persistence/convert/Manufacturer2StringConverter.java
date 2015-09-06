package by.dak.persistence.convert;

import by.dak.persistence.entities.Manufacturer;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 18.01.2009
 * Time: 16:11:38
 * To change this template use File | Settings | File Templates.
 */
public class Manufacturer2StringConverter implements EntityToStringConverter<Manufacturer>
{
    @Override
    public String convert(Manufacturer entity)
    {
        return entity.getName();
    }
}
