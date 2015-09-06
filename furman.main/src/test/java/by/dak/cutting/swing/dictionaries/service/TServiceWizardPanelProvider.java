package by.dak.cutting.swing.dictionaries.service;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.entities.Service;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 20.06.2009
 * Time: 18:02:53
 */
public class TServiceWizardPanelProvider
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        ServiceWizardController wizardController = new ServiceWizardController(new Service());
        TestUtils.showFrameWithButtonAction(TestUtils.createWizardAction(wizardController));
    }

}
