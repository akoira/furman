package by.dak.cutting.swing.dictionaries.service;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.entities.Service;
import by.dak.test.TestUtils;

import java.util.ArrayList;

public class TServicePriceTab
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        ServicePricesTab servicePricesTab = new ServicePricesTab();
        servicePricesTab.setValue(new Service());
        servicePricesTab.setPrices(new ArrayList());
        TestUtils.showFrame(servicePricesTab, "servicePricesTab");
    }
}
