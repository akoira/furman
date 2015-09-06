package by.dak.persistence.convert;

import by.dak.persistence.entities.types.FurnitureType;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 02.01.2010
 * Time: 15:02:21
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureType2StringConverter implements EntityToStringConverter<FurnitureType>
{
    @Override
    public String convert(FurnitureType entity)
    {
        return entity.getName();
    }
}

