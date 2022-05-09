package by.dak.persistence.convert;

import by.dak.utils.convert.EntityToStringConverter;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.io.IOException;
import java.util.Properties;

/**
 * Значение получает из ресурсов
 */
public abstract class AStringFromResourceMapConverter<E extends Enum> implements EntityToStringConverter<E>
{
    private final Properties properties = new Properties();

    public AStringFromResourceMapConverter()
    {
        try {
            properties.load(this.getClass().getResourceAsStream("resources/" + this.getClass().getSimpleName() + ".properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public String convert(E entity)
    {
        return properties.getProperty(entity.name());
    }
}
