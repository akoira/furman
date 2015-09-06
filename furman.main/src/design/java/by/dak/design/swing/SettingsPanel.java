package by.dak.design.swing;

import by.dak.cutting.swing.store.Constants;
import by.dak.design.settings.Settings;
import by.dak.swing.AValueTab;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 17.09.11
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public class SettingsPanel extends AValueTab<Settings>
{
    public SettingsPanel()
    {
        setVisibleProperties(new String[]{
                "boardNumeration",
                "cellNumeration"
        });
        setCacheEditorCreator(Constants.getEntityEditorCreators(Settings.class));
    }

    public void saveSettings(ObjectOutputStream objectOutputStream) throws IOException
    {
        objectOutputStream.writeObject(getValue());
    }

    public void loadSettings(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException
    {
        Settings settings = (Settings) objectInputStream.readObject();
        if (settings != null)
        {
            getValue().setBoardNumeration(settings.isBoardNumeration());
            getValue().setCellNumeration(settings.isCellNumeration());
        }
    }
}
