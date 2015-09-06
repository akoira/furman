package by.dak.cutting.swing.order.action;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.order.wizard.OrderWizard;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 11:07
 */
public class OpenAction extends AbstractAction
{
    private long id;

    public OpenAction(long id)
    {
        this.id = id;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Order order = FacadeContext.getOrderFacade().findBy(id);
        final OrderWizard orderWizard = new OrderWizard(order.getNumber().getStringValue());
        orderWizard.setOrder(order);
        DialogShowers.showWizard(orderWizard, orderWizard.getWizardObserver());
    }
}
