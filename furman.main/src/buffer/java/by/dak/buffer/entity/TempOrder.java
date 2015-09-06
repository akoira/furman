package by.dak.buffer.entity;

import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.entities.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.10.11
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */

/**
 * for import
 * the copy of AOrder
 */

@Entity
@Table(name = "TEMP_TABLE")
public class TempOrder extends PersistenceEntity
{
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    @Column(name = "DESCRIPTION", nullable = true, length = 255)
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
    private OrderStatus status = OrderStatus.design;


    @Column(name = "READY_DATE", nullable = true)
    private Date readyDate;

    @Column(name = "ORDER_NUMBER", nullable = false)
    private Long orderNumber;


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

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getFileUuid()
    {
        return fileUuid;
    }

    public List<OrderItem> getOrderItems()
    {
        return orderItems;
    }

    public Double getCost()
    {
        return cost;
    }

    public Double getDialerCost()
    {
        return dialerCost;
    }

    public OrderStatus getStatus()
    {
        return status;
    }

    public Date getReadyDate()
    {
        return readyDate;
    }

    public Long getOrderNumber()
    {
        return orderNumber;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public DesignerEntity getDesigner()
    {
        return designer;
    }

    public Dailysheet getCreatedDailySheet()
    {
        return createdDailySheet;
    }

    public OrderGroup getOrderGroup()
    {
        return orderGroup;
    }
}
