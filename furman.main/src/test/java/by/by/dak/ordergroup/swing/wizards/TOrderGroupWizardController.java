package by.by.dak.ordergroup.swing.wizards;

import by.by.dak.ordergroup.swing.TOrderGroupLinksTab;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.SpringConfiguration;
import by.dak.ordergroup.swing.wizard.OrderGroupWizardController;

/**
 * User: akoyro
 * Date: 17.01.2011
 * Time: 15:51:52
 */
public class TOrderGroupWizardController
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        OrderGroupWizardController orderGroupWizardController = new OrderGroupWizardController();
        orderGroupWizardController.setModel(TOrderGroupLinksTab.getOrderGroup());
        DialogShowers.showWizard(orderGroupWizardController);
    }
}
