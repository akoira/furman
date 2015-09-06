package by.dak.buffer.entity.migrator.detail.furniture.code;

import by.dak.buffer.entity.migrator.detail.furniture.AFurnitureCodeMigrator;
import by.dak.cutting.zfacade.ZProfileColor;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Priced;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:42:02
 * To change this template use File | Settings | File Templates.
 */
public class ZProfileColorMigrator extends AFurnitureCodeMigrator<ZProfileColor>
{
    @Override
    public ZProfileColor migrate(Priced priced)
    {
        /*ZProfileColor profileColor = new ZProfileColor();
        profileColor.setCode(priced.getCode());
        profileColor.setGroupIdentifier(priced.getGroupIdentifier());
        profileColor.setManufacturer(priced.getManufacturer());
        profileColor.setName(priced.getName());
        profileColor.setPricedType(priced.getPricedType());
        profileColor.setPrices(priced.getPrices());
        profileColor.setId(priced.getId());*/
        ZProfileColor profileColor = FacadeContext.getZProfileColorFacade().findById(priced.getId(), true);

        return profileColor;
    }
}
