package by.dak.persistence.entities;

import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Proxy(lazy = false)
@Entity

@Table(name = "FURNITURE_CODE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@DiscriminatorColumn(name = "PRICED_TYPE")
@DiscriminatorOptions(force = true)

public abstract class Priced extends PersistenceEntity
{

    public static final String PROPERTY_name = "name";
    public static final String PROPERTY_code = "code";
    public static final String PROPERTY_manufacturer = "manufacturer";
    public static final String PROPERTY_groupIdentifier = "groupIdentifier";

    /**
     * Нужна для постраения правельного запроса для прайсов
     */
    @Column(name = "PRICED_TYPE", nullable = false, updatable = false, insertable = false)
    private String pricedType;

    @OneToMany(mappedBy = "priced", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PriceEntity> prices;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Manufacturer.class)
    @JoinColumn(nullable = false)
    private Manufacturer manufacturer;

    @Column(name = "NAME", nullable = false)
    private String name;


    @Column(name = "CODE", nullable = false)
    private String code = "0";


    @Column(name = "GROUP_IDENTIFIER", nullable = true, length = 255)
    private String groupIdentifier;

    public String getGroupIdentifier()
    {
        return groupIdentifier;
    }

    public void setGroupIdentifier(String groupIdentifier)
    {
        this.groupIdentifier = groupIdentifier;
    }


    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }


    public List<PriceEntity> getPrices()
    {
        return prices;
    }

    public void setPrices(List<PriceEntity> prices)
    {
        this.prices = prices;
    }

    public Manufacturer getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getPricedType()
    {
        return pricedType;
    }

    public void setPricedType(String pricedType)
    {
        this.pricedType = pricedType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


}
