package by.dak.cutting.swing.archive;

import by.dak.cutting.SpringConfiguration;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 13:49
 */
public class TOrderExplorer
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        TestUtils.showFrame(new OrderExplorer(), "OrderExplorer");
    }
}
