package by.dak.cutting.doors.mech.wizard;

import by.dak.swing.wizard.WizardDisplayerHelper;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 21.08.2009
 * Time: 13:10:29
 * To change this template use File | Settings | File Templates.
 */
public class TestDoorMechDefWizard
{
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                DoorMechDefController doorMechDefController = new DoorMechDefController(null);
                WizardDisplayerHelper wizard = new WizardDisplayerHelper(doorMechDefController);
                wizard.showWizard();

                doorMechDefController = new DoorMechDefController(doorMechDefController.getDoorMechDef());
                wizard = new WizardDisplayerHelper(doorMechDefController);
                wizard.showWizard();

            }
        });
    }
}
