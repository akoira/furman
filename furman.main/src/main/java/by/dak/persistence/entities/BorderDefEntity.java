/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 *
 * This is an automatic generated file. It will be regenerated every time you generate persistence class.
 *
 * Modifying its content may cause the program not work, or your work may lost.
 */
/**
 * Licensee: Anonymous License Type: Purchased
 */
package by.dak.persistence.entities;

import by.dak.persistence.convert.BorderDef2StringConverter;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.utils.convert.StringValue;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static by.dak.persistence.entities.predefined.MaterialType.border;

@Proxy(lazy = false)
@Entity
@DiscriminatorValue(value = "BorderDef")
@DiscriminatorOptions(force = true)

@StringValue(converterClass = BorderDef2StringConverter.class)
public class BorderDefEntity extends PriceAware
{
    public BorderDefEntity()
    {
        setType(border);
        setUnit(Unit.linearMetre);
    }

    @Column(name = "HEIGHT", nullable = false)
    private Long height;

    @Column(name = "THICKNESS", nullable = false)
    private Long thickness;

    /**
     * @return the height
     */
    public Long getHeight()
    {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(Long height)
    {
        this.height = height;
    }

    /**
     * @return the thickness
     */
    public Long getThickness()
    {
        return thickness;
    }

    /**
     * @param thickness the thickness to set
     */
    public void setThickness(Long thickness)
    {
        this.thickness = thickness;
    }
}
