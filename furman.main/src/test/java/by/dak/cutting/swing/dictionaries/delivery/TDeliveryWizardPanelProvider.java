package by.dak.cutting.swing.dictionaries.delivery;

import by.dak.cutting.SpringConfiguration;
import by.dak.swing.wizard.WizardDisplayerHelper;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 01.02.2010
 * Time: 17:10:09
 * To change this template use File | Settings | File Templates.
 */
public class TDeliveryWizardPanelProvider
{
    public static void main(String[] args)
    {
        new SpringConfiguration();

        DeliveryController controller = new DeliveryController();

        WizardDisplayerHelper helper = new WizardDisplayerHelper(controller);
        helper.showWizard();
    }
}
