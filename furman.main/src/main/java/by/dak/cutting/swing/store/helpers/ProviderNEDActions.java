package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.MessageDialog;
import by.dak.cutting.swing.ValueSave;
import by.dak.cutting.swing.store.modules.MProviderPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Provider;
import by.dak.swing.table.NewEditDeleteActions;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 01.03.2010
 * Time: 15:33:48
 * To change this template use File | Settings | File Templates.
 */
public class ProviderNEDActions extends NewEditDeleteActions<Provider>
{

    @Override
    public void newValue()
    {
        MProviderPanel panel = new MProviderPanel();
        panel.setValue(new Provider());
        panel.setValueSave(new ValueSave<Provider>()
        {
            @Override
            public void save(Provider value)
            {
                FacadeContext.getProviderFacade().save(value);
                firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, value);
            }
        });
        DialogShowers.showBy(panel, getRelatedComponent(), true);
    }

    @Override
    public void openValue()
    {
        if (getSelectedElement() != null)
        {
            MProviderPanel panel = new MProviderPanel();
            panel.setValue(getSelectedElement());
            panel.setEditable(false);
            panel.setShowOkCancel(false);
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
                FacadeContext.getProviderFacade().delete(getSelectedElement());
                firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
            }
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }
}
