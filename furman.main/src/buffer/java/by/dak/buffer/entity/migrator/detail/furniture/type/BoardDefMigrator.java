package by.dak.buffer.entity.migrator.detail.furniture.type;

import by.dak.buffer.entity.migrator.detail.furniture.AFurnitureTypeMigrator;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.PriceAware;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:37:13
 * To change this template use File | Settings | File Templates.
 */
public class BoardDefMigrator extends AFurnitureTypeMigrator<BoardDef>
{

    @Override
    public BoardDef migrate(PriceAware priceAware)
    {
        BoardDef boardDef = FacadeContext.getBoardDefFacade().findById(priceAware.getId(),true);
        return boardDef;
    }
}
