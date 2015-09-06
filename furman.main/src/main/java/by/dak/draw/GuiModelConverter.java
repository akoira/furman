package by.dak.draw;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static by.dak.draw.Graphics2DUtils.OFFSET;
import static by.dak.draw.Graphics2DUtils.POINT_SIZE;

/**
 * User: akoyro
 * Date: 09.07.2009
 * Time: 22:26:02
 */
public class GuiModelConverter
{

    public Point2D convertGui2ModelPoint(Point guiPoint)
    {
        return new Point2D.Double(guiPoint.getX() - OFFSET, guiPoint.getY() - OFFSET);
    }

    public Rectangle2D convertPoint2Rectangle(Point2D point2D)
    {
        return new Rectangle2D.Double(point2D.getX() - POINT_SIZE / 2d,
                point2D.getY() - POINT_SIZE / 2d,
                POINT_SIZE,
                POINT_SIZE);
    }

    public Rectangle2D convertDim2Rec(Dimension2D dimension)
    {
        return new Rectangle2D.Double(0, 0, dimension.getWidth(), dimension.getHeight());
    }

}
