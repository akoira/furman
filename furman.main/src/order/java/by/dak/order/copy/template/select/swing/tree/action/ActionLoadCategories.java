package by.dak.order.copy.template.select.swing.tree.action;

import by.dak.category.Category;
import by.dak.order.copy.template.select.swing.SelectPanelController;
import by.dak.order.copy.template.select.swing.tree.ANode;
import by.dak.order.copy.template.select.swing.tree.CategoryNode;

import javax.swing.*;
import java.util.List;

public class ActionLoadCategories extends SwingWorker
{

    private CategoryNode categoryNode;
    private SelectPanelController selectPanelController;

    @Override
    protected Object doInBackground() throws Exception
    {
        final ANode parentNode = categoryNode != null ? categoryNode : selectPanelController.getRootNode();
        Category parent = categoryNode != null ? categoryNode.getValue() : null;
        List<Category> categories = selectPanelController.getMainFacade().getCategoryFacade().loadBy(parent);
        for (Category category : categories)
        {
            final CategoryNode child = new CategoryNode();
            child.setValue(category);
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    selectPanelController.getModel().insertNodeInto(child, parentNode, parentNode.getChildCount());
                }
            };
            SwingUtilities.invokeLater(runnable);

            ActionLoadCategories loadCategories = new ActionLoadCategories();
            loadCategories.setCategoryNode(child);
            loadCategories.setSelectPanelController(selectPanelController);
            loadCategories.doInBackground();

            ActionLoadTemplates loadTemplates = new ActionLoadTemplates();
            loadTemplates.setCategoryNode(child);
            loadTemplates.setSelectPanelController(selectPanelController);
            loadTemplates.doInBackground();

        }
        return null;
    }

    public SelectPanelController getSelectPanelController()
    {
        return selectPanelController;
    }

    public void setSelectPanelController(SelectPanelController selectPanelController)
    {
        this.selectPanelController = selectPanelController;
    }

    public CategoryNode getCategoryNode()
    {
        return categoryNode;
    }

    public void setCategoryNode(CategoryNode categoryNode)
    {
        this.categoryNode = categoryNode;
    }
}
