package by.dak.swing.image;

import org.jdesktop.application.session.PropertySupport;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * User: akoyro
 * Date: 03.03.11
 * Time: 17:12
 */
public class FileChooserProperty implements PropertySupport
{
    private void checkComponent(Component component)
    {
        if (component == null)
        {
            throw new IllegalArgumentException("null component");
        }
        if (!(component instanceof JFileChooser))
        {
            throw new IllegalArgumentException("invalid component");
        }
    }

    @Override
    public Object getSessionState(Component c)
    {
        checkComponent(c);
        JFileChooser fileChooser = (JFileChooser) c;
        File file = fileChooser.getSelectedFile();
        if (file != null)
        {
            File dir = file.getParentFile();
            if (dir.exists())
                return new FileChooserState(dir.getAbsolutePath());
            else
                return null;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setSessionState(Component c, Object state)
    {
        checkComponent(c);
        if (!(state instanceof FileChooserState))
        {
            throw new IllegalArgumentException("invalid state");
        }
        JFileChooser fc = (JFileChooser) c;
        fc.setCurrentDirectory(new File(((FileChooserState) state).getCurrentDirectory()));
    }


    public static class FileChooserState
    {
        private String currentDirectory;

        public FileChooserState()
        {
            super();
        }

        public FileChooserState(String currentDirectory)
        {
            this.currentDirectory = currentDirectory;
        }

        public String getCurrentDirectory()
        {
            return currentDirectory;
        }

        public void setCurrentDirectory(String dir)
        {
            this.currentDirectory = dir;
        }
    }
}
