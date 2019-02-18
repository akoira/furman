package by.dak.cutting;

import by.dak.cutting.currency.facade.impl.CurrencyFacadeImpl;
import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.cutting.currency.swing.CurrencyListTab;
import by.dak.cutting.currency.swing.CurrencyPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Dailysheet;

import java.util.List;

/**
 * @author admin
 * @version 0.1 07.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class NewDayManager {
	private static long DEFAULT_MANAGERS_DEPARTMENT_ID = 0;

	public NewDayManager() {
		super();
	}

	public Dailysheet checkDailysheet() {
		Dailysheet dailysheet = FacadeContext.getDailysheetFacade().loadCurrentDailysheet();
		// todo this will be replaced on creating Dailysheet WIzard.
		if (dailysheet == null) {
			dailysheet = new Dailysheet();
			dailysheet.setDate(new java.sql.Date(System.currentTimeMillis()));
			dailysheet.setEmployee(FacadeContext.getEmployee());
			FacadeContext.getDailysheetFacade().save(dailysheet);
		}
		return dailysheet;
	}

	public boolean checkCurrency(Dailysheet dailysheet) {
		List<Currency> list = FacadeContext.getCurrencyFacade().loadAll(CurrencyFacadeImpl.getSearchFilterBy(dailysheet));
		if (list.size() != CurrencyType.values().length) {

			if (CuttingApp.getApplication().getPermissionManager().checkPermission(CurrencyListTab.class.getSimpleName(),
					false)) {
				DialogShowers.showBy(CurrencyPanel.valueOf(dailysheet, FacadeContext.getCurrencyFacade()), true);
			} else {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
}
