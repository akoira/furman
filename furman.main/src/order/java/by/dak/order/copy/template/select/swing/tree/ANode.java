package by.dak.order.copy.template.select.swing.tree;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import javax.swing.*;

public abstract class ANode<V> extends DefaultMutableTreeTableNode
{
    private V value;

    private Icon leafIcon;
    private Icon closedIcon;
    private Icon openIcon;

    public V getValue()
    {
        return value;
    }

    public void setValue(V value)
    {
        this.value = value;
    }

    public Icon getLeafIcon()
    {
        return leafIcon;
    }

    public void setLeafIcon(Icon leafIcon)
    {
        this.leafIcon = leafIcon;
    }

    public Icon getClosedIcon()
    {
        return closedIcon;
    }

    public void setClosedIcon(Icon closedIcon)
    {
        this.closedIcon = closedIcon;
    }

    public Icon getOpenIcon()
    {
        return openIcon;
    }

    public void setOpenIcon(Icon openIcon)
    {
        this.openIcon = openIcon;
    }
}
