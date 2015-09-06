package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.DragHandle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey Koyro
 * Date: 29.7.2009
 * Time: 14.39.10
 * To change this template use File | Settings | File Templates.
 */
public class DragLineHandle extends DragHandle
{

    /**
     * Creates a new instance.
     */
    private Point2D.Double last = null;

    public DragLineHandle(Figure owner)
    {
        super(owner);
    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        if (last == null)
        {
            last = view.viewToDrawing(lead);
        }
        AffineTransform tx = new AffineTransform();
        tx.translate(view.viewToDrawing(lead).x - last.x, view.viewToDrawing(lead).y - last.y);
        getOwner().willChange();
        getOwner().transform(tx);
        getOwner().changed();
        last = view.viewToDrawing(lead);
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
        last = null;
    }
}
