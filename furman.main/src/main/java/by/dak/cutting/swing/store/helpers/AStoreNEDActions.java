package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.MessageDialog;
import by.dak.cutting.swing.ValueSave;
import by.dak.cutting.swing.store.modules.AEntityEditorPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.ReservedSaver;
import by.dak.persistence.entities.AStoreElement;
import by.dak.persistence.entities.Delivery;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.utils.GenericUtils;

import javax.swing.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 20.03.2010
 * Time: 17:32:53
 * To change this template use File | Settings | File Templates.
 */
public abstract class AStoreNEDActions<E extends AStoreElement> extends AEntityNEDActions<E>
{
    private List<E> list;
    private ReservedSaver reservedSaver;
    private Delivery delivery;

    @Override
    public void newValue()
    {
        final E e = (E) FacadeContext.createNewInstance(GenericUtils.getParameterClass(getClass(), 0));

        if (delivery != null)
        {
            e.setDelivery(delivery);
            e.setProvider(delivery.getProvider());
            e.setStatus(StoreElementStatus.exist);
        }
        AEntityEditorPanel panel = DialogShowers.getPanelBy(e);

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
                list.add(value);
                getReservedSaver().add(value);
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
            panel.setValueSave(new ValueSave<E>()
            {
                @Override
                public void save(E value)
                {
                    getReservedSaver().add(value);
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
                list.remove(getSelectedElement());
                getReservedSaver().remove(getSelectedElement());
                firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
            }
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }

    public void setDelivery(Delivery delivery)
    {
        this.delivery = delivery;
    }

    public ReservedSaver getReservedSaver()
    {
        return reservedSaver;
    }

    public void setReservedSaver(ReservedSaver reservedSaver)
    {
        this.reservedSaver = reservedSaver;
    }

    public List<E> getList()
    {
        return list;
    }

    public void setList(List<E> list)
    {
        this.list = list;
    }


}
