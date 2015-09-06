package by.dak.draw;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.DragHandle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 09.09.2009
 * Time: 17:52:57
 * To change this template use File | Settings | File Templates.
 */
public class VDragHandle extends DragHandle
{
    private Point2D.Double last;

    /**
     * Creates a new instance.
     * @param owner owner
     */
    public VDragHandle(Figure owner)
    {
        super(owner);
    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        Rectangle2D.Double r = getOwner().getBounds();
        double deltaX = 0.0;

        if (last == null)
        {
            deltaX = getView().viewToDrawing(lead).getX() - r.getMinX();
            last = getView().viewToDrawing(lead);
        }

        double x = getView().viewToDrawing(lead).getX() - deltaX;
        if (getView().isConstrainerVisible())
        {
            x = ((int) (x / 10)) * 10;
        }

        getOwner().willChange();
        getOwner().setBounds(
                new Point2D.Double(Math.min(r.x + r.width - 1, x), r.y),
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
