package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.AbstractRotateHandle;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 23.07.2009
 * Time: 13:47:59
 * To change this template use File | Settings | File Templates.
 */
public class RotateArcAngleStartHandle extends AbstractRotateHandle
{
    private ArcEveryFigure arc;
    private ArcValue value;

    /**
     * Creates a new instance.
     */
    public RotateArcAngleStartHandle(Figure owner)
    {
        super(owner);
        arc = (ArcEveryFigure) owner;
    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        value = new ArcValue();
        Arc2D.Double arc2D = (Arc2D.Double) arc.getArc().clone();

        ArcUtil.invokeSetting(value, arc2D);

        double x2D = getView().viewToDrawing(lead).getX();
        double y2D = getView().viewToDrawing(lead).getY();

        double x2 = ((int) (x2D / 10)) * 10;
        double y2 = ((int) (y2D / 10)) * 10;

        value.start = new Point2D.Double(x2, y2);
        ArcUtil.invokeD(value);
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
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
    }

    @Override
    protected Point2D.Double getOrigin()
    {
        return (Point2D.Double) arc.getArc().getStartPoint();
    }

    @Override
    protected Point2D.Double getCenter()
    {
        return (Point2D.Double) arc.getArc().getEndPoint();
    }

    @Override
    public String getToolTipText(Point p)
    {
        return "rotate StartPoint";
    }
}