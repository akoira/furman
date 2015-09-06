package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.MessageDialog;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.swing.table.NewEditDeleteActions;
import by.dak.swing.wizard.DWizardController;
import by.dak.utils.GenericUtils;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 22.12.2009
 * Time: 22:31:31
 * To change this template use File | Settings | File Templates.
 */
public class AEntityNEDActions<E extends PersistenceEntity> extends NewEditDeleteActions<E>
{
    protected void showWizard()
    {
        DWizardController wizardController = DialogShowers.getWizardControllerBy(getSelectedElement());
        wizardController.getProvider().addWizardFinishedListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                firePropertyChange(PROPERTY_updateGui, null, getSelectedElement());
            }
        });
        DialogShowers.showWizard(wizardController);
    }

    public void newValue()
    {
        E e = (E) FacadeContext.createNewInstance(GenericUtils.getParameterClass(getClass(), 0));
        setSelectedElement(e);
        showWizard();
    }

    public void openValue()
    {
        if (getSelectedElement() != null)
        {
            E e = (E) FacadeContext
                    .getFacadeBy(getSelectedElement().getClass()).findById(getSelectedElement().getId(), true);
            setSelectedElement(e);
            showWizard();
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
                SwingWorker swingWorker = new SwingWorker()
                {
                    @Override
                    protected Object doInBackground() throws Exception
                    {
                        FacadeContext.getFacadeBy(getSelectedElement().getClass()).delete(getSelectedElement());
                        return null;
                    }
                };
                DialogShowers.showWaitDialog(swingWorker, getRelatedComponent());
                firePropertyChange(PROPERTY_updateGui, null, getSelectedElement());
            }
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }

}
