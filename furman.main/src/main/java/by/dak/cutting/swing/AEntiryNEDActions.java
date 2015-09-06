package by.dak.cutting.swing;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.store.helpers.AEntityNEDActions;
import by.dak.cutting.swing.store.modules.AEntityEditorPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.swing.table.NewEditDeleteActions;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 20.03.2010
 * Time: 17:32:53
 * To change this template use File | Settings | File Templates.
 */
public abstract class AEntiryNEDActions<E extends PersistenceEntity> extends NewEditDeleteActions<E>
{
    @Override
    public void newValue()
    {
        E e = FacadeContext.createNewInstance(getEntityClass());

        AEntityEditorPanel panel = DialogShowers.getPanelBy(e);
        panel.setEditable(true);
        panel.setShowOkCancel(true);
        setSelectedElement(e);
        panel.setValueSave(getValueSave());

        DialogShowers.showBy(panel, getRelatedComponent(), true);
    }

    protected ValueSave getValueSave()
    {
        ValueSave valueSave = new ValueSave<E>()
        {
            @Override
            public void save(E value)
            {
                FacadeContext.getFacadeBy(getEntityClass()).save(value);
                firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, value);
            }
        };
        return valueSave;
    }

    @Override
    public void openValue()
    {
        if (getSelectedElement() != null)
        {
            AEntityEditorPanel panel = DialogShowers.getPanelBy(getSelectedElement());
            panel.setEditable(true);
            panel.setShowOkCancel(true);
            panel.setValueSave(getValueSave());
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
                FacadeContext.getFacadeBy(getEntityClass()).delete(getSelectedElement());
                firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
            }
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }
}
