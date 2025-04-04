package by.dak.persistence.entities;

import by.dak.persistence.convert.ServicePlugType2StringConverter;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.utils.convert.StringValue;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

import static by.dak.persistence.entities.predefined.MaterialType.servicePlug;

@Entity
@DiscriminatorValue(value = "ServicePlugType")
@DiscriminatorOptions(force = true)

@StringValue(converterClass = ServicePlugType2StringConverter.class)
public class ServicePlugType extends PriceAware {

    public ServicePlugType() {
        setType(servicePlug);
    }

}