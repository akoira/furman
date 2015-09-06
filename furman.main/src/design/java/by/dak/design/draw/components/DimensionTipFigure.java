package by.dak.design.draw.components;

import org.jhotdraw.draw.TextAreaFigure;
import org.jhotdraw.geom.Insets2D;

import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/21/11
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * содержит текст dimension
 */
public class DimensionTipFigure extends TextAreaFigure
{
    @Override
    public Insets2D.Double getInsets()
    {
        double sw = Math.ceil(get(STROKE_WIDTH) / 2);
        Insets2D.Double insets = new Insets2D.Double(0, 0, 0, 0);
        return new Insets2D.Double(insets.top + sw, insets.left + sw, insets.bottom + sw, insets.right + sw);
    }
}
