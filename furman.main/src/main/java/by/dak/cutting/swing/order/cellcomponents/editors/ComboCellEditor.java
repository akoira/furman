package by.dak.cutting.swing.order.cellcomponents.editors;


import javax.swing.*;


public class ComboCellEditor extends ComplexCell
{

    protected JTable jTable;

    public ComboCellEditor(JTable jTable)
    {
        this.jTable = jTable;
    }

    public Object getCellEditorValue()
    {
        int selRow = jTable.getSelectedRow();
        if (selRow == -1)
            return null;
        Object item = control.getSelectedItem();
        return item;
    }

    public JComponent getFocusComponent()
    {
        return control.getFocusedComponent();
    }


}
