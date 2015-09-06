package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.CurveFigure;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Горизонтальный нижний
 * User: akoyro
 * Date: 01.10.2009
 * Time: 22:41:16
 */
public class DownProfileCompFigure extends AbstractProfileCompFigure
{
    public DownProfileCompFigure()
    {
        super(new CurveFigure());
    }

    @Override
    public void transform(Rectangle2D.Double cellBounds)
    {
        getJoinFigure().setBounds(new Point2D.Double(cellBounds.getX(), cellBounds.getMaxY()),
                new Point2D.Double(cellBounds.getMaxX(), cellBounds.getMaxY()));
    }
}
