package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Border;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.table.ListUpdater;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.02.2010
 * Time: 18:00:32
 * To change this template use File | Settings | File Templates.
 */
public class BordersNode extends AFilterNode<MaterialType, Border>
{
    public BordersNode(StoreElementStatus status)
    {
        super(MaterialType.border, status, new ArrayList());
    }


    @Override
    public ListUpdater getListUpdater()
    {
        return ListUpdaters.createBorderListUpdaterBy(getStatus());
    }

    @Override
    protected void initChildren()
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.addAscOrder("name");
        List<BorderDefEntity> borderDefs = FacadeContext.getBorderDefFacade().loadAll(filter);

        for (BorderDefEntity borderDef : borderDefs)
        {
            add(new BorderDefNode(borderDef, getCriterions()));
        }
    }

    @Override
    public void adjustCriterions()
    {
        SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>("status", Restrictions.eq("status", getStatus()));
        getCriterions().add(criterion);

    }
}
