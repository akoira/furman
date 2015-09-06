package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.doors.ColorConstants;
import by.dak.draw.HDragHandle;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.DragHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.ResizeHandleKit;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Графическое представление
 * User: vishutinov
 * Date: 09.09.2009
 * Time: 19:37:34
 * To change this template use File | Settings | File Templates.
 */
public class HProfileCompFigure extends AProfileCompFigure
{

    public HProfileCompFigure()
    {
        set(AttributeKeys.FILL_COLOR, ColorConstants.HORIZONTAL_PROFILE_COLOR);
        setRemovable(false);
        setSelectable(false);
    }

    @Override
    public List<Figure> getLines()
    {
        ArrayList<Figure> figures = new ArrayList<Figure>();
//        figures.add(leftLine);
//        figures.add(rigthLine);
        return figures;
    }

    @Override
    public List<Figure> getLinesButt()
    {
        ArrayList<Figure> figures = new ArrayList<Figure>();
//        figures.add(downLine);
//        figures.add(upLine);
        return figures;
    }

    @Override
    protected DragHandle getDragHandle()
    {
        return new HDragHandle(this);
    }

    @Override
    protected List<Handle> getResizeHandles()
    {
        ArrayList<Handle> handles = new ArrayList<Handle>();
        handles.add(ResizeHandleKit.west(this));
        handles.add(ResizeHandleKit.east(this));
        return handles;
    }

    @Override
    protected Point2D.Double getBoundsPoint(Point2D.Double anchor, Point2D.Double lead)
    {
        return new Point2D.Double(lead.getX(), anchor.getY());
    }

    @Override
    protected Rectangle2D.Double calcJoinBounds(Point2D.Double min, Point2D.Double max)
    {
        return new Rectangle2D.Double(min.getX(), min.getY(), getProfileComp().getLength(), getProfileComp().getProfileCompDef().getWidth());
    }

    @Override
    protected Rectangle2D.Double calcFillingBounds()
    {
        switch (getPosition())
        {
            case top:
                return new Rectangle2D.Double(joinBounds.getX(),
                        joinBounds.getY(),
                        joinBounds.getBounds2D().getWidth(),
                        getProfileComp().getProfileCompDef().getIndent());
            case down:
                return new Rectangle2D.Double(joinBounds.getX(),
                        joinBounds.getY() + getProfileComp().getProfileCompDef().getWidth() - getProfileComp().getProfileCompDef().getIndent(),
                        joinBounds.getBounds2D().getWidth(),
                        getProfileComp().getProfileCompDef().getIndent());
            default:
                throw new IllegalArgumentException();
        }

    }


    @Override
    protected void updateProfileComp(Point2D.Double min, Point2D.Double max)
    {
        getProfileComp().setLength((long) Math.abs(max.getX() - min.getX()));
    }

    @Override
    public void transform(Rectangle2D.Double cellBounds)
    {
        Rectangle2D.Double result;
        switch (getPosition())
        {
            case top:
                result = new Rectangle2D.Double(
                        cellBounds.getX(),
                        cellBounds.getY(),
                        cellBounds.getWidth(),
                        getProfileComp().getProfileCompDef().getWidth());
                break;
            case down:
                result = new Rectangle2D.Double(
                        cellBounds.getX(),
                        cellBounds.getY() + cellBounds.getHeight() - getProfileComp().getProfileCompDef().getWidth(),
                        cellBounds.getWidth(),
                        getProfileComp().getProfileCompDef().getWidth());
                break;
            default:
                throw new IllegalArgumentException();
        }
        setBounds(result);
    }
    
}
