package by.dak.cutting.swing.order.cellcomponents.editors;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class CheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor
{

    private JCheckBox checkBox;

    public CheckBoxCellEditor()
    {
        checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public Component getTableCellEditorComponent(
            JTable table,
            Object value,
            boolean isSelected,
            int row,
            int column)
    {

        checkBox.setSelected((Boolean) value);

        return checkBox;
    }

    public Object getCellEditorValue()
    {
        return checkBox.isSelected();
    }

    public JComponent getComponent()
    {
        return checkBox;
    }
}