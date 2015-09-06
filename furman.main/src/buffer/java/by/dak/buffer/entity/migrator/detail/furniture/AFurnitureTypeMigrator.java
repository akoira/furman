package by.dak.buffer.entity.migrator.detail.furniture;

import by.dak.persistence.entities.PriceAware;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:27:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class AFurnitureTypeMigrator<P extends PriceAware>
{
    public abstract P migrate(PriceAware priceAware);
}
