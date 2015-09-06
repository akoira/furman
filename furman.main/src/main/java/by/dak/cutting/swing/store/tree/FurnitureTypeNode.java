package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.Constants;
import by.dak.cutting.swing.store.helpers.FurnitureListUpdater;
import by.dak.cutting.swing.store.helpers.FurnitureTreeNEDActions;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.NewEditDeleteActions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.util.List;

/**
 * User: akoyro
 * Date: 13.05.2010
 * Time: 19:40:09
 */
public class FurnitureTypeNode extends AFilterNode<FurnitureType, Furniture>
{
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

    public FurnitureTypeNode(FurnitureType type, List<SearchFilter.DCriterion<Criterion>> criterions)
    {
        super(type, null, criterions);
    }

    @Override
    public void adjustCriterions()
    {
        SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>(Furniture.PROPERTY_priceAware, Restrictions.eq(Furniture.PROPERTY_priceAware, getUserObject()));
        getCriterions().add(criterion);
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return new FurnitureFilterListUpdater(this);
    }

    @Override
    protected void initChildren()
    {
        List<FurnitureCode> codes = FacadeContext.getFurnitureCodeFacade().findBy((FurnitureType) getUserObject());
        for (FurnitureCode code : codes)
        {
            FurnitureCodeNode node = new FurnitureCodeNode(code, getCriterions());
            add(node);
        }
    }

    @Action
    public void addFilter()
    {

    }
}
