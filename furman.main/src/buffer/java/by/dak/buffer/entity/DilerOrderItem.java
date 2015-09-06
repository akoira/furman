package by.dak.buffer.entity;

import by.dak.persistence.entities.PersistenceEntity;
import by.dak.persistence.entities.predefined.OrderItemType;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.11.2010
 * Time: 10:54:07
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Proxy(lazy = true)
@Table(name = "DILER_ORDER_ITEM")

public class DilerOrderItem extends PersistenceEntity
{
    public static String PROPERTY_dilerOrder = "dilerOrder";

    @ManyToOne(targetEntity = DilerOrder.class)
    @JoinColumns({@JoinColumn(name = "DILER_ORDER_ID", nullable = true, referencedColumnName = "ID")})
    private DilerOrder dilerOrder;

    @Column(name = "DISCRIMINATOR", nullable = false, updatable = true, insertable = true)
    private String discriminator;

    @Column(name = "NAME", nullable = true, length = 255)
    private String name;

    @Column(name = "TYPE", nullable = true)
    @Enumerated(EnumType.STRING)
    private OrderItemType type;

    @Column(name = "NUMBER", nullable = true)
    private Long number = 1L;

    @Column(name = "AMOUNT", nullable = true, columnDefinition = "bigint")
    private Integer amount = 1;

    @Column(name = "LENGTH")
    private Double length;

    @Column(name = "WIDTH")
    private Double width;

    @Transient
    private Long orderItemId;

    @OneToMany(mappedBy = "dilerOrderItem", fetch = FetchType.LAZY)
    private List<DilerOrderDetail> dilerOrderDetail;

    public Long getOrderItemId()
    {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId)
    {
        this.orderItemId = orderItemId;
    }

    public List<DilerOrderDetail> getDilerOrderDetail()
    {
        return dilerOrderDetail;
    }

    public void setDiscriminator(String discriminator)
    {
        this.discriminator = discriminator;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setType(OrderItemType type)
    {
        this.type = type;
    }

    public void setNumber(Long number)
    {
        this.number = number;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public void setLength(Double length)
    {
        this.length = length;
    }

    public void setWidth(Double width)
    {
        this.width = width;
    }

    public void setDilerOrderDetail(List<DilerOrderDetail> dilerOrderDetail)
    {
        this.dilerOrderDetail = dilerOrderDetail;
    }

    public String getDiscriminator()
    {
        return discriminator;
    }

    public String getName()
    {
        return name;
    }

    public OrderItemType getType()
    {
        return type;
    }

    public Long getNumber()
    {
        return number;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public DilerOrder getDilerOrder()
    {
        return dilerOrder;
    }

    public void setDilerOrder(DilerOrder dilerOrder)
    {
        this.dilerOrder = dilerOrder;
    }

    public Double getLength()
    {
        return length;
    }

    public Double getWidth()
    {
        return width;
    }
}
