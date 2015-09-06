package by.dak.autocad.ocom;


import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class AcadBlock extends AcadModelSpace
        implements AcadEntity
{
    AcadBlock(Variant createFrom, AcadApplication app)
    {
        super(createFrom, app);
    }

    public void Delete()
    {
        Dispatch.call(this.impl.toDispatch(), "Delete");
    }

    public AcadDocument getDocument()
    {
        return new AcadDocument(Dispatch.get(this.impl.toDispatch(), "Document"), this.application);
    }

    public String getObjectName()
    {
        return Dispatch.get(this.impl.toDispatch(), "ObjectName").toString();
    }

    public String getHandle()
    {
        return Dispatch.get(this.impl.toDispatch(), "Handle").toString();
    }

    public boolean getHasExtentionDictionary()
    {
        return Dispatch.get(this.impl.toDispatch(), "HasExtensionDictionary").getBoolean();
    }

    public AcadHyperlinks getHyperlinks()
    {
        return new AcadHyperlinks(Dispatch.get(this.impl.toDispatch(), "Hyperlinks"), this.application);
    }

    public String getLayer()
    {
        return Dispatch.get(this.impl.toDispatch(), "Layer").toString();
    }

    public void setLayer(String layer)
    {
        Dispatch.put(this.impl.toDispatch(), "Layer", layer);
    }


}




