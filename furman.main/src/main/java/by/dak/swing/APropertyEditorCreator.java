package by.dak.swing;

import org.jdesktop.beansbinding.BindingGroup;

import java.awt.*;
import java.beans.PropertyDescriptor;

/**
 * User: akoyro
 * Date: 20.11.2009
 * Time: 18:17:09
 */
public abstract class APropertyEditorCreator<V>
{
    public abstract Component createEditor(V value, String property, PropertyDescriptor descriptor, BindingGroup bindingGroup);
}
