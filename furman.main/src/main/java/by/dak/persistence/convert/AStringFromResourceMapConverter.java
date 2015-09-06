package by.dak.persistence.convert;

import by.dak.utils.convert.EntityToStringConverter;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * Значение получает из ресурсов
 */
public abstract class AStringFromResourceMapConverter<E extends Enum> implements EntityToStringConverter<E>
{
    private ResourceMap resourceMap;

    public AStringFromResourceMapConverter()
    {
        resourceMap = Application.getInstance().
                getContext().getResourceMap(this.getClass());
    }

    @Override
    public String convert(E entity)
    {
        return resourceMap.getString(entity.name());
    }
}
