package by.dak.order.copy.template.select.swing.tree.action;

import by.dak.order.copy.template.select.swing.SelectPanelController;
import by.dak.order.copy.template.select.swing.tree.CategoryNode;
import by.dak.order.copy.template.select.swing.tree.TemplateOrderNode;
import by.dak.template.TemplateOrder;
import by.dak.template.facade.TemplateOrderFacade;

import javax.swing.*;
import java.util.List;

public class ActionLoadTemplates extends SwingWorker
{

    private CategoryNode categoryNode;
    private SelectPanelController selectPanelController;

    @Override
    protected Object doInBackground() throws Exception
    {
        List<TemplateOrder> templateOrders = selectPanelController.getMainFacade().getApplicationContext().
                getBean(TemplateOrderFacade.class).loadAllBy(categoryNode.getValue());
        for (TemplateOrder templateOrder : templateOrders)
        {
            final TemplateOrderNode child = new TemplateOrderNode();
            child.setValue(templateOrder);
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    selectPanelController.getModel().insertNodeInto(child, categoryNode, categoryNode.getChildCount());
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        return null;
    }

    public CategoryNode getCategoryNode()
    {
        return categoryNode;
    }

    public void setCategoryNode(CategoryNode categoryNode)
    {
        this.categoryNode = categoryNode;
    }

    public SelectPanelController getSelectPanelController()
    {
        return selectPanelController;
    }

    public void setSelectPanelController(SelectPanelController selectPanelController)
    {
        this.selectPanelController = selectPanelController;
    }
}
