package by.dak.cutting.cut.swing.furniture;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.swing.StringPainter;
import by.dak.cutting.linear.report.data.LinearBufferedImageCreator;
import by.dak.cutting.swing.AElement2StringConverter;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 27.04.11
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureElementDescriptionPainter extends StringPainter
{
    private Rectangle rectangle;
    private Element element;

    public FurnitureElementDescriptionPainter(Rectangle rectangle, Element element)
    {
        super(rectangle);
        this.rectangle = rectangle;
        this.element = element;
    }

    private void drawText(Graphics g)
    {
        AElement2StringConverter.setDescriptionsTo(element);
        String text = element.getDescription();
        Graphics2D g2 = (Graphics2D) g;

        int cX = (int) ((int) (rectangle.getHeight() / 2) * getScale());
        int cY = (int) ((int) (rectangle.getWidth() / 2) * LinearBufferedImageCreator.scaleFactorY);

        int sw = g.getFontMetrics().stringWidth(text);
        int fd = g.getFontMetrics().getDescent();

        if (sw > (rectangle.getHeight() * getScale()))
        {
            sw = g.getFontMetrics().stringWidth(element.getShortDescription());
        }

        int sX = cX - sw / 2;
        int sY = cY + fd;

        g2.drawString(text, sX, sY);
    }

    @Override
    public void drawString(Graphics g)
    {
        Graphics2D g2 = createGraphics2D(g, rectangle);
        AffineTransform oldTransform = g2.getTransform();
        AffineTransform newTransform = new AffineTransform(oldTransform);
        newTransform.concatenate(AffineTransform.getScaleInstance(1 / getScale(),
                1 / LinearBufferedImageCreator.scaleFactorY));
        g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, DEFAULT_FONT_SIZE));
        g2.setTransform(newTransform);
        drawText(g2);

        g2.setTransform(oldTransform);
    }

}
