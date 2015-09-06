package by.dak.ordergroup.swing;

import by.dak.cutting.swing.store.helpers.AEntityNEDActions;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;

/**
 * User: akoyro
 * Date: 17.01.2011
 * Time: 17:12:04
 */
public class OrderGroupActions extends AEntityNEDActions<OrderGroup>
{
    @Override
    public void newValue()
    {
        OrderGroup e = FacadeContext.getOrderGroupFacade().createOrderGroup();
        setSelectedElement(e);
        showWizard();
    }
}
