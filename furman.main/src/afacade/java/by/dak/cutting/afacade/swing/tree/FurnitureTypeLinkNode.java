package by.dak.cutting.afacade.swing.tree;

import by.dak.cutting.afacade.FurnitureTypeLink;
import by.dak.cutting.def.swing.tree.PriceAwareNode;
import by.dak.cutting.swing.store.helpers.AFurnitureTypeListUpdater;
import by.dak.lang.Named;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 07.08.2010
 * Time: 14:00:20
 */
public class FurnitureTypeLinkNode<E extends Named> extends ATreeNode implements ListUpdaterProvider
{
    private AFurnitureTypeListUpdater<FurnitureType> listUpdater = new AFurnitureTypeListUpdater<FurnitureType>()
    {
        @Override
        public void adjustFilter()
        {
            super.adjustFilter();
            getSearchFilter().eq(FurnitureType.PROPERTY_childLinks + "." + FurnitureTypeLink.PROPERTY_keyword, getType().name());
        }
    };

    public FurnitureTypeLinkNode(E type)
    {
        super(type);
    }

    public E getType()
    {
        return ((E) getUserObject());
    }


    @Override
    protected void initChildren()
    {
        List<FurnitureType> types = FacadeContext.getFurnitureTypeFacade().findChildTypesBy(getType().name());
        for (FurnitureType furnitureType : types)
        {
            PriceAwareNode node = new PriceAwareNode(furnitureType);
            add(node);
        }
    }


    @Override
    public ListUpdater getListUpdater()
    {
        return listUpdater;
    }
}
