package by.dak.autocad.ocom;


import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class AcadViewport extends AcadObject
{
    AcadViewport(Variant createFrom, AcadApplication app)
    {
        super(createFrom, app);
    }

    public void setDirection(Direction dir)
    {
        Dispatch.put(this.impl.toDispatch(), "Direction", dir.toVariant());
    }

    public Direction getDirection()
    {
        return new Direction(Dispatch.get(this.impl.toDispatch(), "Direction"));
    }
}




