package by.dak.order.copy.template.select.swing.tree.action;

import by.dak.order.copy.template.select.swing.SelectPanelController;
import by.dak.template.TemplateOrder;

import javax.swing.*;

public class ActionSelectTemplateOrder extends SwingWorker
{

    private SelectPanelController selectPanelController;
    private TemplateOrder templateOrder;


    @Override
    protected Object doInBackground() throws Exception
    {

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

    public TemplateOrder getTemplateOrder()
    {
        return templateOrder;
    }

    public void setTemplateOrder(TemplateOrder templateOrder)
    {
        this.templateOrder = templateOrder;
    }
}
