package by.dak.cutting.swing.table;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 25.04.2009
 * Time: 16:41:43
 */
public class CellContext
{
    private JTable table;
    private Object value;
    private boolean isSelected;
    private int row;
    private int column;

    public CellContext(JTable table,
                       Object value,
                       boolean selected,
                       int row, int column)
    {
        this.table = table;
        this.value = value;
        isSelected = selected;
        this.row = row;
        this.column = column;
    }

    public JTable getTable()
    {
        return table;
    }

    public Object getValue()
    {
        return value;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public int getRow()
    {
        return row;
    }

    public int getColumn()
    {
        return column;
    }
}
