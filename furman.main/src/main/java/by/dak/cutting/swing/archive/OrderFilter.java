package by.dak.cutting.swing.archive;

import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.OrderStatus;
import org.jdesktop.beans.AbstractBean;

import java.util.Date;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 16:46
 */
public class OrderFilter extends AbstractBean
{
    private Long number;
    private String name;
    private Customer customer;
    private OrderStatus status;
    private Date startCreated;
    private Date endCreated;

    public Long getNumber()
    {
        return number;
    }

    public void setNumber(Long number)
    {
        Long old = this.number;
        this.number = number;
        firePropertyChange("number", old, number);

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        String old = this.name;
        this.name = name;
        firePropertyChange("name", old, number);
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        Customer old = this.customer;
        this.customer = customer;
        firePropertyChange("customer", old, customer);
    }

    public OrderStatus getStatus()
    {
        return status;
    }

    public void setStatus(OrderStatus status)
    {
        OrderStatus old = this.status;
        this.status = status;
        firePropertyChange("status", old, status);
    }

    public Date getStartCreated()
    {
        return startCreated;
    }

    public void setStartCreated(Date startCreated)
    {
        Date old = this.startCreated;
        this.startCreated = startCreated;
        firePropertyChange("startCreated", old, startCreated);
    }

    public Date getEndCreated()
    {
        return endCreated;
    }

    public void setEndCreated(Date endCreated)
    {
        Date old = this.endCreated;
        this.endCreated = endCreated;
        firePropertyChange("endCreated", old, endCreated);
    }
}
