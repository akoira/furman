package by.dak.autocad.ocom;


import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class AcadView extends AcadObject
{
    AcadView(Variant createFrom, AcadApplication app)
    {
        super(createFrom, app);
    }

    public void Delete()
    {
        Dispatch.call(this.impl.toDispatch(), "Delete");
    }

    public void setCenter(Point2D center)
    {
        Dispatch.put(this.impl.toDispatch(), "Center", center.getVariant());
    }

    public void setTarget(Point target)
    {
        Dispatch.put(this.impl.toDispatch(), "Target", target.toVariant());
    }

    public void setDirection(Point direction)
    {
        Dispatch.put(this.impl.toDispatch(), "Direction", direction.toVariant());
    }
}




