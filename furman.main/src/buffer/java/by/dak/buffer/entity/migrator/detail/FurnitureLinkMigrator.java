package by.dak.buffer.entity.migrator.detail;

import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.buffer.entity.migrator.DilerOrderDetailMigrator;
import by.dak.buffer.entity.migrator.detail.furniture.FurnitureCodeMigrationFactory;
import by.dak.buffer.entity.migrator.detail.furniture.FurnitureTypeMigrationFactory;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 14:43:46
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureLinkMigrator extends DilerOrderDetailMigrator<FurnitureLink>
{
    @Override
    public FurnitureLink migrate(DilerOrderDetail dilerOrderDetail)
    {
        FurnitureLink furnitureLink = new FurnitureLink();
        FurnitureTypeMigrationFactory furnitureTypeMigrationFactory = (FurnitureTypeMigrationFactory) FacadeContext.
                getApplicationContext().getBean("furnitureTypeMigrationFactory");
        FurnitureCodeMigrationFactory furnitureCodeMigrationFactory = (FurnitureCodeMigrationFactory) FacadeContext.
                getApplicationContext().getBean("furnitureCodeMigrationFactory");

        furnitureLink.setAmount(dilerOrderDetail.getAmount());
        furnitureLink.setNumber(dilerOrderDetail.getNumber());
        furnitureLink.setName(dilerOrderDetail.getName());
        furnitureLink.setDiscriminator(dilerOrderDetail.getDiscriminator());
        furnitureLink.setSize(dilerOrderDetail.getSize());

        if (dilerOrderDetail.getFurnitureType() != null)
        {
            FurnitureType furnitureType = (FurnitureType) furnitureTypeMigrationFactory.
                    migrate(dilerOrderDetail.getFurnitureType());
            furnitureLink.setFurnitureType(furnitureType);
        }
        if (dilerOrderDetail.getFurnitureCode() != null)
        {
            FurnitureCode furnitureCode = (FurnitureCode) furnitureCodeMigrationFactory.
                    migrate(dilerOrderDetail.getFurnitureCode());
            furnitureLink.setFurnitureCode(furnitureCode);
        }

        return furnitureLink;
    }
}
