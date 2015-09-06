package by.dak.persistence.convert;

import by.dak.persistence.entities.AStoreElement;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 12.02.2010
 * Time: 10:04:55
 * To change this template use File | Settings | File Templates.
 */
public abstract class AStoreElement2StringConverter<T extends AStoreElement> implements EntityToStringConverter<T>
{
    @Override
    public String convert(T entity)
    {
        return entity.getPriceAware().getName() + " " + entity.getPriced().getName() + " " + entity.getProvider().getName();
    }
}