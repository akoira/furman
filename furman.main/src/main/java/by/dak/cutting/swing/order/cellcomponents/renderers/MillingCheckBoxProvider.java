package by.dak.cutting.swing.order.cellcomponents.renderers;

import org.jdesktop.swingx.renderer.CellContext;
import org.jdesktop.swingx.renderer.CheckBoxProvider;

/**
 * User: akoyro
 * Date: 25.04.2009
 * Time: 23:20:00
 */
public class MillingCheckBoxProvider extends CheckBoxProvider
{
    @Override
    protected boolean getValueAsBoolean(CellContext context)
    {
        if (context.getValue() != null)
            return true;
        else
            return super.getValueAsBoolean(context);
    }
}