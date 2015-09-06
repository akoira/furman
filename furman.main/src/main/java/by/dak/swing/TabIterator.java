package by.dak.swing;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 19.07.2010
 * Time: 12:00:42
 */
public abstract class TabIterator<R>
{
    private JTabbedPane tabbedPane;

    public TabIterator(JTabbedPane tabbedPane)
    {
        this.tabbedPane = tabbedPane;
    }

    public R iterate()
    {
        R result = null;
        int count = tabbedPane.getTabCount();
        for (int i = 0; i < count; i++)
        {
            Component tab = tabbedPane.getComponentAt(i);
            result = iterate(tab);
            if (!canNext(result))
            {
                break;
            }
        }
        return result;
    }

    protected abstract R iterate(Component tab);

    public boolean canNext(R previosResult)
    {
        return true;
    }

}
