package by.dak.cutting;

import by.dak.swing.filter.Expression;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Filter class for Lists
 *
 * @author Rudak Alexei
 */
public class SearchFilter implements Serializable
{
    public static class DCriterion<C>
    {
        public String propertyPath;
        public C criterion;
        public int joinType = -1;

        public DCriterion(String propertyPath, C criterion)
        {
            this(propertyPath, criterion, -1);
        }


        public DCriterion(String propertyPath, C criterion, int joinType)
        {
            this.propertyPath = propertyPath;
            this.criterion = criterion;
            this.joinType = joinType;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == this)
            {
                return true;
            }
            if (!(obj instanceof DCriterion))
            {
                return false;
            }


            DCriterion criterion = (DCriterion) obj;
            return criterion.propertyPath.equals(propertyPath) &&
                    this.criterion.toString().equals(criterion.criterion.toString());

        }
    }

    public static final Integer MAINPANEL = 1;
    public static final Integer SELPANEL = 2;
    public static final Integer DEFAULT_PAGE_SIZE = 40;

    private Integer pageSize = DEFAULT_PAGE_SIZE;
    private Integer resultSize = 0;
    private Integer startIndex = 0;
    private List<DCriterion<Order>> columnOrders = new ArrayList<DCriterion<Order>>();
    private List<DCriterion<Criterion>> criterions = new ArrayList<DCriterion<Criterion>>();
    private Integer mode = SELPANEL;
    private ResultTransformer resultTransformer;


    public static SearchFilter valueOf(SearchFilter template)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.columnOrders = new ArrayList<DCriterion<Order>>(template.getColumnOrders());
        searchFilter.criterions = new ArrayList<DCriterion<Criterion>>(template.getCriterions());
        return searchFilter;
    }

    public void setResultTransformer(ResultTransformer resultTransformer)
    {
        this.resultTransformer = resultTransformer;
    }

    public ResultTransformer getResultTransformer()
    {
        return resultTransformer;
    }

    public void reset()
    {
        pageSize = DEFAULT_PAGE_SIZE;
        resultSize = 0;
        startIndex = 0;
        columnOrders.clear();
        criterions.clear();
        setResultTransformer(null);
    }

    public Integer getResultSize()
    {
        return resultSize;
    }

    public void setResultSize(Integer resultSize)
    {
        this.resultSize = resultSize;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public List<DCriterion<Criterion>> getCriterions()
    {
        return criterions;
    }

    public void setCriterions(List<DCriterion<Criterion>> criterions)
    {
        this.criterions = criterions;
    }

    public void addAllCriterion(List<DCriterion<Criterion>> criterions)
    {
        this.criterions.addAll(criterions);
    }

    public void eq(Object value, String... path)
    {
        eq(StringUtils.join(path, '.'), value);
    }

    public void eq(String property, Object value)
    {
        if (value != null)
        {
            addExpression(Expression.eq, property, value);
        }
        else
        {
            isNull(property);
        }
    }

    public void ge(String property, Object value)
    {
        addExpression(Expression.ge, property, value);
    }

    public void le(String property, Object value)
    {
        addExpression(Expression.le, property, value);
    }

    public void in(String property, Collection values)
    {
        addExpression(Expression.in, property, values);
    }

    public void gt(String property, Object value)
    {
        addExpression(Expression.gt, property, value);
    }

    public void lt(String property, Object value)
    {
        addExpression(Expression.lt, property, value);
    }

    public void ilike(String property, Object value)
    {
        addExpression(Expression.ilike, property, value);
    }

    private void addExpression(Expression expression, String property, Object value)
    {
        try
        {
            Method method = Restrictions.class.getMethod(expression.name(), String.class, Object.class);
            String[] properties = StringUtils.split(property, '.');
            Criterion criterion = (Criterion) method.invoke(null, properties[properties.length - 1], value);
            DCriterion<Criterion> dCriterion = new DCriterion<Criterion>(property, criterion);
            addCriterion(dCriterion);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    private void addExpression(Expression expression, String property, Collection value)
    {
        try
        {
            Method method = Restrictions.class.getMethod(expression.name(), String.class, Collection.class);
            String[] properties = StringUtils.split(property, '.');
            Criterion criterion = (Criterion) method.invoke(null, properties[properties.length - 1], value);
            DCriterion<Criterion> dCriterion = new DCriterion<Criterion>(property, criterion);
            addCriterion(dCriterion);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public void or(Criterion first, Criterion second)
    {
        DCriterion<Criterion> criterion = new DCriterion<Criterion>("", Restrictions.or(first, second));
        addCriterion(criterion);
    }

    public void isNotNull(String property)
    {
        String[] properties = StringUtils.split(property, '.');
        SearchFilter.DCriterion<Criterion> dCriterion = new SearchFilter.DCriterion<Criterion>(property, Restrictions.isNotNull(properties[properties.length - 1]));
        addCriterion(dCriterion);
    }

    public void isNull(String property)
    {
        this.isNull(property, -1);
    }

    public void isNull(String property, int joinType)
    {
        String[] properties = StringUtils.split(property, '.');
        SearchFilter.DCriterion<Criterion> dCriterion = new SearchFilter.DCriterion<Criterion>(property, Restrictions.isNull(properties[properties.length - 1]), joinType);
        addCriterion(dCriterion);
    }

    public void isEmpty(String property)
    {
        String[] properties = StringUtils.split(property, '.');
        SearchFilter.DCriterion<Criterion> dCriterion = new SearchFilter.DCriterion<Criterion>(property, Restrictions.isEmpty(properties[properties.length - 1]));
        addCriterion(dCriterion);
    }


    public void addCriterion(DCriterion<Criterion> criterion)
    {
        if (!hasCriterion(criterion))
        {
            this.criterions.add(criterion);
        }
    }

    public Integer getStartIndex()
    {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex)
    {
        this.startIndex = startIndex;
    }

    public List<DCriterion<Order>> getColumnOrders()
    {
        return columnOrders;
    }

    public void addColumnOrder(DCriterion<Order> columnOrder)
    {
        if (!hasColumnOrder(columnOrder.propertyPath))
        {
            this.columnOrders.add(columnOrder);
        }
    }

    public void addAscOrder(String propertyPath)
    {
        String[] properties = StringUtils.split(propertyPath, '.');
        addColumnOrder(new SearchFilter.DCriterion<Order>(propertyPath, Order.asc(properties[properties.length - 1])));
    }

    public void addDescOrder(String propertyPath)
    {
        String[] properties = StringUtils.split(propertyPath, '.');
        addColumnOrder(new SearchFilter.DCriterion<Order>(propertyPath, Order.desc(properties[properties.length - 1])));
    }


    public Integer getMode()
    {
        return mode;
    }

    public void setMode(Integer mode)
    {
        this.mode = mode;
    }

    public boolean hasColumnOrder(String propertyPath)
    {
        for (DCriterion<Order> orderDCriterion : columnOrders)
        {
            if (orderDCriterion.propertyPath.equals(propertyPath))
            {
                return true;
            }
        }
        return false;
    }

    public boolean hasCriterion(DCriterion c)
    {
        for (DCriterion<Criterion> criterion : criterions)
        {
            if (criterion.equals(c))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает неограниченный фильтер
     *
     * @return
     */
    public static SearchFilter instanceUnbound()
    {
        SearchFilter filter = new SearchFilter();
        filter.setResultSize(Integer.MAX_VALUE);
        filter.setPageSize(Integer.MAX_VALUE);
        return filter;
    }

    public static SearchFilter instanceSingle()
    {
        SearchFilter filter = new SearchFilter();
        filter.setResultSize(1);
        filter.setPageSize(1);
        return filter;
    }

    public void removeCriterion(String propertyPath)
    {
        List<DCriterion<Criterion>> removed = new ArrayList<DCriterion<Criterion>>();
        for (DCriterion<Criterion> criterion : criterions)
        {
            if (criterion.propertyPath.equals(propertyPath))
            {
                removed.add(criterion);
            }
        }
        criterions.removeAll(removed);
    }

    @Override
    public SearchFilter clone()
    {
        SearchFilter searchFilter = new SearchFilter();

        searchFilter.pageSize = DEFAULT_PAGE_SIZE;
        searchFilter.resultSize = 0;
        searchFilter.startIndex = 0;
        searchFilter.columnOrders = new ArrayList<>(this.columnOrders);
        searchFilter.criterions = new ArrayList<>(this.criterions);
        searchFilter.mode = SELPANEL;
        searchFilter.resultTransformer = this.resultTransformer;
        return searchFilter;
    }
}
