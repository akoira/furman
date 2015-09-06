package by.dak.furman.templateimport;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * User: akoyro
 * Date: 10/7/13
 * Time: 10:15 PM
 */
public class SelectableCellRenderer extends JCheckBox implements TableCellRenderer
{
    public SelectableCellRenderer()
    {
        setHorizontalAlignment(CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (value instanceof Boolean)
            this.setSelected((Boolean) value);
        return this;
    }
}
