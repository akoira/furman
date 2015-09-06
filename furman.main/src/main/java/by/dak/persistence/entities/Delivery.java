package by.dak.persistence.entities;

import by.dak.persistence.convert.Delivery2StringConverter;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.validator.DeliveryValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 01.02.2010
 * Time: 15:23:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "DELIVERY")

@Validator(validatorClass = DeliveryValidator.class)
@StringValue(converterClass = Delivery2StringConverter.class)

public class Delivery extends PersistenceEntity
{
    public static final String PROPERTY_materialType = "materialType";

    @Column(name = "DELIVERY_DATE")
    private Date deliveryDate = new Date();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Provider.class)
    @JoinColumns(value = {@JoinColumn(name = "PROVIDER_ID", referencedColumnName = "ID")})
    private Provider provider;

    @Column(name = "MAT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private by.dak.persistence.entities.predefined.MaterialType materialType;

    public Date getDeliveryDate()
    {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate)
    {
        this.deliveryDate = deliveryDate;
    }

    public Provider getProvider()
    {
        return provider;
    }

    public void setProvider(Provider provider)
    {
        this.provider = provider;
    }

    public MaterialType getMaterialType()
    {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType)
    {
        this.materialType = materialType;
    }
}
