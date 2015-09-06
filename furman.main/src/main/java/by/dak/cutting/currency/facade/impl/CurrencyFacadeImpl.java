package by.dak.cutting.currency.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.currency.facade.CurrencyFacade;
import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.persistence.entities.Dailysheet;

import java.util.HashMap;
import java.util.List;

/**
 * User: akoyro
 * Date: 29.04.2010
 * Time: 14:50:21
 */
public class CurrencyFacadeImpl extends BaseFacadeImpl<Currency> implements CurrencyFacade
{
    private static HashMap<CurrencyType, Currency> currencyCashe = new HashMap<CurrencyType, Currency>();

    public static SearchFilter getSearchFilterBy(Dailysheet dailysheet)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(Currency.PROPERTY_dailysheet, dailysheet);
        return searchFilter;
    }

    @Override
    public Currency findCurrentBy(CurrencyType currencyType, Dailysheet dailysheet)
    {
        return findCurrentBy(currencyType, dailysheet, true);
    }

    @Override
    public Currency findCurrentBy(CurrencyType currencyType, Dailysheet dailysheet, boolean loadFromBase)
    {
        Currency currency = currencyCashe.get(currencyType);
        if (currency == null || loadFromBase)
        {
            SearchFilter filter = SearchFilter.instanceSingle();
            filter.eq(Currency.PROPERTY_type, currencyType);
            //filter.eq(Currency.PROPERTY_dailysheet, dailysheet);
            List<Currency> currencies = loadAll(filter);
            if (currencies.size() > 0)
            {
                currency = loadAll(filter).get(0);
                currencyCashe.put(currencyType, currency);
            }
        }
        return currency;
    }
}
