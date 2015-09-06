package by.dak.autocad.com;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 11:13
 */
public class PlotConfigurations extends AObject
{
    public PlotConfigurations(Dispatch dispatch)
    {
        super(dispatch);
    }

    public PlotConfiguration Item(int index)
    {
        return new PlotConfiguration(invoke("Item", index).getDispatch());
    }

    public PlotConfiguration Item(String name)
    {
        return new PlotConfiguration(invoke("Item", name).getDispatch());
    }

    public int getCount()
    {
        return this.getPropertyAsInt("Count");
    }

    public PlotConfiguration Add(String name, boolean modelType)
    {
        return new PlotConfiguration(this.invoke("Add", new Variant(name), new Variant(modelType)).getDispatch());
    }

}
