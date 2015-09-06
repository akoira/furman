package by.dak.persistence.entities;

import by.dak.persistence.convert.FurnitureLink2StringConverter;
import by.dak.persistence.entities.validator.FurnitureLinkValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.03.2010
 * Time: 10:54:09
 * To change this template use File | Settings | File Templates.
 */
@Entity

@DiscriminatorValue(value = "FurnitureLink")
@DiscriminatorOptions(force = true)
@NamedQueries(value = {
        @NamedQuery(name = "loadUniquePairsByOrderGroup",
                query = "select distinct fl.priceAware, fl.priced from FurnitureLink fl" +
                        " where fl.orderItem.order.orderGroup = :orderGroup and fl.priceAware.unit = :unit"
        ),
        @NamedQuery(name = "sumDetailsByOrderGroup",
                query = "select sum (fl.amount * fl.orderItem.amount) from FurnitureLink fl " +
                        "where fl.orderItem.order.orderGroup = :orderGroup" +
                        " and fl.priceAware = :furnitureType " +
                        "and fl.priced = :furnitureCode")

})

@StringValue(converterClass = FurnitureLink2StringConverter.class)
@Validator(validatorClass = FurnitureLinkValidator.class)


public class FurnitureLink extends AFurnitureDetail
{

    public FurnitureLink()
    {
        super();
    }

    public FurnitureLink(String name)
    {
        setName(name);
    }

    public static FurnitureLink valueOf(FurnitureLink furnitureLink)
    {
        FurnitureLink newFurnitureLink = new FurnitureLink();
        newFurnitureLink.setSize(furnitureLink.getSize());
        newFurnitureLink.setAmount(furnitureLink.getAmount());
        newFurnitureLink.setName(furnitureLink.getName());
        newFurnitureLink.setNumber(furnitureLink.getNumber());
        newFurnitureLink.setFurnitureCode(furnitureLink.getFurnitureCode());
        newFurnitureLink.setFurnitureType(furnitureLink.getFurnitureType());
        return newFurnitureLink;
    }

    @Override
    public void clear()
    {
        setFurnitureCode(null);
        setFurnitureType(null);
    }
}
