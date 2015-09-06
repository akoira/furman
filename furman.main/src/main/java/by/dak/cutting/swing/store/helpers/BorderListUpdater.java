package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.entities.Border;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.table.NewEditDeleteActions;

/**
 * User: akoyro
 * Date: 04.04.11
 * Time: 22:31
 */
public class BorderListUpdater extends ListUpdaters.AStoreElementListUpdater<Border>
{
    public BorderListUpdater(StoreElementStatus status)
    {
        super(status);
    }

    private BorderTreeNEDActions actions;

    @Override
    public NewEditDeleteActions getNewEditDeleteActions()
    {
        if (actions == null)
        {
            actions = new BorderTreeNEDActions();
            actions.setStatus(getStatus());
        }
        return actions;
    }

    public void adjustFilter()
    {
        getSearchFilter().eq("status", getStatus());
        getSearchFilter().addAscOrder("priceAware.name");
    }


    @Override
    public String[] getVisibleProperties()
    {
        if (getStatus() == StoreElementStatus.order)
        {
            return Constants.ORDERED_BORDER_TABLE_VISIBLE_PROPERTIES;
        }
        else
        {
            return Constants.BORDER_TABLE_VISIBLE_PROPERTIES;
        }
    }


}
