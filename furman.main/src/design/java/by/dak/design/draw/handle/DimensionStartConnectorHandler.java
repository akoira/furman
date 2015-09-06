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
 * Date: 7/22/11
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class DimensionStartConnectorHandler extends AbstractHandle
{
    private DimensionConnector startConnector;
    private MoveScrollHandle scrollHandle = new MoveScrollHandle();

    public DimensionStartConnectorHandler(DimensionFigure owner)
    {
        super(owner);
    }

    public void draw(Graphics2D g)
    {
        super.draw(g);
        if (startConnector != null)
        {
            Graphics2D gg = (Graphics2D) g.create();
            gg.transform(getView().getDrawingToViewTransform());
            startConnector.draw(gg);
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
        ((DimensionFigure) getOwner()).setStartPoint(view.viewToDrawing(lead));
        getOwner().changed();

        Figure figure = getView().getDrawing().
                findFigureExcept(view.viewToDrawing(lead), getOwner());
        if (figure != null)
        {
            if (figure instanceof Connectable)
            {
                startConnector = ((Connectable) figure).getConnector(view.viewToDrawing(lead));
                startConnector.setScale(getView().getScaleFactor());
            }
        }
        else
        {
            startConnector = null;
        }
        scrollHandle.setView(getView());
        scrollHandle.moveScroll(lead);
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
        if (startConnector != null)
        {
            Point2D.Double startPoint = startConnector.findStart(view.viewToDrawing(lead));
            getOwner().willChange();
            ((DimensionFigure) getOwner()).setStartConnector(startConnector);
            ((DimensionFigure) getOwner()).setStartPoint(startPoint);
            getOwner().changed();
        }
    }

    private Point getLocation()
    {
        return view.drawingToView(getOwner().getStartPoint());
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
}
