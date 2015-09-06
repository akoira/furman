package by.dak.swing.tree;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.def.swing.tree.TypesRootNode;
import by.dak.swing.explorer.ExplorerPanel;
import by.dak.test.TestUtils;

import javax.swing.tree.DefaultTreeModel;

/**
 * User: akoyro
 * Date: 26.11.2009
 * Time: 19:49:28
 */
public class TATreePanel
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        ExplorerPanel panel = new ExplorerPanel();

        DefaultTreeModel treeModel = new DefaultTreeModel(new TypesRootNode());

        panel.getTreePanel().getTree().setModel(treeModel);

        TestUtils.showFrame(panel, "ATreePanel");
    }
}
