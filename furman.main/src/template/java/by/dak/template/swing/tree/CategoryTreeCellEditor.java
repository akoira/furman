package by.dak.template.swing.tree;

import by.dak.persistence.FacadeContext;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * User: akoyro
 * Date: 25.03.11
 * Time: 13:42
 */
public class CategoryTreeCellEditor extends DefaultTreeCellEditor
{
    private CategoryNode categoryNode;

    public CategoryTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer)
    {
        super(tree, renderer);
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row)
    {
        if (value instanceof CategoryNode)
        {
            categoryNode = (CategoryNode) value;
            value = categoryNode.getCategory().getName();
        }
        return super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Object getCellEditorValue()
    {
        if (categoryNode != null)
        {
            categoryNode.getCategory().setName((String) super.getCellEditorValue());
            FacadeContext.getCategoryFacade().save(categoryNode.getCategory());
            return categoryNode.getCategory();
        }
        return super.getCellEditorValue();
    }
}
