/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting;

import by.dak.utils.GenericUtils;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;

/**
 * @author admin
 */
public abstract class DialogShower<V extends JFrame>
{
    private V dialog;
    private Component relativeToComponet;

    public DialogShower(Component relativeToComponet)
    {
        this.relativeToComponet = relativeToComponet;
    }

    public V getDialog()
    {
        return dialog;
    }

    protected V createDialog()
    {
        try
        {
            Class dialogClass = GenericUtils.getParameterClass(this.getClass(), 0);

            Window window = getParentWindows();

            Constructor<V> constructor = dialogClass.getConstructor(Window.class, boolean.class);
            V dialog = constructor.newInstance(window, true);
            return dialog;
        }
        catch (Exception e)
        {
            CuttingApp.getApplication().getExceptionHandler().handle(relativeToComponet, e);
            throw new IllegalArgumentException("Invalid dialog argument");
        }
    }

    public Window getParentWindows()
    {
        Window window = SwingUtilities.getWindowAncestor(relativeToComponet);
        if (window == null)
        {
            window = CuttingApp.getApplication().getMainFrame();
        }
        return window;
    }

    public V createDictionaryDialog()
    {
        dialog = createDialog();
        return dialog;
    }

    protected abstract void postCreatingSetting();

    public void show()
    {
        if (dialog == null)
        {
            dialog = createDialog();
            dialog.setLocationRelativeTo(relativeToComponet);
        }
        postCreatingSetting();
        CuttingApp.getApplication().show(dialog);
    }
}
