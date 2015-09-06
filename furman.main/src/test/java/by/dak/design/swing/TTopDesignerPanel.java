package by.dak.design.swing;

import by.dak.test.TestUtils;
import org.jhotdraw.draw.DefaultDrawing;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 24.08.11
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */
public class TTopDesignerPanel
{
    public static void main(String[] args)
    {
        TopDesignerPanel topDesignerPanel = new TopDesignerPanel();
        topDesignerPanel.setDrawing(new DefaultDrawing());
        TestUtils.showFrame(topDesignerPanel, "");
    }
}
