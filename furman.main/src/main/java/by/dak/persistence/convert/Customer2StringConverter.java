package by.dak.persistence.convert;

import by.dak.persistence.entities.Customer;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * @author admin
 * @version 0.1 24.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class Customer2StringConverter implements EntityToStringConverter<Customer>
{
    @Override
    public String convert(Customer entity)
    {
        return entity.getName();
    }
}