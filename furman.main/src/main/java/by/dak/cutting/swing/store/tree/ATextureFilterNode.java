package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.entities.AStoreElement;
import by.dak.persistence.entities.TextureEntity;
import by.dak.swing.table.ListUpdaterProvider;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 24.02.2010
 * Time: 14:49:30
 * To change this template use File | Settings | File Templates.
 */
public abstract class ATextureFilterNode<O extends TextureEntity, E> extends AFilterNode<O, E> implements ListUpdaterProvider
{
    public ATextureFilterNode(O userObject, List<SearchFilter.DCriterion<Criterion>> criterions)
    {
        super(userObject, null, criterions);
    }

    @Override
    protected void initChildren()
    {

    }

    @Override
    public void adjustCriterions()
    {
        SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>(AStoreElement.PROPERTY_priced, Restrictions.eq(AStoreElement.PROPERTY_priced, getUserObject()));
        getCriterions().add(criterion);
    }
}
