/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.swing.order.cellcomponents.editors.cuttoff;

import by.dak.cutting.swing.order.data.Cutoff;
import by.dak.draw.DimensionLine;
import by.dak.draw.DimensionLinePainter;
import by.dak.draw.PaintAttributesProvider;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
public class CutoffPainter
{

    public static final int OFFSET = 5;

    private static final Stroke LINE_STROKE = new BasicStroke(1);

    private static final double POINT_SIZE = 5d;

    private Dimension element;

    private Cutoff cutoff;

    private CutoffLine cutoffLine;

    private ElementRectangle elementRectangle;

    private double scale;

    private boolean isPainted = false;

    public CutoffPainter(Dimension element, Cutoff cutoff, CutoffLine cutoffLine, double scale)
    {
        this.element = element;
        this.cutoff = cutoff;
        this.cutoffLine = cutoffLine;
        this.scale = scale;
        this.elementRectangle = getElementRectangleBy(element, scale);
        PaintAttributesProvider.getInstanse().setPlanScale((float) scale);
    }

    public CutoffLine getCutoffLine()
    {
        return cutoffLine;
    }

    public void setCutoffLine(CutoffLine cutoffLine)
    {
        this.cutoffLine = cutoffLine;
    }

    public void paint(Graphics2D g2D)
    {
        checkState();

        AffineTransform transform = g2D.getTransform();

        AffineTransform newTransform = new AffineTransform(transform);
        newTransform.concatenate(AffineTransform.getTranslateInstance(OFFSET, OFFSET));

        g2D.setTransform(newTransform);
        g2D.draw(elementRectangle);

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (cutoffLine != null)
        {
            cutoffLine.paint(g2D);

            List<DimensionLine> dls = createDimentionLines(cutoffLine);
            for (DimensionLine dl : dls)
            {
                DimensionLinePainter painter = new DimensionLinePainter(dl, false);
                painter.paint(g2D);
            }
        }
        g2D.setTransform(newTransform);
    }

    private void checkState() throws IllegalStateException
    {
        if (isPainted)
        {
            throw new IllegalStateException("Alredy painted. Need new instance");
        }
        isPainted = true;
    }

    private List<DimensionLine> createDimentionLines(CutoffLine line)
    {
        ArrayList<DimensionLine> dls = new ArrayList<DimensionLine>(2);

        Point2D startP1, startP2, endP1, endP2;

        startP1 = line.getP1();
        startP2 = line.getP2();

        Point2D p1 = line.getP1();
        Point2D p2 = line.getP2();
        if (p1.getY() == elementRectangle.getY() || p1.getY() == elementRectangle.getHeight())
        {
            p2 = line.getP1();
            p1 = line.getP2();
        }


        if (line.isAdjusted(elementRectangle))
        {
            Cutoff.Angle angle = CutoffPainter.getTypeBy(line);
            //todo что то не правельно не должен быть null
            if (angle != null)
            {
                switch (angle)
                {
                    case upLeft:
                        dls.add(new DimensionLine(p1, elementRectangle.getDownLeft(), -10));
                        dls.add(new DimensionLine(p2, elementRectangle.getUpRight(), 10));
                        break;
                    case downLeft:
                        dls.add(new DimensionLine(p1, elementRectangle.getUpLeft(), 10));
                        dls.add(new DimensionLine(p2, elementRectangle.getDownRight(), -10));
                        break;
                    case upRight:
                        dls.add(new DimensionLine(p1, elementRectangle.getDownRight(), 10));
                        dls.add(new DimensionLine(p2, elementRectangle.getUpLeft(), -10));
                        break;
                    case downRight:
                        dls.add(new DimensionLine(p1, elementRectangle.getUpRight(), -10));
                        dls.add(new DimensionLine(p2, elementRectangle.getDownLeft(), 10));
                        break;
                }
            }
        }
        return dls;
    }

    public static ElementRectangle getElementRectangleBy(Dimension element, double scale)
    {
        if (element == null)
        {
            return null;
        }
        {
            return new ElementRectangle(0, 0,
                    element.getWidth() / scale, element.getHeight() / scale);
        }
    }

    public static class ElementRectangle extends Rectangle2D.Double
    {

        public ElementRectangle(double x, double y, double w, double h)
        {
            super(x, y, w, h);
        }

        public Point2D getUpLeft()
        {
            return new Point2D.Double(getX(), getY());
        }

