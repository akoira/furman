package by.dak.utils;

import org.jdesktop.application.Application;

import java.awt.*;
import java.io.IOException;

/**
 * Сохранияет состаяние компонента
 * User: user0
 * Date: 05.04.2010
 * Time: 11:33:37
 * To change this template use File | Settings | File Templates.
 */
public class StateUtils
{
    public static void saveSessionState(Component component, String name)
    {
        try
        {
            if (component != null)
            {
                Application.getInstance().getContext().getSessionStorage().save(component, name + ".xml");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void loadSessionState(Component component, String name)
    {
        try
        {
            if (component != null)
            {
                Application.getInstance().getContext().getSessionStorage().restore(component, name + ".xml");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
