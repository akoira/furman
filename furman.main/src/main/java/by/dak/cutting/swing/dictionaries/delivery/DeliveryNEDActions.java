package by.dak.cutting.swing.dictionaries.delivery;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.MessageDialog;
import by.dak.cutting.swing.store.helpers.AEntityNEDActions;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Delivery;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.wizard.DWizardController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: user0
 * Date: 08.02.2010
 * Time: 13:47:18
 */
public class DeliveryNEDActions extends AEntityNEDActions<Delivery>
{
    @Override
    public void newValue()
    {
        final Delivery delivery = new Delivery();
        showWizard(delivery, true);
    }

    private void showWizard(final Delivery delivery, boolean editable)
    {
        DWizardController controller = DialogShowers.getWizardControllerBy(delivery, editable, StoreElementStatus.exist);

        controller.getProvider().addWizardFinishedListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                firePropertyChange(PROPERTY_updateGui, null, delivery);
            }
        });
        DialogShowers.showWizard(controller);
    }

    @Override
    public void openValue()
    {
        if (getSelectedElement() != null)
        {
            Delivery delivery = FacadeContext.getDeliveryFacade().findById(getSelectedElement().getId(), true);
            showWizard(delivery, false);
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }

}
