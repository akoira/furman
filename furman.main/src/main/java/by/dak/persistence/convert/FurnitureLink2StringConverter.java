package by.dak.persistence.convert;

import by.dak.persistence.entities.FurnitureLink;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.03.2010
 * Time: 11:15:24
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureLink2StringConverter implements EntityToStringConverter<FurnitureLink>
{
    @Override
    public String convert(FurnitureLink entity)
    {
        return (entity.getFurnitureType() != null ? entity.getFurnitureType().getName() : "") +
                " / " +
                (entity.getFurnitureCode() != null ? entity.getFurnitureCode().getName() : "");
    }
}
