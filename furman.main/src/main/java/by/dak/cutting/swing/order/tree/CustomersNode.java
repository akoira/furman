package by.dak.cutting.swing.order.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Customer;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValue;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 19.02.2010
 * Time: 13:38:59
 * To change this template use File | Settings | File Templates.
 */
@StringValue(converterClass = CustomersNode.class)
public class CustomersNode extends ATreeNode implements ListUpdaterProvider, EntityToStringConverter
{
    public CustomersNode()
    {
        setUserObject(this);
    }

    @Override
    protected void initChildren()
    {
        removeAllChildren();

        SearchFilter searchFilter = new SearchFilter();
        searchFilter.addColumnOrder(new SearchFilter.DCriterion<Order>("name", Order.asc("name")));

        List<Customer> customerList = FacadeContext.getCustomerFacade().loadAll(searchFilter);

        for (Customer customer : customerList)
        {
            add(new CustomerNode(customer));
        }
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return ListUpdaters.OrdersListUpdater;
    }

    @Override
    public String convert(Object entity)
    {
        return resourceMap.getString("node.name");
    }
}
