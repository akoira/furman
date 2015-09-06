package by.dak.cutting.linear.entity;

import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.cutting.entities.AStripsEntity;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 06.05.11
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
@Entity
@DiscriminatorValue(value = "LinearStripsEntity")
@DiscriminatorOptions(force = true)

public class LinearStripsEntity extends AStripsEntity<FurnitureType, FurnitureCode>
{
    public static final String PROPERTY_ORDER_GROUP = "orderGroup";

    @ManyToOne
    @JoinColumn(name = "ORDER_GROUP_ID", nullable = true)
    private OrderGroup orderGroup;


    public OrderGroup getOrderGroup()
    {
        return orderGroup;
    }

    public void setOrderGroup(OrderGroup orderGroup)
    {
        this.orderGroup = orderGroup;
    }

    public FurnitureCode getFurnitureCode()
    {
        return getPriced();
    }

    public void setFurnitureCode(FurnitureCode furnitureCode)
    {
        setPriced(furnitureCode);
    }

    public FurnitureType getFurnitureType()
    {
        return getPriceAware();
    }

    public void setFurnitureType(FurnitureType furnitureType)
    {
        setPriceAware(furnitureType);
    }
}
