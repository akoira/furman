package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.doors.ColorConstants;
import by.dak.draw.VDragHandle;
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
 * User: akoyro
 * Date: 12.09.2009
 * Time: 13:43:16
 */
public class VProfileCompFigure extends AProfileCompFigure
{

    public VProfileCompFigure()
    {
        set(AttributeKeys.FILL_COLOR, ColorConstants.VERTICAL_PROFILE_COLOR);
        setRemovable(false);
        setSelectable(false);
    }

    @Override
    public List<Figure> getLines()
    {
        ArrayList<Figure> figures = new ArrayList<Figure>();
//        figures.add(downLine);
//        figures.add(upLine);
        return figures;
    }

    @Override
    public List<Figure> getLinesButt()
    {
        ArrayList<Figure> figures = new ArrayList<Figure>();
//        figures.add(leftLine);
//        figures.add(rigthLine);
        return figures;
    }

    @Override
    protected DragHandle getDragHandle()
    {
        return new VDragHandle(this);
    }

    @Override
    protected List<Handle> getResizeHandles()
    {
        ArrayList<Handle> handles = new ArrayList<Handle>();
        handles.add(ResizeHandleKit.north(this));
        handles.add(ResizeHandleKit.south(this));
        return handles;
    }

    @Override
    protected Point2D.Double getBoundsPoint(Point2D.Double anchor, Point2D.Double lead)
    {
        return new Point2D.Double(anchor.getX(), lead.getY());
    }

    protected void updateProfileComp(Point2D.Double min, Point2D.Double max)
    {
        getProfileComp().setLength((long) Math.abs(max.getY() - min.getY()));
    }


    @Override
    protected Rectangle2D.Double calcJoinBounds(Point2D.Double min, Point2D.Double max)
    {

        return new Rectangle2D.Double(min.getX(),
                min.getY(),
                getProfileComp().getProfileCompDef().getWidth(),
                getProfileComp().getLength());
    }


    @Override
    protected Rectangle2D.Double calcFillingBounds()
    {
        switch (getPosition())
        {
            case left:
                return new Rectangle2D.Double(joinBounds.getX(),
                        joinBounds.getY(),
                        getProfileComp().getProfileCompDef().getIndent(),
                        joinBounds.getHeight());
            case right:
                return new Rectangle2D.Double(joinBounds.getMaxX() - getProfileComp().getProfileCompDef().getIndent(),
                        joinBounds.getY(),
                        getProfileComp().getProfileCompDef().getIndent(),
                        joinBounds.getHeight());
            default:
                throw new IllegalArgumentException();
        }
    }


    @Override
    public void transform(Rectangle2D.Double cellBounds)
    {
        Rectangle2D.Double result;
        switch (getPosition())
        {
            case left:
                result = new Rectangle2D.Double(cellBounds.getX(), cellBounds.getY(),
                        getProfileComp().getProfileCompDef().getWidth(),
                        cellBounds.getHeight());
                break;
            case right:
                result = new Rectangle2D.Double(
                        cellBounds.getX() + cellBounds.getWidth() - getProfileComp().getProfileCompDef().getWidth(),
                        cellBounds.getY(),
                        getProfileComp().getProfileCompDef().getWidth(),
                        cellBounds.getHeight());
                break;
            default:
                throw new IllegalArgumentException();
        }
        setBounds(result);
    }
}
