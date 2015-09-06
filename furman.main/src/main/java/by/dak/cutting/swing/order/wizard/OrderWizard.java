/*
 * To change this template, choose Tools | Templates and open the template in the draw.
 */
package by.dak.cutting.swing.order.wizard;

import by.dak.cutting.swing.order.NewOrderPanel;
import by.dak.order.swing.AOrderWizard;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.swing.wizard.Step;
import org.netbeans.spi.wizard.Wizard;

import java.util.Map;

/**
 * @author admin
 */
public class OrderWizard extends AOrderWizard<NewOrderPanel, Order>
{
    private OrderInfoStep orderInfoStep = new OrderInfoStep();

    public OrderWizard(String orderName)
    {
        super(orderName, OrderWizard.class);
    }

    @Override
    protected Step<NewOrderPanel> getInfoStep()
    {
        return orderInfoStep;
    }

    private class OrderInfoStep extends Step<NewOrderPanel>
    {

        public OrderInfoStep()
        {
            super(new NewOrderPanel());
        }

        @Override
        protected boolean validate(Map map, Wizard wizard)
        {
            return getView().validateData();
        }

        @Override
        protected void proceedNext(Map map, Wizard wizard)
        {
            FacadeContext.getOrderFacade().save(getView().getOrder());
            getItemsStep().setOrder(getView().getOrder());
        }

        @Override
        protected void remainOnPage(Map map, Wizard wizard)
        {
            getView().setFocusToFirstComponent();
        }
    }
}
