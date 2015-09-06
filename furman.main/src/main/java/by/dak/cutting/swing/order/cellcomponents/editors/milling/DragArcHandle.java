package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.DragHandle;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 23.07.2009
 * Time: 15:13:14
 * To change this template use File | Settings | File Templates.
 */
public class DragArcHandle extends DragHandle
{
    private ArcEveryFigure arc;
    private Point2D.Double last = null;
    private double deltaX;
    private double deltaY;

    /**
     * Creates a new instance.
     */
    public DragArcHandle(Figure owner)
    {
        super(owner);
        arc = (ArcEveryFigure) owner;
    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        Arc2D.Double arc2D = (Arc2D.Double) arc.getArc().clone();

        if (last == null)
        {
            last = view.viewToDrawing(lead);
            deltaX = arc2D.getStartPoint().getX();
            deltaY = arc2D.getStartPoint().getY();
        }

        deltaX += view.viewToDrawing(lead).getX() - last.getX();
        deltaY += view.viewToDrawing(lead).getY() - last.getY();

        double x = ((int) (deltaX / 10)) * 10.0;
        double y = ((int) (deltaY / 10)) * 10.0;

        double xc = x - Math.cos(Math.toRadians(arc2D.getAngleStart())) * arc2D.getHeight() / 2;
        double yc = y + Math.sin(Math.toRadians(arc2D.getAngleStart())) * arc2D.getHeight() / 2;

        arc2D.setFrame(xc - arc2D.getHeight() / 2, yc - arc2D.getHeight() / 2, arc2D.getHeight(), arc2D.getHeight());

        arc.willChange();
        arc.setArc(arc2D);

        arc.changed();

        last = view.viewToDrawing(lead);
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
        last = null;
    }
}
