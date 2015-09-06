package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.MoveHandle;
import org.jhotdraw.draw.locator.Locator;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 23.07.2009
 * Time: 16:23:50
 * To change this template use File | Settings | File Templates.
 */
public class ResizeArcHandle extends MoveHandle
{
    private ArcEveryFigure arc;
    private Point last = null;
    private ArcValue value;
    private static double speedOfResize = 1;

    /**
     * Creates a new instance.
     */
    public ResizeArcHandle(Figure owner, Locator locator)
    {
        super(owner, locator);
        arc = (ArcEveryFigure) owner;
    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        value = new ArcValue();
        if (last == null)
        {
            last = anchor;
        }

        Arc2D.Double arc2D = (Arc2D.Double) arc.getArc().clone();

        ArcUtil.invokeSetting(value, arc2D);

        Point2D.Double near = new Point2D.Double(arc.getCenterPoint().getX() +
                Math.cos(Math.toRadians(arc2D.getAngleStart() + arc2D.getAngleExtent() / 2)) * arc2D.getHeight() * 20,
                arc.getCenterPoint().getY() -
                        Math.sin(Math.toRadians(arc2D.getAngleStart() + arc2D.getAngleExtent() / 2)) * arc2D.getHeight() * 20);

        double x;
        double y;

        double angle = ArcUtil.calcAngle(new Point2D.Double(value.xc1, value.yc1), new Point2D.Double(value.xc2, value.yc2));

        double d = speedOfResize * (near.distance(last) - near.distance(lead));

        if (value.direction)
        {
            if (value.position)
            {
                x = value.xc1 + Math.cos(Math.toRadians(angle)) * d;
                y = value.yc1 - Math.sin(Math.toRadians(angle)) * d;
            }
            else
            {
                x = value.xc2 + Math.cos(Math.toRadians(angle)) * d;
                y = value.yc2 - Math.sin(Math.toRadians(angle)) * d;
            }
        }
        else
        {
            if (value.position)
            {
                x = value.xc2 - Math.cos(Math.toRadians(angle)) * d;
                y = value.yc2 + Math.sin(Math.toRadians(angle)) * d;
            }
            else
            {
                x = value.xc1 - Math.cos(Math.toRadians(angle)) * d;
                y = value.yc1 + Math.sin(Math.toRadians(angle)) * d;
            }
        }

        if (near.distance(x, y) < near.distance(value.xm, value.ym))
        {
            value.position = false;
            value.direction = true;
        }

        if (Math.abs(near.distance(x, y) - near.distance(value.xm, value.ym)) < 0.5 && !value.position)
        {
            value.position = true;
            value.direction = false;
        }

        value.d = value.start.distance(x, y);
        ArcUtil.invokeXY(value);
        ArcUtil.invokeAngleStart(value);
        ArcUtil.invokeAngleExtent(value);

        arc.willChange();

        arc2D.setAngleStart(value.angleStart);
        arc2D.setAngleExtent(value.angleExtent);

        if (value.direction)
        {
            arc2D.setFrame(value.x1, value.y1, value.d * 2, value.d * 2);
        }
        else
        {
            arc2D.setFrame(value.x2, value.y2, value.d * 2, value.d * 2);
        }
        arc.setArc(arc2D);

        arc.changed();
        super.trackEnd(anchor, lead, modifiersEx);
        last = lead;
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
        last = null;
    }
}
