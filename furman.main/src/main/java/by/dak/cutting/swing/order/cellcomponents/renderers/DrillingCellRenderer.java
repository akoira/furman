package by.dak.cutting.swing.order.cellcomponents.renderers;

import by.dak.cutting.swing.order.data.Drilling;
import com.jidesoft.swing.JideButton;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class DrillingCellRenderer implements TableCellRenderer {

    private JideButton renderer = new JideButton("NO");

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        renderer.setText((value == null || ((Drilling) value).isEmpty()) ? "NO" : "Yes");
        renderer.setSelected(hasFocus);
        return renderer;
    }
}
