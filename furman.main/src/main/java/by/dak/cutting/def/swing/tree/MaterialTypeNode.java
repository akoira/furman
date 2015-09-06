package by.dak.cutting.def.swing.tree;

import by.dak.cutting.agt.swing.tree.AGTTypeListUpdater;
import by.dak.cutting.facade.BaseFacade;
import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.cutting.zfacade.swing.tree.ZProfileTypeListUpdater;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 26.11.2009
 * Time: 23:03:24
 */
public class MaterialTypeNode extends ATreeNode implements ListUpdaterProvider
{
    @Override
    protected void initChildren()
    {
        BaseFacade facade = FacadeContext.getMaterialTypeHelper().getTypeFacadeBy((MaterialType) getUserObject());
        if (facade != null)
        {
            List<PersistenceEntity> list = facade.loadAllSortedBy("name");
            for (PersistenceEntity entity : list)
            {
                add(new PriceAwareNode((PriceAware) entity));
            }
        }
    }

    @Override
    public ListUpdater getListUpdater()
    {
        MaterialType type = (MaterialType) getUserObject();
        switch (type)
        {
            case board:
                return ListUpdaters.BOARD_DEF_LIST_UPDATER;
            case border:
                return ListUpdaters.BORDER_DEF_LIST_UPDATER;
            case furniture:
                return ListUpdaters.FurnitureTypeListUpdater;
            case zprofile:
                return new ZProfileTypeListUpdater();
            case agtprofile:
                return new AGTTypeListUpdater();
            default:
                throw new IllegalArgumentException();
        }
    }
}
