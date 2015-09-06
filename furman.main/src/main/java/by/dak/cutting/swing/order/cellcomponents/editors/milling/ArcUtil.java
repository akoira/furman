package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 06.08.2009
 * Time: 17:19:29
 * To change this template use File | Settings | File Templates.
 */
public class ArcUtil
{
    public static void invokeD(ArcValue value)
    {
        value.len = value.end.distance(value.start);

        if (value.position)
        {
            value.d = Math.sqrt(value.len * value.len / 2 / (1 - Math.cos(Math.toRadians(value.angleExtent))));
        }
        else
        {
            value.d = Math.sqrt(value.len * value.len / 2 / (1 - Math.cos(Math.toRadians(360 - value.angleExtent))));
        }
    }

    private static void calcL(ArcValue value)
    {
        value.len = value.end.distance(value.start);

        value.l = Math.sqrt(value.d * value.d - (value.len / 2) * (value.len / 2));
    }

    public static void invokeXY(ArcValue value)
    {
        calcL(value);
        value.corner = ArcUtil.calcAngle(value.end, value.start);

        value.xm = value.start.getX() + (value.end.getX() - value.start.getX()) / 2;
        value.ym = value.start.getY() + (value.end.getY() - value.start.getY()) / 2;

        value.xc1 = value.xm - Math.sin(Math.toRadians(value.corner)) * value.l;
        value.yc1 = value.ym - Math.cos(Math.toRadians(value.corner)) * value.l;

        value.xc2 = value.xm + Math.sin(Math.toRadians(value.corner)) * value.l;
        value.yc2 = value.ym + Math.cos(Math.toRadians(value.corner)) * value.l;

        value.x1 = value.xc1 - value.d;
        value.y1 = value.yc1 - value.d;

        value.x2 = value.xc2 - value.d;
        value.y2 = value.yc2 - value.d;
    }

    public static void invokeAngleStart(ArcValue value)
    {
        if (value.direction)
        {
            value.angleStart = ArcUtil.calcAngle(new Point2D.Double(value.xc1, value.yc1), value.end);
        }
        else
        {
            value.angleStart = ArcUtil.calcAngle(new Point2D.Double(value.xc2, value.yc2), value.start);
        }
    }

    public static void invokeAngleExtent(ArcValue value)
    {
        value.angleExtent = Math.toDegrees(Math.acos(1 - value.len * value.len / (2 * value.d * value.d)));
        if (!value.position)
        {
            value.angleStart += value.angleExtent;
            value.angleExtent = 360 - value.angleExtent;
        }
    }

    public static void invokeSetting(ArcValue value, Arc2D.Double arc)
    {
        value.angleStart = arc.getAngleStart();
        value.angleExtent = arc.getAngleExtent();
        value.end = (Point2D.Double) arc.getEndPoint();
        value.start = (Point2D.Double) arc.getStartPoint();
        if (value.angleExtent > 180)
        {
            value.position = false;
        }
        else
        {
            value.position = true;
        }
        invokeD(value);
        invokeXY(value);
        if (Math.abs(arc.getCenterX() - value.xc1) <= 0.00001 && Math.abs(arc.getCenterY() - value.yc1) <= 0.00001)
        {
            value.direction = true;
        }
        else
        {
            value.direction = false;
        }

        value.xm = value.start.getX() + (value.end.getX() - value.start.getX()) / 2;
        value.ym = value.start.getY() + (value.end.getY() - value.start.getY()) / 2;
    }

    public static double calcAngle(Point2D.Double anchor, Point2D.Double lead)
    {
        double corner;
        double y = lead.getY() - anchor.getY();
        double x = lead.getX() - anchor.getX();
        if (y == 0)
        {
            if (x >= 0)
            {
                return 0;
            }
            else
            {
                return 180;
            }
        }
        corner = Math.toDegrees(Math.atan(Math.abs(y) / Math.abs(x)));
        if (x > 0 && y < 0)
        {
        }
        else if (x < 0 && y < 0)
        {
            corner = 180 - corner;
        }
        else if (x < 0 && y > 0)
        {
            corner = 180 + corner;
        }
        else
        {
            corner = 360 - corner;
        }
        return corner;
    }

}
