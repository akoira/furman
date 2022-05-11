package by.dak.persistence.entities;

import by.dak.ordergroup.OrderGroup;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValue;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User: akoyro
 * Date: 09.03.11
 * Time: 14:57
 */
@Entity

@Table(name = "FURN_ORDER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
@DiscriminatorOptions(force = true)
public abstract class AOrder extends PersistenceEntity
{
    public static final String PROPERTY_orderItems = "orderItems";
    public static final String PROPERTY_name = "name";
    public static final String PROPERTY_orderNumber = "orderNumber";
    public static final String PROPERTY_description = "description";
    public static final String PROPERTY_fileUuid = "fileUuid";

    public static final String PROPERTY_customer = "customer";
    public static final String PROPERTY_readyDate = "readyDate";
    public static final String PROPERTY_cost = "cost";
    public static final String PROPERTY_designer = "designer";
    public static final String PROPERTY_dialerCost = "dialerCost";

    public static final String PROPERTY_createdDailySheet = "createdDailySheet";
    public static final String PROPERTY_orderGroup = "orderGroup";

    public static final String PROPERTY_locked = "locked";


    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    @Column(name = "DESCRIPTION", nullable = true, columnDefinition = "longtext")
    private String description;

    /**
     * File uuid with representation of the order item saved in repository
     */
    @Column(name = "FILE_UUID", nullable = true)
    private String fileUuid;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @Column(name = "COST", nullable = true)
    private Double cost;

    @Column(name = "DIALER_COST")
    private Double dialerCost;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.design;


    @Column(name = "READY_DATE", nullable = true)
    private Date readyDate;

    @Column(name = "ORDER_NUMBER", nullable = false)
    private Long orderNumber;

    @Column(name = "LOCKED", nullable = false, columnDefinition = "bit default 0")
    private Boolean locked;

    @ManyToOne(cascade =
            {
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
            }, targetEntity = Customer.class)
    @JoinColumns(
            {
                    @JoinColumn(name = "FK_CUSTOMER_ID", nullable = false, referencedColumnName = "ID")
            })
    private Customer customer;


    @ManyToOne(cascade =
            {
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
            }, targetEntity = DesignerEntity.class)
    @JoinColumns(
            {
                    @JoinColumn(name = "FK_DESIGNER_ID", nullable = true, referencedColumnName = "ID")
            })
    private DesignerEntity designer;

    @ManyToOne(cascade =
            {
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
            }, targetEntity = Dailysheet.class)
    @JoinColumns(
            {
                    @JoinColumn(name = "FK_CREATED_DAILY_SHEET_ID", nullable = false, referencedColumnName = "ID")
            })
    private Dailysheet createdDailySheet;


    @ManyToOne
    @JoinColumns(
            {
                    @JoinColumn(name = "ORDER_GROUP_ID", nullable = true, referencedColumnName = "ID")
            })
    private OrderGroup orderGroup;


    public Date getReadyDate()
    {
        return readyDate;
    }

    public void setReadyDate(Date readyDate)
    {
        this.readyDate = readyDate;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public Customer getCustomer()
    {
        return customer;
    }


    public OrderStatus getStatus()
    {
        return status;
    }

    public void setStatus(OrderStatus status)
    {
        OrderStatus old = this.status;
        this.status = status;
        support.firePropertyChange("orderStatus", old, this.status);
    }


    public void setOrderItems(List<OrderItem> value)
    {
        this.orderItems = value;
    }

    public List<OrderItem> getOrderItems()
    {
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem)
    {
        orderItem.setOrder(this);
        getOrderItems().add(orderItem);
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public String getFileUuid()
    {
        return fileUuid;
    }

    public void setFileUuid(String fileUuid)
    {
        this.fileUuid = fileUuid;
    }

    public Double getCost()
    {
        return cost;
    }

    public void setCost(Double cost)
    {
        this.cost = cost;
    }

    @Transient
    public boolean isEditable()
    {
        return true;
    }

    public void setDesigner(DesignerEntity designerEntity)
    {
        this.designer = designerEntity;
    }

    public DesignerEntity getDesigner()
    {
        return designer;
    }

    public void setOrderNumber(Long orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public Long getOrderNumber()
    {
        return orderNumber;
    }

    public Dailysheet getCreatedDailySheet()
    {
        return createdDailySheet;
    }

    public void setCreatedDailySheet(Dailysheet createdDailySheet)
    {
        this.createdDailySheet = createdDailySheet;
    }


    public Double getDialerCost()
    {
        return dialerCost;
    }

    public void setDialerCost(Double dialerCost)
    {
        this.dialerCost = dialerCost;
    }

    public OrderNumber getNumber()
    {
        return new OrderNumber(this);
    }

    public OrderGroup getOrderGroup()
    {
        return orderGroup;
    }

    public void setOrderGroup(OrderGroup orderGroup)
    {
        this.orderGroup = orderGroup;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @StringValue(converterClass = OrderNumber.class)
    public static class OrderNumber implements EntityToStringConverter<OrderNumber>
    {
        private AOrder entity;

        public OrderNumber()
        {
            super();
        }

        public OrderNumber(AOrder entity)
        {
            this.entity = entity;
        }

        public int getMonth()
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(entity.getCreatedDailySheet().getDate());
            return calendar.get(Calendar.MONTH) + 1;
        }

        public long getOrderNumber()
        {
            return entity.getOrderNumber();
        }

        public String getStringValue()
        {
            return new StringBuffer(new DecimalFormat("#00").format(getMonth())).append("-").append(new DecimalFormat("#0000").format(getOrderNumber())).toString();
        }

        @Override
        public String convert(OrderNumber entity)
        {
            return entity.getStringValue();
        }
    }

}
