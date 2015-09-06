package by.dak.delivery.swing;

import by.dak.delivery.swing.tree.RootNode;
import by.dak.swing.explorer.AExplorerPanel;

import javax.swing.tree.DefaultTreeModel;

/**
 * User: akoyro
 * Date: 06.04.11
 * Time: 11:44
 */
public class DeliveryExplorer extends AExplorerPanel
{
    public DeliveryExplorer()
    {
        final DefaultTreeModel defaultTreeModel = new DefaultTreeModel(new RootNode());
        getTreePanel().getTree().setModel(defaultTreeModel);
        getTreePanel().getTree().setRootVisible(true);
    }

    @Override
    protected void save()
    {
    }

    @Override
    protected boolean validateCurrentContent()
    {
        return true;
    }
}
