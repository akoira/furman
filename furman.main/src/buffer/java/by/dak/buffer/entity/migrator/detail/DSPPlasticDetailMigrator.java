package by.dak.buffer.entity.migrator.detail;

import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.buffer.entity.migrator.DilerOrderDetailMigrator;
import by.dak.buffer.entity.migrator.detail.furniture.FurnitureCodeMigrationFactory;
import by.dak.buffer.entity.migrator.detail.furniture.FurnitureTypeMigrationFactory;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import by.dak.plastic.DSPPlasticDetail;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 10.10.11
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public class DSPPlasticDetailMigrator extends DilerOrderDetailMigrator<DSPPlasticDetail>
{
    @Override
    public DSPPlasticDetail migrate(DilerOrderDetail dilerOrderDetail)
    {
        DSPPlasticDetail dspPlasticDetail = new DSPPlasticDetail();
        dspPlasticDetail.setPrimary(dilerOrderDetail.getPrimary());

        dspPlasticDetail.setNumber(dilerOrderDetail.getNumber());
        dspPlasticDetail.setName(dilerOrderDetail.getName());

        dspPlasticDetail.setLength(dilerOrderDetail.getLength());
        dspPlasticDetail.setWidth(dilerOrderDetail.getWidth());
        dspPlasticDetail.setAmount(dilerOrderDetail.getAmount());
        dspPlasticDetail.setRotatable(dilerOrderDetail.isRotatable());


        dspPlasticDetail.setDrilling(dilerOrderDetail.getDrilling());
        dspPlasticDetail.setGlueing(dilerOrderDetail.getGlueing());
        dspPlasticDetail.setGroove(dilerOrderDetail.getGroove());
        dspPlasticDetail.setMilling(dilerOrderDetail.getMilling());
        dspPlasticDetail.setAngle45(dilerOrderDetail.getAngle45());
        dspPlasticDetail.setCutoff(dilerOrderDetail.getCutoff());


        FurnitureTypeMigrationFactory furnitureTypeMigrationFactory = (FurnitureTypeMigrationFactory) FacadeContext.
                getApplicationContext().getBean("furnitureTypeMigrationFactory");
        FurnitureCodeMigrationFactory furnitureCodeMigrationFactory = (FurnitureCodeMigrationFactory) FacadeContext.
                getApplicationContext().getBean("furnitureCodeMigrationFactory");

        if (dilerOrderDetail.getFurnitureType() != null)
        {
            BoardDef boardDef = (BoardDef) furnitureTypeMigrationFactory.
                    migrate(dilerOrderDetail.getFurnitureType());
            dspPlasticDetail.setBoardDef(boardDef);
        }

        if (dilerOrderDetail.getFurnitureCode() != null)
        {
            TextureEntity textureEntity = (TextureEntity) furnitureCodeMigrationFactory.
                    migrate(dilerOrderDetail.getFurnitureCode());
            dspPlasticDetail.setTexture(textureEntity);
        }

        if (dilerOrderDetail.getComlexFurnitureType() != null)
        {
            BoardDef complexBoardDef = (BoardDef) furnitureTypeMigrationFactory.
                    migrate(dilerOrderDetail.getComlexFurnitureType());
            dspPlasticDetail.setComlexBoardDef(complexBoardDef);
        }

        dspPlasticDetail.setSecond(dilerOrderDetail.getSecond());

        return dspPlasticDetail;
    }
}
