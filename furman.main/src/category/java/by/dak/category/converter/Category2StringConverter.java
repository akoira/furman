package by.dak.category.converter;

import by.dak.category.Category;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * User: akoyro
 * Date: 24.03.11
 * Time: 12:58
 */
public class Category2StringConverter implements EntityToStringConverter<Category>
{
    @Override
    public String convert(Category entity)
    {
        return entity.getName();
    }
}
