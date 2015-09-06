package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.Selectable;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

import java.util.Enumeration;

public abstract class AValueNode<V> extends DefaultMutableTreeTableNode implements Selectable
{
    public static final Property SELECT_PROPERTY = Property.valueOf(Selectable.class, StringUtils.EMPTY, true);
    private boolean valid = true;
    private boolean selected = false;

    private Property[] properties;

    public V getValue()
    {
        return (V) getUserObject();
    }

    public void setValue(V value)
    {
        setUserObject(value);
    }

    public <V> AValueNode<V> getChildBy(V value)
    {
        Enumeration<? extends MutableTreeTableNode> enumeration = children();
        while (enumeration.hasMoreElements())
        {
            AValueNode<V> node = (AValueNode<V>) enumeration.nextElement();
            if (node.getValue() == value)
                return node;

        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isEditable(int column)
    {
        return properties[column].isEditable();
    }

    public boolean isValid()
    {
        return valid;
    }

    public void setValid(boolean valid)
    {
        this.valid = valid;
        if (!valid && parent != null && parent instanceof AValueNode)
            ((AValueNode) parent).setValid(false);
    }

    public Property[] getProperties()
    {
        return properties;
    }

    public void setProperties(Property... properties)
    {
        this.properties = properties;
    }

    @Override
    public Object getValueAt(int column)
    {
        Property property = properties[column];
        if (property == SELECT_PROPERTY)
        {
            return isSelected();
        }
        else
        {
            try
            {
                return PropertyUtils.getProperty(getValue(), properties[column].getPath());
            }
            catch (Exception e)
            {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void setValueAt(Object aValue, int column)
    {
        Property property = properties[column];
        if (property == SELECT_PROPERTY)
            setSelected((Boolean) aValue);
        else
            try
            {
                BeanUtils.setProperty(getValue(), property.getPath(), aValue);
            }
            catch (Exception e)
            {
                throw new IllegalArgumentException(e);
            }
    }

    @Override
    public int getColumnCount()
    {
        return this.properties.length;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public Class getColumnClass(int column)
    {
        return properties[column].getPropertyClass();
    }

}
