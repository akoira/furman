package by.dak.additional;

import by.dak.additional.validator.AdditionalValidator;
import by.dak.persistence.entities.AOrderDetail;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: akoyro
 * Date: 19.06.2010
 * Time: 16:17:49
 */
@Entity
@DiscriminatorValue(value = "Additional")
@DiscriminatorOptions(force = true)

@Validator(validatorClass = AdditionalValidator.class)
public class Additional extends AOrderDetail
{
    @Column(nullable = false)
    private String type;
    @Column(name = "SIZE", nullable = false)
    private Double size;
    @Column(nullable = false)
    private Double price;

    public Additional()
    {
        setAmount(1);
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }


    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getSize()
    {
        return size;
    }

    public void setSize(Double size)
    {
        this.size = size;
    }

    public static Additional valueOf(Additional additional)
    {
        Additional result = new Additional();
        result.setPrice(additional.getPrice());
        result.setSize(additional.getSize());
        result.setType(additional.getType());
        result.setName(additional.getName());
        result.setNumber(additional.getNumber());
        return result;
    }
}
