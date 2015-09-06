package by.dak.autocad.com.event;

import com.jacob.com.Variant;

/**
 * User: akoyro
 * Date: 23.02.11
 * Time: 14:06
 */
public class PlotEventHandler extends AcadApplicationEventHandler
{
    private boolean progress = false;

    public void BeginPlot(Variant[] arguments)
    {
        progress = true;
    }

    public void EndPlot(Variant[] arguments)
    {
        progress = false;
    }

    public boolean isProgress()
    {
        return progress;
    }
}
