package by.dak.buffer.entity.migrator.detail.furniture.code;

import by.dak.buffer.entity.migrator.detail.furniture.AFurnitureCodeMigrator;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.TextureEntity;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:41:41
 * To change this template use File | Settings | File Templates.
 */
public class TextureEntityMigrator extends AFurnitureCodeMigrator<TextureEntity>
{
    @Override
    public TextureEntity migrate(Priced priced)
    {
        /*TextureEntity textureEntity = new TextureEntity();
        textureEntity.setCode(priced.getCode());
        textureEntity.setGroupIdentifier(priced.getGroupIdentifier());
        textureEntity.setManufacturer(priced.getManufacturer());
        textureEntity.setName(priced.getName());
        textureEntity.setPricedType(priced.getPricedType());
        textureEntity.setPrices(priced.getPrices());
        textureEntity.setId(priced.getId());*/
        TextureEntity textureEntity = FacadeContext.getTextureFacade().findById(priced.getId(), true);

        return textureEntity;
    }
}
