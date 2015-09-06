package by.dak.cutting.swing.order.wizard;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.SpringConfiguration;
import by.dak.test.TestUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: akoyro
 * Date: 05.08.2010
 * Time: 11:06:52
 */
public class TOrderWizard
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();

        AbstractAction action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DialogShowers.showOrderWizardBy(845924L);
            }
        };

        TestUtils.showFrameWithButtonAction(action);
    }
}
