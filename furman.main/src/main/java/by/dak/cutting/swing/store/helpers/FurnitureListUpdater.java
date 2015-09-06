package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.table.ListNaviTable;
import by.dak.swing.table.NewEditDeleteActions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * User: akoyro
 * Date: 04.04.11
 * Time: 22:28
 */
public class FurnitureListUpdater extends ListUpdaters.AStoreElementListUpdater<Furniture>
{
    private FurnitureTreeNEDActions actions;

    public FurnitureListUpdater(StoreElementStatus status)
    {
        super(status);
    }

    @Override
    public void initAdditionalTableControls(ListNaviTable
                                                    listNaviTable)
    {
        super.initAdditionalTableControls(listNaviTable);
    }

    @Override
    public NewEditDeleteActions getNewEditDeleteActions()
    {
        if (actions == null)
        {
            actions = new FurnitureTreeNEDActions();
            actions.setStatus(getStatus());
        }
        return actions;
    }

    public void adjustFilter()
    {
        getSearchFilter().addCriterion(new SearchFilter.DCriterion<Criterion>("status", Restrictions.eq("status", getStatus())));
        getSearchFilter().addAscOrder("priceAware.name");
        getSearchFilter().addAscOrder("priced.name");
    }

    @Override
    public String[] getVisibleProperties()
    {
        if (getStatus() == StoreElementStatus.order)
        {
            return Constants.OrderedFurnitureTableVisibleProperties;
        }
        else
        {
            return Constants.FurnitureTableVisibleProperties;
        }
    }

}
