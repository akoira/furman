package by.dak.buffer.entity.migrator.detail;

import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.buffer.entity.migrator.DilerOrderDetailMigrator;
import by.dak.buffer.entity.migrator.detail.furniture.FurnitureCodeMigrationFactory;
import by.dak.buffer.entity.migrator.detail.furniture.FurnitureTypeMigrationFactory;
import by.dak.cutting.zfacade.ZButtLink;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 14:44:26
 * To change this template use File | Settings | File Templates.
 */
public class ZButtLinkMigrator extends DilerOrderDetailMigrator<ZButtLink>
{
    @Override
    public ZButtLink migrate(DilerOrderDetail dilerOrderDetail)
    {
        ZButtLink buttLink = new ZButtLink();

        buttLink.setAmount(dilerOrderDetail.getAmount());
        buttLink.setNumber(dilerOrderDetail.getNumber());
        buttLink.setName(dilerOrderDetail.getName());
        buttLink.setDiscriminator(dilerOrderDetail.getDiscriminator());
        buttLink.setSize(dilerOrderDetail.getSize());
        buttLink.setSide(dilerOrderDetail.getSide());

        FurnitureTypeMigrationFactory furnitureTypeMigrationFactory = (FurnitureTypeMigrationFactory) FacadeContext.
                getApplicationContext().getBean("furnitureTypeMigrationFactory");
        FurnitureCodeMigrationFactory furnitureCodeMigrationFactory = (FurnitureCodeMigrationFactory) FacadeContext.
                getApplicationContext().getBean("furnitureCodeMigrationFactory");

        if (dilerOrderDetail.getFurnitureCode() != null)
        {
            buttLink.setFurnitureCode((FurnitureCode) furnitureCodeMigrationFactory.migrate(dilerOrderDetail.getFurnitureCode()));
        }
        if (dilerOrderDetail.getFurnitureType() != null)
        {
            buttLink.setFurnitureType((FurnitureType) furnitureTypeMigrationFactory.migrate(dilerOrderDetail.getFurnitureType()));
        }

        return buttLink;
    }
}
