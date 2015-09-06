package by.dak.template.swing.tree;

import by.dak.category.Category;
import by.dak.cutting.DialogShowers;
import by.dak.persistence.FacadeContext;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;
import by.dak.template.TemplateOrder;
import org.jdesktop.application.Action;
import org.jdesktop.application.ApplicationContext;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

/**
 * User: akoyro
 * Date: 25.03.11
 * Time: 13:10
 */
public class CategoryNode extends ACategoryNode implements ListUpdaterProvider<TemplateOrder>
{
    private static final String[] ACTIONS = new String[]{"delete", "print"};

    public CategoryNode()
    {
        setAllowsChildren(true);
    }

    @Override
    protected void initChildren()
    {
    }

    @Override
    public String[] getActions()
    {
        return ACTIONS;
    }

    @Action
    public void delete()
    {
        ATreeNode parent = (ATreeNode) getParent();
        removeFromParent();
        if (getTree() != null)
        {
            ((DefaultTreeModel) getTree().getModel()).reload(parent);
            SwingWorker swingWorker = new SwingWorker()
            {
                @Override
                protected Object doInBackground() throws Exception
                {
                    if (getCategory().hasId())
                    {
                        FacadeContext.getCategoryFacade().delete(getCategory());
                    }
                    return null;
                }
            };
            DialogShowers.showWaitDialog(swingWorker, getTree());
        }
    }

    public static CategoryNode valueOf(Category category, ApplicationContext context)
    {
        CategoryNode categoryNode = new CategoryNode();
        categoryNode.setCategory(category);
        categoryNode.setContext(context);
        categoryNode.init();
        return categoryNode;
    }
}
