package by.dak.template;

import by.dak.category.Category;
import by.dak.persistence.entities.AOrder;
import by.dak.template.validator.TemplateOrderValidator;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Proxy(lazy = false)
@DiscriminatorValue(value = "TemplateOrder")
@DiscriminatorOptions(force = true)

@Validator(validatorClass = TemplateOrderValidator.class)
public class TemplateOrder extends AOrder
{
    public static final String PROPERTY_category = "category";
    public static final String PROPERTY_saleFactor = "saleFactor";
    public static final String PROPERTY_salePrice = "salePrice";

    @ManyToOne
    private Category category;

    @Column(name = "SALE_FACTOR")
    private Double saleFactor = 1d;

    @Column(name = "SALE_PRICE")
    private Double salePrice;

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public Double getSaleFactor()
    {
        return saleFactor;
    }

    public void setSaleFactor(Double saleFactor)
    {
        Double old = this.saleFactor;
        this.saleFactor = saleFactor;
        support.firePropertyChange(PROPERTY_saleFactor, old, saleFactor);
    }

    public Double getSalePrice()
    {
        return salePrice;
    }

    public void setSalePrice(Double salePrice)
    {
        Double old = this.salePrice;
        this.salePrice = salePrice;
        support.firePropertyChange(PROPERTY_salePrice, old, salePrice);
    }
}
