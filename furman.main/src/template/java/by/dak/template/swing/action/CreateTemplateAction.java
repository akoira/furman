package by.dak.template.swing.action;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.MessageDialog;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.template.TemplateOrder;
import by.dak.template.swing.TemplateOrderWizard;

/**
 * User: akoyro
 * Date: 19.03.11
 * Time: 15:23
 */
public class CreateTemplateAction
{

    private Long orderId = 0l;

    public CreateTemplateAction(Long orderId)
    {
        this.orderId = orderId;
    }

    public void action()
    {
        if (!CuttingApp.getApplication().getPermissionManager().checkPermission("createTemplate"))
        {
            return;
        }
        if (orderId != -1)
        {
            Order order = FacadeContext.getOrderFacade().findBy(orderId);
            TemplateOrder templateOrder = FacadeContext.getTemplateOrderFacade().initNewOrderEntity(order.getName());

            final TemplateOrderWizard orderWizard = new TemplateOrderWizard(order.getNumber().getStringValue());
            orderWizard.setOrder(templateOrder);
            orderWizard.setSourceOrder(order);
            DialogShowers.showWizard(orderWizard, orderWizard.getWizardObserver());
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }
}
