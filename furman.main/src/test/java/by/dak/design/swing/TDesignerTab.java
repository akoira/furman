package by.dak.design.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.test.TestUtils;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 6/22/11
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TDesignerTab
{
    public static void main(String[] args)
    {
        new SpringConfiguration();

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                DesignerTab designerTab = new DesignerTab();

                TestUtils.showFrame(designerTab, "");

            }
        };
        SwingUtilities.invokeLater(runnable);

    }
}
