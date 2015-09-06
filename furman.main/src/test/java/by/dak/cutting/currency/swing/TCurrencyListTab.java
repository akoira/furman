package by.dak.cutting.currency.swing;

import by.dak.cutting.NewDayManager;
import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 30.04.2010
 * Time: 13:05:27
 */
public class TCurrencyListTab
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        FacadeContext.setEmployee(FacadeContext.getEmployeeFacade().loadAll().get(0));
        NewDayManager newDayManager = new NewDayManager();
        newDayManager.checkDailysheet();
        CurrencyListTab currencyListTab = new CurrencyListTab();
        currencyListTab.setValue(FacadeContext.getDailysheetFacade().loadCurrentDailysheet());
        TestUtils.showFrame(currencyListTab, currencyListTab.getTitle());
    }
}
