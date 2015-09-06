package by.dak.cutting.swing.order.tree;

import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Customer;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 19.02.2010
 * Time: 13:39:17
 * To change this template use File | Settings | File Templates.
 */
public class CustomerNode extends ATreeNode implements ListUpdaterProvider
{
    public CustomerNode(Customer customer)
    {
        setUserObject(customer);
    }

    @Override
    protected void initChildren()
    {
        removeAllChildren();

        List<Date> dates = FacadeContext.getOrderFacade().getCreatedDateBy((Customer) getUserObject());

        for (Date date : dates)
        {
            add(new DateOrderNode(date, (Customer) getUserObject()));
        }
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return ListUpdaters.createOrderListUpdaterBy((Customer) getUserObject());
    }
}
