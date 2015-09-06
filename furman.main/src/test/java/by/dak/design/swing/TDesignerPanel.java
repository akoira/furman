package by.dak.design.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.CellFigure;
import by.dak.test.TestUtils;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.08.11
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class TDesignerPanel
{
    public static void main(String[] args)
    {
        new SpringConfiguration();

        double offsetX = 30;
        double offsetY = 30;

        double length = 2700;
        double heigth = 1700;
        long width = 200;

        CellFigure cellFigure = new CellFigure();
        cellFigure.setBounds(new Point2D.Double(0, 0),
                new Point2D.Double(0 + length, 0 + heigth));
        cellFigure.setWidth(width);

        FrontDesignerPanel frontDesignerPanel = new FrontDesignerPanel();

        FrontDesignerDrawing frontDesignerDrawing = new FrontDesignerDrawing();
        frontDesignerDrawing.setTopFigure(cellFigure);
        frontDesignerPanel.setDrawing(frontDesignerDrawing);

        TestUtils.showFrame(frontDesignerPanel, "");
    }
}
