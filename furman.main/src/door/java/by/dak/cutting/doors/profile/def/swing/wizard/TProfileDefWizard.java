package by.dak.cutting.doors.profile.def.swing.wizard;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.swing.wizard.WizardDisplayerHelper;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 28.9.2009
 * Time: 17.49.28
 * To change this template use File | Settings | File Templates.
 */
public class TProfileDefWizard {
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SpringConfiguration();

				ProfileDefController profileDefController = new ProfileDefController(new ProfileDef());
				WizardDisplayerHelper helper = new WizardDisplayerHelper(profileDefController);
				helper.showWizard();
			}
		});
	}
}
