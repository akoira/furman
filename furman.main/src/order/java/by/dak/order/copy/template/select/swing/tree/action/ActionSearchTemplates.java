package by.dak.order.copy.template.select.swing.tree.action;

import by.dak.cutting.SearchFilter;
import by.dak.order.copy.template.select.swing.SelectPanelController;
import by.dak.order.copy.template.select.swing.tree.ANode;
import by.dak.order.copy.template.select.swing.tree.RootNode;
import by.dak.order.copy.template.select.swing.tree.TemplateOrderNode;
import by.dak.template.TemplateOrder;

import javax.swing.*;
import java.util.List;

public class ActionSearchTemplates extends SwingWorker
{
    private String searchText;
    private SelectPanelController selectPanelController;

    @Override
    protected Object doInBackground() throws Exception
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.ilike(TemplateOrder.PROPERTY_name, String.format("%%%s%%", searchText.toLowerCase()));
        filter.addAscOrder(TemplateOrder.PROPERTY_name);
        List<TemplateOrder> templateOrders = selectPanelController.getMainFacade().getTemplateOrderFacade().loadAll(filter);

        final RootNode rootNode = selectPanelController.getRootNode();

        Runnable runnable = new Runnable()
        {
            public void run()
            {
                int count = rootNode.getChildCount();
                for (int i = 0; i < count; i++)
                {
                    selectPanelController.getModel().removeNodeFromParent((ANode) rootNode.getChildAt(0));
                }
            }
        };
        SwingUtilities.invokeLater(runnable);

        for (TemplateOrder templateOrder : templateOrders)
        {
            final TemplateOrderNode node = new TemplateOrderNode();
            node.setValue(templateOrder);
            runnable = new Runnable()
            {
                public void run()
                {
                    selectPanelController.getModel().insertNodeInto(node, rootNode, rootNode.getChildCount());
                }
            };
            SwingUtilities.invokeLater(runnable);
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

    public void setSearchText(String searchText)
    {
        this.searchText = searchText;
    }
}
