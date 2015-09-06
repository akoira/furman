package by.dak.cutting;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.util.HashMap;
import java.util.List;

/**
 * User: akoyro
 * Date: 23.08.2009
 * Time: 21:06:59
 */
public class CriteriaFiller
{
    private Criteria rootCriteria;
    private SearchFilter searchFilter;
    private HashMap<String, Criteria> map = new HashMap<String, Criteria>();

    public CriteriaFiller(Criteria rootCriteria, SearchFilter searchFilter)
    {
        this.rootCriteria = rootCriteria;
        this.searchFilter = searchFilter;
    }

    public void fillForCount()
    {
        fillСriterions();
    }

    public void fill()
    {
        rootCriteria.setFirstResult(searchFilter.getStartIndex());
        rootCriteria.setMaxResults(searchFilter.getPageSize());
        if (searchFilter.getResultTransformer() != null)
        {
            rootCriteria.setResultTransformer(searchFilter.getResultTransformer());
        }
        fillСriterions();
        fillOrders();
    }

    private void fillOrders()
    {
        if (searchFilter.getColumnOrders() != null)
        {
            List<SearchFilter.DCriterion<Order>> criterions = searchFilter.getColumnOrders();
            for (SearchFilter.DCriterion<Order> d : criterions)
            {

                Criteria criteria = getCriteriaBy(d.propertyPath, d.joinType);
                criteria.addOrder(d.criterion);
            }
        }
    }


    private void fillСriterions()
    {
        List<SearchFilter.DCriterion<Criterion>> criterions = searchFilter.getCriterions();
        if (criterions != null)
        {
            for (SearchFilter.DCriterion<Criterion> criterion : criterions)
            {
                getCriteriaBy(criterion.propertyPath, criterion.joinType).add(criterion.criterion);
            }
        }
    }

    private Criteria getCriteriaBy(String propertyPath, int joinType)
    {
        Criteria criteria = rootCriteria;
        String[] strings = StringUtils.split(propertyPath, '.');
        if (strings.length > 1)
        {
            for (int i = 0; i < strings.length - 1; i++)
            {
                String string = strings[i];
                Criteria subCriteria = map.get(string);
                if (subCriteria == null)
                {
                    if (joinType != -1)
                    {
                        subCriteria = criteria.createCriteria(string, joinType);
                    }
                    else
                    {
                        subCriteria = criteria.createCriteria(string);
                    }
                    map.put(string, subCriteria);
                }
                criteria = subCriteria;
            }
        }
        return criteria;
    }

    public static String getLastPropertyBy(String propertyPath)
    {
        String[] strings = StringUtils.split(propertyPath, '.');
        return strings[strings.length - 1];
    }

}
