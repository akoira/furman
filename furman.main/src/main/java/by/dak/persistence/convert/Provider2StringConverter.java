package by.dak.persistence.convert;

import by.dak.persistence.entities.Provider;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg Birulia
 * Date: 29.11.2009
 * Time: 12:27:12
 * To change this template use File | Settings | File Templates.
 */
public class Provider2StringConverter implements EntityToStringConverter<Provider>
{

    @Override
    public String convert(Provider entity)
    {
        return entity.getName();
    }
}
