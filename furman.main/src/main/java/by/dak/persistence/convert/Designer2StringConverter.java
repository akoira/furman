package by.dak.persistence.convert;

import by.dak.persistence.entities.DesignerEntity;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * @author admin
 * @version 0.1 07.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class Designer2StringConverter implements EntityToStringConverter<DesignerEntity>
{
    public String convert(DesignerEntity entity)
    {
        return new StringBuffer(entity.getName()).toString();
    }
}