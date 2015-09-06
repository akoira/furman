package by.dak.persistence.entities;

import by.dak.persistence.convert.Furniture2StringConverter;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.persistence.entities.validator.FurnitureValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 27.01.2010
 * Time: 15:50:48
 * To change this template use File | Settings | File Templates.
 */
@Entity

@DiscriminatorValue(value = "Furniture")
@DiscriminatorOptions(force = true)

@Validator(validatorClass = FurnitureValidator.class)
@StringValue(converterClass = Furniture2StringConverter.class)

@NamedQueries(value =
        {
                @NamedQuery(name = "deleteFurnitureByTemplate",
                        query = "delete from Furniture b where " +
                                "b.order is null and " +
                                "(b.status = :status0 or " +
                                "b.status = :status1) and " +
                                "b.priced = :furnitureCode and " +
                                "b.priceAware = :furnitureType and " +
                                "b.provider = :provider"
                )
        }
)

public class Furniture extends AStoreElement<FurnitureType, FurnitureCode>
{
    public static final String PROPERTY_size = "size";

    public static final String PROPERTY_furnitureType = "furnitureType";

    public static final String PROPERTY_furnitureCode = "furnitureCode";

    @Column(name = "SIZE", nullable = false)
    private Double size = 1d;

    public Furniture()
    {
        setAmount(1);
    }

    public FurnitureType getFurnitureType()
    {
        return getPriceAware();
    }

    public void setFurnitureType(FurnitureType furnitureType)
    {
        setPriceAware(furnitureType);
    }

    public FurnitureCode getFurnitureCode()
    {
        return getPriced();
    }

    public void setFurnitureCode(FurnitureCode furnitureCode)
    {
        setPriced(furnitureCode);
    }


    public Furniture clone()
    {
        Furniture entity = new Furniture();
        entity.setSize(getSize());
        cloneFilling(entity);
        return entity;
    }

    public Double getSize()
    {
        return size;
    }

    public void setSize(Double size)
    {
        Double old = this.size;
        this.size = size;
        support.firePropertyChange("size", old, size);
    }

    public void setUnit(Unit unit)
    {
        support.firePropertyChange("unit", null, unit);
    }

    public Unit getUnit()
    {
        return getFurnitureType() != null ? getFurnitureType().getUnit() : null;
    }
}
