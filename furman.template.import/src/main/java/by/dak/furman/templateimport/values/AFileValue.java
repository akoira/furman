package by.dak.furman.templateimport.values;

import java.io.File;

/**
 * User: akoyro
 * Date: 9/28/13
 * Time: 12:06 PM
 */
public class AFileValue<P extends AValue, C extends AValue> extends AValue<P, C> implements IFileAware
{
    private File file;

    @Override
    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    public void addMessage(String message, Exception e, Object... param)
    {
        if (param == null)
            param = new Object[]{file.getAbsoluteFile()};
        else
        {
            Object[] newParams = new Object[param.length + 1];
            System.arraycopy(param, 0, newParams, 0, param.length);
            newParams[param.length] = file.getAbsoluteFile();
            param = newParams;
        }
        getMessages().add(Message.valueOf(message, e, param));
    }

    public void addMessage(String message, Object... param)
    {
        addMessage(message, null, param);
    }

}
