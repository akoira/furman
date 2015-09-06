package by.dak.persistence.convert;

import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * @author akoyro
 * @version 0.1 30.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class OrderFurniture2StringConverter implements EntityToStringConverter<AOrderBoardDetail>
{
    @Override
    public String convert(AOrderBoardDetail entity)
    {
        return new StringBuffer(entity.getName()).append("-").
                append(entity.getNumber().toString()).
                append(" ").
                append(entity.getLength()).append("x").append(entity.getWidth()).
                toString();
    }
}
