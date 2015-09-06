package by.dak.buffer.entity;

import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.AOrderDetail;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.persistence.entities.predefined.OrderItemType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.10.11
 * Time: 10:12
 * To change this template use File | Settings | File Templates.
 */

/**
 * copy of OrderItem
 */
@Table(name = "TEMP_TABLE")
@Entity
public class TempOrderItem extends PersistenceEntity
{
    @Column(name = "TYPE", nullable = false)
    private OrderItemType type;

    @Column(name = "NUMBER", nullable = false)
    private Long number = 1L;

    @Column(name = "DESIGN", nullable = true)
    private String design;

    @Column(name = "DISCRIMINATOR", nullable = false, updatable = false, insertable = false)
    private String discriminator;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    /**
     * Description of the Order Item
     */
    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    /**
     * File uuid with representation of the order item saved in repository
     */
    @Column(name = "FILE_UUID", nullable = true)
    private String fileUuid;

    @OneToMany(mappedBy = "orderItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AOrderDetail> details = new ArrayList<AOrderDetail>();

    @Column(name = "AMOUNT", nullable = false)
    private Integer amount = 1;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private AOrder order;

    @Column(name = "LENGTH")
    private Double length;

    @Column(name = "WIDTH")
    private Double width;

    public Double getLength()
    {
        return length;
    }

    public Double getWidth()
    {
        return width;
    }

    public OrderItemType getType()
    {
        return type;
    }

    public Long getNumber()
    {
        return number;
    }

    public String getDesign()
    {
        return design;
    }

    public String getDiscriminator()
    {
        return discriminator;
    }

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

    public List<AOrderDetail> getDetails()
    {
        return details;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public AOrder getOrder()
    {
        return order;
    }
}
