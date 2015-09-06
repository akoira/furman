package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.swing.order.PopupDrillingPanel;
import by.dak.cutting.swing.order.ViewControl;
import org.jdesktop.application.ApplicationContext;


public class DrillingCellEditor extends ComplexCell
{
    private ApplicationContext context;

    @Override
    public ViewControl createControl()
    {
        PopupDrillingPanel result = new PopupDrillingPanel(this, context);
        return result;
    }

    public ApplicationContext getContext()
    {
        return context;
    }

    public void setContext(ApplicationContext context)
    {
        this.context = context;
    }
}
