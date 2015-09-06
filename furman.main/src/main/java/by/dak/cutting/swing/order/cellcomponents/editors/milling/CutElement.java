package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 29.07.2009
 * Time: 13:58:06
 * To change this template use File | Settings | File Templates.
 */
public class CutElement
{
    private CurveFigure line1;
    private CurveFigure line2;

    private Point2D.Double point1;
    private Point2D.Double point2;

    public CutElement()
    {
    }

    public void cut()
    {
        Point2D.Double point11 = line1.getStartPoint();
        Point2D.Double point12 = line1.getEndPoint();
        Point2D.Double point21 = line2.getStartPoint();
        Point2D.Double point22 = line2.getEndPoint();

        double b1;
        double c1;
        double b2;
        double c2;

        if (Math.abs(point11.getX() - point12.getX()) <= 0.001)
        {
            b1 = 1;
            c1 = 0;
        }
        else
        {
            b1 = (point11.getY() - point12.getY()) / (point11.getX() - point12.getX());
            c1 = point11.getY() - b1 * point11.getX();
        }

        if (Math.abs(point21.getX() - point22.getX()) <= 0.001)
        {
            b2 = 1;
            c2 = 0;
        }
        else
        {
            b2 = (point21.getY() - point22.getY()) / (point21.getX() - point22.getX());
            c2 = point21.getY() - b2 * point21.getX();
        }

        if (b1 - b2 == 0)
        {
            return;
        }


        if (b1 != 0 && b2 != 0 && c1 != 0 && c2 != 0)
        {
            return;
        }
        else
        {
            line1.willChange();
            line2.willChange();

            double dist1 = line1.getStartPoint().distance(line2.getStartPoint());
            double dist2 = line1.getStartPoint().distance(line2.getEndPoint());
            double dist3 = line1.getEndPoint().distance(line2.getStartPoint());
            double dist4 = line1.getEndPoint().distance(line2.getEndPoint());
            double dist = Math.max(Math.max(dist1, dist2), Math.max(dist3, dist4));

            if (dist == dist1)
            {
                line1.setStartPoint(line1.getStartPoint());
                line2.setStartPoint(line2.getStartPoint());
                line1.setEndPoint(point1);
                line2.setEndPoint(point2);
            }
            else if (dist == dist2)
            {
                line1.setStartPoint(line1.getStartPoint());
                line2.setEndPoint(line2.getEndPoint());
                line1.setEndPoint(point1);
                line2.setStartPoint(point2);
            }
            else if (dist == dist3)
            {
                line1.setEndPoint(line1.getEndPoint());
                line2.setStartPoint(line2.getStartPoint());
                line1.setStartPoint(point1);
                line2.setEndPoint(point2);
            }
            else
            {
                line1.setEndPoint(line1.getEndPoint());
                line2.setEndPoint(line2.getEndPoint());
                line1.setStartPoint(point1);
                line2.setStartPoint(point2);
            }

            line1.setStartPoint(new Point2D.Double((int) line1.getStartPoint().getX(), (int) line1.getStartPoint().getY()));
            line1.setEndPoint(new Point2D.Double((int) line1.getEndPoint().getX(), (int) line1.getEndPoint().getY()));

            line2.setStartPoint(new Point2D.Double((int) line2.getStartPoint().getX(), (int) line2.getStartPoint().getY()));
            line2.setEndPoint(new Point2D.Double((int) line2.getEndPoint().getX(), (int) line2.getEndPoint().getY()));

            line1.changed();
            line2.changed();
        }
    }

    public void setLine1(CurveFigure line1)
    {
        this.line1 = line1;
    }

    public void setLine2(CurveFigure line2)
    {
        this.line2 = line2;
    }

    public void setPoint1(Point2D.Double point1)
    {
        this.point1 = point1;
    }

    public void setPoint2(Point2D.Double point2)
    {
        this.point2 = point2;
    }
}
