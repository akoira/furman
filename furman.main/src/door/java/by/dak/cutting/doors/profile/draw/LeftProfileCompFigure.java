package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.CurveFigure;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Вертикальный левый
 * User: akoyro
 * Date: 01.10.2009
 * Time: 22:41:56
 */
public class LeftProfileCompFigure extends AbstractProfileCompFigure
{
    public LeftProfileCompFigure()
    {
        super(new CurveFigure());
    }

    @Override
    public void transform(Rectangle2D.Double cellBounds)
    {
        getJoinFigure().setBounds(new Point2D.Double(cellBounds.getX(), cellBounds.getY()),
                new Point2D.Double(cellBounds.getX(), cellBounds.getMaxY()));
    }
}
