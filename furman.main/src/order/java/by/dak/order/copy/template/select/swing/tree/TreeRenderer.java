package by.dak.order.copy.template.select.swing.tree;

import org.apache.commons.lang3.StringUtils;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.tree.DefaultXTreeCellRenderer;

import javax.swing.*;
import java.awt.*;

public class TreeRenderer extends DefaultXTreeCellRenderer
{
    private ResourceMap resourceMap;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {

        String sValue = convertValue(value);
        updateIcons(value);
        return super.getTreeCellRendererComponent(tree, sValue, sel, expanded, leaf, row, hasFocus);
    }

    public String convertValue(Object value)
    {
        if (value instanceof RootNode)
            return StringUtils.EMPTY;
        if (value instanceof CategoryNode)
            return ((CategoryNode) value).getValue().getName();
        if (value instanceof TemplateOrderNode)
            return ((TemplateOrderNode) value).getValue().getName();
        else
            throw new IllegalArgumentException();
    }

    public void updateIcons(Object value)
    {
        if (value instanceof RootNode)
            return;
        if (value instanceof CategoryNode)
        {
            setLeafIcon(getResourceMap().getIcon("icon.category"));
            setClosedIcon(getResourceMap().getIcon("icon.category"));
            setOpenIcon(getResourceMap().getIcon("icon.category"));
            return;
        }
        if (value instanceof TemplateOrderNode)
        {
            setLeafIcon(getResourceMap().getIcon("icon.template"));
            setClosedIcon(getResourceMap().getIcon("icon.template"));
            setOpenIcon(getResourceMap().getIcon("icon.template"));
            return;
        }
        else
            throw new IllegalArgumentException();
    }

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }
}
