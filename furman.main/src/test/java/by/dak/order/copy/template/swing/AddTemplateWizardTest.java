package by.dak.order.copy.template.swing;

import by.dak.swing.wizard.WizardDisplayerHelper;
import by.dak.test.TestUtils;
import org.jdesktop.application.SingleFrameApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: akoyro
 * Date: 11/24/13
 * Time: 8:30 PM
 */
public class AddTemplateWizardTest
{
    public static void main(String[] args)
    {
        TestUtils.showFrameWithButtonAction(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                WizardBuilder builder = new WizardBuilder();
                builder.setContext(SingleFrameApplication.getInstance().getContext());
                WizardDisplayerHelper helper = new WizardDisplayerHelper(builder.build(),
                        null);

                helper.showWizard();
            }
        });


    }
}
