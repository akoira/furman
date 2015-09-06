package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.swing.order.SidePanel;
import by.dak.cutting.swing.order.data.Glueing;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.order.models.OrderDetailsTableModel;
import by.dak.cutting.swing.order.popup.GlueingSideMenu;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: Mar 25, 2009
 * Time: 6:00:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class GlueingCellEditor extends AbstractCellEditor implements TableCellEditor
{
    private Glueing value;
    private SidePanel sidePanel;

    public GlueingCellEditor()
    {
        sidePanel = new SidePanel(new GlueingSideMenu(this));
    }

    @Override
    public Object getCellEditorValue()
    {
        return value;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        OrderDetailsDTO dto = ((OrderDetailsTableModel) table.getModel()).getRowBy(row);
        sidePanel.setData(dto);
        this.value = null;
        return sidePanel.getFocusedComponent();
    }


}
