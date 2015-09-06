package by.dak.persistence.convert;

import by.dak.persistence.entities.Cutter;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * User: akoyro
 * Date: 30.07.2010
 * Time: 22:00:28
 */
public class Cutter2StringConverter implements EntityToStringConverter<Cutter>
{
    @Override
    public String convert(Cutter entity)
    {
        return entity.getName();
    }
}
