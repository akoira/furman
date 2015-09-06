package by.dak.autocad.ocom;


import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import javax.vecmath.Point2d;

public class Point2D
{
    double x;
    double y;

    public Point2D()
    {
        this(0.0D, 0.0D);
    }

    public Point2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Point2D(Point2d p)
    {
        this(p.x, p.y);
    }

    Variant getVariant()
    {
        Variant point = new Variant();
        SafeArray pointArray = new SafeArray(5, 2);
        pointArray.fromDoubleArray(new double[]{this.x, this.y});
        point.putSafeArray(pointArray);
        return point;
    }

    public static Variant toArray(Point2D[] points)
    {
        SafeArray pointArray = new SafeArray(5, points.length * 2);
        for (int i = 0; i < points.length; i++)
        {
            pointArray.setDouble(i * 2, points[i].x);
            pointArray.setDouble(i * 2 + 1, points[i].y);
        }
        Variant array = new Variant();
        array.putSafeArray(pointArray);
        return array;
    }
}




