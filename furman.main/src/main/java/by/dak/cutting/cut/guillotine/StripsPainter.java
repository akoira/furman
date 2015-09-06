/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Peca
 */
public class StripsPainter
{

    private static Dimension computeStripsBounds(Strips strips)
    {
        int maxWidth = 0;
        int height = 0;
        for (Segment segment : strips.getSegments())
        {
            maxWidth = Math.max(maxWidth, segment.getLength());
            height += segment.getWidth() + 30;
        }

        return new Dimension(maxWidth, height);
    }

    public static void draw(Graphics g, Strips strips)
    {
        draw(g, strips, g.getClipBounds());
    }

    public static void draw(Graphics g, Strips strips, Rectangle bounds)
    {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform originalTransform = g2.getTransform();
        //strips.sort();
        g.setColor(Color.white);
        g.fillRect(0, 0, (int) g.getClipBounds().getWidth(), (int) g.getClipBounds().getHeight());

        Dimension dim = computeStripsBounds(strips);
        dim.setSize(dim.getWidth() + 10, dim.getHeight() + 10);
        double sx = bounds.getWidth() / dim.getWidth();
        double sy = bounds.getHeight() / dim.getHeight();
        double scale = Math.min(sx, sy);

        g2.transform(AffineTransform.getScaleInstance(scale, scale));

        int y = 5;
        int x = 5;
        for (Segment segment : strips.getSegments())
        {
            SegmentPainter.draw(g, x, y, segment, 1);
            y += segment.getWidth() + 30;
        }

        g2.setTransform(originalTransform);
    }
}
