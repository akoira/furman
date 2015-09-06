package by.dak.draw;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * User: akoyro
 * Date: 06.07.2009
 * Time: 17:21:57
 */
public class Graphics2DUtils
{
    public static final int DIMENTION_LINE_OFFSET = 10;

    public static final Color SELECTED_COLOR = Color.BLUE;
    public static final Color DIMENTION_LINE_COLOR = Color.BLACK;
    public static final BasicStroke SELECTED_BASIC_STROKE = new BasicStroke(2);
    public static final BasicStroke DIMENTION_BASIC_STROKE = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 5f, new float[]{5f, 10f}, 0);

    public static final int OFFSET = 100;
    public static final int POINT_SIZE = 5;


    public static boolean isHorizontal(Line2D line2D)
    {
        return line2D.getP1().getX() == line2D.getP2().getX();
    }

    public static void paintPoint(Graphics2D g2D, Point2D point2D)
    {
        g2D.fill(new Rectangle2D.Double(point2D.getX() - POINT_SIZE / 2d,
                point2D.getY() - POINT_SIZE / 2d,
                POINT_SIZE,
                POINT_SIZE));
    }

    public static Rectangle2D createRec2D(Point2D startPoint, Point2D endPoint)
    {
        Rectangle2D d = new Rectangle2D.Double();
        d.setFrameFromDiagonal(startPoint, endPoint);
        return d;
    }


    public static void rotate90AndMirror(Graphics2D g2)
    {
        AffineTransform affineTransform = g2.getTransform();
        AffineTransform affineTransform1 = AffineTransform.getScaleInstance(1, 1);
//        affineTransform1.concatenate(AffineTransform.getTranslateInstance(0,10));
        affineTransform1.concatenate(AffineTransform.getRotateInstance(-Math.PI / 2));
        affineTransform1.scale(-1, 1);
        g2.transform(affineTransform1);
    }

    public static BufferedImage createBufferedImage(int width, int heigth)
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        return gc.createCompatibleImage(width, heigth, Transparency.OPAQUE);

    }


    public static Rectangle2D.Double increaseRectangle(Rectangle2D rect, double delta)
    {
        return new Rectangle2D.Double(rect.getMinX() - delta,
                rect.getMinY() - delta,
                rect.getMaxX() + delta,
                rect.getMaxY() + delta);
    }


    public static Rectangle2D.Double decreaseRectangle(Rectangle2D rect, Position position, double delta)
    {
        Rectangle2D.Double result = (Rectangle2D.Double) rect.getBounds2D();
        switch (position)
        {
            case top:
                result.setFrame(rect.getX(), rect.getY() + delta, rect.getWidth(), rect.getHeight() - delta);
                break;
            case down:
                result.setFrame(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight() - delta);
                break;
            case left:
                result.setFrame(rect.getX() + delta, rect.getY(), rect.getWidth() - delta, rect.getHeight());
                break;
            case right:
                result.setFrame(rect.getX(), rect.getY(), rect.getWidth() - delta, rect.getHeight());
                break;
            default:
                throw new IllegalArgumentException();
        }
        return result;
    }


    /**
     * Перемещает границы из одной точки в другую.
     *
     * @param original figure
     * @param oldPoint point
     * @param newPoint point
     * @return rectangle
     */
    public static Rectangle2D.Double translateBounds(Rectangle2D.Double original, Point2D.Double oldPoint, Point2D.Double newPoint)
    {
        Rectangle2D.Double fRect = (Rectangle2D.Double) original.getBounds2D();
        Point2D.Double fAnchor = new Point2D.Double(fRect.getMinX(), fRect.getMinY());
        Point2D.Double fLead = new Point2D.Double(fRect.getMaxX(), fRect.getMaxY());

        AffineTransform tx = new AffineTransform();
        tx.translate(newPoint.x - oldPoint.x, newPoint.y - oldPoint.y);

        tx.transform(fAnchor, fAnchor);
        tx.transform(fLead, fLead);
        fRect.setFrameFromDiagonal(fAnchor, fLead);
        return fRect;
    }

    public static Rectangle2D.Double translateBounds(Rectangle2D.Double original, AffineTransform tx)
    {
        Rectangle2D.Double fRect = (Rectangle2D.Double) original.getBounds2D();
        Point2D.Double fAnchor = new Point2D.Double(fRect.getMinX(), fRect.getMinY());
        Point2D.Double fLead = new Point2D.Double(fRect.getMaxX(), fRect.getMaxY());

        tx.transform(fAnchor, fAnchor);
        tx.transform(fLead, fLead);
        fRect.setFrameFromDiagonal(fAnchor, fLead);
        return fRect;
    }

    public static double getLengthTo(Position position, Rectangle2D.Double parent, Rectangle2D.Double child)
    {
        switch (position)
        {
            case down:
                break;
            case left:
                break;
            case right:
                break;
            case top:
                Line2D.Double line = new Line2D.Double(parent.getX(), parent.getY(), parent.getMaxX(), parent.getY());
                return line.ptLineDist(new Point2D.Double(child.getCenterX(), child.getY()));
            default:
                throw new IllegalArgumentException();
        }
        return 0;
    }


    public static double getLengthTo(Position position, Rectangle parent, Rectangle child)
    {
        switch (position)
        {
            case down:
                break;
            case left:
                break;
            case right:
                break;
            case top:
                Line2D.Double line = new Line2D.Double(parent.getX(), parent.getY(), parent.getMaxX(), parent.getY());
                return line.ptLineDist(new Point2D.Double(child.getCenterX(), child.getY()));
            default:
                throw new IllegalArgumentException();
        }
        return 0;
    }


    /**
     * Возвращает точку пересечения двух линий
     */
    public static Point2D.Double getIntersectionPoint(Line2D.Double line1, Line2D.Double line2)
    {
        if (!line1.intersectsLine(line2)) return null;
        double px = line1.getX1(),
                py = line1.getY1(),
                rx = line1.getX2() - px,
                ry = line1.getY2() - py;
        double qx = line2.getX1(),
                qy = line2.getY1(),
                sx = line2.getX2() - qx,
                sy = line2.getY2() - qy;

        double det = sx * ry - sy * rx;
        if (det == 0)
        {
            return null;
        }
        else
        {
            double z = (sx * (qy - py) + sy * (px - qx)) / det;
            if (z == 0 || z == 1) return null;  // intersection at end point!
            return new Point2D.Double(
                    (px + z * rx), (py + z * ry));
        }
    } // end intersection line-line


    public static double getQuarterOfLine(Line2D.Double line)
    {
        //I четверть
        if (line.x1 < line.x2 && line.y1 >= line.y2)
        {
            return 0;
        }
        //II четверть
        else if (line.x1 >= line.x2 && line.y1 > line.y2)
        {
            return 90;
        }
        //III четверть
        else if (line.x1 > line.x2 && line.y1 <= line.y2)
        {
            return 180;
        }
        //IV четверть
        else if (line.x1 <= line.x2 && line.y1 < line.y2)
        {
            return 270;
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Меняет местами start и end если не I IV четверть. Нужно менять так как atag возвражает -90 до 90
     *
     * @param line
     * @return
     */

    public static Line2D.Double orderPoints(Line2D.Double line)
    {
        if (line.x1 > line.x2)
        {
            line.setLine(line.getP2(), line.getP1());
        }
        return line;
    }


    /**
     * Угол относительно оси x
     *
     * @param line
     * @return
     */
    public static double getXAngle(Line2D.Double line)
    {
        double catx = line.getP2().getX() - line.getP1().getX();
        //положительная коордената когда вторая точка имеет меньшую
        double caty = (line.getP2().getY() - line.getP1().getY()) * -1;
        //угол относительно оси x
        double ax = Math.atan(caty / catx) * 180 / Math.PI;
        return ax;
    }


    /**
     * Вращает point вокруг центра
     */
    public static Point2D.Double rotate(Point2D.Double point, Point2D.Double center, double angle)
    {
        AffineTransform affineTransform = AffineTransform.getRotateInstance(angle, center.getX(), center.getY());
        Point2D.Double p = new Point2D.Double();
        affineTransform.transform(point, p);
        return p;
    }
}
