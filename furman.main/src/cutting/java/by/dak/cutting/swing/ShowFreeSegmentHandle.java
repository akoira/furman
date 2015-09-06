package by.dak.cutting.swing;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.AbstractHandle;

import java.awt.*;

import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;

/**
 * User: akoyro
 * Date: 16.12.2010
 * Time: 12:39:11
 */
public class ShowFreeSegmentHandle extends AbstractHandle
{
    public ShowFreeSegmentHandle(ElementFigure owner)
    {
        super(owner);
    }

    @Override
    protected Rectangle basicGetBounds()
    {
        Shape bounds = getOwner().getBounds();
        if (getOwner().get(TRANSFORM) != null)
        {
            bounds = getOwner().get(TRANSFORM).createTransformedShape(bounds);
        }
        bounds = view.getDrawingToViewTransform().createTransformedShape(bounds);

        Rectangle r = bounds.getBounds();
        r.grow(2, 2);
        return r;
    }

    @Override
    public void trackStart(Point anchor, int modifiersEx)
    {
    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
    }

    @Override
    public void draw(Graphics2D g)
    {
        //getSegments();
        //drawRectangle(g, Color.BLACK, Color.BLACK);
    }

    public void getSegments()
    {
        java.util.List<Figure> figures = getView().getDrawing().getChildren();
    }
}
