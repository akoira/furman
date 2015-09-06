package by.dak.buffer.statistic.swing.tree;

import by.dak.buffer.statistic.filter.DilerOrderFilter;
import by.dak.order.swing.IOrderWizardDelegator;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Customer;
import by.dak.swing.tree.ATreeNode;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 28.11.2010
 * Time: 23:34:35
 * To change this template use File | Settings | File Templates.
 */
public class DilerCustomerNodes extends ATreeNode
{
    private DilerOrderFilter dilerOrderFilter;
    private IOrderWizardDelegator orderWizardDelegator;

    public IOrderWizardDelegator getOrderWizardDelegator()
    {
        return orderWizardDelegator;
    }

    public void setOrderWizardDelegator(IOrderWizardDelegator orderWizardDelegator)
    {
        this.orderWizardDelegator = orderWizardDelegator;
    }

    public DilerCustomerNodes(DilerOrderFilter dilerOrderFilter)
    {
        super();
        this.dilerOrderFilter = dilerOrderFilter;
        setUserObject(getResourceMap().getString("customer.node"));
    }

    @Override
    protected void initChildren()
    {
        if (dilerOrderFilter.getCustomer() != null && !Customer.isNull(dilerOrderFilter.getCustomer()))
        {

            DilerCustomerNode dilerCustomerNode = new DilerCustomerNode(getDilerOrderFilter().getCustomer());
            dilerCustomerNode.setDilerOrderFilter(getDilerOrderFilter());
            dilerCustomerNode.setOrderWizardDelegator(getOrderWizardDelegator());
            add(dilerCustomerNode);
        }
        else
        {
            List<Customer> customers = FacadeContext.getCustomerFacade().loadAll();
            for (Customer customer : customers)
            {
                DilerCustomerNode dilerCustomerNode = new DilerCustomerNode(customer);
                dilerCustomerNode.setDilerOrderFilter(getDilerOrderFilter());
                dilerCustomerNode.setOrderWizardDelegator(getOrderWizardDelegator());
                add(dilerCustomerNode);

            }
        }
    }

    public DilerOrderFilter getDilerOrderFilter()
    {
        return dilerOrderFilter;
    }
}
