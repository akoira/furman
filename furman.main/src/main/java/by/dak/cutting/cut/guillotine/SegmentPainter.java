/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.graphics.Draw;
import by.dak.cutting.cut.swing.Constants;
import by.dak.cutting.cut.swing.SegmentLevel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

/**
 * @author Peca
 */
@Deprecated
public class SegmentPainter
{

    private static Color[] cutColors = {Color.RED, Color.BLUE, new Color(0, 200, 0), Color.MAGENTA,
            Color.GREEN, Color.PINK};

    private static long totalCuttedArea;
    private static long totalRedSegmentArea;

    public static void draw(Graphics g, Segment segment)
    {
        draw(g, 0, 0, segment, 1);
    }

    public static synchronized void draw(Graphics g, int x, int y, Segment segment, double scale)
    {
        totalCuttedArea = 0;
        totalRedSegmentArea = 0;

//        paintSegment(x + 1, y + 1, segment, g, scale);


        drawSegment(x + 1, y + 1, segment, g, scale);
        drawSegmentCut(x + 1, y + 1, segment, g, false, scale);
    }

    private static void paintSegmentLevel0(Graphics g, Segment segment)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        int width = segment.getWidth();
        int height = segment.getLength();
        g.setColor(Constants.COLOR_STRIP_DISPLAY);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(-1, -1, width + 1, height + 1);
    }

    private static void paintElementSegment(Graphics g, int x, int y, Segment elementSegment)
    {
        g.setColor(Color.ORANGE);
        g.fillRect(y, x, elementSegment.getWidth(), elementSegment.getLength());
    }

    private static void paintSegment(int x, int y, Segment segment, Graphics g, double scale)
    {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform originalTransform = g2.getTransform();
        AffineTransform transform = new AffineTransform(originalTransform);
        transform.concatenate(AffineTransform.getTranslateInstance(x, y));
        g2.setTransform(transform);
        x = 0;
        y = 0;
        switch (SegmentLevel.valueOf(segment.getLevel()))
        {
            case FIRST:
                paintSegmentLevel0(g, segment);
                break;
            case SECOND:
            case FOURTH:
            case THIRD:
            case FIFTH:
                break;
            default:
                throw new IllegalArgumentException("Illegal level + " + segment.getLevel());
        }

        for (Segment seg : segment.getItems())
        {
            paintSegment(x, y, seg, g, scale);
            if ((segment.getLevel() % 2) == 0)
            {
                y += seg.getWidth() + segment.getPadding();
            }
            else
            {
                x += seg.getWidth() + segment.getPadding();
            }
        }

        if (segment.getElement() != null)
        {
            paintElementSegment(g, x, y, segment);
        }


        g2.setTransform(originalTransform);
        if (true)
            return;
    }

    private static void drawSegment(int x, int y, Segment segment, Graphics g, double scale)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        int w, h;
        Color c;

        if ((segment.getLevel() % 2) == 0)
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
            g.fillRect(x, y, w, h);
            g.setColor(Color.BLACK);
            g.drawRect(x - 1, y - 1, w + 1, h + 1);
        }
        /* else{
            g.setColor(Draw.getColor(segment.getLevel()));
            g.fillRect(x, y, w+s, h+s);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, w+s, h+s);
        }*/

        int y2 = y;
        int x2 = x;
        for (Segment seg : segment.getItems())
        {
            drawSegment(x2, y2, seg, g, scale);
            if ((segment.getLevel() % 2) == 0)
            {
                x2 += seg.getWidth() + segment.getPadding();
            }
            else
            {
                y2 += seg.getWidth() + segment.getPadding();
            }
        }

        if (segment.getElement() != null)
        {
            Element el = segment.getElement();
            g.setColor(Color.ORANGE);
            //g.setColor(Draw.getColor(el.index));
            g.fillRect(x2, y2, w, h);
            g.setColor(Color.BLACK);
            //g.drawRect(x2, y2, w, h);
            int sw = g.getFontMetrics().stringWidth(String.valueOf(el.getId()));
            int sh = g.getFontMetrics().getHeight();
            //g.drawString(String.valueOf(el.getId()), x2+(w-sw)/2, y2+(h+sh)/2);
            Rectangle rct = new Rectangle(x2, y2, segment.getLength(), segment.getWidth());
            if ((segment.getLevel() % 2) == 1) rotateRect(rct);
            drawString(g, String.valueOf(el.getId()), rct);
        }

        boolean isRedSegment = segment.getLevel() == 1;
        if (isRedSegment)
        {
            long cutted = Utils.getElementsTotalArea(segment.getElements());
            totalCuttedArea += cutted;
            totalRedSegmentArea += segment.getTotalArea();

            /*int waste = segment.getWaste();
            g.drawString(String.format("%d", waste), x2+3, y+segment.getLength()+10);
            
           /* float ratio = cutted / (float)segment.getTotalArea();
            g.drawString(String.format("%1.3f", ratio), x2+3, y+segment.getLength()+10);
            
            float ratioTotal = totalCuttedArea / (float)totalRedSegmentArea;
            g.drawString(String.format("%1.3f", ratioTotal), x2+3, y+segment.getLength()+25);
            */
        }
    }

    private static void rotateRect(Rectangle rect)
    {
        rect.setSize(rect.height, rect.width);
    }

    //private static ProfileCompDef createRectShape(double x, double y, double width, double height)

    private static void drawString(Graphics g, String text, Rectangle rect)
    {
        Font oldFont = g.getFont();
        Font f = oldFont;
        int width = g.getFontMetrics().stringWidth(text);
        if (width > rect.width)
        {
            float ratio = rect.width / (float) width;
            f = new Font(oldFont.getName(), oldFont.getStyle(), (int) (oldFont.getSize() * ratio));
            g.setFont(f);
        }
        int sw = g.getFontMetrics().stringWidth(text);
        int sh = g.getFontMetrics().getHeight();
        g.drawString(text, (int) (rect.getX() + (rect.getWidth() - sw) / 2), (int) (rect.getY() + (rect.getHeight() + sh) / 2));
        g.setFont(oldFont);
        // g.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
    }

    public static void drawSegmentCut(int x, int y, Segment segment, Graphics g, boolean isLastSegment, double scale)
    {

        int sawWidth = segment.getPadding();
        int sawOffset = -(sawWidth / 2);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(sawWidth));
        int y2 = y;
        int x2 = x;
        List<Segment> segments = segment.getItems();
        for (int i = 0; i < segments.size(); i++)
        {
            Segment seg = segments.get(i);
            drawSegmentCut(x2, y2, seg, g, i == segments.size() - 1, scale);
            if ((segment.getLevel() % 2) == 0)
            {
                x2 += seg.getWidth() + sawWidth;
            }
            else
            {
                y2 += seg.getWidth() + sawWidth;
            }
        }


        if ((segment.getLevel() > 0) && (segment.getParent() != null))
        {
            if ((segment.getParent().getFreeLength() == 0) && (isLastSegment)) return;
            //int s = maxLevel - segment.getLevel() + 1;
            int s = 1;
            //s = Math.max(1, Math.max(s, sawWidth));
            Stroke stroke1 = new BasicStroke(s, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{2 * s, s}, 0.0f);
            Stroke stroke2 = new BasicStroke(s, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{2 * s, s}, s);
            Color color1 = cutColors[(segment.getLevel() - 1) % cutColors.length];
            Color color2 = Color.BLACK;

            if ((segment.getLevel() % 2) == 0)
            {
                y += segment.getWidth() + sawWidth;
                //if((y < segment.getParent().getWidth())||(segment.getParent().getLevel()>0))
                g.setColor(color2);
                g2.setStroke(stroke2);
                g.drawLine(x + sawOffset, y + sawOffset, x + segment.getLength() - sawOffset, y + sawOffset);
                g.setColor(color1);
                g2.setStroke(stroke1);
                g.drawLine(x + sawOffset, y + sawOffset, x + segment.getLength() - sawOffset, y + sawOffset);
            }
            else
            {
                x += segment.getWidth() + sawWidth;
                g.setColor(color2);
                g2.setStroke(stroke2);
                g.drawLine(x + sawOffset, y + sawOffset, x + sawOffset, y + segment.getLength() - sawOffset);
                g.setColor(color1);
                g2.setStroke(stroke1);
                g.drawLine(x + sawOffset, y + sawOffset, x + sawOffset, y + segment.getLength() - sawOffset);
            }
        }
    }


}
