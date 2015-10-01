package by.dak.swing.infonode;

import net.infonode.docking.RootWindow;
import net.infonode.docking.util.ViewMap;
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
 * User: akoyro
 * Date: 12.09.11
 * Time: 16:14
 */
public class RootWindowProperty implements PropertySupport
{

    private final static String FILE_SUFFIX = ".RootWindow.layout";


    private void checkComponent(Component component)
    {
        if (component == null)
        {
            throw new IllegalArgumentException("null component");
        }
        if (!(component instanceof RootWindow))
        {
            throw new IllegalArgumentException("invalid component");
        }
    }

    @Override
    public Object getSessionState(Component c)
    {
        checkComponent(c);
        prepareToSave((RootWindow) c);

        String fileName = c.getName() + FILE_SUFFIX;
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {

            outputStream = Application.getInstance().getContext().getLocalStorage().openOutputFile(fileName);
            objectOutputStream = new ObjectOutputStream(outputStream);
            ((RootWindow) c).write(objectOutputStream);

            objectOutputStream.flush();
        }
        catch (Throwable e)
        {
            Logger.getLogger(RootWindowProperty.class.getName()).log(Level.WARNING, null, e);
        }
        finally
        {
            IOUtils.closeQuietly(objectOutputStream);
            IOUtils.closeQuietly(outputStream);
        }

        return null;
    }

    /**
     * Introduced to fix 9315
     *
     * @param rootWindow
     */
    private void prepareToSave(RootWindow rootWindow)
    {
        ViewMap viewMap = (ViewMap) rootWindow.getViewSerializer();
        if (viewMap.getViewCount() > 0 && rootWindow.getFocusedView() != null)
        {
            rootWindow.setFocusedView(viewMap.getView(0));
        }
    }

    @Override
    public void setSessionState(Component c, Object state)
    {
        checkComponent(c);
        restore((RootWindow) c);
    }

    public static void restore(RootWindow rootWindow)
    {
        String fileName = rootWindow.getName() + FILE_SUFFIX;

        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try
        {
            inputStream = Application.getInstance().getContext().getLocalStorage().openInputFile(fileName);
            if (inputStream != null)
            {
                objectInputStream = new ObjectInputStream(inputStream);
                rootWindow.read(objectInputStream);
            }
        }
        catch (Throwable e)
        {
            Logger.getLogger(RootWindowProperty.class.getName()).log(Level.WARNING, null, e);
        }
        finally
        {
            IOUtils.closeQuietly(objectInputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }
}
