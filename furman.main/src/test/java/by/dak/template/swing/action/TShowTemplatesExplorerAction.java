package by.dak.template.swing.action;

import by.dak.cutting.SpringConfiguration;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 25.03.11
 * Time: 13:20
 */
public class TShowTemplatesExplorerAction
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        TestUtils.showFrameWithButtonAction(new ShowTemplatesExplorerAction());
    }
}
