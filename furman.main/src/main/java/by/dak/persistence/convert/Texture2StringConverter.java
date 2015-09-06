package by.dak.persistence.convert;

import by.dak.persistence.entities.TextureEntity;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * @author admin
 * @version 0.1 24.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class Texture2StringConverter implements EntityToStringConverter<TextureEntity>
{

    public String convert(TextureEntity entity)
    {
        return new StringBuffer(entity.getName()).append(" ").append(entity.getCode().toString())
                .append(" - ").append(entity.getManufacturer().getName())
                .append("(").append(entity.getGroupIdentifier()).append(")")
                .append(entity.getSurface() != null ? " " + entity.getSurface() : "").toString();
    }
}
