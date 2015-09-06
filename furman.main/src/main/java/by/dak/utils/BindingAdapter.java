package by.dak.utils;

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 * User: akoyro
 * Date: 06.08.2009
 * Time: 12:07:20
 */
public abstract class BindingAdapter implements BindingListener
{
    @Override
    public void bindingBecameBound(Binding binding)
    {
    }

    @Override
    public void bindingBecameUnbound(Binding binding)
    {
    }

    @Override
    public void syncFailed(Binding binding, Binding.SyncFailure failure)
    {
    }

    @Override
    public void synced(Binding binding)
    {
    }

    @Override
    public void sourceChanged(Binding binding, PropertyStateEvent event)
    {
    }

    @Override
    public void targetChanged(Binding binding, PropertyStateEvent event)
    {
    }
}
