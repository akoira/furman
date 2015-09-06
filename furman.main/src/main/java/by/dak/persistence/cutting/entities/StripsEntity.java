package by.dak.persistence.cutting.entities;

import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * User: admin
 * Date: 06.03.2009
 * Time: 20:18:12
 */
@Entity
@NamedQueries(value =
        {
                @NamedQuery(
                        name = "deleteAllStripsEntity",
                        query = "delete from StripsEntity se where se.order = :order"
                ),

                @NamedQuery(name = "statistics",
                        query = "select strips.priceAware.id as type,strips.priced.id as code, count(strips.id) as amount, sum(strips.paidValue) as size " +
                                "from StripsEntity strips " +
                                "where " +
                                "strips.priceAware.id = :type and " +
                                "strips.priced.id = :code and " +
                                "strips.order.readyDate >= :start and " +
                                "strips.order.readyDate <= :end and " +
                                "strips.order.customer.id >= :startCustomerId and " +
                                "strips.order.customer.id <= :endCustomerId and " +
                                "strips.order.id >= :startOrderId and " +
                                "strips.order.id <= :endOrderId and " +
                                "strips.order.status in ( :status )" +
                                "group by strips.priceAware.id, " +
                                "strips.priced.id"),
                @NamedQuery(name = "uniquePairsByStatisticFilter",
                        query = "select distinct strips.priced as texture, strips.priceAware as furnitureDef, " +
                                "strips.priceAware.name,  strips.priced.name " +
                                "from StripsEntity strips where " +
                                "strips.order.readyDate >= :start and " +
                                "strips.order.readyDate <= :end and " +
                                "strips.order.customer.id >= :startCustomerId and " +
                                "strips.order.customer.id <= :endCustomerId and " +
                                "strips.order.id >= :startOrderId and " +
                                "strips.order.id <= :endOrderId and " +
                                "strips.order.status in ( :status ) " +
                                "order by " +
                                "strips.priceAware.name, " +
                                "strips.priced.name")


        }
)

@DiscriminatorValue(value = "StripsEntity")
@DiscriminatorOptions(force = true)

public class StripsEntity extends ABoardStripsEntity
{
}
