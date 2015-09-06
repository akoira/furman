package by.dak.buffer.entity.migrator.detail.furniture.type;

import by.dak.buffer.entity.migrator.detail.furniture.AFurnitureTypeMigrator;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.types.FurnitureType;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:38:46
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureTypeMigrator extends AFurnitureTypeMigrator<FurnitureType>
{
    @Override
    public FurnitureType migrate(PriceAware priceAware)
    {
        FurnitureType furnitureType = FacadeContext.getFurnitureTypeFacade().findById(priceAware.getId(), true);

        return furnitureType;
    }
}
