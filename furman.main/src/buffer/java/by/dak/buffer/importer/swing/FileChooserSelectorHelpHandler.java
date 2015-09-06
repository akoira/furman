package by.dak.buffer.importer.swing;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 17.11.2010
 * Time: 20:45:26
 * To change this template use File | Settings | File Templates.
 */
public class FileChooserSelectorHelpHandler
{
    private FileChooserSelector fileChooserSelector = new FileChooserSelector();

    public FileChooserSelectorHelpHandler(File file)
    {
        getFileChooserSelector().setFile(file);
    }

    public FileChooserSelector getFileChooserSelector()
    {
        return fileChooserSelector;
    }

    public void setFileChooserSelector(FileChooserSelector fileChooserSelector)
    {
        this.fileChooserSelector = fileChooserSelector;
    }
}
