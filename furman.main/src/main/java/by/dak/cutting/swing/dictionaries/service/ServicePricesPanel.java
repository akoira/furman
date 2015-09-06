package by.dak.cutting.swing.dictionaries.service;

import by.dak.cutting.swing.DModPanel;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Service;

import java.util.List;

/**
 * User: akoyro
 * Date: 20.06.2009
 * Time: 17:54:40
 */
public class ServicePricesPanel extends DModPanel<Service>
{
    private ServicePricesTab servicePricesTab = new ServicePricesTab();

    public ServicePricesPanel()
    {
        addTabs();
    }

    @Override
    protected void addTabs()
    {
        addTab(getResourceMap().getString("ServicePricesTab.title"), servicePricesTab);
        servicePricesTab.setWarningList(getWarningList());
    }

    @Override
    public void save()
    {

    }

    public List<PriceEntity> getPrices()
    {
        return servicePricesTab.getPrices();
    }

    public void setPrices(List<PriceEntity> list)
    {
        servicePricesTab.setPrices(list);
    }

}
