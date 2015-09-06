package by.dak.autocad.com;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 11:58
 */
public class Plot extends AObject
{
    public Plot(Dispatch dispatchToBeWrapped)
    {
        super(dispatchToBeWrapped);
    }

    public boolean PlotToFile(String plotFile)
    {
        return this.invoke("PlotToFile", plotFile).getBoolean();
    }

    public boolean PlotToFile(String plotFile, String plotConfig)
    {
        return this.invoke("PlotToFile", new Variant(plotFile), new Variant(plotConfig)).getBoolean();
    }

}
