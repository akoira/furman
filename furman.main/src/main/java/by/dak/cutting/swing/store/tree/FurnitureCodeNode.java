package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.swing.table.ListUpdater;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * User: akoyro
 * Date: 13.05.2010
 * Time: 19:40:09
 */
public class FurnitureCodeNode extends AFilterNode<FurnitureCode, Furniture>
{

    public FurnitureCodeNode(FurnitureCode code, List<SearchFilter.DCriterion<Criterion>> criterions)
    {
        super(code, null, criterions);
    }

    @Override
    public void adjustCriterions()
    {
        SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>(Furniture.PROPERTY_priced, Restrictions.eq(Furniture.PROPERTY_priced, getUserObject()));
        getCriterions().add(criterion);
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return new FurnituresNode.FurnitureFilterListUpdater(this);
    }

    @Override
    protected void initChildren()
    {
    }
}