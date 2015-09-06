package by.dak.persistence.convert;

import by.dak.persistence.entities.Employee;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * @author admin
 * @version 0.1 07.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class Employee2StringConverter implements EntityToStringConverter<Employee>
{
    public String convert(Employee entity)
    {
        return new StringBuffer(entity.getName()).append(' ').append(entity.getSurname()).toString();
    }
}
