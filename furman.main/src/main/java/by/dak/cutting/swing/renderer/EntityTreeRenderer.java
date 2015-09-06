package by.dak.cutting.swing.renderer;

import by.dak.swing.tree.ATreeNode;
import by.dak.utils.convert.StringValueAnnotationProcessor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * User: akoyro
 * Date: 26.11.2009
 * Time: 23:18:18
 */
public class EntityTreeRenderer<E> extends DefaultTreeCellRenderer
{
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        if (value != null && value instanceof DefaultMutableTreeNode)
        {
            if (value instanceof ATreeNode)
            {
                Icon icon = ((ATreeNode) value).getClosedIcon();
                if (icon != null)
                    setClosedIcon(icon);
                else
                    setClosedIcon(getDefaultClosedIcon());
                icon = ((ATreeNode) value).getLeafIcon();
                if (icon != null)
                    setLeafIcon(icon);
                else
                    setLeafIcon(getDefaultLeafIcon());

                icon = ((ATreeNode) value).getOpenIcon();
                if (icon != null)
                    setOpenIcon(icon);
                else
                    setOpenIcon(getDefaultOpenIcon());

            }

            Object entity = ((DefaultMutableTreeNode) value).getUserObject();
            if (entity != null)
            {
                value = StringValueAnnotationProcessor.getProcessor().getEntityToStringConverter(entity.getClass()).convert(entity);
            }

        }
        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
