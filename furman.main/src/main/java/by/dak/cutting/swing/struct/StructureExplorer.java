package by.dak.cutting.swing.struct;

import by.dak.cutting.swing.struct.tree.RootNode;
import by.dak.swing.explorer.AExplorerPanel;

import javax.swing.tree.DefaultTreeModel;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 20:44
 */
public class StructureExplorer extends AExplorerPanel
{
    public StructureExplorer()
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
