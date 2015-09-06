package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.ArcEveryFigure;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Горизонтальный верхний
 * User: akoyro
 * Date: 01.10.2009
 * Time: 22:40:19
 */
public class TopProfileCompFigure extends AbstractProfileCompFigure
{
    public TopProfileCompFigure()
    {
        super(new ArcEveryFigure());
    }

    @Override
    public void transform(Rectangle2D.Double doorBounds)
    {
        ((ArcEveryFigure) getJoinFigure()).setBounds(new Point2D.Double(doorBounds.getX(), doorBounds.getY()),
                new Point2D.Double(doorBounds.getMaxX(), doorBounds.getY()));
    }

}
