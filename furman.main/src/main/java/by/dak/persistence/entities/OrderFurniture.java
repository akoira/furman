package by.dak.persistence.entities;

import by.dak.persistence.convert.OrderFurniture2StringConverter;
import by.dak.persistence.entities.validator.OrderFurnitureValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Proxy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Denis Koyro
 * @version 0.1 16.10.2008
 * @introduced [Builder | Overview ]
 * @since 2.0.0
 */
@Entity
@Proxy(lazy = false)
@DiscriminatorValue(value = "OrderFurniture")
@DiscriminatorOptions(force = true)

@NamedQueries(value =
        {
                @NamedQuery(name = "uniquePairsByOrder",
                        query = "select distinct ofe.priced as texture, ofe.priceAware as furnitureDef  from OrderFurniture  ofe where ofe.orderItem.order = :order"),
                @NamedQuery(name = "uniqueSimpleDefinitions",
                        query = "select distinct ofe.priceAware as furnitureDef  from OrderFurniture  ofe where ofe.orderItem.order = :order and ofe.comlexBoardDef is null"),
                @NamedQuery(name = "simpleFurniture",
                        query = "from OrderFurniture ofe where ofe.orderItem.order = :order and ofe.comlexBoardDef is null and ofe.second is null"),
//                @NamedQuery(name = "withA45ByOrderDef",
//                        query = "select distinct ofe from OrderFurniture  ofe where ofe.orderItem.order = :order and ofe.definition = :definition and ofe.childComponents is empty and ofe.angle is not null")
                @NamedQuery(
                        name = "deleteSecond",
                        query = "delete from OrderFurniture where second =:fist"),
                @NamedQuery(
                        name = "findOrderedWithGlueing",
                        query = "from OrderFurniture ofe where ofe.orderItem.order = :order and ofe.primary = :primary and (ofe.milling is not null or ofe.glueing is not null)"),
                @NamedQuery(
                        name = "findOrderedComplex",
                        query = "from OrderFurniture ofe where ofe.orderItem.order = :order and ofe.primary = :primary and ofe.comlexBoardDef is not null"),
                @NamedQuery(
                        name = "findOrderedBySize",
                        query = "from OrderFurniture ofe where ofe.orderItem.order = :order " +
                                "and ofe.priceAware = :boardDef " +
                                "and ofe.priced = :texture " +
                                "order by ofe.length , ofe.width"),
                @NamedQuery(name = "furnituresWithCutoff",
                        query = "from OrderFurniture ofe where ofe.orderItem.order = :order and ofe.cutoff is not null"),
                @NamedQuery(name = "furnituresWithMilling",
                        query = "from OrderFurniture ofe where ofe.orderItem.order = :order and ofe.milling is not null"),

                @NamedQuery(name = "sumOrderFurnitures",
                        query = "select sum(ofe.amount * ofe.orderItem.amount) from OrderFurniture ofe where ofe.orderItem.order = :order and ofe.priced = :texture and ofe.priceAware = :boardDef")
        }
)

@StringValue(converterClass = OrderFurniture2StringConverter.class)
@Validator(validatorClass = OrderFurnitureValidator.class)
public class OrderFurniture extends AOrderBoardDetail
{
    public OrderFurniture()
    {
        super();
    }

    public OrderFurniture(String name)
    {
        super(name);
    }
}
