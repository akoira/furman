package by.dak.design.draw.components;

import by.dak.design.draw.handle.BoardMagneticHandler;
import org.jhotdraw.draw.Figure;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/21/11
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class DimensionConnector
{
    private int oldStartIndex = -1;
    private int oldEndIndex = -1;
    private double scale = 1;
    private Figure owner;
    private int ownerLayoutDepth;
    /**
     * все точки к которым можно присоединить dimension
     */
    private List<Point2D.Double> connectors = new ArrayList<Point2D.Double>();

    public DimensionConnector(Figure owner)
    {
        this.owner = owner;
    }

    public int getOwnerLayoutDepth()
    {
        return ownerLayoutDepth;
    }

    public void setOwnerLayoutDepth(int ownerLayoutDepth)
    {
        this.ownerLayoutDepth = ownerLayoutDepth;
    }

    public Point2D.Double findStart(Point2D.Double relative)
    {
        updateConnectors();

        oldStartIndex = isMagnetizedOwnerConnector(relative, oldStartIndex);
        if (oldStartIndex != -1)
        {
            return connectors.get(oldStartIndex);
        }

        Point2D.Double currentConnector = connectors.get(0);
        for (Point2D.Double connector : connectors)
        {
            if (relative.distance(connector) < relative.distance(currentConnector))
            {
                currentConnector = connector;
            }
        }

        return currentConnector;
    }

    public double getScale()
    {
        return scale;
    }

    public void setScale(double scale)
    {
        this.scale = scale;
    }

    /**
     * в случае смыкания board'а с Cell (эффект магнитизма), точку к которой
     * присоединён dimension надо запомнить
     *
     * @param relative
     * @param index
     * @return
     */
    private int isMagnetizedOwnerConnector(Point2D.Double relative, int index)
    {
        int count = 0;
        for (Point2D.Double connector : connectors)
        {
            if (relative.distance(connector) <= BoardMagneticHandler.SIZE_TO_MAGNETIZE)
            {
                count++;
            }
        }
        if (count > 1)
        {
            if (index != -1)
            {
                return index;
            }
            else
            {
                for (int i = 0; i < connectors.size(); i++)
                {
                    if (connectors.get(i).equals(relative))
                    {
                        return i;
                    }
                }

            }
        }
        return -1;
    }


    public Point2D.Double findEnd(Point2D.Double relative, Point2D.Double exept)
    {
        updateConnectors();

        oldEndIndex = isMagnetizedOwnerConnector(relative, oldEndIndex);
        if (oldEndIndex != -1)
        {
            return connectors.get(oldEndIndex);
        }

        Point2D.Double currentConnector = connectors.get(0);
        if (exept.equals(currentConnector))
        {
            currentConnector = connectors.get(1);
        }
        for (Point2D.Double connector : connectors)
        {
            if (relative.distance(connector) < relative.distance(currentConnector) &&
                    !exept.equals(connector))
            {
                currentConnector = connector;
            }
        }
        return currentConnector;
    }

    private void updateConnectors()
    {
        connectors.clear();
        connectors.add(owner.getStartPoint());
        connectors.add(owner.getEndPoint());
        connectors.add(new Point2D.Double(owner.getStartPoint().x, owner.getEndPoint().y));
        connectors.add(new Point2D.Double(owner.getEndPoint().x, owner.getStartPoint().y));

    }

    public Rectangle2D.Double getDrawingArea()
    {
        updateConnectors();
        Rectangle2D.Double rectangle = new Rectangle2D.Double();
        for (Point2D.Double connector : connectors)
        {
            rectangle.add(new Rectangle2D.Double(connector.x - 4 * 1 / scale, connector.y - 4 * 1 / scale,
                    8 * 1 / scale, 8 * 1 / scale));
        }
        return rectangle;
    }

    public void draw(Graphics2D g)
    {
        if (connectors.isEmpty())
        {
            updateConnectors();
        }
        for (Point2D.Double connector : connectors)
        {
            Ellipse2D.Double e = new Ellipse2D.Double(connector.x - 3 * 1 / scale, connector.y - 3 * 1 / scale,
                    6 * 1 / scale, 6 * 1 / scale);
            g.setColor(Color.BLUE);
            g.fill(e);
        }
    }

    public Figure getOwner()
    {
        return owner;
    }
}
