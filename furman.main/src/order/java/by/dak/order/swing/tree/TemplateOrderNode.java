package by.dak.order.swing.tree;

import by.dak.cutting.DialogShowers;
import by.dak.swing.tree.ATreeNode;
import org.jdesktop.application.Action;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * User: akoyro
 * Date: 11/21/13
 * Time: 8:04 PM
 */
public class TemplateOrderNode extends AOrderNode
{
    private final static String[] ACTIONS = new String[]{"delete"};

    @Override
    public String[] getActions()
    {
        return ACTIONS;
    }

    @Action
    public void delete()
    {
        SwingWorker worker = new SwingWorker()
        {

            @Override
            protected Object doInBackground() throws Exception
            {
                Runnable runnable = new Runnable()
                {
                    public void run()
                    {
                        getTree().setSelectionPath(new TreePath(((ATreeNode) getParent()).getPath()));
                        deleteNode();
                    }
                };
                SwingUtilities.invokeLater(runnable);
                return null;
            }

        };
        DialogShowers.showWaitDialog(worker, getTree());
    }

    private void deleteNode()
    {
        while (this.getChildCount() > 0)
        {
            TreeNode node = this.getFirstChild();
            if (node instanceof TemplateItemNode)
            {
                ((TemplateItemNode) node).deleteNode();
            }
        }
        ((DefaultTreeModel) getTree().getModel()).removeNodeFromParent(this);
    }

    @Override
    protected void initChildren()
    {
    }
}
