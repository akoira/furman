package by.dak.cutting.agt.swing.tree;

import by.dak.cutting.agt.AGTType;
import by.dak.cutting.swing.store.Constants;
import by.dak.cutting.swing.store.modules.FurnitureTypePanel;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.NewEditDeleteActions;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * User: akoyro
 * Date: 19.07.2010
 * Time: 21:35:51
 */
public class AGTTypeListUpdater extends AListUpdater<AGTType>
{
    private AGTTypeNEDActions actions = new AGTTypeNEDActions();

    @Override
    public void adjustFilter()
    {
        getSearchFilter().addAscOrder(AGTType.PROPERTY_name);
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
