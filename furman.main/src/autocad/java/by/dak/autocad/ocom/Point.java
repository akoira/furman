package by.dak.autocad.ocom;


import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple3f;

public class Point extends Point3d
{
    Variant toVariant()
    {
        Variant point = new Variant();
        SafeArray pointArray = new SafeArray(5, 3);
        pointArray.fromDoubleArray(new double[]{this.x, this.y, this.z});
        point.putSafeArray(pointArray);
        return point;
    }

    Point(Variant pointAsVariant)
    {
        this(pointAsVariant.toSafeArray(false).toDoubleArray());
    }

    public Point(double arg0, double arg1, double arg2)
    {
        super(arg0, arg1, arg2);
    }

    public Point(double[] arg0)
    {
        super(arg0);
    }

    public Point(Point3d arg0)
    {
        super(arg0);
    }

    public Point(Point3f arg0)
    {
        super(arg0);
    }

    public Point(Tuple3f arg0)
    {
        super(arg0);
    }

    public Point(Tuple3d arg0)
    {
        super(arg0);
    }

    public Point()
    {
    }

    public static Variant toArray(Point[] points)
    {
        SafeArray pointArray = new SafeArray(5, points.length * 3);
        for (int i = 0; i < points.length; i++)
        {
            pointArray.setDouble(i * 3, points[i].x);
            pointArray.setDouble(i * 3 + 1, points[i].y);
            pointArray.setDouble(i * 3 + 2, points[i].z);
        }
        Variant array = new Variant();
        array.putSafeArray(pointArray);
        return array;
    }
}




