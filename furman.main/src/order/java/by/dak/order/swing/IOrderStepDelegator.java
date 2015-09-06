package by.dak.order.swing;

import by.dak.persistence.entities.AOrder;
import by.dak.swing.FocusToFirstComponent;

/**
 * User: akoyro
 * Date: 16.03.11
 * Time: 18:44
 */
public interface IOrderStepDelegator<O extends AOrder> extends FocusToFirstComponent
{
    public void setOrder(O order);

    public O getOrder();

    public void setEditable(boolean editable);

    public boolean isEditable();
}
