package by.dak.cutting.swing.renderer;

import by.dak.utils.convert.StringValueAnnotationProcessor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @author admin
 * @version 0.1 07.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class EntityTableRenderer<E> extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (value != null)
        {
            value = StringValueAnnotationProcessor.getProcessor().getEntityToStringConverter(value.getClass()).convert((E) value);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
