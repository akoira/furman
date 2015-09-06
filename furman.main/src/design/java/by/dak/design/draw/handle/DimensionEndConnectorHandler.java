package by.dak.design.draw.handle;

import by.dak.design.draw.components.Connectable;
import by.dak.design.draw.components.DimensionConnector;
import by.dak.design.draw.components.DimensionFigure;
import by.dak.draw.MoveScrollHandle;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.AbstractHandle;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/25/11
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class DimensionEndConnectorHandler extends AbstractHandle
{
    private DimensionConnector endConnector;
    private MoveScrollHandle scrollHandle = new MoveScrollHandle();

    public DimensionEndConnectorHandler(DimensionFigure owner)
    {
        super(owner);
    }

    @Override
    protected Rectangle basicGetBounds()
    {
        Rectangle r = new Rectangle(getLocation());
        int h = getHandlesize();
        r.x -= h / 2;
        r.y -= h / 2;
        r.width = r.height = h;
        return r;
    }

    private Point getLocation()
    {
        return view.drawingToView(getOwner().getEndPoint());
    }

    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
        if (endConnector != null)
        {
            Graphics2D gg = (Graphics2D) g.create();
            gg.transform(getView().getDrawingToViewTransform());
            endConnector.draw(gg);
            gg.dispose();
        }
    }

    @Override
    public void trackStart(Point anchor, int modifiersEx)
    {
    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        getOwner().willChange();
        ((DimensionFigure) getOwner()).setEndPoint(view.viewToDrawing(lead));
        getOwner().changed();

        Figure figure = getView().getDrawing().
                findFigureExcept(view.viewToDrawing(lead), getOwner());
        if (figure != null)
        {
            if (figure instanceof Connectable)
            {
                endConnector = ((Connectable) figure).getConnector(view.viewToDrawing(lead));
                endConnector.setScale(getView().getScaleFactor());
            }
        }
        else
        {
            endConnector = null;
        }
        scrollHandle.setView(getView());
        scrollHandle.moveScroll(lead);
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
        if (endConnector != null)
        {
            Point2D.Double endPoint = endConnector.findEnd(view.viewToDrawing(lead),
                    getOwner().getStartPoint());
            getOwner().willChange();
            ((DimensionFigure) getOwner()).setEndConnector(endConnector);
            ((DimensionFigure) getOwner()).setEndPoint(endPoint);
            getOwner().changed();
        }
    }
}
