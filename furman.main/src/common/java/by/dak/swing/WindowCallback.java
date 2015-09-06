package by.dak.swing;

import java.awt.*;

/**
 * User: akoyro
 * Date: 11.11.2010
 * Time: 12:38:43
 */
public class WindowCallback
{
    private Window window;

    public void dispose()
    {
        getWindow().dispose();
    }

    public Window getWindow()
    {
        return window;
    }

    public void setWindow(Window window)
    {
        this.window = window;
    }
}
