package by.dak.buffer.entity.migrator.detail.furniture.type;

import by.dak.buffer.entity.migrator.detail.furniture.AFurnitureTypeMigrator;
import by.dak.cutting.agt.AGTType;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:37:46
 * To change this template use File | Settings | File Templates.
 */
public class AGTTypeMigrator extends AFurnitureTypeMigrator<AGTType>
{
    @Override
    public AGTType migrate(PriceAware priceAware)
    {
        AGTType agtType = FacadeContext.getAGTTypeFacade().findById(priceAware.getId(), true);

        return agtType;
    }
}
