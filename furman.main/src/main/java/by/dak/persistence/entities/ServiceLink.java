package by.dak.persistence.entities;

import by.dak.persistence.convert.FurnitureLink2StringConverter;
import by.dak.persistence.entities.validator.ServiceLinkValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity

@DiscriminatorValue(value = "Service")
@DiscriminatorOptions(force = true)

@StringValue(converterClass = FurnitureLink2StringConverter.class)
@Validator(validatorClass = ServiceLinkValidator.class)

@NamedQueries(value =
        {
                @NamedQuery(name = "statServices",
                        query = "select sl.priced as service, sl.priceAware.name as name, sum(sl.size) as size " +
                                "from ServiceLink sl " +
                                "where " +
                                "sl.orderItem.order.readyDate >= :start and " +
                                "sl.orderItem.order.readyDate <= :end and " +
                                "sl.orderItem.order.customer.id >= :startCustomerId and " +
                                "sl.orderItem.order.customer.id <= :endCustomerId and " +
                                "sl.orderItem.order.id >= :startOrderId and " +
                                "sl.orderItem.order.id <= :endOrderId and " +
                                "sl.priced.name = :serviceType and " +
                                "sl.orderItem.order.status in ( :status ) and " +
                                "sl.orderItem.order.deleted = false " +
                                "group by sl.priced.name, sl.priceAware.name " +
                                "order by sl.priced.name, sl.priceAware.name")
        }
)

public class ServiceLink extends AServiceDetail {

    public ServiceLink() {
        super();
        setAmount(1);
    }

    public ServiceLink(String name) {
        setName(name);
        setAmount(1);
    }

    public static ServiceLink valueOf(ServiceLink serviceLink) {
        ServiceLink newFurnitureLink = new ServiceLink();
        newFurnitureLink.setSize(serviceLink.getSize());
        newFurnitureLink.setName(serviceLink.getName());
        newFurnitureLink.setNumber(serviceLink.getNumber());
        newFurnitureLink.setService(serviceLink.getService());
        newFurnitureLink.setPriceAware(serviceLink.getPriceAware());
        return newFurnitureLink;
    }

    @Override
    public void clear() {
        setService(null);
        setPriceAware(null);
    }
}
