package by.dak.persistence.entities;

import by.dak.persistence.convert.Service2StringConverter;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.persistence.entities.validator.ServiceValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

/**
 * User: akoyro
 * Date: 17.06.2009
 * Time: 15:40:46
 */
@Entity

@Validator(validatorClass = ServiceValidator.class)
@StringValue(converterClass = Service2StringConverter.class)
@DiscriminatorValue(value = "Service")
@DiscriminatorOptions(force = true)
public class Service extends Priced
{
    public static final String PROPERTY_serviceType = "serviceType";

    @Column(name = "SERVICE_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    public ServiceType getServiceType()
    {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType)
    {
        this.serviceType = serviceType;
        setName(serviceType.name());
    }
}
