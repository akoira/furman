package by.dak.autocad.com;

import by.dak.autocad.com.event.AcadApplicationEventHandler;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.DispatchEvents;
import com.jacob.com.Variant;

/**
 * User: akoyro
 * Date: 27.03.11
 * Time: 20:32
 */
public class Application extends ActiveXComponent
{
    private static final String PROG_ID = "AutoCAD.Application";


    public static final String SYS_BACKGROUNDPLOT = "BACKGROUNDPLOT";
    public static final String SYS_PELLIPSE = "PELLIPSE";

    private DispatchEvents dispatchEvents;

    public Application()
    {
        super(PROG_ID);
    }

    public Application(Dispatch dispatchToBeWrapped)
    {
        super(dispatchToBeWrapped);
    }

    public AcadState GetAcadState()
    {
        return new AcadState(this.invoke("GetAcadState").getDispatch());
    }

    public void setVisible(boolean visible)
    {
        this.setProperty("Visible", new Variant(visible));
    }

    public boolean isVisible()
    {
        return this.getProperty("Visible").getBoolean();
    }

    public Documents getDocuments()
    {
        return new Documents(this);
    }

    public Document getActiveDocument()
    {
        return new Document(this);
    }


    public void addListener(AcadApplicationEventHandler acadApplicationEventHandler)
    {
        dispatchEvents = new DispatchEvents(this, acadApplicationEventHandler, PROG_ID);
    }

    public void removeListener()
    {
        if (dispatchEvents != null)
        {
            dispatchEvents.safeRelease();
        }
    }

    public void Quit()
    {
        this.invoke("Quit");
    }

}
