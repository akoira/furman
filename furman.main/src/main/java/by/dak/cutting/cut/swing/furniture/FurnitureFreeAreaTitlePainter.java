package by.dak.cutting.cut.swing.furniture;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.swing.FreeAreaTitlePainter;
import by.dak.cutting.cut.swing.StringPainter;
import by.dak.cutting.linear.UnitConverter;
import by.dak.cutting.linear.report.data.LinearBufferedImageCreator;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 27.04.11
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureFreeAreaTitlePainter extends FreeAreaTitlePainter
{
    private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(this.getClass());

    @Override
    public void titleFreeAria(Graphics g, Segment segment, int x2, int y2)
    {
        int free = segment.getFreeLength() - segment.getPadding();
        if (free > 0)
        {
            final Rectangle rectangle = new Rectangle(x2, y2, free, segment.getWidth());
            if ((segment.getLevel() % 2) == 1)
            {
                rotateRect(rectangle);
            }
            StringPainter stringPainter = new StringPainter(rectangle)
            {
                //todo! подумать что делать, когда текст не вмещается. потому что класс переписан
                @Override
                protected void drawString(Graphics g, String text)
                {
                    Graphics2D g2 = (Graphics2D) g;

                    int cX = (int) ((int) (rectangle.getHeight() / 2) * getScale());
                    int cY = (int) ((int) (rectangle.getWidth() / 2) * LinearBufferedImageCreator.scaleFactorY);

                    int sw = g.getFontMetrics().stringWidth(text);
                    int fd = g.getFontMetrics().getDescent();

                    int sX = cX - sw / 2;
                    int sY = cY + fd;

                    g2.drawString(text, sX, sY);
                }

                //todo в случаи unUseful сегмента убрать надпись со склада
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

                    String text = Double.toString(UnitConverter.convertToMetre((int) rectangle.getHeight()));

                    drawString(g2, text);

                    g2.setTransform(oldTransform);
                }
            };

            stringPainter.setScale(getScale());
            stringPainter.drawString(g);
        }
    }
}
