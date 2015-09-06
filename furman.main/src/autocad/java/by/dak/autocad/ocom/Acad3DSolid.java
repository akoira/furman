package by.dak.autocad.ocom;


import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Acad3DSolid extends BaseAcadEntity
{
    public Acad3DSolid(Variant createFrom, AcadApplication app)
    {
        super(createFrom, app);
    }

    public void Rotate(Point p1, Point p2, double angle)
    {
        Dispatch.call(this.impl.toDispatch(), "Rotate3D", p1.toVariant(), p2.toVariant(), new Double(angle));
    }

    public void Move(Point p1, Point p2)
    {
        Dispatch.call(this.impl.toDispatch(), "Move", p1.toVariant(), p2.toVariant());
    }

    public void Boolean(int acBooleanType, Acad3DSolid solid)
    {
        Variant v = new Variant(acBooleanType);
        Dispatch.call(this.impl.toDispatch(), "Boolean", v, solid.impl);
    }
}




