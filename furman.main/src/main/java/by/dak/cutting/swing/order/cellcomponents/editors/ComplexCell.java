package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.swing.order.SideCanvas;
import by.dak.cutting.swing.order.SidePanel;
import by.dak.cutting.swing.order.ViewControl;
import by.dak.cutting.swing.order.data.DTO;
import by.dak.cutting.swing.order.data.Drilling;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.order.models.OrderDetailsTableModel;
import by.dak.cutting.swing.order.popup.GlueingSideMenu;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.EventObject;


public class ComplexCell extends AbstractCellEditor implements TableCellEditor
{
    protected ViewControl control;

    public ComplexCell()
    {
    }


    public Object getCellEditorValue()
    {
        return "";
    }


    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        JPanel sidePanel;
        control = createControl();
        sidePanel = control.buildView();
        setData(table, row, control);
        refreshComponent(table, row, column, sidePanel.getComponent(0));
        return sidePanel;
    }

    public ViewControl createControl()
    {
        return new SidePanel( new GlueingSideMenu(this));
    }

    protected void setData(JTable table, int row, ViewControl control)
    {
        TableModel model = table.getModel();
        if (model instanceof OrderDetailsTableModel)
        {
            OrderDetailsDTO dto = ((OrderDetailsTableModel) model).getRowBy(row);
            control.setData(dto);
        }
    }

    @Override
    public boolean isCellEditable(EventObject e)
    {
        return true;
    }

    protected void refreshComponent(JTable table, int row, int column, Component component)
    {
        DTO paramDTo = null;
        TableModel model = table.getModel();
        if (model instanceof OrderDetailsTableModel)
        {
            OrderDetailsDTO dto = ((OrderDetailsTableModel) model).getRowBy(row);
            switch (column)
            {
                case 10:
                    paramDTo = dto.getGlueing();
                    break;
                case 13:
                    paramDTo = dto.getGroove();
                    break;
                case 14:
                    paramDTo = dto.getA45();
                    break;
            }
        }
        if (paramDTo != null && component instanceof JButton)
        {
            Icon icon = ((JButton) component).getIcon();
            if (icon instanceof SideCanvas)
            {
                SideCanvas canvas = (SideCanvas) icon;
                canvas.setDown(paramDTo.isDown());
                canvas.setUp(paramDTo.isUp());
                canvas.setLeft(paramDTo.isLeft());
                canvas.setRight(paramDTo.isRight());
            }
            component.repaint();
        }
    }
}
