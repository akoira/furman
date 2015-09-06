package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.AbstractAttributedFigure;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * User: akoyro
 * Date: 15.07.2009
 * Time: 12:17:33
 */
public class DimentionLineFigure extends AbstractAttributedFigure
{
    @Override
    protected void drawFill(Graphics2D g)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void drawStroke(Graphics2D g)
    {
    }

    @Override
    public Rectangle2D.Double getBounds()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(Point2D.Double p)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getTransformRestoreData()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void restoreTransformTo(Object restoreData)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void transform(AffineTransform tx)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
