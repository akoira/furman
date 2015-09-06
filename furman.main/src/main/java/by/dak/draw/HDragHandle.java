package by.dak.draw;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.DragHandle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * User: vishutinov
 * Date: 09.09.2009
 * Time: 19:14:25
 */
public class HDragHandle extends DragHandle
{
    private Point2D.Double last;
//    private double deltaY;

    /**
     * Creates a new instance.
     *
     * @param owner owner
     */
    public HDragHandle(Figure owner)
    {
        super(owner);
    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        Rectangle2D.Double r = getOwner().getBounds();

        double deltaY = 0;
        if (last == null)
        {
            deltaY = getView().viewToDrawing(lead).getY() - r.getMinY();
            last = getView().viewToDrawing(lead);
        }

        double y = getView().viewToDrawing(lead).getY() - deltaY;
        if (getView().isConstrainerVisible())
        {
            //todo: зачем это ?
            y = ((int) (y / 10)) * 10;
        }

        getOwner().willChange();
        //todo: зачем отнимаем -1 ?
        getOwner().setBounds(
                new Point2D.Double(r.x, Math.min(r.y + r.height - 1, y)),
                new Point2D.Double(r.x + r.width, r.y + r.height));
        getOwner().changed();
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
        last = null;
        super.trackEnd(anchor, lead, modifiersEx);
    }
}
