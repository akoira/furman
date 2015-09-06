package by.dak.autocad;

import by.dak.autocad.com.Application;

/**
 * User: akoyro
 * Date: 30.03.11
 * Time: 13:35
 */
public interface AutocadDelegator
{
    public void applicationStopped(Application application);

    void applicationStarting();
}


