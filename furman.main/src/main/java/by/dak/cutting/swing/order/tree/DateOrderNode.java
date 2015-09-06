package by.dak.cutting.swing.order.tree;

import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.persistence.convert.OrderDate2StringConverter;
import by.dak.persistence.entities.Customer;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;
import by.dak.utils.convert.StringValue;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 19.02.2010
 * Time: 13:39:45
 * To change this template use File | Settings | File Templates.
 */
@StringValue(converterClass = OrderDate2StringConverter.class)
public class DateOrderNode extends ATreeNode implements ListUpdaterProvider
{
    private Date date;
    private Customer customer;

    public DateOrderNode()
    {
        setUserObject(this);
    }

    public DateOrderNode(Date date, Customer customer)
    {
        this();
        this.date = date;
        this.customer = customer;
    }

    @Override
    protected void initChildren()
    {
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return ListUpdaters.createOrderListUpdateBy(date, customer);
    }

    public Date getDate()
    {
        return date;
    }
}
