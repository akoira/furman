package by.dak.cutting.currency.swing;

import by.dak.cutting.currency.facade.CurrencyFacade;
import by.dak.cutting.currency.facade.impl.CurrencyFacadeImpl;
import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.cutting.swing.AListTab;
import by.dak.persistence.entities.Dailysheet;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdater;
import by.dak.utils.BindingAdapter;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.Binding;

import java.util.List;

/**
 * User: akoyro
 * Date: 30.04.2010
 * Time: 13:02:00
 */
public class CurrencyListTab extends AListTab<Currency, Dailysheet> {

	private CurrencyFacade currencyFacade;

	public CurrencyListTab(CurrencyFacade currencyFacade) {

		this.currencyFacade = currencyFacade;
	}

	@Override
	protected void initEnvironment() {
		ListUpdater<Currency> updater = new AListUpdater<Currency>() {
			@Override
			public void update() {
			}


			@Override
			public String[] getVisibleProperties() {
				return new String[]{"type", "price"};
			}

			@Override
			public ResourceMap getResourceMap() {
				return CurrencyListTab.this.getResourceMap();
			}

			@Override
			public String[] getEditableProperties() {
				return new String[]{"price"};
			}

			@Override
			public int getCount() {
				return CurrencyType.values().length;
			}
		};
		getListNaviTable().setListUpdater(updater);
		getListNaviTable().init();

	}

	@Override
	protected void initBindingListeners() {
		getListNaviTable().getBindingGroup().addBindingListener(new BindingAdapter() {
			@Override
			public void synced(Binding binding) {
				if (binding.getName().equals("price")) {
					int row = getListNaviTable().getTable().getSelectedRow();
					if (row > -1 && getListNaviTable().getListUpdater() != null) {
						Currency currency = getListNaviTable().getListUpdater().getList().get(row);
						currencyFacade.save(currency);
					}
				}
			}
		});
	}

	@Override
	protected void valueChanged() {
		if (getValue() != null) {
			List<Currency> list = currencyFacade.loadAll(CurrencyFacadeImpl.getSearchFilterBy(getValue()));
			if (list.size() == CurrencyType.values().length) {
				getListNaviTable().getListUpdater().getList().addAll(list);
			} else {
				getListNaviTable().getListUpdater().getList().addAll(currencyFacade.create(getValue()));
			}
		}
	}


	@Override
	public String getTitle() {
		return getResourceMap().getString("panel.title", getValue().getDate());
	}
}
