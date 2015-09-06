package by.dak.cutting.doors.wizard;

import by.dak.cutting.SpringConfiguration;
import by.dak.swing.wizard.WizardDisplayerHelper;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 19.08.2009
 * Time: 11:29:26
 * To change this template use File | Settings | File Templates.
 */
public class TestDoorsWizard
{
    public static void main(String args[])
    {
        new SpringConfiguration();
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                DoorsController doorsController = new DoorsController(null);
                WizardDisplayerHelper wizard = new WizardDisplayerHelper(doorsController);
                wizard.showWizard();

                doorsController = new DoorsController(doorsController.getDoors());
                wizard = new WizardDisplayerHelper(doorsController);
                wizard.showWizard();

                int i = 0;
            }
        });
    }
}