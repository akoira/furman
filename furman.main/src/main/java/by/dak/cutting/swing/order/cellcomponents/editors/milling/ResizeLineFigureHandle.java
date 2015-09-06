package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.MoveHandle;
import org.jhotdraw.draw.locator.Locator;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 4.8.2009
 * Time: 12.10.18
 * To change this template use File | Settings | File Templates.
 */
public class ResizeLineFigureHandle extends MoveHandle
{
    private Point2D.Double lastPoint = null;
    Point2D.Double newPoint = null;
    private CurveFigure figure;
    private boolean isStart;


    /**
     * Creates a new instance.
     */
    public ResizeLineFigureHandle(Figure owner, Locator locator, boolean isStart)
    {
        super(owner, locator);
        figure = (CurveFigure) owner;
        this.isStart = isStart;

    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        getOwner().willChange();


        if (lastPoint == null)
        {
            lastPoint = view.viewToDrawing(lead);
        }
        newPoint = view.viewToDrawing(lead);
        if (!isStart)
        {

            double xEnd = (getOwner()).getEndPoint().getX() + (newPoint.x - lastPoint.x);
            double yEnd = (getOwner()).getEndPoint().getY() + (newPoint.y - lastPoint.y);
            figure.setEndPoint(new Point2D.Double(xEnd, yEnd));
        }
        else
        {
            double xStart = (getOwner()).getStartPoint().getX() + (newPoint.x - lastPoint.x);
            double yStart = (getOwner()).getStartPoint().getY() + (newPoint.y - lastPoint.y);
            figure.setStartPoint(new Point2D.Double(xStart, yStart));
        }
        lastPoint = newPoint;
        getOwner().changed();
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
        newPoint = null;
    }
}
