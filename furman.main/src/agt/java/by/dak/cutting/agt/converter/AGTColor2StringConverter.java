package by.dak.cutting.agt.converter;

import by.dak.cutting.agt.AGTColor;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * User: akoyro
 * Date: 09.11.2010
 * Time: 15:35:30
 */
public class AGTColor2StringConverter implements EntityToStringConverter<AGTColor>
{
    @Override
    public String convert(AGTColor entity)
    {
        StringBuffer buffer = new StringBuffer(entity.getName()).append(" ");
        if (entity.getWidthGroove() != null)
        {
            buffer.append("[").append(entity.getWidthGroove().intValue()).append("] ");
        }
        buffer.append(entity.getCode().toString()).append(entity.getGroupIdentifier() != null ? " (" + entity.getGroupIdentifier() + ")" : "");

        buffer.append(" - ").append(entity.getManufacturer().getName());
        return buffer.toString();
    }
}
