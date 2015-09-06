package by.dak.cutting.def.swing.tree;

import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

/**
 * User: akoyro
 * Date: 01.12.2009
 * Time: 20:54:26
 */
public class PriceAwareNode extends ATreeNode implements ListUpdaterProvider
{
    public PriceAwareNode(PriceAware userObject)
    {
        super(userObject);
    }

    @Override
    protected void initChildren()
    {
        PriceAware priceAware = (PriceAware) getUserObject();
        List<PriceEntity> list = FacadeContext.getPriceFacade().findAllBy(priceAware);
        for (PriceEntity priceEntity : list)
        {
            add(new DefaultMutableTreeNode(priceEntity, false));
        }
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return ListUpdaters.createListUpdaterBy((PriceAware) getUserObject());
    }
}
