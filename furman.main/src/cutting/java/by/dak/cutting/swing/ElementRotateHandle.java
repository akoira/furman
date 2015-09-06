package by.dak.cutting.swing;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.event.TransformEdit;
import org.jhotdraw.draw.handle.AbstractHandle;
import org.jhotdraw.draw.handle.HandleAttributeKeys;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;

/**
 * User: akoyro
 * Date: 22.12.2010
 * Time: 13:39:40
 */
public class ElementRotateHandle extends AbstractHandle
{
    private Point location;
    private Point2D.Double center;

    private Point startAnchor;

    public ElementRotateHandle(ElementFigure owner)
    {
        super(owner);
    }

    @Override
    public void draw(Graphics2D g)
    {
        if (getEditor().getTool().supportsHandleInteraction())
        {
            drawCircle(g,
                    (Color) getEditor().getHandleAttribute(HandleAttributeKeys.ROTATE_HANDLE_FILL_COLOR),
                    (Color) getEditor().getHandleAttribute(HandleAttributeKeys.ROTATE_HANDLE_STROKE_COLOR));
        }
        else
        {
            drawCircle(g,
                    (Color) getEditor().getHandleAttribute(HandleAttributeKeys.ROTATE_HANDLE_FILL_COLOR_DISABLED),
                    (Color) getEditor().getHandleAttribute(HandleAttributeKeys.ROTATE_HANDLE_STROKE_COLOR_DISABLED));
        }
    }


    @Override
    protected Rectangle basicGetBounds()
    {
        Rectangle r = new Rectangle(getLocation());
        int h = getHandlesize() * 2;
        r.x -= h / 2;
        r.y -= h / 2;
        r.width = r.height = h;
        return r;
    }

    public Point getLocation()
    {
        if (location == null)
        {
            return view.drawingToView(getOrigin());
        }
        return location;
    }

    protected Rectangle2D.Double getTransformedBounds()
    {
        Figure owner = getOwner();
        Rectangle2D.Double bounds = owner.getBounds();
        if (owner.get(TRANSFORM) != null)
        {
            Rectangle2D r = owner.get(TRANSFORM).
                    createTransformedShape(bounds).getBounds2D();
            bounds.x = r.getX();
            bounds.y = r.getY();
            bounds.width = r.getWidth();
            bounds.height = r.getHeight();
        }
        return bounds;
    }


    protected Point2D.Double getOrigin()
    {
        Rectangle2D.Double bounds = getTransformedBounds();
        return new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
    }

    protected Point2D.Double getCenter()
    {
        return getOrigin();
    }

    @Override
    public void trackStart(Point anchor, int modifiersEx)
    {
        this.startAnchor = anchor;
    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        double delta = this.startAnchor.getX() - lead.getX();
        if (delta < 0)
        {
            rotate(true);
        }
        else if (delta > 0)
        {
            rotate(false);
        }
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
    }

    @Override
    public void keyPressed(KeyEvent evt)
    {
        switch (evt.getKeyCode())
        {
            case KeyEvent.VK_RIGHT:
                rotate(true);
                break;
            case KeyEvent.VK_LEFT:
                rotate(false);
                break;
            default:
                break;
        }
        evt.consume();
    }

    private void rotate(boolean rotate)
    {
        Figure f = getOwner();
        if (((ElementFigure) f).isRotated() != rotate && ((ElementFigure) f).getParent() == null)
        {
            center = getCenter();
            AffineTransform tx = new AffineTransform();
            tx.rotate(-Math.PI / 2, center.x, center.y);
            f.willChange();
            ((ElementFigure) f).setRotated(!((ElementFigure) f).isRotated());
            f.transform(tx);
            f.changed();
            fireUndoableEditHappened(
                    new TransformEdit(f, tx));
        }
    }
}
