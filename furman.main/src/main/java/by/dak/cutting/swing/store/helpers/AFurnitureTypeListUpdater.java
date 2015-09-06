package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.swing.store.Constants;
import by.dak.cutting.swing.store.modules.FurnitureTypePanel;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.NewEditDeleteActions;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * User: akoyro
 * Date: 08.08.2010
 * Time: 15:47:31
 */
public abstract class AFurnitureTypeListUpdater<E extends FurnitureType> extends AListUpdater<E>
{
    private FurnitureTypeNEDActions actions = new FurnitureTypeNEDActions();

    @Override
    public void adjustFilter()
    {
        getSearchFilter().addAscOrder(FurnitureType.PROPERTY_name);
    }

    @Override
    public String[] getVisibleProperties()
    {
        return Constants.FurnitureTypeTableVisibleProperties;
    }

    @Override
    public NewEditDeleteActions getNewEditDeleteActions()
    {
        return actions;
    }

    @Override
    public ResourceMap getResourceMap()
    {
        return Application.getInstance().getContext().getResourceMap(FurnitureTypePanel.class);
    }
}
