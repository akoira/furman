package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.draw.MillingLength;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.CurveFigure;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.ShapeProvider;
import org.jhotdraw.draw.AbstractAttributedFigure;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.ResizeHandleKit;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 09.09.2009
 * Time: 19:05:04
 * To change this template use File | Settings | File Templates.
 */
public class HProfileJoin extends AbstractAttributedFigure implements MillingLength, ShapeProvider
{
    public static final float size = 2f;  // размер линий
    private Rectangle2D.Double rec;
    private double profileWidth;
    private List<Figure> lines;
    private java.util.List<Figure> linesButt;

    public HProfileJoin(double profileWidth)
    {
        this.profileWidth = profileWidth;
    }

    @Override
    protected void drawFill(Graphics2D g)
    {
    }

    @Override
    public Connector findConnector(Point2D.Double p, ConnectionFigure prototype)
    {
        return null;
    }

    @Override
    public Connector findCompatibleConnector(Connector c, boolean isStartConnector)
    {
        return null;
    }

    @Override
    protected void drawStroke(Graphics2D g)
    {
        Color color = g.getColor();
        Stroke stroke = g.getStroke();

        g.draw(rec);

        g.setStroke(stroke);
        g.setColor(color);
    }

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead)
    {
        Point2D.Double near = new Point2D.Double(-1000, -1000);
        Point2D.Double min = new Point2D.Double();
        Point2D.Double max = new Point2D.Double();

        if (anchor.distance(near) < lead.distance(near))
        {
            min.setLocation(anchor);
            max.setLocation(lead.getX(), anchor.getY());
        }
        else
        {
            min.setLocation(lead.getX(), anchor.getY());
            max.setLocation(anchor);
        }

        if (rec == null)
        {
            rec = new Rectangle2D.Double();
        }

        rec.setRect(min.getX(), min.getY(), max.getX() - min.getX(), profileWidth);

        createLine();
    }

    private void createLine()
    {
        if (lines == null)
        {
            lines = new ArrayList<Figure>();
            linesButt = new ArrayList<Figure>();
        }
        CurveFigure line0;
        CurveFigure line1;
        CurveFigure line2;
        CurveFigure line3;
        if (lines.isEmpty())
        {
            line0 = new CurveFigure();
            line1 = new CurveFigure();
            line2 = new CurveFigure();
            line3 = new CurveFigure();
        }
        else
        {
            line0 = (CurveFigure) lines.get(0);
            line1 = (CurveFigure) linesButt.get(0);
            line2 = (CurveFigure) lines.get(1);
            line3 = (CurveFigure) linesButt.get(1);
        }
        line0.setStartPoint(new Point2D.Double(rec.getMinX(), rec.getMinY()));
        line0.setEndPoint(new Point2D.Double(rec.getMaxX(), rec.getMinY()));
        line0.setDirection(-1);

        line1.setStartPoint(new Point2D.Double(rec.getMaxX(), rec.getMinY()));
        line1.setEndPoint(new Point2D.Double(rec.getMaxX(), rec.getMaxY()));
        line1.setDirection(-1);

        line2.setStartPoint(new Point2D.Double(rec.getMaxX(), rec.getMaxY()));
        line2.setEndPoint(new Point2D.Double(rec.getMinX(), rec.getMaxY()));
        line2.setDirection(1);

        line3.setStartPoint(new Point2D.Double(rec.getMinX(), rec.getMaxY()));
        line3.setEndPoint(new Point2D.Double(rec.getMinX(), rec.getMinY()));
        line3.setDirection(-1);

        if (lines.isEmpty())
        {
            lines.add(line0);
            linesButt.add(line1);
            lines.add(line2);
            linesButt.add(line3);
        }
    }

    @Override
    public Rectangle2D.Double getDrawingArea()
    {
        Rectangle2D.Double r = new Rectangle2D.Double(rec.getMinX() - 10, rec.getMinY() - 10, rec.getMaxX() + 10, rec.getMaxY() + 10);
        return r;
    }

    @Override
    public Rectangle2D.Double getBounds()
    {
        return (Rectangle2D.Double) rec.clone();
    }

    @Override
    public boolean contains(Point2D.Double p)
    {
        return rec.contains(p);
    }

    @Override
    public Object getTransformRestoreData()
    {
        return rec.clone();
    }

    @Override
    public void restoreTransformTo(Object restoreData)
    {
        Rectangle2D.Double r = (Rectangle2D.Double) restoreData;
        rec.setRect(r);
    }

    @Override
    public void transform(AffineTransform tx)
    {
        int i = 0;
    }

    @Override
    public Collection<Handle> createHandles(int detailLevel)
    {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        switch (detailLevel)
        {
            case -1:
                handles.add(new BoundsOutlineHandle(this, false, true));
                break;
            case 0:
                handles.add(new BoundsOutlineHandle(this));
                handles.add(ResizeHandleKit.west(this));
                handles.add(ResizeHandleKit.east(this));

                break;
        }
        return handles;
    }

    @Override
    public double getMillingLength()
    {
        return rec.getHeight();
    }

    @Override
    public Shape getShape()
    {
        return rec;
    }

    public Rectangle2D.Double getRec()
    {
        return rec;
    }

    public double getProfileWidth()
    {
        return profileWidth;
    }

    @Override
    public void draw(Graphics2D g)
    {
        g.setStroke(new BasicStroke(size));
        g.setColor(Color.BLACK);
        drawStroke(g);
    }

    public List<Figure> getLines()
    {
        return lines;
    }

    public List<Figure> getLinesButt()
    {
        return linesButt;
    }

    @Override
    public Shape getInvertedStartEndShape()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
