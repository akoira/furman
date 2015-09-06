package by.dak.autocad.com;


import by.dak.autocad.Point2D;
import by.dak.autocad.com.entity.AEntity;
import by.dak.autocad.com.entity.LWPolyline;
import com.jacob.com.Dispatch;

public class ModelSpace extends ASet<AEntity>
{
    ModelSpace(Dispatch dispatch)
    {
        super(dispatch);
    }

    public LWPolyline AddLightweightPolyline(Point2D[] vertices)
    {
        return new LWPolyline(this.invoke("AddLightweightPolyline", Point2D.toArray(vertices)).getDispatch());
    }

}






