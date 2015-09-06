package by.dak.cutting.swing.archive;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.swing.FilterPanel;
import by.dak.utils.convert.TimeUtils;
import by.dak.utils.validator.ValidationUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 16:51
 */
public class OrderFilterPanel extends FilterPanel<OrderFilter>
{
    public OrderFilterPanel()
    {
        setVisibleProperties("number", "name", "customer", "status", "startCreated", "endCreated");
        setCacheEditorCreator(Constants.getEntityEditorCreators(OrderFilter.class));
    }

    public List<SearchFilter.DCriterion<Criterion>> getCriterions()
    {
        ArrayList<SearchFilter.DCriterion<Criterion>> criterions = new ArrayList<SearchFilter.DCriterion<Criterion>>(2);
        if (getValue().getCustomer() != null && getValue().getCustomer() != Customer.NULL_CUSTOMER)
        {
            criterions.add(new SearchFilter.DCriterion<Criterion>(Order.PROPERTY_customer, Restrictions.eq(Order.PROPERTY_customer, getValue().getCustomer())));
        }
        if (ValidationUtils.isMoreThan(0, getValue().getNumber()))
        {
            SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>(Order.PROPERTY_orderNumber, Restrictions.eq(Order.PROPERTY_orderNumber, getValue().getNumber()));
            criterions.add(criterion);
        }

        if (!StringUtils.isEmpty(getValue().getName()))
        {
            SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>(Order.PROPERTY_name, Restrictions.like(Order.PROPERTY_name, getValue().getName(), MatchMode.ANYWHERE));
            criterions.add(criterion);
        }

        if (getValue().getStatus() != null)
        {
            SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>(Order.PROPERTY_orderNumber, Restrictions.eq(Order.PROPERTY_orderStatus, getValue().getStatus()));
            criterions.add(criterion);
        }

        if (getValue().getStartCreated() != null)
        {
            if (getValue().getEndCreated() != null)
            {
                SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>("createdDailySheet.date", Restrictions.between("date", TimeUtils.getDayTimestamp(getValue().getStartCreated()), TimeUtils.getDayTimestamp(getValue().getEndCreated())));
                criterions.add(criterion);
            }
            else
            {
                SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>("createdDailySheet.date", Restrictions.eq("date", TimeUtils.getDayTimestamp(getValue().getStartCreated())));
                criterions.add(criterion);
            }
        }
        return criterions;
    }

}
