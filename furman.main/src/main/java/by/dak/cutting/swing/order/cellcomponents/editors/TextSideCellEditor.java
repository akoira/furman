package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.swing.order.SidePanel;
import by.dak.cutting.swing.order.ViewControl;
import by.dak.cutting.swing.order.popup.TextSideMenu;


public class TextSideCellEditor extends ComplexCell
{

    @Override
    public ViewControl createControl()
    {
        return new SidePanel(new TextSideMenu(this));
    }
}
