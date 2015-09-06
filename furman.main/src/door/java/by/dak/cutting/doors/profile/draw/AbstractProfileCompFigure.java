package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.ShapeProvider;
import by.dak.draw.ChildFigure;
import by.dak.draw.ShowDimention;
import org.jhotdraw.draw.AbstractAttributedFigure;
import org.jhotdraw.draw.AbstractFigure;
import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.Handle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;

/**
 * User: akoyro
 * Date: 01.10.2009
 * Time: 22:43:22
 */
public abstract class AbstractProfileCompFigure extends AbstractAttributedFigure
        implements ChildFigure, ShowDimention, ShapeProvider
{

    private CompositeFigure parent;
    protected AbstractFigure joinFigure;

    /**
     * Трансформируем при установке размеров двери вызывается из setParent
     *
     * @param doorBounds
     */
    protected abstract void transform(Rectangle2D.Double doorBounds);

    public AbstractProfileCompFigure(AbstractAttributedFigure joinFigure)
    {
        this.joinFigure = joinFigure;
    }

    @Override
    protected void drawFill(Graphics2D g)
    {
    }

    @Override
    protected void drawStroke(Graphics2D g)
    {

    }


    @Override
    public Point2D.Double getStartPoint()
    {
        return joinFigure.getStartPoint();
    }

    @Override
    public Point2D.Double getEndPoint()
    {
        return joinFigure.getEndPoint();
    }


    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead)
    {
        joinFigure.setBounds(anchor, lead);

    }


    @Override
    public void draw(Graphics2D g)
    {
        joinFigure.draw(g);
    }


    @Override
    public Rectangle2D.Double getBounds()
    {
        return joinFigure.getBounds();
    }

    @Override
    public Rectangle2D.Double getDrawingArea()
    {
        return joinFigure.getDrawingArea();
    }


    @Override
    public boolean contains(Point2D.Double p)
    {
        return joinFigure.contains(p);
    }

    @Override
    public Object getTransformRestoreData()
    {
        return joinFigure.getTransformRestoreData();
    }

    @Override
    public void restoreTransformTo(Object restoreData)
    {
        joinFigure.restoreTransformTo(restoreData);
    }

    @Override
    public void transform(AffineTransform tx)
    {
        joinFigure.transform(tx);
    }

    @Override
    public void willChange()
    {
        joinFigure.willChange();
        super.willChange();
    }

    @Override
    public void changed()
    {
        joinFigure.changed();
        super.changed();
    }

    @Override
    public boolean isShowDimention()
    {
        return joinFigure instanceof ShowDimention ? ((ShowDimention) joinFigure).isShowDimention() : false;
    }

    @Override
    public void setShowDimention(boolean showDimention)
    {
        if (joinFigure instanceof ShowDimention)
        {
            ((ShowDimention) joinFigure).setShowDimention(showDimention);
        }
    }

    @Override
    public Collection<Handle> createHandles(int detailLevel)
    {
        LinkedList<Handle> handles = new LinkedList<Handle>();

        handles.addAll(joinFigure.createHandles(detailLevel));

        return handles;
    }

    @Override
    public Shape getShape()
    {
        return ((ShapeProvider) joinFigure).getShape();
    }

    @Override
    public Shape getInvertedStartEndShape()
    {
        return ((ShapeProvider) joinFigure).getInvertedStartEndShape();
    }

    public Figure getJoinFigure()
    {
        return joinFigure;
    }

    @Override
    public CompositeFigure getParent()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setParent(CompositeFigure parent)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
