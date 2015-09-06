package by.dak.autocad.ocom;


import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class AcadDocument extends AcadObject
{
    AcadDocument(Variant createFrom, AcadApplication app)
    {
        super(createFrom, app);
    }

    public AcadModelSpace getModelSpace()
    {
        return new AcadModelSpace(Dispatch.get(this.impl.toDispatch(), "ModelSpace"), this.application);
    }

    public AcadViewport getActiveViewport()
    {
        return new AcadViewport(Dispatch.get(this.impl.toDispatch(), "ActiveViewport"), this.application);
    }

    public void setActiveViewport(AcadViewport vp)
    {
        Dispatch.put(this.impl.toDispatch(), "ActiveViewport", vp.impl);
    }

    public AcadViews getViews()
    {
        return new AcadViews(Dispatch.get(this.impl.toDispatch(), "Views"), this.application);
    }

    public AcadUtility getUtility()
    {
        return new AcadUtility(Dispatch.get(this.impl.toDispatch(), "Utility"), this.application);
    }

    public AcadBlocks getBlocks()
    {
        return new AcadBlocks(this);
    }
}




