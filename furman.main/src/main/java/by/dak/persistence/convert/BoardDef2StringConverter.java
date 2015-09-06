package by.dak.persistence.convert;

import by.dak.persistence.entities.BoardDef;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * @author admin
 * @version 0.1 24.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class BoardDef2StringConverter implements EntityToStringConverter<BoardDef>
{
    @Override
    public String convert(BoardDef entity)
    {
        return entity.getName() + " (" + entity.getThickness() + ")";
    }
}
