package by.dak.utils;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.BindingListener;

import java.util.ArrayList;

/**
 * User: akoyro
 * Date: 24.03.2010
 * Time: 12:32:26
 */
public class DBindingGroup extends BindingGroup
{
    private ArrayList<BindingListener> list = new ArrayList<BindingListener>();

    /**
     * Метод используется для добавление листнеров которые
     * не должны быть выполнина на bind только после bind
     *
     * @param bindingListener
     */
    public void addValueBindingListener(BindingListener bindingListener)
    {
        addBindingListener(bindingListener);
        list.add(bindingListener);
    }

    public void removeValueBindingListener(BindingListener bindingListener)
    {
        removeBindingListener(bindingListener);
        list.remove(bindingListener);
    }

    public BindingListener[] getValueBindingListeners()
    {
        return list.toArray(new BindingListener[]{});
    }
}
