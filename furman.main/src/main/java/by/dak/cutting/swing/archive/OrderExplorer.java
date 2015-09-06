package by.dak.cutting.swing.archive;

import by.dak.cutting.swing.archive.tree.RootNode;
import by.dak.swing.explorer.AExplorerPanel;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 11:11
 */
public class OrderExplorer extends AExplorerPanel
{

    public OrderExplorer()
    {
        OrderStatusManager orderStatusManager = new OrderStatusManager(this);
        final DefaultTreeModel defaultTreeModel = new DefaultTreeModel(new RootNode(orderStatusManager));
        getTreePanel().getTree().setModel(defaultTreeModel);
        getTreePanel().getTree().setRootVisible(true);

        addHierarchyListener(new HierarchyListener()
        {
            @Override
            public void hierarchyChanged(HierarchyEvent e)
            {
                if ((e.getChangeFlags() & HierarchyEvent.DISPLAYABILITY_CHANGED) > 0 && OrderExplorer.this.isVisible())
                {
                    getTreePanel().getTree().setSelectionPath(new TreePath(defaultTreeModel.getRoot()));
                }
            }
        });
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