        public Point2D getUpRight()
        {
            return new Point2D.Double(getWidth(), getY());
        }

        public Point2D getDownLeft()
        {
            return new Point2D.Double(getX(), getHeight());
        }

        public Point2D getDownRight()
        {
            return new Point2D.Double(getWidth(), getHeight());
        }
    }

    public static class CutoffLine extends Line2D.Double
    {

        public CutoffLine()
        {
            super();
        }

        public CutoffLine(Point2D p1, Point2D p2)
        {
            super(p1, p2);
        }

        public Shape getT1()
        {
            return createTermPoint(getP1());
        }

        public Shape getT2()
        {
            return createTermPoint(getP2());
        }

        private Shape createTermPoint(Point2D point2D)
        {
            return new Rectangle2D.Double(
                    point2D.getX() - POINT_SIZE / 2,
                    point2D.getY() - POINT_SIZE / 2,
                    POINT_SIZE,
                    POINT_SIZE);
        }

        public void paint(Graphics2D g2D)
        {
            Stroke stroke = g2D.getStroke();
            Color color = g2D.getColor();

            g2D.setStroke(LINE_STROKE);
            g2D.draw(this);
            g2D.setColor(Color.BLACK);
            g2D.fill(getT1());
            g2D.setColor(Color.BLACK);
            g2D.fill(getT2());

            g2D.setColor(color);
            g2D.setStroke(stroke);
        }

        public boolean isAdjusted(Rectangle2D rectangle2D)
        {
            return (rectangle2D.getX() == getP1().getX() ||
                    rectangle2D.getWidth() == getP1().getX()) &&
                    (rectangle2D.getY() == getP2().getY() ||
                            rectangle2D.getHeight() == getP2().getY());
        }
    }

    /**
     * Метод введен чтобы использовать для отрисовки cutoff на карте раскроя.
     *
     * @param cutoffLine
     * @param rect
     * @param cutoff
     * @param scale
     * @return
     */
    public static void adjustLineBy(CutoffLine cutoffLine, Dimension element, Cutoff cutoff, double scale)
    {
        ElementRectangle rect = getElementRectangleBy(element, scale);
        double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        switch (cutoff.getAngle())
        {
            case upLeft:
                x1 = rect.getX();
                y1 = rect.getHeight() - cutoff.getVOffset() / scale;
                x2 = rect.getWidth() - cutoff.getHOffset() / scale;
                y2 = rect.getY();
                break;
            case upRight:
                x1 = rect.getWidth();
                y1 = rect.getHeight() - cutoff.getVOffset() / scale;
                x2 = cutoff.getHOffset() / scale;
                y2 = rect.getY();
                break;
            case downLeft:
                x1 = rect.getX();
                y1 = cutoff.getVOffset() / scale;
                x2 = rect.getWidth() - cutoff.getHOffset() / scale;
                y2 = rect.getHeight();
                break;
            case downRight:
                x1 = rect.getWidth();
                y1 = cutoff.getVOffset() / scale;
                x2 = cutoff.getHOffset() / scale;
                y2 = rect.getHeight();
                break;
        }
        cutoffLine.setLine(x1, y1, x2, y2);
    }

    public static double lengthCutoff(Cutoff cutoff, Dimension element)
    {
        CutoffLine cutoffLine = new CutoffLine();
        adjustLineBy(cutoffLine, element, cutoff, 1.0);
        return cutoffLine.getP1().distance(cutoffLine.getP2());
    }

    public static Cutoff.Angle getTypeBy(CutoffLine line)
    {
        double angle = Math.atan2(line.getY2() - line.getY1(),
                line.getX2() - line.getX1());

        if (angle < 0 && angle > -Math.PI / 2)
        {
            return Cutoff.Angle.upLeft;
        }
        else if (angle > 0 && angle < Math.PI / 2)
        {
            return Cutoff.Angle.downLeft;
        }
        else if (angle > Math.PI / 2 && angle < Math.PI)
        {
            return Cutoff.Angle.downRight;
        }
        else if (angle < -Math.PI / 2 && angle > -Math.PI)
        {
            return Cutoff.Angle.upRight;
        }
        return null;
    }

    public static double getScale(Dimension element, Dimension area)
    {
        return Math.max(element.getWidth() / (area.getWidth() - CutoffPainter.OFFSET * 2),
                element.getHeight() / (area.getHeight() - CutoffPainter.OFFSET * 2));
    }
}
