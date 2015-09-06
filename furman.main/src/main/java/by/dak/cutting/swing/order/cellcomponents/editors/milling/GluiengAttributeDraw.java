package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.AbstractAttributedFigure;
import org.jhotdraw.geom.DoubleStroke;

import java.awt.*;

/**
 * Отрисовывает атрибут glueing
 * User: akoyro
 * Date: 16.07.2009
 * Time: 17:50:56
 */
public class GluiengAttributeDraw
{
    private AbstractAttributedFigure figure;

    public GluiengAttributeDraw(AbstractAttributedFigure figure)
    {
        this.figure = figure;

    }

    public void draw(Graphics2D g2D)
    {
        if (figure.hasAttribute(AttributeKeys.GLUEING))
        {
            g2D.setStroke(new DoubleStroke(4f, 2f));
        }
    }
}
