package by.dak.buffer.entity.migrator.detail;

import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.buffer.entity.migrator.DilerOrderDetailMigrator;
import by.dak.buffer.entity.migrator.detail.furniture.FurnitureCodeMigrationFactory;
import by.dak.buffer.entity.migrator.detail.furniture.FurnitureTypeMigrationFactory;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.TextureEntity;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 14:44:11
 * To change this template use File | Settings | File Templates.
 */
public class OrderFurnitureMigrator extends DilerOrderDetailMigrator<OrderFurniture>
{
    @Override
    public OrderFurniture migrate(DilerOrderDetail dilerOrderDetail)
    {
        OrderFurniture orderFurniture = new OrderFurniture();

        orderFurniture.setAmount(dilerOrderDetail.getAmount());
        orderFurniture.setNumber(dilerOrderDetail.getNumber());
        orderFurniture.setName(dilerOrderDetail.getName());
        orderFurniture.setDiscriminator(dilerOrderDetail.getDiscriminator());
        orderFurniture.setLength(dilerOrderDetail.getLength());
        orderFurniture.setWidth(dilerOrderDetail.getWidth());
        orderFurniture.setRotatable(dilerOrderDetail.getRotatable());
        orderFurniture.setGlueing(dilerOrderDetail.getGlueing());
        orderFurniture.setMilling(dilerOrderDetail.getMilling());
        orderFurniture.setGroove(dilerOrderDetail.getGroove());
        orderFurniture.setAngle45(dilerOrderDetail.getAngle45());
        orderFurniture.setDrilling(dilerOrderDetail.getDrilling());
        orderFurniture.setCutoff(dilerOrderDetail.getCutoff());
        orderFurniture.setPrimary(dilerOrderDetail.getPrimary());

        FurnitureTypeMigrationFactory furnitureTypeMigrationFactory = (FurnitureTypeMigrationFactory) FacadeContext.
                getApplicationContext().getBean("furnitureTypeMigrationFactory");
        FurnitureCodeMigrationFactory furnitureCodeMigrationFactory = (FurnitureCodeMigrationFactory) FacadeContext.
                getApplicationContext().getBean("furnitureCodeMigrationFactory");

        if (dilerOrderDetail.getFurnitureType() != null)
        {
            BoardDef boardDef = (BoardDef) furnitureTypeMigrationFactory.
                    migrate(dilerOrderDetail.getFurnitureType());
            orderFurniture.setBoardDef(boardDef);
        }

        if (dilerOrderDetail.getFurnitureCode() != null)
        {
            TextureEntity textureEntity = (TextureEntity) furnitureCodeMigrationFactory.
                    migrate(dilerOrderDetail.getFurnitureCode());
            orderFurniture.setTexture(textureEntity);
        }

        if (dilerOrderDetail.getComlexFurnitureType() != null)
        {
            BoardDef complexBoardDef = (BoardDef) furnitureTypeMigrationFactory.
                    migrate(dilerOrderDetail.getComlexFurnitureType());
            orderFurniture.setComlexBoardDef(complexBoardDef);
        }

        orderFurniture.setSecond(dilerOrderDetail.getSecond());

        return orderFurniture;
    }
}
