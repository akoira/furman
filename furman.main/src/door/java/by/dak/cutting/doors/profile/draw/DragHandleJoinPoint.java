package by.dak.cutting.doors.profile.draw;

import by.dak.draw.ChildFigure;
import org.aspectj.lang.ProceedingJoinPoint;
import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.DragHandle;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * User: akoyro
 * Date: 29.09.2009
 * Time: 13:51:47
 */
public class DragHandleJoinPoint
{
    private ProceedingJoinPoint joinPoint;

    private DragHandle handle;
    private Figure owner;
    private CompositeFigure parent;
    private DrawingView view;

    private Point anchor;
    private Point lead;
    private int modifiersEx;

    private Point2D.Double oldPoint;


    public DragHandleJoinPoint(ProceedingJoinPoint joinPoint)
    {
        this.joinPoint = joinPoint;
        handle = (DragHandle) joinPoint.getThis();
        owner = handle.getOwner();
        view = handle.getView();

        anchor = (Point) joinPoint.getArgs()[0];
        lead = (Point) joinPoint.getArgs()[1];
        modifiersEx = (Integer) joinPoint.getArgs()[2];

        //oldPoint = handle.getOldPoint();

        if (owner instanceof ChildFigure)
        {
            parent = ((ChildFigure) getOwner()).getParent();
        }
    }

    public ProceedingJoinPoint getJoinPoint()
    {
        return joinPoint;
    }

    public DragHandle getHandle()
    {
        return handle;
    }

    public Figure getOwner()
    {
        return owner;
    }

    public DrawingView getView()
    {
        return view;
    }

    public Point getAnchor()
    {
        return anchor;
    }

    public Point getLead()
    {
        return lead;
    }

    public int getModifiersEx()
    {
        return modifiersEx;
    }

    public Point2D.Double getOldPoint()
    {
        return oldPoint;
    }

    public CompositeFigure getParent()
    {
        return parent;
    }
}
