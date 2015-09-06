package by.dak.delivery.swing.tree;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.tree.EntityNEDActions;
import by.dak.persistence.entities.Delivery;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.wizard.DWizardController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 21:30
 */
public class DeliveryNEDActions extends EntityNEDActions<Delivery>
{
    public DeliveryNEDActions()
    {
        super(Delivery.class);
    }

    protected void showPanel(final Delivery e)
    {
        DWizardController controller = DialogShowers.getWizardControllerBy(e, !e.hasId(), StoreElementStatus.exist);

        controller.getProvider().addWizardFinishedListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                firePropertyChange(PROPERTY_updateGui, null, e);
            }
        });
        DialogShowers.showWizard(controller);
    }

}
