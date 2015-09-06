package by.dak.cutting.currency.swing;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.currency.facade.impl.CurrencyFacadeImpl;
import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.cutting.swing.AListTab;
import by.dak.persistence.FacadeContext;
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
public class CurrencyListTab extends AListTab<Currency, Dailysheet>
{

    @Override
    protected void initEnvironment()
    {
        ListUpdater<Currency> updater = new AListUpdater<Currency>()
        {
            @Override
            public void update()
            {
            }


            @Override
            public String[] getVisibleProperties()
            {
                return new String[]{"type", "price"};
            }

            @Override
            public ResourceMap getResourceMap()
            {
                return CurrencyListTab.this.getResourceMap();
            }

            @Override
            public String[] getEditableProperties()
            {
                return new String[]{"price"};
            }

            @Override
            public int getCount()
            {
                return CurrencyType.values().length;
            }
        };
        getListNaviTable().setListUpdater(updater);
        getListNaviTable().init();

    }

    @Override
    protected void initBindingListeners()
    {
        getListNaviTable().getBindingGroup().addBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                if (binding.getName().equals("price"))
                {
                    int row = getListNaviTable().getTable().getSelectedRow();
                    if (row > -1 && getListNaviTable().getListUpdater() != null)
                    {
                        Currency currency = (Currency) getListNaviTable().getListUpdater().getList().get(row);
                        FacadeContext.getCurrencyFacade().save(currency);
                    }
                }
            }
        });
    }

    @Override
    protected void valueChanged()
    {
        if (getValue() != null)
        {
            List<Currency> list = FacadeContext.getCurrencyFacade().loadAll(CurrencyFacadeImpl.getSearchFilterBy(getValue()));
            if (list.size() == CurrencyType.values().length)
            {
                getListNaviTable().getListUpdater().getList().addAll(list);
            }
            else
            {
                CurrencyType[] currencyTypes = CurrencyType.values();
                for (CurrencyType currencyType : currencyTypes)
                {
                    SearchFilter filter = new SearchFilter();
                    filter.eq(Currency.PROPERTY_dailysheet, getValue());
                    filter.eq(Currency.PROPERTY_type, currencyType);
                    list = FacadeContext.getCurrencyFacade().loadAll(filter);
                    if (list.size() > 0)
                    {
                        getListNaviTable().getListUpdater().getList().addAll(list);
                    }
                    else
                    {
                        Currency currency = new Currency();
                        currency.setDailysheet(getValue());
                        currency.setPrice(currencyType.getDefaultPrice());
                        currency.setType(currencyType);
                        FacadeContext.getCurrencyFacade().save(currency);
                        getListNaviTable().getListUpdater().getList().add(currency);
                    }
                }
            }
        }
    }


    @Override
    public String getTitle()
    {
        return getResourceMap().getString("panel.title", getValue().getDate());
    }
}
