package by.dak.cutting.swing.order.tree;

import by.dak.cutting.SpringConfiguration;
import by.dak.swing.explorer.ExplorerPanel;
import by.dak.test.TestUtils;

import javax.swing.tree.DefaultTreeModel;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 19.02.2010
 * Time: 13:52:29
 * To change this template use File | Settings | File Templates.
 */
public class TOrderTree
{
    public static void main(String[] args)
    {
        new SpringConfiguration();

        ExplorerPanel panel = new ExplorerPanel();

        DefaultTreeModel model = new DefaultTreeModel(new OrderRootNode());

        panel.getTreePanel().getTree().setModel(model);

        TestUtils.showFrame(panel, "TOrderPanel");
    }
}
