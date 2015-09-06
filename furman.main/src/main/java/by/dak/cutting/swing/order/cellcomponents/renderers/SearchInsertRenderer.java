package by.dak.cutting.swing.order.cellcomponents.renderers;

import by.dak.cutting.swing.order.SearchInsertPanel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class SearchInsertRenderer implements TableCellRenderer
{

    private SearchInsertPanel control;
    private JPanel panel;

    public SearchInsertRenderer()
    {
        this.control = new SearchInsertPanel(true);
        this.panel = control.buildView();
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        control.getDComboBox().setSelectedItem(value);
        return panel;

    }
}
