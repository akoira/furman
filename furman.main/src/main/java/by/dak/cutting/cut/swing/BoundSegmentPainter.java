package by.dak.cutting.cut.swing;

import by.dak.cutting.cut.guillotine.Segment;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

/**
 * Рисует как режит пила
 *
 * @author akoyro
 * @version 0.1 01.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class BoundSegmentPainter implements by.dak.cutting.configuration.Constants
{

    /**
     * Должно быть true когда пропилы делаются по гаризантали
     */
    private boolean rotate = ROTATE_SHEET;
    private double scale = 1.0;


    public void drawSegmentCut(Graphics2D g2, float x, float y, Segment segment, boolean isLastSegment)
    {
        int sawWidth = segment.getPadding();
        int sawOffset = -(sawWidth / 2);
        g2.setStroke(new BasicStroke(sawWidth));
        float y2 = y;
        float x2 = x;
        List<Segment> segments = segment.getItems();
        for (int i = 0; i < segments.size(); i++)
        {
            Segment seg = segments.get(i);
            drawSegmentCut(g2, x2, y2, seg, i == segments.size() - 1);
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
            float s = 1f;
            //s = Math.max(1, Math.max(s, sawWidth));
//            Stroke stroke1 = new BasicStroke(s, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{2 * s, s}, 0.0f);
//            Stroke stroke2 = new BasicStroke(s, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{2 * s, s}, s);

            //расчет ширины линии для отображениея пропила
            int ws = segment.getPadding();
            ws = ws < 0 ? 1 : ws;
            Stroke stroke1 = new BasicStroke((float) (s * ws * scale));


            Color color1 = Constants.SAW_COLORS_DISPLAY[(segment.getLevel()) % Constants.SAW_COLORS_DISPLAY.length];
            Color color2 = Color.BLACK;

            if ((segment.getLevel() % 2) == 0)
            {
                y += segment.getWidth() + sawWidth / 2f;
                //if((y < segment.getParent().getWidth())||(segment.getParent().getLevel()>0))
                //g.setColor(color2);
                //g2.setStroke(stroke2);
                //drawLine(g, x + sawOffset, y + sawOffset, x + segment.getLength() - sawOffset, y + sawOffset);
                g2.setColor(color1);
                g2.setStroke(stroke1);
                drawLine(g2, x + sawOffset / 2f, y + sawOffset / 2f, x + segment.getLength() - sawOffset / 2f, y + sawOffset / 2f);
            }
            else
            {
                x += segment.getWidth() + sawWidth / 2f;
                //g.setColor(color2);
                //g2.setStroke(stroke2);
                //drawLine(g, x + sawOffset, y + sawOffset, x + sawOffset, y + segment.getLength() - sawOffset);
                g2.setColor(color1);
                g2.setStroke(stroke1);
                drawLine(g2, x + sawOffset / 2f,
                        y + sawOffset / 2f, x + sawOffset / 2f, y + segment.getLength() - sawOffset / 2f);
            }
        }
    }

    public void drawLine(Graphics2D g, float x1, float y1, float x2, float y2)
    {
        if (rotate)
            g.draw(new Line2D.Float(y1, x1, y2, x2));
        else
            g.draw(new Line2D.Float(x1, y1, x2, y2));
    }


    public double getScale()
    {
        return scale;
    }

    public void setScale(double scale)
    {
        this.scale = scale;
    }

    public boolean isRotate()
    {
        return rotate;
    }

    public void setRotate(boolean rotate)
    {
        this.rotate = rotate;
    }
}
