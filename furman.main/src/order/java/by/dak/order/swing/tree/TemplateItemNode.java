package by.dak.order.swing.tree;

import by.dak.cutting.DialogShowers;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.OrderItem;
import by.dak.swing.tree.ATreeNode;
import org.jdesktop.application.Action;
import org.jdesktop.application.ApplicationContext;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * User: akoyro
 * Date: 22.03.11
 * Time: 18:27
 */
public class TemplateItemNode extends OrderItemNode
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
        SwingWorker runnable = new SwingWorker()
        {

            @Override
            protected Object doInBackground() throws Exception
            {
                getTree().setSelectionPath(new TreePath(((ATreeNode) getParent()).getPath()));
                deleteNode();
                return null;
            }

        };
        DialogShowers.showWaitDialog(runnable, getTree());
    }

    public void deleteNode()
    {
        FacadeContext.getOrderItemFacade().delete(getOrderItem());
        System.out.println("tree == " + getTree());
        System.out.println("tree == " + getTree().getModel());
        ((DefaultTreeModel) getTree().getModel()).removeNodeFromParent(TemplateItemNode.this);
    }

    public static TemplateItemNode valueOf(OrderItem orderItem, ApplicationContext context)
    {
        TemplateItemNode node = new TemplateItemNode();
        node.setOrderItem(orderItem);
        node.setContext(context);
        node.init();
        return node;
    }
}
