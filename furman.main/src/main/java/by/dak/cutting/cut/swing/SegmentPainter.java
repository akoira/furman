package by.dak.cutting.cut.swing;

import by.dak.cutting.cut.graphics.Draw;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.swing.board.BoardFreeAreaTitlePainter;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.awt.*;
import java.io.IOException;
import java.util.Properties;

/**
 * @author akoyro
 * @version 0.1 01.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class SegmentPainter extends AbstractPainter
{
    private ElementPainter elementPainter;

    private FreeAreaTitlePainter freeAreaTitlePainter;

    private final Properties properties = by.dak.cutting.configuration.Constants.properties(this.getClass());

    private int borderSheetWidth = 2;

    private long totalCuttedArea = 0L;

    private long totalRedSegmentArea = 0L;


    public void drawSegment(Graphics g, int x, int y, Segment segment)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        int w, h;
        Color c;

        if ((segment.getLevel() % 2) == 0 || !isRotate())
        {
            w = segment.getLength();
            h = segment.getWidth();
        }
        else
        {
            w = segment.getWidth();
            h = segment.getLength();
        }
        //g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {10, 5}, 0.0f));
        //if(segment.getLevel()== 4)g2.drawRect(x, y, w2+s, h2+s);
        if (segment.getLevel() == 0)
        {
            c = Constants.COLOR_STRIP_DISPLAY;
        }
        else
        {
            c = Draw.getColor(segment.getLevel());
        }

        if (segment.getLevel() == 0)
        {
            g.setColor(c);
            fillRect(g, x, y, w, h);
            g.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(getBorderSheetWidth()));
            drawRect(g, x - getBorderSheetWidth(), y - getBorderSheetWidth(), w + getBorderSheetWidth(), h + getBorderSheetWidth());
        }

        int y2 = y;
        int x2 = x;
        for (Segment seg : segment.getItems())
        {
            drawSegment(g, x2, y2, seg);
            if ((segment.getLevel() % 2) == 0)
            {
                x2 += seg.getWidth() + segment.getPadding();
            }
            else
            {
                y2 += seg.getWidth() + segment.getPadding();
            }
        }

        getElementPainter().setScale(getScale());
        getElementPainter().paintElement(g, segment, x2, y2, w, h);

        getFreeAreaTitlePainter().setScale(getScale());
        getFreeAreaTitlePainter().titleFreeAria(g, segment, x2, y2);
    }

    public FreeAreaTitlePainter getFreeAreaTitlePainter()
    {
        return freeAreaTitlePainter;
    }

    public void setFreeAreaTitlePainter(FreeAreaTitlePainter freeAreaTitlePainter)
    {
        this.freeAreaTitlePainter = freeAreaTitlePainter;
    }

    public ElementPainter getElementPainter()
    {
        return elementPainter;
    }

    public void setElementPainter(ElementPainter elementPainter)
    {
        this.elementPainter = elementPainter;
    }


    public int getBorderSheetWidth()
    {
        return borderSheetWidth;
    }

    public void setBorderSheetWidth(int borderSheetWidth)
    {
        this.borderSheetWidth = borderSheetWidth;
    }
}
