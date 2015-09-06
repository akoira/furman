/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui.cuttingApp;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * @author Peca
 */
public class DimensionItemTableModel extends AbstractTableModel
{

    ArrayList<DimensionItem> list;

    public DimensionItemTableModel(ArrayList<DimensionItem> dimensionList)
    {
        this.list = dimensionList;
    }

    public int getColumnCount()
    {
        return 4;
    }

    public int getRowCount()
    {
        return list.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        DimensionItem dimensionItem = list.get(rowIndex);
        switch (columnIndex)
        {
            case 0:
                return dimensionItem.getId();
            case 1:
                return dimensionItem.getDimension().getWidth();
            case 2:
                return dimensionItem.getDimension().getHeight();
            case 3:
                return dimensionItem.getCount();
        }
        return null;
    }

    public boolean isCellEditable(int row, int col)
    {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return true;
    }

    private int parseInt(Object value)
    {
        if (value == null) return 0;
        return Integer.parseInt(value.toString());
    }

    @Override
    public void setValueAt(Object value, int row, int col)
    {
        DimensionItem dimensionItem = list.get(row);
        try
        {
            switch (col)
            {
                case 0:
                    dimensionItem.setId(value.toString());
                    break;
                case 1:
                    dimensionItem.getDimension().setWidth(parseInt(value));
                    break;
                case 2:
                    dimensionItem.getDimension().setHeight(parseInt(value));
                    break;
                case 3:
                    dimensionItem.setCount(parseInt(value));
                    break;
            }
        }
        catch (Exception ex)
        {

        }
        fireTableCellUpdated(row, col);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return Object.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
        }
        return Object.class;
    }

    @Override
    public String getColumnName(int column)
    {
        switch (column)
        {
            case 0:
                return "Id";
            case 1:
                return "Width [mm]";
            case 2:
                return "Height [mm]";
            case 3:
                return "Count";
        }
        return "Column " + String.valueOf(column);
    }


}
