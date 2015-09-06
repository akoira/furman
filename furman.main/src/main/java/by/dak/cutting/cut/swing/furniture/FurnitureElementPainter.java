package by.dak.cutting.cut.swing.furniture;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.swing.Constants;
import by.dak.cutting.cut.swing.ElementPainter;
import by.dak.cutting.cut.swing.StringPainter;
import by.dak.cutting.linear.report.data.LinearBufferedImageCreator;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 27.04.11
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureElementPainter extends ElementPainter
{
    @Override
    public void paintElement(Graphics g, Segment segment, int x2, int y2, int w, int h)
    {
        if (segment.getElement() != null)
        {
            g.setColor(Constants.COLOR_ELEMENT_PRINT);
            fillRect(g, x2 + segment.getPadding(), (int) (y2 + (1 / getScale() * LinearBufferedImageCreator.borderWidth) / 2),
                    w - segment.getPadding(), (int) (h - (1 / getScale() * LinearBufferedImageCreator.borderWidth) / 2));
            g.setColor(Color.BLACK);
            Rectangle rct = new Rectangle(x2, y2,
                    segment.getLength(), segment.getWidth());
            StringPainter elementDescriptionPainter = new FurnitureElementDescriptionPainter(rct, segment.getElement());
            elementDescriptionPainter.setScale(getScale());
            elementDescriptionPainter.drawString(g);
        }
    }

}
