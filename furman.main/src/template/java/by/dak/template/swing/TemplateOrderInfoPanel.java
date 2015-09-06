package by.dak.template.swing;

import by.dak.cutting.swing.CleanModPanel;
import by.dak.order.swing.IOrderStepDelegator;
import by.dak.template.TemplateOrder;

/**
 * User: akoyro
 * Date: 10/13/13
 * Time: 6:20 PM
 */
public class TemplateOrderInfoPanel extends CleanModPanel implements IOrderStepDelegator<TemplateOrder>
{
    private TemplateOrderTab templateOrderTab;

    public void init()
    {
        templateOrderTab = new TemplateOrderTab();
        templateOrderTab.init();
        tabbedPane.addTab(templateOrderTab.getTitle(), templateOrderTab);
    }

    @Override
    public void setOrder(TemplateOrder order)
    {
        templateOrderTab.setValue(order);
    }

    @Override
    public TemplateOrder getOrder()
    {
        return templateOrderTab.getValue();
    }

    @Override
    public void setFocusToFirstComponent()
    {
        templateOrderTab.setFocusToFirstComponent();
    }
}
