package by.dak.cutting.statistics;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.impl.AStoreElementFacadeImpl;
import by.dak.persistence.NamedQueryDefinition;
import by.dak.persistence.NamedQueryParameter;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderStatus;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.beans.AbstractBean;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: akoyro
 * Date: 17.11.2010
 * Time: 12:27:33
 */
public class StatisticFilter extends AbstractBean implements EntityToStringConverter<StatisticFilter>
{
    public static final String PROPERTY_order = "order";
    private Date start;
    private Date end;
    private MaterialType type;
    private Customer customer;
    private Order order;
    private ServiceType serviceType;

    private NamedQueryParameter[] queryParameters;

    public Date getStart()
    {
        if (start == null)
        {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            start = calendar.getTime();
        }
        return start;
    }

    public void setStart(Date start)
    {
        Date old = this.start;
        this.start = start;
        firePropertyChange("start", old, this.start);
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
        Date old = this.end;
        this.end = end;
        firePropertyChange("end", old, this.end);
    }

    public MaterialType getType()
    {
        return type;
    }

    public void setType(MaterialType type)
    {
        MaterialType old = this.type;
        this.type = type;
        firePropertyChange("type", old, this.type);
    }

    @Override
    public String convert(StatisticFilter entity)
    {
        List<String> strings = new ArrayList<String>();
        strings.add(StringValueAnnotationProcessor.getProcessor().convert(getType()));
        strings.add(entity.getStart() != null ?
                DateFormat.getDateInstance(DateFormat.SHORT).format(entity.getStart()) :
                "");
        return StringUtils.join(strings, ' ');
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        Customer old = this.customer;
        this.customer = customer;
        firePropertyChange("customer", old, this.customer);
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        Order old = this.order;
        this.order = order;
        firePropertyChange("order", old, this.order);
    }

    public NamedQueryDefinition getNamedQueryDefinition()
    {
        NamedQueryDefinition namedQueryDefinition = new NamedQueryDefinition();
        List<NamedQueryParameter> parameters = new ArrayList<NamedQueryParameter>();


        parameters.add(NamedQueryParameter.getLongParameter("startOrderId", Order.isNull(getOrder()) ? 0 : getOrder().getId()));
        parameters.add(NamedQueryParameter.getLongParameter("endOrderId", Order.isNull(getOrder()) ? Long.MAX_VALUE : getOrder().getId()));

        parameters.add(NamedQueryParameter.getLongParameter("startCustomerId", Customer.isNull(getCustomer()) ? 0 : getCustomer().getId()));
        parameters.add(NamedQueryParameter.getLongParameter("endCustomerId", Customer.isNull(getCustomer()) ? Long.MAX_VALUE : getCustomer().getId()));


        parameters.add(NamedQueryParameter.getDateParameter("start", getStart() != null ? getStart() : new Date(0l)));
        parameters.add(NamedQueryParameter.getDateParameter("end", getEnd() != null ? getEnd() : Calendar.getInstance().getTime()));

        parameters.add(NamedQueryParameter.getParameterListParameter("status", OrderStatus.notEditables()));
        namedQueryDefinition.setParameterList(parameters);

        return namedQueryDefinition;
    }

    public SearchFilter getSearchFilter(String orderPath)
    {
        return AStoreElementFacadeImpl.getSearchFilterBy(orderPath, this);
    }

    public ServiceType getServiceType()
    {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType)
    {
        ServiceType old = this.serviceType;
        this.serviceType = serviceType;
        firePropertyChange("serviceType", old, serviceType);


    }
}
