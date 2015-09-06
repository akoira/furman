package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.MessageDialog;
import by.dak.cutting.swing.ValueSave;
import by.dak.cutting.swing.store.modules.AEntityEditorPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AStoreElement;
import by.dak.persistence.entities.Delivery;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.wizard.DWizardController;
import by.dak.utils.GenericUtils;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Actions для склада
 */
public abstract class ATreeNEDActions<E extends AStoreElement> extends AEntityNEDActions<E>
{
    private StoreElementStatus status;

    @Override
    public void newValue()
    {
        final Delivery delivery = new Delivery();

        final E e = (E) FacadeContext.createNewInstance(GenericUtils.getParameterClass(getClass(), 0));
        delivery.setMaterialType(MaterialType.valueByStoreElementClass(e.getClass()));

        showWizard(delivery, true);
    }


    @Override
    public void openValue()
    {
        if (getSelectedElement() != null)
        {
            E e = (E) FacadeContext.getFacadeBy(getSelectedElement().getClass()).findById(getSelectedElement().getId(), true);
            AEntityEditorPanel panel = DialogShowers.getPanelBy(getSelectedElement());
            panel.setEditable(true);
            panel.setShowOkCancel(true);
            panel.setValueSave(new ValueSave<E>()
            {
                @Override
                public void save(E value)
                {
                    FacadeContext.getFacadeBy(value.getClass()).save(value);
                    firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, value);
                }
            });

            DialogShowers.showBy(panel, getRelatedComponent(), true);
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }

    @Override
    public void deleteValue()
    {
        if (getSelectedElement() != null)
        {
            if (MessageDialog.showConfirmationMessage(MessageDialog.IS_DELETE_RECORD) == JOptionPane.OK_OPTION)
            {
                FacadeContext.getFacadeBy(getSelectedElement().getClass()).delete(getSelectedElement());
                firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
            }
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }


    private void showWizard(final Delivery delivery, boolean editable)
    {
        DWizardController controller = DialogShowers.getWizardControllerBy(delivery, editable, status);

        controller.getProvider().addWizardFinishedListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, delivery);
            }
        });
        DialogShowers.showWizard(controller);
    }

    public StoreElementStatus getStatus()
    {
        return status;
    }

    public void setStatus(StoreElementStatus status)
    {
        this.status = status;
    }
}
