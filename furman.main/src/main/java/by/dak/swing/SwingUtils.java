package by.dak.swing;

import org.apache.commons.beanutils.BeanUtils;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 30.07.2010
 * Time: 13:23:55
 */
public class SwingUtils
{
    public static final void setPropertyForAllComponents(String property, Object value, Component parent)
    {
        try
        {
            BeanUtils.setProperty(parent, property, value);
        }
        catch (Exception e)
        {
        }
        if (parent instanceof JComponent)
        {
            Component[] components = ((JComponent) parent).getComponents();
            if (components != null)
            {
                for (Component component : components)
                {
                    setPropertyForAllComponents(property, value, component);
                }
            }
        }
    }
}
