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

import by.dak.persistence.convert.BoardDef2StringConverter;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.validator.BoardDefValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

import static by.dak.persistence.entities.predefined.MaterialType.board;

@Entity
@DiscriminatorValue(value = "BoardDef")
@DiscriminatorOptions(force = true)

@NamedQueries(value =
        {
                @NamedQuery(name = "simpleTypes", query = "from BoardDef fde where fde.simpleType1 is null and fde.simpleType2 is null")
        })
@StringValue(converterClass = BoardDef2StringConverter.class)
@Validator(validatorClass = BoardDefValidator.class)
public class BoardDef extends PriceAware
{
    public static final String PROPERTY_composite = "composite";
    public static final String PROPERTY_cutter = "cutter";
    public static final String PROPERTY_thickness = "thickness";

    public static final String PROPERTY_reservedLength = "reservedLength";
    public static final String PROPERTY_reservedWidth = "reservedWidth";

    public static final String PROPERTY_defaultLength = "defaultLength";
    public static final String PROPERTY_defaultWidth = "defaultWidth";

    public BoardDef()
    {
        setType(board);
        setUnit(Unit.squareMetre);
    }

    @Column(name = "THICKNESS", nullable = false)
    private Long thickness;

    @OneToOne
    @JoinColumn(name = "SIMPLE_ID_1", nullable = true)
    private BoardDef simpleType1;

    @OneToOne
    @JoinColumn(name = "SIMPLE_ID_2", nullable = true)
    private BoardDef simpleType2;

    @Column(name = "DEFAULT_LENGTH", nullable = true)
    private Long defaultLength;

    @Column(name = "DEFAULT_WIDTH", nullable = true)
    private Long defaultWidth;


    @Column(name = "OFFSET_LENGTH", nullable = false)
    private Double reservedLength = 0.0;

    @Column(name = "OFFSET_WIDTH", nullable = false)
    private Double reservedWidth = 0.0;

    public void setThickness(Long thickness)
    {
        this.thickness = thickness;
    }

    public Long getThickness()
    {
        return thickness;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (getName() == null ? 0 : getName().hashCode());
        result = prime * result + (simpleType1 == null ? 0 : simpleType1.hashCode());
        result = prime * result + (simpleType2 == null ? 0 : simpleType2.hashCode());
        result = prime * result + (thickness == null ? 0 : thickness.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!super.equals(obj))
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        BoardDef other = (BoardDef) obj;
        if (getName() == null)
        {
            if (other.getName() != null)
            {
                return false;
            }
        }
        else if (!getName().equals(other.getName()))
        {
            return false;
        }
        if (simpleType1 == null)
        {
            if (other.simpleType1 != null)
            {
                return false;
            }
        }
        else if (!simpleType1.equals(other.simpleType1))
        {
            return false;
        }
        if (simpleType2 == null)
        {
            if (other.simpleType2 != null)
            {
                return false;
            }
        }
        else if (!simpleType2.equals(other.simpleType2))
        {
            return false;
        }
        if (thickness == null)
        {
            if (other.thickness != null)
            {
                return false;
            }
        }
        else if (!thickness.equals(other.thickness))
        {
            return false;
        }
        return true;
    }

    public BoardDef getSimpleType1()
    {
        return simpleType1;
    }

    public void setSimpleType1(BoardDef simpleType1)
    {
        this.simpleType1 = simpleType1;
    }

    public BoardDef getSimpleType2()
    {
        return simpleType2;
    }

    public void setSimpleType2(BoardDef simpleType2)
    {
        this.simpleType2 = simpleType2;
    }

    public Boolean getComposite()
    {
        return simpleType1 != null && simpleType2 != null;
    }

    //dummy метод нужет только для создания правильного editor 
    public void setComposite(Boolean complex)
    {
    }


    public Long getDefaultLength()
    {
        return defaultLength;
    }

    public void setDefaultLength(Long defaultLength)
    {
        this.defaultLength = defaultLength;
    }

    public Long getDefaultWidth()
    {
        return defaultWidth;
    }

    public void setDefaultWidth(Long defaultWidth)
    {
        this.defaultWidth = defaultWidth;
    }

    public Double getReservedLength()
    {
        return reservedLength;
    }

    public void setReservedLength(Double reservedLength)
    {
        this.reservedLength = reservedLength;
    }

    public Double getReservedWidth()
    {
        return reservedWidth;
    }

    public void setReservedWidth(Double reservedWidth)
    {
        this.reservedWidth = reservedWidth;
    }
}