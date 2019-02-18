package by.dak.persistence.entities;

import by.dak.persistence.convert.Order2StringConverter;
import by.dak.utils.convert.Converter;
import by.dak.utils.convert.StringValue;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Proxy;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;

/**
 * @author Denis Koyro
 * @version 0.1 16.10.2008
 * @introduced [Builder | Overview ]
 * @since 2.0.0
 */
@Entity
@Proxy(lazy = false)
@DiscriminatorValue(value = "Order")
@DiscriminatorOptions(force = true)


@NamedQueries(value =
        {
                @NamedQuery(name = "lastOrderNumber",
                        query = "select max(orderNumber) from Order"),

                @NamedQuery(name = "allArchive",
                        query = "from Order o order by o.createdDailySheet.date, o.orderNumber"),
                @NamedQuery(name = "allByStatus",
                        query = "from Order o where o.status=:status order by name"),

                /*           @NamedQuery(name = "readyDateByLastOrder",
query = "from Order order where order.id = (  select max(oe.id) from Order as oe )")*/

                @NamedQuery(name = "createdDateGroup",
                        query = "select month(ds.date),year(ds.date) from Order o inner join " +
                                "o.createdDailySheet ds where o.customer=:customer group by month(ds.date), year(ds.date)")
        }
)

@StringValue(converterClass = Order2StringConverter.class)
public class Order extends AOrder
{
    public static final Order NULL_Order = new Order();

    static
    {
        NULL_Order.setName(Converter.NULL_STRING);
    }

    public static final String PROPERTY_orderStatus = "status";
    public static final String PROPERTY_number = "number";
    public static final String PROPERTY_workedDailySheet = "workedDailySheet";

    public static boolean isNull(Order order)
    {
        return order == null || order == NULL_Order;
    }


    @ManyToOne(cascade =
            {
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
            }, targetEntity = Dailysheet.class)
    @JoinColumns(
            {
                    @JoinColumn(name = "FK_WORKED_DAILY_SHEET_ID", nullable = true, referencedColumnName = "ID")
            })
    private Dailysheet workedDailySheet;

    public Dailysheet getWorkedDailySheet()
    {
        return workedDailySheet;
    }

    public void setWorkedDailySheet(Dailysheet workedDailySheet)
    {
        Dailysheet old = this.workedDailySheet;
        this.workedDailySheet = workedDailySheet;
        support.firePropertyChange("workedDailySheet", old, workedDailySheet);
    }


    public static Order valueOf(Order order)
    {
        if (order == null)
        {
            return null;
        }
        Order result = new Order();
        result.setOrderNumber(order.getOrderNumber());
        result.setReadyDate(order.getReadyDate());
        result.setCost(order.getCost());
        result.setCreatedDailySheet(order.getCreatedDailySheet());
        result.setCustomer(order.getCustomer());
        result.setDesigner(order.getDesigner());
        result.setStatus(order.getStatus());
        return result;
    }

    @Override
    public String toString()
    {
        ToStringCreator toStringCreator = new ToStringCreator(this);
        toStringCreator.append("orderNumber", getOrderNumber());
        toStringCreator.append("createdDailySheet", getCreatedDailySheet());
        return toStringCreator.toString();
    }

    public boolean isEditable()
    {
        return OrderStatus.miscalculation == this.getStatus() ||
                OrderStatus.design == this.getStatus() ||
                OrderStatus.webDesign == this.getStatus() ||
                OrderStatus.webMiscalculation == this.getStatus();
    }


    public void setMiscalculation(boolean value)
    {
        if (OrderStatus.design == this.getStatus() && value) {
            setStatus(OrderStatus.miscalculation);
            return;
        }
        if (OrderStatus.webDesign == this.getStatus() && value) {
            setStatus(OrderStatus.webMiscalculation);
            return;
        }
        if (OrderStatus.miscalculation == this.getStatus() && !value)
        {
            setStatus(OrderStatus.design);
            return;
        }
        if (OrderStatus.webMiscalculation == this.getStatus() && !value) {
            setStatus(OrderStatus.webDesign);
        }

        throw new IllegalArgumentException();
    }

    public boolean isMiscalculation()
    {
        return OrderStatus.miscalculation == this.getStatus() ||
                OrderStatus.webMiscalculation == this.getStatus();
    }

}