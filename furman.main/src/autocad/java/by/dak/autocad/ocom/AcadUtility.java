package by.dak.autocad.ocom;


import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class AcadUtility extends AcadObject
{
    AcadUtility(Variant createFrom, AcadApplication app)
    {
        super(createFrom, app);
    }

    public Point GetPoint(Point point, String question)
    {
        return new Point(Dispatch.call(this.impl.toDispatch(), "GetPoint", point.toVariant(), new Variant(question)));
    }

    public Point GetPoint(Point point)
    {
        return GetPoint(point, "");
    }

    public Point GetPoint()
    {
        return GetPoint(new Point());
    }

    public Point GetPoint(String question)
    {
        return GetPoint(new Point(), question);
    }
}




