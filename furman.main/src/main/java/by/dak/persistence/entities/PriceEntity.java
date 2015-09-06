/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 *
 * This is an automatic generated file. It will be regenerated every time you generate persistence class.
 *
 * Modifying its content may cause the program not work, or your work may lost.
 */
/**
 * Licensee: Anonymous License Type: Purchased
 */
package by.dak.persistence.entities;

import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.cutting.swing.dictionaries.multiselect.Priced2StringConverter;
import by.dak.persistence.entities.validator.PriceValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity

@Table(name = "PRICE")
@NamedQueries(value =
        {
        })

@Validator(validatorClass = PriceValidator.class)
@StringValue(converterClass = Priced2StringConverter.class)

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PriceEntity extends PersistenceEntity
{
    public static final String PROPERTY_price = "price";

    public static final String PROPERTY_priceDealer = "priceDealer";

    public static final String PROPERTY_priceFaktor = "priceFaktor";

    public static final String PROPERTY_priceAware = "priceAware";

    public static final String PROPERTY_priced = "priced";

    public static final String PROPERTY_currencyType = "currencyType";

    public static final String PROPERTY_priceSale = "priceSale";

    public static final String PROPERTY_priceSaleFaktor = "priceSaleFaktor";

    @Column(name = "PRICE", nullable = false)
    private Double price = 0d;

    @Column(name = "PRICE_DEALER", nullable = true)
    private Double priceDealer = 0d;

    @Column(name = "PRICE_FAKTOR", nullable = true)
    private Double priceFaktor = 0.0d;


    @Column(name = "PRICE_SALE", nullable = false)
    private Double priceSale = 0d;

    @Column(name = "PRICE_SALE_FACTOR", nullable = true)
    private Double priceSaleFaktor = 0.0d;


    @ManyToOne(targetEntity = PriceAware.class)
    @JoinColumn(nullable = false)
    private PriceAware priceAware;

    @ManyToOne(targetEntity = Priced.class)
    @JoinColumn(nullable = false)
    private Priced priced;


    @Column(name = "CURRENCY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType = CurrencyType.USD;

    public Double getPriceDealer()
    {
        return priceDealer;
    }

    public void setPriceDealer(Double priceDealer)
    {
        Double old = this.priceDealer;
        this.priceDealer = priceDealer;
        support.firePropertyChange(PROPERTY_priceDealer, old, priceDealer);
    }

    public Double getPriceFaktor()
    {
        return priceFaktor;
    }

    public void setPriceFaktor(Double priceFaktor)
    {
        Double old = this.priceFaktor;
        this.priceFaktor = priceFaktor;
        support.firePropertyChange(PROPERTY_priceFaktor, old, priceFaktor);
    }

    public void setPrice(Double price)
    {
        Double old = this.price;
        this.price = price;
        support.firePropertyChange(PROPERTY_price, old, price);
    }

    public Double getPrice()
    {
        return price;
    }


    public PriceAware getPriceAware()
    {
        return priceAware;
    }

    public void setPriceAware(PriceAware priceAware)
    {
        this.priceAware = priceAware;
    }

    public Priced getPriced()
    {
        return priced;
    }

    public void setPriced(Priced priced)
    {
        this.priced = priced;
    }

    public static PriceEntity valueOf(PriceEntity price)
    {
        PriceEntity newPrice = new PriceEntity();
        newPrice.setPriceAware(price.getPriceAware());
        newPrice.setPriced(price.getPriced());
        newPrice.setPrice(price.getPrice());
        newPrice.setPriceDealer(price.getPriceDealer());
        newPrice.setPriceFaktor(price.getPriceFaktor());
        newPrice.setPriceSale(price.getPriceSale());
        newPrice.setPriceSale(price.getPriceSaleFaktor());
        return newPrice;
    }

    public CurrencyType getCurrencyType()
    {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType)
    {
        this.currencyType = currencyType;
    }

    public Double getPriceSale()
    {
        return priceSale;
    }

    public void setPriceSale(Double priceSale)
    {
        Double old = this.priceSale;
        this.priceSale = priceSale;
        support.firePropertyChange(PROPERTY_priceSale, old, priceSale);
    }

    public Double getPriceSaleFaktor()
    {
        return priceSaleFaktor;
    }

    public void setPriceSaleFaktor(Double priceSaleFaktor)
    {
        Double old = this.priceSaleFaktor;
        this.priceSaleFaktor = priceSaleFaktor;
        support.firePropertyChange(PROPERTY_priceSaleFaktor, old, priceSaleFaktor);

    }
}