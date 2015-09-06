package by.dak.persistence.cutting.entities;

import by.dak.persistence.entities.PersistenceEntity;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 05.05.11
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "STRIPS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")

public abstract class AStripsEntity<T extends PriceAware, C extends Priced> extends PersistenceEntity
{
    public final static String PROPERTY_priceAware = "priceAware";
    public final static String PROPERTY_priced = "priced";

    @Column(name = "SAW_WIDTH", columnDefinition = "bigint")
    private Integer sawWidth = 0;

    @Column(name = "ROTATION", columnDefinition = "bit")
    private Boolean rotation = Boolean.FALSE;

    @Column(name = "STRIPS", nullable = false, columnDefinition = "longtext")
    private String strips;

    @Column(name = "PAIDVALUE")
    private Double paidValue = 0.0d;

    @Column(name = "DISCRIMINATOR", nullable = false, updatable = false, insertable = false)
    private String discriminator;

    @ManyToOne(targetEntity = PriceAware.class)
    @JoinColumns({@JoinColumn(name = "FURNITURE_TYPE_ID", referencedColumnName = "ID")})
    private T priceAware;

    @ManyToOne(targetEntity = Priced.class)
    @JoinColumns({@JoinColumn(name = "FURNITURE_CODE_ID", referencedColumnName = "ID")})
    private C priced;

    public String getDiscriminator()
    {
        return discriminator;
    }

    public void setDiscriminator(String discriminator)
    {
        this.discriminator = discriminator;
    }

    public Integer getSawWidth()
    {
        return sawWidth;
    }

    public void setSawWidth(Integer sawWidth)
    {
        this.sawWidth = sawWidth;
    }

    public Boolean getRotation()
    {
        return rotation;
    }

    public void setRotation(Boolean rotation)
    {
        this.rotation = rotation;
    }

    public Double getPaidValue()
    {
        return paidValue;
    }

    public void setPaidValue(Double paidValue)
    {
        this.paidValue = paidValue;
    }

    public String getStrips()
    {
        return strips;
    }

    public void setStrips(String strips)
    {
        this.strips = strips;
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
}
