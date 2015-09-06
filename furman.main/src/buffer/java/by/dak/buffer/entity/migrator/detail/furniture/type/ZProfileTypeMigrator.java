package by.dak.buffer.entity.migrator.detail.furniture.type;

import by.dak.buffer.entity.migrator.detail.furniture.AFurnitureTypeMigrator;
import by.dak.cutting.zfacade.ZProfileType;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:39:24
 * To change this template use File | Settings | File Templates.
 */
public class ZProfileTypeMigrator extends AFurnitureTypeMigrator<ZProfileType>
{
    @Override
    public ZProfileType migrate(PriceAware priceAware)
    {
        ZProfileType profileType = FacadeContext.getZProfileTypeFacade().findById(priceAware.getId(), true);

        return profileType;
    }
}
