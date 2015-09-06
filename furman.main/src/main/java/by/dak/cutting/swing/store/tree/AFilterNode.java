package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;
import by.dak.utils.GenericUtils;
import org.hibernate.criterion.Criterion;
import org.jdesktop.application.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 22.02.2010
 * Time: 15:36:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class AFilterNode<O, E> extends ATreeNode implements ListUpdaterProvider
{
    private StoreElementStatus status;
    protected List<SearchFilter.DCriterion<Criterion>> criterions = new ArrayList<SearchFilter.DCriterion<Criterion>>();

    public AFilterNode(O userObject, StoreElementStatus status, List<SearchFilter.DCriterion<Criterion>> criterions)
    {
        this.status = status;
        setUserObject(userObject);
        this.criterions.addAll(criterions);
        adjustCriterions();
    }

    public O getEntity()
    {
        return (O) getUserObject();
    }

    public List<SearchFilter.DCriterion<Criterion>> getCriterions()
    {
        return criterions;
    }

    public abstract void adjustCriterions();

    public boolean hasChildren()
    {
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.addAllCriterion(getCriterions());
        return FacadeContext.getFacadeBy(GenericUtils.getParameterClass(this.getClass(), 1)).getCount(searchFilter) > 0;
    }


    public void add(AFilterNode newChild)
    {
        if (newChild.hasChildren())
        {
            super.add(newChild);
        }
    }

    public StoreElementStatus getStatus()
    {
        return status;
    }

    @Action
    public void addFilter()
    {

    }

    @Override
    public String[] getActions()
    {
        return new String[]{"addFilter"};
    }
}
