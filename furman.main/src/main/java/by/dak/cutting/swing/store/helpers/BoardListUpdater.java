package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.table.NewEditDeleteActions;

/**
 * User: akoyro
 * Date: 04.04.11
 * Time: 22:21
 */
public class BoardListUpdater extends ListUpdaters.AStoreElementListUpdater<Board>
{
    private BoardTreeNEDActions actions;

    public BoardListUpdater(StoreElementStatus status)
    {
        super(status);
    }

    @Override
    public NewEditDeleteActions getNewEditDeleteActions()
    {
        if (actions == null)
        {
            actions = new BoardTreeNEDActions();
            actions.setStatus(getStatus());
        }
        return actions;
    }

    public void adjustFilter()
    {
        getSearchFilter().eq(Board.PROPERTY_status, getStatus());
    }

    @Override
    public String[] getVisibleProperties()
    {
        if (getStatus() == StoreElementStatus.order)
        {
            return Constants.ORDER_BOARD_TABLE_VISIBLE_PROPERTIES;
        }
        else
        {
            return Constants.BOARD_TABLE_VISIBLE_PROPERTIES;
        }
    }


}
