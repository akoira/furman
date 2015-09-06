package by.dak.cutting.swing.order.cellcomponents.renderers;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class CheckBoxCellRenderer  extends JCheckBox implements TableCellRenderer {

    public  CheckBoxCellRenderer() {
        super();
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        if (value instanceof Boolean) {
            setSelected((Boolean) value);
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
        }
        return this;
    }
}
