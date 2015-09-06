package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.IValueConverter;
import by.dak.furman.templateimport.values.AValue;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;

public abstract class AValueConverter<V extends AValueNode<? extends AValue>> implements IValueConverter<V>
{
    private ResourceMap resourceMap;

    private String iconKey;

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }


    public Icon getIcon(V value)
    {
        if (value.isValid())
            return getResourceMap().getIcon(getIconKey());
        else
            return getResourceMap().getIcon(String.format("%s.warning", getIconKey()));
    }

    public String getString(V value)
    {
        return value.getValue().getName();
    }

    public String getIconKey()
    {
        return iconKey;
    }

    public void setIconKey(String iconKey)
    {
        this.iconKey = iconKey;
    }
}
