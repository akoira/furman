package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.CuttingView;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.MessageDialog;
import by.dak.cutting.swing.order.wizard.OrderWizard;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 23.02.2010
 * Time: 13:58:20
 * To change this template use File | Settings | File Templates.
 */
public class OrderNEDActions extends AEntityNEDActions<Order>
{
    @Override
    public void deleteValue()
    {
        if (getSelectedElement() != null)
        {
            if (MessageDialog.showConfirmationMessage(MessageDialog.IS_DELETE_RECORD) == JOptionPane.OK_OPTION)
            {
                FacadeContext.getOrderFacade().delete(getSelectedElement());
                firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
            }
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }

    @Override
    public void newValue()
    {
        CuttingView.OrderCreator orderCreator = new CuttingView.OrderCreator();
        Order entity = orderCreator.create();
        final OrderWizard orderWizard = new OrderWizard(entity.getNumber().getStringValue());
        orderWizard.setOrder(entity);
        DialogShowers.showWizard(orderWizard, orderWizard.getWizardObserver());

        firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, entity);
    }

    @Override
    public void openValue()
    {
        if (getSelectedElement() != null)
        {
            Order order = FacadeContext.getOrderFacade().findBy(getSelectedElement().getId());
            order.setOrderItems(FacadeContext.getOrderItemFacade().loadBy(order));
            final OrderWizard orderWizard = new OrderWizard(order.getNumber().getStringValue());
            orderWizard.setOrder(order);
            DialogShowers.showWizard(orderWizard, orderWizard.getWizardObserver());

            firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }

}
