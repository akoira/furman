package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.swing.order.SearchInsertPanel;
import by.dak.cutting.swing.order.TypeSearchInsertPanel;

import javax.swing.*;


public class BoardDefCellEditor extends ComboCellEditor
{

    public BoardDefCellEditor(JTable jTable)
    {
        super(jTable);
    }

    public SearchInsertPanel createControl()
    {
        return new TypeSearchInsertPanel(false);
    }

}
