package by.dak.autocad.ocom;


import com.jacob.com.Variant;

import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple3f;

public class Direction extends Point
{
    public Direction(Variant v)
    {
        super(v);
    }

    public Direction(double arg0, double arg1, double arg2)
    {
        super(arg0, arg1, arg2);
    }

    public Direction(double[] arg0)
    {
        super(arg0);
    }

    public Direction(Point3d arg0)
    {
        super(arg0);
    }

    public Direction(Point3f arg0)
    {
        super(arg0);
    }

    public Direction(Tuple3f arg0)
    {
        super(arg0);
    }

    public Direction(Tuple3d arg0)
    {
        super(arg0);
    }

    public Direction()
    {
    }
}




