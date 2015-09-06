package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.swing.order.SearchInsertPanel;
import by.dak.cutting.swing.order.TextureSearchInsertPanel;

import javax.swing.*;


public class TextureCellEditor extends ComboCellEditor
{

    public TextureCellEditor(JTable jTable)
    {
        super(jTable);
    }

    public SearchInsertPanel createControl()
    {
        return new TextureSearchInsertPanel(false);
    }
}
