package by.dak.cutting.swing.store.helpers;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import org.jdesktop.application.Action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 16.02.2010
 * Time: 17:02:46
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureTreeNEDActions extends ATreeNEDActions<Furniture>
{

    public FurnitureTreeNEDActions()
    {
//        ActionMap actionMap = Application.getInstance().getContext().getActionMap(FurnitureTreeNEDActions.class, this);
//        actionMap.setParent(getActionMap());
//        setActionMap(actionMap);

        String[] actions = new String[]{"newValue", "openValue", "deleteValue"};
        setActions(actions);
    }

    @Action
    public void replaceValue()
    {
        if (getSelectedElement() != null &&
                getSelectedElement().getStatus() == StoreElementStatus.order &&
                getSelectedElement().getOrder() != null &&
                FacadeContext.getFurnitureLinkFacade().findAllBy(getSelectedElement()).size() > 0)
        {
            ReplaceHelper replaceHelper = new ReplaceHelper();
            replaceHelper.setSource(getSelectedElement());
            replaceHelper.setRelatedComponent(getRelatedComponent());
            replaceHelper.init();
            replaceHelper.addPropertyChangeListener(AEntityNEDActions.PROPERTY_updateGui, new PropertyChangeListener()
            {
                @Override
                public void propertyChange(PropertyChangeEvent evt)
                {
                    firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null,getSelectedElement());
                }
            });
            replaceHelper.show();
        }
    }

    @Override
    public void setStatus(StoreElementStatus status)
    {
        super.setStatus(status);
        if (getStatus() != null && getStatus() == StoreElementStatus.order)
        {
            setActions(new String[]{"newValue", "openValue", "deleteValue", "replaceValue"});
        }
    }
}
