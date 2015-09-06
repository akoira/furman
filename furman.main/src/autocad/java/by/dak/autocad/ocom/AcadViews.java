package by.dak.autocad.ocom;


import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class AcadViews extends AcadObject
{
    AcadViews(Variant createFrom, AcadApplication app)
    {
        super(createFrom, app);
    }

    public void Add(String name)
    {
        Dispatch.call(this.impl.toDispatch(), "Add", name);
    }

    public AcadView Item(int index)
    {
        return new AcadView(Dispatch.call(this.impl.toDispatch(), "Item", new Integer(index)), this.application);
    }

    public int getCount()
    {
        return Dispatch.get(this.impl.toDispatch(), "Count").getInt();
    }
}




