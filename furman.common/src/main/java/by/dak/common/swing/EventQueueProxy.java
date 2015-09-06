package by.dak.common.swing;

import java.awt.*;

/**
 * User: akoyro
 * Date: 10/13/13
 * Time: 9:32 PM
 */
public class EventQueueProxy extends EventQueue
{
    private ExceptionHandler exceptionHandler;

    protected void dispatchEvent(AWTEvent newEvent)
    {
        try
        {
            super.dispatchEvent(newEvent);
        }
        catch (Throwable t)
        {
            getExceptionHandler().handle(t);
        }
    }

    public ExceptionHandler getExceptionHandler()
    {
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler)
    {
        this.exceptionHandler = exceptionHandler;
    }
}
