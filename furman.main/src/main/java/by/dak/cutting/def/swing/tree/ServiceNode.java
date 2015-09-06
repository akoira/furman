package by.dak.cutting.def.swing.tree;

import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.persistence.entities.Service;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;

/**
 * User: akoyro
 * Date: 07.12.2009
 * Time: 13:54:42
 */
public class ServiceNode extends ATreeNode implements ListUpdaterProvider
{
    public ServiceNode(Service service)
    {
        setUserObject(service);
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return ListUpdaters.createListUpdaterBy((Service) getUserObject());
    }

    @Override
    protected void initChildren()
    {
    }
}
