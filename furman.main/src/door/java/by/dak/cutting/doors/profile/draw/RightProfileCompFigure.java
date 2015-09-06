package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.CurveFigure;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Вертикальный правый
 * User: akoyro
 * Date: 01.10.2009
 * Time: 22:42:08
 */
public class RightProfileCompFigure extends AbstractProfileCompFigure
{
    public RightProfileCompFigure()
    {
        super(new CurveFigure());
    }

    @Override
    public void transform(Rectangle2D.Double cellBounds)
    {
        getJoinFigure().setBounds(new Point2D.Double(cellBounds.getMaxX(), cellBounds.getY()),
                new Point2D.Double(cellBounds.getMaxX(), cellBounds.getMaxY()));
    }
}
