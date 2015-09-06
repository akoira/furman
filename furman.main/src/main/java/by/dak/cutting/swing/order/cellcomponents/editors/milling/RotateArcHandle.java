package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.AbstractRotateHandle;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 22.07.2009
 * Time: 13:52:44
 * To change this template use File | Settings | File Templates.
 */
public class RotateArcHandle extends AbstractRotateHandle
{
    private ArcEveryFigure arc;
    private Point2D.Double last = null;

    /**
     * Creates a new instance.
     */
    public RotateArcHandle(Figure owner)
    {
        super(owner);
        arc = (ArcEveryFigure) owner;
    }


    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        if (last == null)
        {
            last = view.viewToDrawing(anchor);
        }
        ArcEveryFigure arcE = arc.clone();

        Arc2D.Double arc2D = (Arc2D.Double) arcE.getArc().clone();

        double angle = arc2D.getAngleStart() + direction(arc2D.getAngleStart() + arc2D.getAngleExtent() / 2, last, view.viewToDrawing(lead)) * Math.toDegrees(Math.acos(1 - last.distance(view.viewToDrawing(lead)) * last.distance(view.viewToDrawing(lead)) /
                (2 * arc2D.getHeight() / 2 * arc2D.getHeight() / 2)));
        if (angle > 360 || angle < -360)
        {
            angle = angle - ((int) (angle / 360)) * 360;
        }
        arc2D.setAngleStart(angle);

        arc.setArc(arc2D);

        arc.willChange();
        arc.changed();

        super.trackEnd(anchor, lead, modifiersEx);
        last = view.viewToDrawing(lead);
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
        last = null;
    }

    @Override
    protected Point2D.Double getOrigin()
    {
        return arc.getMiddleArc();
    }

    @Override
    protected Point2D.Double getCenter()
    {
        return arc.getCenterPoint();
    }

    protected int direction(double degree, Point2D.Double anchor, Point2D.Double lead)
    {
        double angle = degree;
        if (degree > 360 || degree < -360)
        {
            angle = degree - ((int) (degree / 360)) * 360;
        }
        if (angle < 135 && angle > 45 || (angle < -225 && angle > -315))
        {
            if (lead.getX() - anchor.getX() <= 0)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
        else if ((angle > 225 && angle < 315) || (angle > -135 && angle < -45))
        {
            if (lead.getX() - anchor.getX() >= 0)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
        else if (Math.cos(Math.toRadians(degree)) > 0)
        {
            if (lead.getY() - anchor.getY() <= 0)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            if (lead.getY() - anchor.getY() >= 0)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
    }

}
