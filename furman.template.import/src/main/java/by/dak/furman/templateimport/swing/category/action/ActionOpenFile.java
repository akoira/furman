package by.dak.furman.templateimport.swing.category.action;

import by.dak.furman.templateimport.parser.AParser;
import by.dak.furman.templateimport.values.AValue;

import java.io.IOException;

/**
 * User: akoyro
 * Date: 9/28/13
 * Time: 11:25 AM
 */
public class ActionOpenFile
{
    private String filePath;
    private AParser parser;
    private AValue value;

    public void action()
    {
        ProcessBuilder processBuilder = new ProcessBuilder("explorer.exe", filePath);
        try
        {
            final Process process = processBuilder.start();
            try
            {
                process.waitFor();
            }
            catch (InterruptedException e)
            {
                return;
            }

        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
}
