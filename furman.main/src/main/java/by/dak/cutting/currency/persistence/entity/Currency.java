package by.dak.cutting.currency.persistence.entity;

import by.dak.cutting.currency.persistence.entity.validator.CurrencyValidator;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.utils.validator.Validator;

import javax.persistence.*;

/**
 * User: akoyro
 * Date: 29.04.2010
 * Time: 14:13:50
 */
@Entity
@Table(name = "CURRENCY")
@Validator(validatorClass = CurrencyValidator.class)

public class Currency extends PersistenceEntity
{
    public static final String PROPERTY_type = "type";
    public static final String PROPERTY_price = "price";
    public static final String PROPERTY_dailysheet = "dailysheet";


    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType type;

    @Column(name = "price", nullable = false)
    private Double price;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Dailysheet.class, fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "DAILYSHEET_ID", referencedColumnName = "ID")})
    private Dailysheet dailysheet;

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Dailysheet getDailysheet()
    {
        return dailysheet;
    }

    public void setDailysheet(Dailysheet dailysheet)
    {
        this.dailysheet = dailysheet;
    }

    public CurrencyType getType()
    {
        return type;
    }

    public void setType(CurrencyType type)
    {
        this.type = type;
    }
}
