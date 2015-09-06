package by.dak.swing.table;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

/**
 * User: akoyro
 * Date: 01.11.2010
 * Time: 10:25:43
 */
public abstract class ATableCellEditorDecorator implements TableCellEditor
{
    private TableCellEditor source;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        return source.getTableCellEditorComponent(table,value, isSelected,row,column);
    }

    @Override
    public Object getCellEditorValue()
    {
        return source.getCellEditorValue();
    }

    @Override
    public boolean isCellEditable(EventObject anEvent)
    {
        return source.isCellEditable(anEvent);
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent)
    {
        return source.shouldSelectCell(anEvent);
    }

    @Override
    public boolean stopCellEditing()
    {
        return source.stopCellEditing();
    }

    @Override
    public void cancelCellEditing()
    {
        source.cancelCellEditing();
    }

    @Override
    public void addCellEditorListener(CellEditorListener l)
    {
        source.addCellEditorListener(l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l)
    {
        source.removeCellEditorListener(l);
    }

    public TableCellEditor getSource()
    {
        return source;
    }

    public void setSource(TableCellEditor source)
    {
        this.source = source;
    }
}
