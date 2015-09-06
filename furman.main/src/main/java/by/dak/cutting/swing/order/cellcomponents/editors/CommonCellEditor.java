package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.swing.order.SidePanel;
import by.dak.cutting.swing.order.ViewControl;
import by.dak.cutting.swing.order.popup.GrooveSideMenu;


public class CommonCellEditor extends ComplexCell
{

    public CommonCellEditor()
    {
    }

    @Override
    public ViewControl createControl()
    {
        return new SidePanel(new GrooveSideMenu(this));
    }
}
