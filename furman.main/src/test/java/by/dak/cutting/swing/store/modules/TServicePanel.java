package by.dak.cutting.swing.store.modules;

import by.dak.persistence.entities.Service;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 17.06.2009
 * Time: 16:45:23
 */
public class TServicePanel
{
    public static void main(String[] args)
    {
        ServicePanel servicePanel = new ServicePanel();
        servicePanel.setValue(new Service());
        TestUtils.showFrame(servicePanel, "TServicePanel");
    }
}
