package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.entities.BoardDef;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.NewEditDeleteActions;

/**
 * User: akoyro
 * Date: 04.04.11
 * Time: 22:35
 */
public class BoardDefListUpdater extends AListUpdater<BoardDef>
{
    private BoardDefNEDActions actions = new BoardDefNEDActions();

    @Override
    public void adjustFilter()
    {
        getSearchFilter().addColumnOrder(new SearchFilter.DCriterion<org.hibernate.criterion.Order>("name", org.hibernate.criterion.Order.asc("name")));
    }

    @Override
    public String[] getVisibleProperties()
    {
        return Constants.BOARD_DEF_TABLE_VISIBLE_PROPERTIES;
    }

    @Override
    public NewEditDeleteActions getNewEditDeleteActions()
    {
        return actions;
    }

    @Override
    public String[] getHiddenProperties()
    {
        return new String[]{"id"};
    }
}
