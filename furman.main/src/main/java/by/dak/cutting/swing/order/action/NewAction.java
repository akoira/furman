package by.dak.cutting.swing.order.action;

import by.dak.cutting.CuttingView;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.order.wizard.OrderWizard;
import by.dak.persistence.entities.Order;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 11:04
 */
public class NewAction extends AbstractAction
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        CuttingView.OrderCreator orderCreator = new CuttingView.OrderCreator();
        Order order = orderCreator.create();
        final OrderWizard orderWizard = new OrderWizard(order.getNumber().getStringValue());
        orderWizard.setOrder(order);
        DialogShowers.showWizard(orderWizard, orderWizard.getWizardObserver());
    }
}
