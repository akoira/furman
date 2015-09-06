package by.dak.cutting.swing.dictionaries.material;

import by.dak.cutting.SpringConfiguration;
import by.dak.swing.wizard.WizardDisplayerHelper;
import by.dak.test.TestUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: akoyro
 * Date: 09.06.2009
 * Time: 10:55:09
 */
public class TMaterialWizardPanelProvider
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        JPanel jPanel = new JPanel(new BorderLayout());
        JButton button = new JButton("show");
        //button.addActionListener(createBoardDefsDialogAction(button));
        button.addActionListener(createWizardAction());
        //button.addActionListener(createBorderDefsDialogAction(button));
        jPanel.add(button, BorderLayout.CENTER);
        TestUtils.showFrame(jPanel, "MaterialWizardPanelProvider");
    }

    private static ActionListener createWizardAction()
    {
        return new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MaterialController materialController = new MaterialController();
                WizardDisplayerHelper helper = new WizardDisplayerHelper(materialController.getProvider(), materialController.getGenericWizardObserver());
                helper.showWizard();
            }
        };
    }
}
