package by.dak.launch;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: akoyro
 * Date: 12.10.2010
 * Time: 19:37:28
 */
public class Launcher
{
    private static final Logger LOGGER = Logger.getLogger(Launcher.class.getName());
    private File application;

    private File file;

    public int launch()
    {
        try
        {
            ProcessBuilder processBuilder = new ProcessBuilder(application.getAbsolutePath(), getFile().getAbsolutePath());
            Process process = processBuilder.start();
            return process.waitFor();
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }

    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    public File getApplication()
    {
        return application;
    }

    public void setApplication(File application)
    {
        this.application = application;
    }

    public static void main(String[] args)
    {
        Launcher launcher = new Launcher();
        launcher.setApplication(new File("C:\\Program Files (x86)\\Total Commander\\Plugins\\exe\\AkelPad.exe"));
        launcher.setFile(new File("D:\\tmp\\1.dwg"));
        launcher.launch();
    }
}
