package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.Constants;
import by.dak.cutting.swing.store.helpers.FurnitureListUpdater;
import by.dak.cutting.swing.store.helpers.FurnitureTreeNEDActions;
import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.NewEditDeleteActions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.02.2010
 * Time: 16:45:41
 * To change this template use File | Settings | File Templates.
 */
public class FurnituresNode extends AFilterNode<MaterialType, Furniture>
{
    public FurnituresNode(StoreElementStatus status)
    {
        super(MaterialType.furniture, status, new ArrayList());
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return ListUpdaters.createFurnitureListUpdaterBy(getStatus());
    }

    @Override
    protected void initChildren()
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
//        filter.addCriterion(new SearchFilter.DCriterion<Criterion>("status", Restrictions.eq("status", status)));
        filter.addAscOrder("name");

        List<FurnitureType> types = FacadeContext.getFurnitureTypeFacade().loadAll(filter);
        for (FurnitureType type : types)
        {
            add(new FurnitureTypeNode(type, getCriterions()));
        }
    }

    @Override
    public void adjustCriterions()
    {
        SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>("status", Restrictions.eq("status", getStatus()));
        getCriterions().add(criterion);
    }

    public static class FurnitureFilterListUpdater extends AListUpdater<Furniture>
    {
        private FurnitureTreeNEDActions actions = new FurnitureTreeNEDActions();
        private AFilterNode filterNode;

        public FurnitureFilterListUpdater(AFilterNode filterNode)
        {
            this.filterNode = filterNode;
        }

        @Override
        public void adjustFilter()
        {
            getSearchFilter().addAllCriterion(filterNode.getCriterions());
        }

        @Override
        public String[] getVisibleProperties()
        {
            return Constants.FurnitureTableVisibleProperties;
        }

        @Override
        public ResourceMap getResourceMap()
        {
            return Application.getInstance().getContext().getResourceMap(FurnitureListUpdater.class);
        }

        @Override
        public NewEditDeleteActions getNewEditDeleteActions()
        {
            return actions;
        }
    }

}
