package by.dak.buffer.entity.migrator.detail.furniture.code;

import by.dak.buffer.entity.migrator.detail.furniture.AFurnitureCodeMigrator;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.types.FurnitureCode;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:40:49
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureCodeMigrator extends AFurnitureCodeMigrator<FurnitureCode>
{
    @Override
    public FurnitureCode migrate(Priced priced)
    {
        /*FurnitureCode furnitureCode = new FurnitureCode();
        furnitureCode.setCode(priced.getCode());
        furnitureCode.setGroupIdentifier(priced.getGroupIdentifier());
        furnitureCode.setManufacturer(priced.getManufacturer());
        furnitureCode.setName(priced.getName());
        furnitureCode.setPricedType(priced.getPricedType());
        furnitureCode.setPrices(priced.getPrices());
        furnitureCode.setId(priced.getId());*/

        FurnitureCode furnitureCode = FacadeContext.getFurnitureCodeFacade().findById(priced.getId(), true);

        return furnitureCode;
    }
}
