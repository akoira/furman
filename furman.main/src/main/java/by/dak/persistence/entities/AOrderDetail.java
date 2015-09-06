package by.dak.persistence.entities;

import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.util.List;

/**
 * User: akoyro
 * Date: 17.07.2010
 * Time: 17:00:30
 */

@Entity

@Table(name = "ORDER_DETAIL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
@DiscriminatorOptions(force = true)

@NamedQueries(value =
        {
                @NamedQuery(name = "deleteByOrderItem",
                        query = "delete from AOrderDetail o where " +
                                "o.orderItem = :orderItem")
        }
)


public abstract class AOrderDetail<T extends PriceAware, C extends Priced> extends PersistenceEntity
{
    public static final String PROPERTY_priceAware = "priceAware";
    public static final String PROPERTY_priced = "priced";
    public static final String PROPERTY_discriminator = "discriminator";
    public static final String PROPERTY_number = "number";
    public static final String PROPERTY_name = "name";
    public static final String PROPERTY_orderItem = "orderItem";
    public static final String PROPERTY_amount = "amount";

    @Column(name = "DISCRIMINATOR", nullable = false, updatable = false, insertable = false)
    private String discriminator;

    @Column(name = "NUMBER", nullable = false)
    private Long number = 0L;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name = "";//Constants.DEFAULT_DETAIL_NAME;

    @Column(name = "AMOUNT", nullable = false, columnDefinition = "bigint")
    private Integer amount;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = OrderItem.class)
    @JoinColumns({@JoinColumn(name = "ORDER_ITEM_ID", referencedColumnName = "ID", nullable = false)})
    private OrderItem orderItem;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<FurnitureLink2FurnitureLink> furnitureLinks;

    @ManyToOne(targetEntity = PriceAware.class)
    @JoinColumns({@JoinColumn(name = "FURNITURE_TYPE_ID", referencedColumnName = "ID")})
    private T priceAware;

    @ManyToOne(targetEntity = Priced.class)
    @JoinColumns({@JoinColumn(name = "FURNITURE_CODE_ID", referencedColumnName = "ID")})
    private C priced;

    public T getPriceAware()
    {
        return priceAware;
    }

    public void setPriceAware(T priceAware)
    {
        this.priceAware = priceAware;
    }

    public C getPriced()
    {
        return priced;
    }

    public void setPriced(C priced)
    {
        this.priced = priced;
    }

    public String getDiscriminator()
    {
        return discriminator;
    }

    public void setDiscriminator(String discriminator)
    {
        this.discriminator = discriminator;
    }

    public OrderItem getOrderItem()
    {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem)
    {
        this.orderItem = orderItem;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Long getNumber()
    {
        return number;
    }

    public void setNumber(Long number)
    {
        this.number = number;
    }


    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public Integer getAmount()
    {
        return amount;
    }

    /**
     * Используется для очистки елемента
     */
    public void clear()
    {

    }

    public List<FurnitureLink2FurnitureLink> getFurnitureLinks()
    {
        return furnitureLinks;
    }

    public void setFurnitureLinks(List<FurnitureLink2FurnitureLink> furnitureLinks)
    {
        this.furnitureLinks = furnitureLinks;
    }
}
