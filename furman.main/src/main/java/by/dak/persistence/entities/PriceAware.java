/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.persistence.entities;

import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.Unit;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

/**
 * @author admin
 */

@Proxy(lazy = false)

@Entity

@Table(name = "FURNITURE_TYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
@DiscriminatorOptions(force = true)

public abstract class PriceAware extends PersistenceEntity
{
    public static final String PROPERTY_type = "type";
    public static final String PROPERTY_name = "name";
    public static final String PROPERTY_unit = "unit";

    @Column(name = "MAT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialType type;

    @OneToMany(mappedBy = "priceAware", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<PriceEntity> prices;

    @Column(name = "NAME", nullable = false, length = 255, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column(name = "DEFAULT_SIZE", nullable = false)
    private Double defaultSize = 1d;

    @Column(name = "DISCRIMINATOR", nullable = false, updatable = false, insertable = false)
    private String discriminator;

    @OneToOne
    @JoinColumn(name = "CUTTER_ID", nullable = true)
    private Cutter cutter;

    public String getDiscriminator()
    {
        return discriminator;
    }

    public void setName(String value)
    {
        this.name = value;
    }

    public String getName()
    {
        return name;
    }

    public List<PriceEntity> getPrices()
    {
        return prices;
    }

    public void setPrices(List<PriceEntity> prices)
    {
        this.prices = prices;
    }

//    public void addPrice(PriceEntity price)
//    {
//        this.prices.add(price);
//    }
//
//    public void addPrices(List<PriceEntity> prices)
//    {
//        this.prices.addAll(prices);
//    }

    public MaterialType getType()
    {
        return type;
    }

    public void setType(MaterialType type)
    {
        this.type = type;
    }

    public Unit getUnit()
    {
        return unit;
    }

    public void setUnit(Unit unit)
    {
        this.unit = unit;
    }

    public Double getDefaultSize()
    {
        return defaultSize;
    }

    public void setDefaultSize(Double defaultSize)
    {
        this.defaultSize = defaultSize;
    }

    public Cutter getCutter()
    {
        return cutter;
    }

    public void setCutter(Cutter cutter)
    {
        this.cutter = cutter;
    }
}
