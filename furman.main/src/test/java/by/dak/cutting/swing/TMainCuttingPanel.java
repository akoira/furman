package by.dak.cutting.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 21.12.2010
 * Time: 13:21:18
 */
public class TMainCuttingPanel
{

    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        CuttersPanel cuttersPanel = new CuttersPanel();


        Order order = FacadeContext.getOrderFacade().findUniqueByField(Order.PROPERTY_name, "TEST_NEW_CUTTING");
        cuttersPanel.setCuttingModel(FacadeContext.getStripsFacade().loadCuttingModel(order).load());
        cuttersPanel.setEditable(true);

        TestUtils.showFrame(cuttersPanel, "test");
    }

}
