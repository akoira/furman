package by.dak.cutting.swing.order.cellcomponents.renderers;

import by.dak.cutting.swing.order.cellcomponents.editors.cuttoff.CutoffValidator;
import by.dak.cutting.swing.order.data.Cutoff;
import org.jdesktop.swingx.renderer.CellContext;
import org.jdesktop.swingx.renderer.CheckBoxProvider;

/**
 * User: akoyro
 * Date: 25.04.2009
 * Time: 23:20:00
 */
public class CutoffCheckBoxProvider extends CheckBoxProvider
{
    @Override
    protected boolean getValueAsBoolean(CellContext context)
    {
        if (context.getValue() instanceof Cutoff)
            return new CutoffValidator((Cutoff) context.getValue()).validate();
        else
            return super.getValueAsBoolean(context);
    }
}