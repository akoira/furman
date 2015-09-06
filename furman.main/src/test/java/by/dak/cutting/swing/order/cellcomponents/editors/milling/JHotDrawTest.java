package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.test.TestUtils;

import java.awt.*;

/**
 * User: akoyro
 * Date: 14.07.2009
 * Time: 23:17:27
 */
public class JHotDrawTest
{
    public static void main(String[] args)
    {
        MillingDrawingPanel panel = new MillingDrawingPanel();
        panel.setElement(new Dimension(700, 800));
        TestUtils.showFrame(panel, "JHotDrawTest");
    }
}
