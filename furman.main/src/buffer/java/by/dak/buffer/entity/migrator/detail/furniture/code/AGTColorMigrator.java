package by.dak.buffer.entity.migrator.detail.furniture.code;

import by.dak.buffer.entity.migrator.detail.furniture.AFurnitureCodeMigrator;
import by.dak.cutting.agt.AGTColor;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Priced;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:40:15
 * To change this template use File | Settings | File Templates.
 */
public class AGTColorMigrator extends AFurnitureCodeMigrator<AGTColor>
{
    @Override
    public AGTColor migrate(Priced priced)
    {
        /*AGTColor agtColor = new AGTColor();
        agtColor.setCode(priced.getCode());
        agtColor.setGroupIdentifier(priced.getGroupIdentifier());
        agtColor.setManufacturer(priced.getManufacturer());
        agtColor.setName(priced.getName());
        agtColor.setPricedType(priced.getPricedType());
        agtColor.setPrices(priced.getPrices());
        agtColor.setId(priced.getId());*/

        AGTColor agtColor = FacadeContext.getAGTColorFacade().findById(priced.getId(), true);


        return agtColor;
    }
}
