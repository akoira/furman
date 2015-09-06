package by.dak.cutting.swing.store.tabs;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.entities.Service;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 25.08.2009
 * Time: 13:55:01
 */
public class TServiceTab
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        ServiceTab serviceTab = new ServiceTab();
        serviceTab.setValue(new Service());
        TestUtils.showFrame(serviceTab, "serviceTab");
    }
}
