package by.dak.autocad.ocom;


import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class AcadLWPolyline extends BaseAcadEntity
{
    public AcadLWPolyline(Variant createFrom, AcadApplication app)
    {
        super(createFrom, app);
    }

    public void setClosed(boolean closed)
    {
        Dispatch.put(this.impl.toDispatch(), "Closed", new Variant(closed));
    }

    public void setBulge(int index, double bulge)
    {
        Dispatch.call(this.impl.toDispatch(), "SetBulge", new Integer(index), new Double(bulge));
    }
}




