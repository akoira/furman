package by.dak.persistence.entities;

import by.dak.persistence.entities.predefined.StoreElementStatus;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

/**
 * User: akoyro
 * Date: 18.11.2009
 * Time: 21:14:14
 */

@Entity
@Table(name = "FURNITURE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
@DiscriminatorOptions(force = true)


public abstract class AStoreElement<T extends PriceAware, C extends Priced> extends PersistenceEntity
{
    public static final String PROPERTY_amount = "amount";
    public static final String PROPERTY_order = "order";
    public static final String PROPERTY_status = "status";
    /**
     * The property is used to mark that the storage element already ordered and we just are waiting delivery.
     */
    public static final String PROPERTY_ordered = "ordered";
    public static final String PROPERTY_priceAware = "priceAware";
    public static final String PROPERTY_priced = "priced";
    public static final String PROPERTY_price = "price";
    public static final String PROPERTY_createdByOrder = "createdByOrder";
    public static final String PROPERTY_discriminator = "discriminator";

    @Column(name = "AMOUNT", nullable = false, columnDefinition = "bigint")
    private Integer amount = 1;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Order.class)
    @JoinColumns({@JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")})
    private Order order;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Order.class)
    @JoinColumns({@JoinColumn(name = "CREATEDBY_ORDER_ID", referencedColumnName = "ID")})
    private Order createdByOrder;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Provider.class)
    @JoinColumns({@JoinColumn(name = "PROVIDER_ID", referencedColumnName = "ID")})
    private Provider provider;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Delivery.class)
    @JoinColumns({@JoinColumn(name = "DELIVERY_ID", referencedColumnName = "ID")})
    private Delivery delivery;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreElementStatus status = StoreElementStatus.order;

    @Column(name = "PRICE", nullable = false)
    private Double price = 0.0;

    @Column(name = "ORDERED", nullable = true, columnDefinition = "bit")
    private Boolean ordered;

    //    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumns({@JoinColumn(name = "FURNITURE_TYPE_ID", referencedColumnName = "ID")})
    @ManyToOne(targetEntity = PriceAware.class)
    @JoinColumns({@JoinColumn(name = "FURNITURE_TYPE_ID", referencedColumnName = "ID")})
    private T priceAware;

    //    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumns({@JoinColumn(name = "FURNITURE_CODE_ID", referencedColumnName = "ID")})
    @ManyToOne(targetEntity = Priced.class)
    @JoinColumns({@JoinColumn(name = "FURNITURE_CODE_ID", referencedColumnName = "ID")})
    private C priced;

    @Column(name = "DISCRIMINATOR", nullable = false, updatable = false, insertable = false)
    private String discriminator;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Order bookedByOrder;


    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public Order getCreatedByOrder()
    {
        return createdByOrder;
    }

    public void setCreatedByOrder(Order createdByOrder)
    {
        this.createdByOrder = createdByOrder;
    }

    public StoreElementStatus getStatus()
    {
        return status;
    }

    public void setStatus(StoreElementStatus status)
    {
        this.status = status;
    }

    public Provider getProvider()
    {
        return provider;
    }

    public void setProvider(Provider provider)
    {
        this.provider = provider;
    }

    public Delivery getDelivery()
    {
        return delivery;
    }

    public void setDelivery(Delivery delivery)
    {
        this.delivery = delivery;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        Double old = this.price;
        this.price = price;
        support.firePropertyChange("price", old, price);
    }

    @Override
    public Object clone()
    {
        return null;
    }

    protected void cloneFilling(AStoreElement element)
    {
        element.setStatus(getStatus());
        element.setProvider(getProvider());
        element.setDelivery(getDelivery());
        element.setOrder(getOrder());
        element.setCreatedByOrder(getCreatedByOrder());
        element.setPrice(getPrice());
        element.setAmount(getAmount());
        element.setPriceAware(getPriceAware());
        element.setPriced(getPriced());
    }

    public Boolean getOrdered()
    {
        return ordered;
    }


    public void setOrdered(Boolean ordered)
    {
        this.ordered = ordered;
    }

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

    public Order getBookedByOrder()
    {
        return bookedByOrder;
    }

    public void setBookedByOrder(Order bookedByOrder)
    {
        this.bookedByOrder = bookedByOrder;
    }
}
