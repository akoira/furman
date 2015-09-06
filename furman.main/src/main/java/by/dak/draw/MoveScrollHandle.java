package by.dak.draw;


import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DrawingView;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 31.7.2009
 * Time: 15.10.28
 * To change this template use File | Settings | File Templates.
 */
public class MoveScrollHandle
{
    public static final double DISTANCE_TO_MOVE = 15;

    private DrawingView view;
    private final int shift = 6; //сдвиг size при перемещении
    private final int shiftScroll = 12; //сдвиг visibleRect (перемещение scrolla)

    public MoveScrollHandle()
    {
    }

    public void moveScroll(Point lead)
    {
        Delta delta = getDelta(lead);
        if (delta != null)
        {
            //todo считать: сколько шагов делать, чтобы перемещать(когда мышь возле границы)
//            while (!needStop())
            {
                view.getComponent().scrollRectToVisible(new Rectangle((int) view.getComponent().getVisibleRect().getX() + delta.dX,
                        (int) view.getComponent().getVisibleRect().getY() + delta.dY,
                        view.getComponent().getVisibleRect().width,
                        view.getComponent().getVisibleRect().height));
//            view.getComponent().setSize(new Dimension((int) view.getComponent().getSize().getWidth() + delta.dS,
//                    (int) view.getComponent().getSize().getHeight() + delta.dS));
            }
        }
    }

    private boolean isAroundBorders(Point point)
    {
        Line2D.Double lineLeft = new Line2D.Double(view.getComponent().getVisibleRect().getBounds().getMinX(),
                view.getComponent().getVisibleRect().getBounds().getMinY(),
                view.getComponent().getVisibleRect().getBounds().getMinX(),
                view.getComponent().getVisibleRect().getBounds().getMaxY());
        Line2D.Double lineTop = new Line2D.Double(view.getComponent().getVisibleRect().getBounds().getMinX(),
                view.getComponent().getVisibleRect().getBounds().getMinY(),
                view.getComponent().getVisibleRect().getBounds().getMaxX(),
                view.getComponent().getVisibleRect().getBounds().getMinY());
        Line2D.Double lineRight = new Line2D.Double(view.getComponent().getVisibleRect().getBounds().getMaxX(),
                view.getComponent().getVisibleRect().getBounds().getMinY(),
                view.getComponent().getVisibleRect().getBounds().getMaxX(),
                view.getComponent().getVisibleRect().getBounds().getMaxY());
        Line2D.Double lineBottom = new Line2D.Double(view.getComponent().getVisibleRect().getBounds().getMinX(),
                view.getComponent().getVisibleRect().getBounds().getMaxY(),
                view.getComponent().getVisibleRect().getBounds().getMaxX(),
                view.getComponent().getVisibleRect().getBounds().getMaxY());

        return lineLeft.ptLineDist(point) <= DISTANCE_TO_MOVE ||
                lineRight.ptLineDist(point) <= DISTANCE_TO_MOVE ||
                lineTop.ptLineDist(point) <= DISTANCE_TO_MOVE ||
                lineBottom.ptLineDist(point) <= DISTANCE_TO_MOVE;
    }

    private Delta getDelta(Point lead)
    {
        Delta delta = null;
        if (!view.getComponent().getVisibleRect().contains(lead) ||
                isAroundBorders(lead))
        {
            AttributeKeys.Orientation orientation = getOrientation(lead);
            delta = new Delta();
            if (orientation != null)
            {
                switch (orientation)
                {
                    case WEST:
                        delta.dX *= -1;
                        delta.dY *= 0;
                        delta.dS *= -1;
                        break;
                    case EAST:
                        delta.dX *= 1;
                        delta.dY *= 0;
                        delta.dS *= 1;
                        break;
                    case NORTH:
                        delta.dX *= 0;
                        delta.dY *= -1;
                        delta.dS *= -1;
                        break;
                    case SOUTH:
                        delta.dX *= 0;
                        delta.dY *= 1;
                        delta.dS *= 1;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
        return delta;
    }

    private AttributeKeys.Orientation getOrientation(Point lead)
    {
        Rectangle2D visibleRect = view.getComponent().getVisibleRect();

        AttributeKeys.Orientation orientation = null;
        if (visibleRect.getMaxX() <= lead.getX() ||
                visibleRect.getMaxX() - lead.getX() <= DISTANCE_TO_MOVE)
            orientation = AttributeKeys.Orientation.EAST;
        else if (visibleRect.getMinX() >= lead.getX() ||
                lead.x - visibleRect.getMinX() <= DISTANCE_TO_MOVE)
            orientation = AttributeKeys.Orientation.WEST;
        else if (visibleRect.getMinY() >= lead.getY() ||
                lead.y - visibleRect.getMinY() <= DISTANCE_TO_MOVE)
            orientation = AttributeKeys.Orientation.NORTH;
        else if (visibleRect.getMaxY() <= lead.getY() ||
                visibleRect.getMaxY() - lead.y <= DISTANCE_TO_MOVE)
            orientation = AttributeKeys.Orientation.SOUTH;

        return orientation;
    }

    public DrawingView getView()
    {
        return view;
    }

    public void setView(DrawingView view)
    {
        this.view = view;
    }


    private class Delta
    {
        private int dX = shiftScroll;
        private int dY = shiftScroll;
        private int dS = shift;
    }

}
