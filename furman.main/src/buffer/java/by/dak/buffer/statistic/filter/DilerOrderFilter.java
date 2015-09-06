package by.dak.buffer.statistic.filter;

import by.dak.persistence.entities.Customer;
import org.jdesktop.beans.AbstractBean;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.11.2010
 * Time: 17:55:24
 * To change this template use File | Settings | File Templates.
 */
public class DilerOrderFilter extends AbstractBean
{

    private Customer customer;
    private Date start;
    private Date end;

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        Customer oldValue = this.customer;
        this.customer = customer;
        firePropertyChange("customer", oldValue, customer);
    }

    public Date getStart()
    {
        if (start == null)
        {
            start = new Date(0);
        }
        return start;
    }

    public void setStart(Date start)
    {
        Date oldValue = this.start;
        this.start = start;
        firePropertyChange("start", oldValue, start);
    }

    public Date getEnd()
    {
        if (end == null)
        {
            end = Calendar.getInstance().getTime();
        }
        return end;
    }

    public void setEnd(Date end)
    {
        Date oldValue = this.end;
        this.end = end;
        firePropertyChange("end", oldValue, end);
    }

}
