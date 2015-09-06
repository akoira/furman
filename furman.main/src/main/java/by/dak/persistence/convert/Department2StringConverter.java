package by.dak.persistence.convert;

import by.dak.persistence.entities.DepartmentEntity;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * @author admin
 * @version 0.1 07.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class Department2StringConverter implements EntityToStringConverter<DepartmentEntity>
{
    @Override
    public String convert(DepartmentEntity entity)
    {
        return entity.getName();
    }
}
