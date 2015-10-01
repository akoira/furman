package by.dak.design.settings;

import by.dak.design.swing.SettingsPanel;
import org.apache.commons.io.IOUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.session.PropertySupport;

import java.awt.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 19.09.11
 * Time: 12:32
 * To change this template use File | Settings | File Templates.
 */
public class SettingsWindowProperty implements PropertySupport
{
    private final static String FILE_SUFFIX = ".settings";

    private void checkComponent(Component component)
    {
        if (component == null)
        {
            throw new IllegalArgumentException("null component");
        }
        if (!(component instanceof SettingsPanel))
        {
            throw new IllegalArgumentException("invalid component");
        }
    }

    @Override
    public Object getSessionState(Component c)
    {
        checkComponent(c);

        String fileName = c.getName() + FILE_SUFFIX;
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            outputStream = Application.getInstance().getContext().getLocalStorage().openOutputFile(fileName);
            objectOutputStream = new ObjectOutputStream(outputStream);
            ((SettingsPanel) c).saveSettings(objectOutputStream);
            objectOutputStream.flush();
        }
        catch (Throwable e)
        {
            Logger.getLogger(SettingsWindowProperty.class.getName()).log(Level.WARNING, null, e);
        }
        finally
        {
            IOUtils.closeQuietly(objectOutputStream);
            IOUtils.closeQuietly(outputStream);
        }

        return null;
    }

    @Override
    public void setSessionState(Component c, Object state)
    {
        checkComponent(c);
        restore((SettingsPanel) c);
    }

    public static void restore(SettingsPanel settingsPanel)
    {
        String fileName = settingsPanel.getName() + FILE_SUFFIX;

        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try
        {
            inputStream = Application.getInstance().getContext().getLocalStorage().openInputFile(fileName);
            if (inputStream != null)
            {
                objectInputStream = new ObjectInputStream(inputStream);
                settingsPanel.loadSettings(objectInputStream);
            }
        }
        catch (Throwable e)
        {
            Logger.getLogger(SettingsWindowProperty.class.getName()).log(Level.WARNING, null, e);
        }
        finally
        {
            IOUtils.closeQuietly(objectInputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }
}
