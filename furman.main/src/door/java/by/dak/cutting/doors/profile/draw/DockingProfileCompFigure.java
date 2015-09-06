package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.CurveFigure;

import java.awt.geom.Rectangle2D;

/**
 * Стыковочный
 * User: akoyro
 * Date: 05.10.2009
 * Time: 14:53:00
 */
public class DockingProfileCompFigure extends AbstractProfileCompFigure
{

    public DockingProfileCompFigure()
    {
        super(new CurveFigure());
    }

    @Override
    protected void transform(Rectangle2D.Double cellBounds)
    {

    }
}
