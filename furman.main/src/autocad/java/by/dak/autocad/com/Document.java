package by.dak.autocad.com;

import com.jacob.com.Variant;

/**
 * User: akoyro
 * Date: 27.03.11
 * Time: 21:16
 */
public class Document extends AObject
{
    public Document(Application application)
    {
        super(application.getProperty("ActiveDocument").getDispatch());
    }

    public ModelSpace getModelSpace()
    {
        return new ModelSpace(this.getProperty("ModelSpace").getDispatch());
    }

    public PlotConfigurations getPlotConfigurations()
    {
        return new PlotConfigurations(this.getProperty("PlotConfigurations").getDispatch());
    }

    public Plot getPlot()
    {
        return new Plot(this.getPropertyAsComponent("Plot"));
    }

    public Variant GetVariable(String name)
    {
        return this.invoke("GetVariable", name);
    }

    public void SetVariable(String name, Object value)
    {
        this.invoke("SetVariable", new Variant(name), new Variant(value));
    }

    public void Save()
    {
        this.invoke("Save");
    }

    public Blocks getBlocks()
    {
        return new Blocks(this.getProperty("Blocks").getDispatch());
    }

    public String getFullName()
    {
        return this.getPropertyAsString("FullName");
    }

    public void Regen()
    {
        this.invoke("Regen", 1);
    }

    public void SendCommand(String command)
    {
        this.invoke("SendCommand", command);
    }
}

