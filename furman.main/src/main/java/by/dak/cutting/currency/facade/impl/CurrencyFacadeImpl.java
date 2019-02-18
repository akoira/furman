package by.dak.cutting.currency.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.currency.facade.CurrencyFacade;
import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.DailysheetFacade;
import by.dak.persistence.entities.Dailysheet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: akoyro
 * Date: 29.04.2010
 * Time: 14:50:21
 */
public class CurrencyFacadeImpl extends BaseFacadeImpl<Currency> implements CurrencyFacade {
	private static HashMap<CurrencyType, Currency> currencyCashe = new HashMap<CurrencyType, Currency>();

	@Autowired
	private DailysheetFacade dailysheetFacade;


	public static SearchFilter getSearchFilterBy(Dailysheet dailysheet) {
		SearchFilter searchFilter = SearchFilter.instanceUnbound();
		searchFilter.eq(Currency.PROPERTY_dailysheet, dailysheet);
		return searchFilter;
	}

	@Override
	public List<Currency> allBy(Dailysheet dailysheet) {
		return loadAll(getSearchFilterBy(dailysheet));
	}

	@Override
	public Currency findCurrentBy(CurrencyType currencyType, Dailysheet dailysheet) {
		return findCurrentBy(currencyType, dailysheet, true);
	}

	@Override
	public Currency findCurrentBy(CurrencyType currencyType, Dailysheet dailysheet, boolean loadFromBase) {
		Currency currency = currencyCashe.get(currencyType);
		if (currency == null || loadFromBase) {
			SearchFilter filter = SearchFilter.instanceSingle();
			filter.eq(Currency.PROPERTY_type, currencyType);
			filter.eq(Currency.PROPERTY_dailysheet, dailysheet);
			List<Currency> currencies = loadAll(filter);
			if (currencies.size() > 0) {
				currency = loadAll(filter).get(0);
				currencyCashe.put(currencyType, currency);
			}
		}
		return currency;
	}

	public void setSelected(Currency currency) {
		SearchFilter filter = SearchFilter.instanceUnbound();
		filter.eq(Currency.PROPERTY_dailysheet, currency.getDailysheet());
		List<Currency> currencies = loadAll(filter);
		for (Currency c : currencies) {
			c.setSelected(currency.getId().equals(c.getId()));
			saveOrUpdate(c);
		}
	}

	@Override
	public Currency getSelected(Dailysheet dailysheet) {
		SearchFilter filter = SearchFilter.instanceSingle();
		filter.eq(Currency.PROPERTY_dailysheet, dailysheet);
		filter.eq(Currency.PROPERTY_selected, Boolean.TRUE);
		return getFirstBy(filter);
	}


	public List<Currency> create(Dailysheet dailysheet) {

		List<Currency> result = allBy(dailysheet);
		if (!result.isEmpty())
			return result;

		SearchFilter filter = SearchFilter.instanceUnbound();
		filter.setResultSize(2);
		filter.setPageSize(2);
		filter.addDescOrder(Dailysheet.PROPERTY_date);

		result = new ArrayList<>();

		List<Dailysheet> last = dailysheetFacade.loadAll(filter);
		if (last.size() == 2) {
			List<Currency> currencies = allBy(last.get(1));
			boolean selected = false;
			Currency byC = null;
			if (currencies.size() == CurrencyType.values().length) {
				for (Currency c : currencies) {
					Currency n = new Currency();
					n.setType(c.getType());
					n.setSelected(c.getSelected());
					n.setPrice(c.getPrice());
					n.setDailysheet(dailysheet);
					byC = c.getType() == CurrencyType.BYR ? n : null;
					selected = !selected ? c.getSelected() : true;
					save(n);
					result.add(n);
				}
				if (!selected) {
					assert byC != null;
					byC.setSelected(true);
					update(byC);
				}
			}
		}
		if (result.isEmpty()) {
			for (CurrencyType t : CurrencyType.values()) {
				Currency n = new Currency();
				n.setType(t);
				n.setSelected(t == CurrencyType.BYR);
				n.setPrice(1D);
				n.setDailysheet(dailysheet);
				save(n);
				result.add(n);
			}
		}

		return result;
	}
}
