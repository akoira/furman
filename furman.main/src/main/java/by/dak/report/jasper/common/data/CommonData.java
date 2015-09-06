package by.dak.report.jasper.common.data;

import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.utils.MathUtils;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;


@Entity
@Table(name = "COMMON_DATA")
@DiscriminatorValue(value = "CommonData")

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")

@DiscriminatorOptions(force = true)

@NamedQueries(value =
        {
                @NamedQuery(name = "deleteAllByOrder",
                        query = "delete from CommonData co where " +
                                "co.order = :order"),
                @NamedQuery(name = "statisticsServices",
                        query = "select cd.service as service, cd.name as name, sum(cd.count) as amount " +
                                "from CommonData cd " +
                                "where " +
                                "cd.order.readyDate >= :start and " +
                                "cd.order.readyDate <= :end and " +
                                "cd.order.customer.id >= :startCustomerId and " +
                                "cd.order.customer.id <= :endCustomerId and " +
                                "cd.order.id >= :startOrderId and " +
                                "cd.order.id <= :endOrderId and " +
                                "cd.commonDataType = :commonDataType and " +
                                "cd.order.status in ( :status ) " +
                                "group by cd.service, cd.name " +
                                "order by cd.service, cd.name")

        }
)


public class CommonData extends PersistenceEntity implements Comparable<CommonData>
{
    public static final String PROPERTY_commonDataType = "commonDataType";
    public static final String PROPERTY_order = "order";

    @Column(name = "CTYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonDataType commonDataType;

    @Column(nullable = false)
    private String service = "";

    @Column(nullable = false)
    private String name = "";

    @Column(name = "AMOUNT", nullable = false)
    private Double count = 0d;

    @Column(nullable = false)
    private Double price = 0d;

    @Column(name = "DIALER_PRICE", nullable = false)
    private Double dialerPrice = 0d;

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean last = false;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private AOrder order;


    @Transient
    protected Double dialerCost;

    @Transient
    protected Double cost;

    public CommonData()
    {
    }

    public CommonData(String service, String name)
    {
        this(service, name, 0L, 0L);
    }

    public CommonData(String service, String name, double count, double price)
    {
        this.service = service;
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getCount()
    {
        return count;
    }

    public double getRoundCount()
    {
        return MathUtils.round(getCount(), 2);
    }

    public void setCount(Double count)
    {
        this.count = count;
    }

    public void increaseCount(Double count)
    {
        increase(count);
    }

    public void increase(Double count)
    {
        this.count += count;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getDialerCost()
    {
        if (dialerCost == null)
        {
            dialerCost = MathUtils.round(getCount() * getDialerPrice(), 2);
        }
        return dialerCost;
    }

    public Double getCost()
    {
        if (cost == null)
        {
            cost = MathUtils.round(getCount() * getPrice(), 2);
        }
        return cost;
    }

    @Override
    public int hashCode()
    {
        return getName().hashCode() + getService().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        assert obj != null && obj instanceof CommonData;
        CommonData data = (CommonData) obj;
        return getName().equals(data.getName()) && getService().equals(data.getService());
    }

    public int compareTo(CommonData o)
    {
		//we need reset this flag because we can sort the objects few times
		last = false;
        o.last = false;
        int result = getService().compareTo(o.getService());
        return result == 0 ? getName().compareTo(o.getName()) : result;
    }

    public void markAsLast()
    {
        last = true;
    }

    public Boolean getLast()
    {
        return last;
    }

    public void setLast(Boolean last)
    {
        this.last = last;
    }

    public AOrder getOrder()
    {
        return order;
    }

    public void setOrder(AOrder order)
    {
        this.order = order;
    }

    public CommonDataType getCommonDataType()
    {
        return commonDataType;
    }

    public void setCommonDataType(CommonDataType commonDataType)
    {
        this.commonDataType = commonDataType;
    }

    public Double getDialerPrice()
    {
        return dialerPrice;
    }

    public void setDialerPrice(Double dialerPrice)
    {
        this.dialerPrice = dialerPrice;
    }

    public boolean isEmptyPrice()
    {
        return (getPrice() == null || getPrice() <= 0);
    }

    public CommonData cloneForDialer()
    {
        CommonData commonData = new CommonData();
        fillCloneForDialer(commonData);
        return commonData;
    }

    protected void fillCloneForDialer(CommonData commonData)
    {
        commonData.commonDataType = commonDataType;
        commonData.service = service;
        commonData.name = name;

        commonData.count = count;

        commonData.price = dialerPrice;
        commonData.dialerPrice = dialerPrice;
        commonData.order = order;
        commonData.last = last;
        commonData.setCost(getDialerCost());
    }


    protected void setDialerCost(Double dialerCost)
    {
        this.dialerCost = dialerCost;
    }

    protected void setCost(Double cost)
    {
        this.cost = cost;
    }

    public static CommonData valueOf(ServiceType serviceType, PriceAware priceAware)
    {
        return new CommonData(StringValueAnnotationProcessor.getProcessor().convert(serviceType),
                StringValueAnnotationProcessor.getProcessor().convert(priceAware));
    }


    public static CommonData valueOf(PriceAware priceAware, Priced priced)
    {
        return new CommonData(StringValueAnnotationProcessor.getProcessor().convert(priceAware),
                StringValueAnnotationProcessor.getProcessor().convert(priced));
    }

}