package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.NewEditDeleteActions;

/**
 * User: akoyro
 * Date: 04.04.11
 * Time: 22:36
 */
public class BorderDefListUpdater extends AListUpdater<BorderDefEntity>
{
    private BorderDefNEDActions actions = new BorderDefNEDActions();


    @Override
    public void adjustFilter()
    {
        getSearchFilter().addAscOrder(BorderDefEntity.PROPERTY_name);
    }

    @Override
    public String[] getVisibleProperties()
    {
        return Constants.BORDER_DEF_TABLE_VISIBLE_PROPERTIES;
    }

    @Override
    public NewEditDeleteActions getNewEditDeleteActions()
    {
        return actions;
    }
}
