package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.swing.MessageDialog;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Service;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 10.02.2010
 * Time: 14:29:49
 * To change this template use File | Settings | File Templates.
 */
public class ServiceNEDActions extends AEntityNEDActions<Service>
{

    @Override
    public void newValue()
    {
        super.newValue();
        Service service = getSelectedElement();
        service.setManufacturer(FacadeContext.getManufacturerFacade().getDefault());
    }

    @Override
    public void openValue()
    {
        if (getSelectedElement() != null)
        {
            Service e = (Service) FacadeContext
                    .getServiceFacade().findById(getSelectedElement().getId(), true);
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
                FacadeContext.getServiceFacade().delete(getSelectedElement());
                firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
            }
        }
        else
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
    }
}
