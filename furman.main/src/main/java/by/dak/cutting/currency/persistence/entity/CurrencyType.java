package by.dak.cutting.currency.persistence.entity;

import java.util.Currency;

import static java.util.Currency.getInstance;

/**
 * User: akoyro
 * Date: 30.04.2010
 * Time: 12:09:32
 */
public enum CurrencyType
{

    USD(getInstance("USD"), 1d),
    EUR(getInstance("EUR"), 1d),
    RUB(getInstance("RUB"), 1d),
    BYR(getInstance("BYR"), 1d);

    private Currency currency;
    private Double defaultPrice;

    CurrencyType(Currency currency, Double defaultPrice)
    {
        this.currency = currency;
        this.defaultPrice = defaultPrice;
    }


    public Currency getCurrency()
    {
        return currency;
    }

    public Double getDefaultPrice()
    {
        return defaultPrice;
    }
}
