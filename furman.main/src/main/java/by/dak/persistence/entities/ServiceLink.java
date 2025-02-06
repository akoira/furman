package by.dak.persistence.entities;

import by.dak.persistence.convert.FurnitureLink2StringConverter;
import by.dak.persistence.entities.validator.ServiceLinkValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity

@DiscriminatorValue(value = "Service")
@DiscriminatorOptions(force = true)

@StringValue(converterClass = FurnitureLink2StringConverter.class)
@Validator(validatorClass = ServiceLinkValidator.class)

public class ServiceLink extends AServiceDetail {

    public ServiceLink() {
        super();
    }

    public ServiceLink(String name) {
        setName(name);
    }

    public static ServiceLink valueOf(ServiceLink serviceLink) {
        ServiceLink newFurnitureLink = new ServiceLink();
        newFurnitureLink.setSize(serviceLink.getSize());
        newFurnitureLink.setAmount(serviceLink.getAmount());
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
