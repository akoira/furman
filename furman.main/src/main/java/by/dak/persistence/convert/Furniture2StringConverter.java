package by.dak.persistence.convert;

import by.dak.persistence.entities.Furniture;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.02.2010
 * Time: 17:13:41
 * To change this template use File | Settings | File Templates.
 */
public class Furniture2StringConverter extends AStoreElement2StringConverter<Furniture>
{
    @Override
    public String convert(Furniture entity)
    {
        return entity.getPriced().getName() + " " + entity.getPriceAware().getName() + " " + (entity.getProvider() != null ? entity.getProvider().getName() : "");
    }
}
