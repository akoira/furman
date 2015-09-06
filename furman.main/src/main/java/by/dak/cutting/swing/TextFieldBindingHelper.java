package by.dak.cutting.swing;

import org.jdesktop.beansbinding.*;

import javax.swing.*;
import java.awt.*;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author admin
 * @version 0.1 28.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class TextFieldBindingHelper
{
    private Object bean;
    private JComponent component;
    private BindingGroup bindingGroup;

    public TextFieldBindingHelper(Object bean, JComponent component, BindingGroup bindingGroup)
    {
        this.bean = bean;
        this.component = component;
        this.bindingGroup = bindingGroup;
    }

    public void bind()
    {
        try
        {
            BeanInfo componentBeanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] entityProperties = componentBeanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : entityProperties)
            {
                if (property.getWriteMethod() == null)
                {
                    continue;
                }

                JTextField textField = getFieldBy(property);
                if (textField != null)
                {
                    Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean, BeanProperty
                            .create(property.getName()), textField, BeanProperty.create("text"));
                    binding.setSourceUnreadableValue(null);
                    bindingGroup.addBinding(binding);
                }
            }
        }
        catch (IntrospectionException e)
        {
            Logger.getLogger(TextFieldBindingHelper.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    public Object getBean()
    {
        return bean;
    }

    public JComponent getComponent()
    {
        return component;
    }

    public BindingGroup getBindingGroup()
    {
        return bindingGroup;
    }

    private JTextField getFieldBy(PropertyDescriptor propertyDescriptor)
    {
        String componentName = "field" + propertyDescriptor.getName();
        Component[] components = getComponent().getComponents();
        for (Component component : components)
        {
            if (component instanceof JTextField && component.getName().equalsIgnoreCase(componentName))
            {
                return (JTextField) component;
            }
        }
        return null;
    }

}