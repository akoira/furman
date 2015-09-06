package by.dak.persistence.convert;

import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 02.01.2010
 * Time: 15:02:08
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureCode2StringConverter implements EntityToStringConverter<FurnitureCode>
{
    @Override
    public String convert(FurnitureCode entity)
    {
        StringBuffer buffer =  new StringBuffer(entity.getName()).append(" ")
                .append(entity.getCode().toString()).append(entity.getGroupIdentifier() != null ? " (" + entity.getGroupIdentifier() + ")": "")
                .append(" - ").append(entity.getManufacturer().getName());
        return buffer.toString();
    }
}
