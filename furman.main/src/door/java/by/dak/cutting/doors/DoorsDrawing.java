package by.dak.cutting.doors;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.CurveFigure;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.ElementDrawing;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.ShapeProvider;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.Figure;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 10.08.2009
 * Time: 10:18:04
 * To change this template use File | Settings | File Templates.
 */
public class DoorsDrawing extends DefaultDrawing
{
    private Point2D.Double start = new Point2D.Double(0, 0); //начало отрисовки елемента
    private double indent = 0; // расстоянеи между дверями
    private boolean isDrowNow;

    private java.util.List<DoorDrawing> drawings = new ArrayList<DoorDrawing>();

    @Override
    public void draw(Graphics2D g, Collection<Figure> children)
    {
        for (DoorDrawing drawing : drawings)
        {
            drawing.drawFrameDoor(g);
        }
        super.draw(g, this.children);
    }

    public void setStart(Point2D.Double start)
    {
        this.start = start;
    }

    @Override
    public Rectangle2D.Double getDrawingArea()
    {
        Rectangle2D.Double d = super.getDrawingArea();
        for (DoorDrawing drawing : drawings)
        {
            d.add(drawing.getDrawingArea());
        }
        return d;
    }

    public void setDrawings(List<DoorDrawing> drawings)
    {
        isDrowNow = true;
        int numberOfDoor = 1;
        this.drawings.clear();
        removeAllChildren();
        double x = start.getX();
        double y = start.getY();
        for (DoorDrawing drawing : drawings)
        {
            if (x == start.getX())
            {
                drawing.setDrawlineHeight(true);
            }
            else
            {
                drawing.setDrawlineHeight(false);
            }

            drawing.setNumberOfDoor(numberOfDoor);
            drawing.setStart(new Point2D.Double(x, y));
            x += drawing.getDoor().getWidth() + indent;
            numberOfDoor++;
            this.drawings.add(drawing);
        }

        for (DoorDrawing drawing : this.drawings)
        {
            addAll(drawing.getChildren());
        }
    }

    public void splitChilds()
    {
        DoorImpl door = new DoorImpl();
        door.setHeight((long) drawings.get(0).getElementRec().getHeight());
        door.setWidth(((long) drawings.get(0).getElementRec().getWidth()));
        door.setDoorColor(null);
        door.setDoorMechType(null);
        DoorDrawing doorDrawing = new DoorDrawing();
        doorDrawing.setDoor(door);

        for (DoorDrawing drawing : this.drawings)
        {
            if (drawing.getStart().distance(0, 0) > 1)
            {
                CurveFigure line = new CurveFigure();
                line.setStartPoint((Point2D.Double) drawing.getStart().clone());
                line.setEndPoint(new Point2D.Double(drawing.getStart().getX(),
                        drawing.getStart().getY() + door.getHeight()));
                doorDrawing.add(line);
            }
        }
        doorDrawing.addAll(this.children);

        ElementDrawing drawing = doorDrawing.getDoorElements();

        addToDoors(drawing);
    }

    // добавление к каждой двери нужного елемента
    private void addToDoors(ElementDrawing elDrawing)
    {
        for (DoorDrawing drawing : this.drawings)
        {
            Point2D.Double min = drawing.getStart();
            Point2D.Double max = new Point2D.Double(drawing.getStart().getX() + drawing.getElementRec().getWidth(),
                    drawing.getStart().getY() + drawing.getElementRec().getHeight());
            List<Figure> listChilds = new ArrayList<Figure>();
            for (Figure figure : elDrawing.getChildren())
            {
                Rectangle2D rec = ((ShapeProvider) figure).getShape().getBounds2D();
                Point2D.Double middle = new Point2D.Double(rec.getMinX() + (rec.getMaxX() - rec.getMinX()) / 2,
                        rec.getMinY() + (rec.getMaxY() - rec.getMinY()) / 2);

                if ((Math.abs(middle.getX() - min.getX()) < 2 || Math.abs(middle.getX() - max.getX()) < 2
                        || Math.abs(middle.getY() - min.getY()) < 2 || Math.abs(middle.getY() - max.getY()) < 2) ||
                        !(middle.getX() - min.getX() >= -1 && middle.getX() - max.getX() <= 1 &&
                                middle.getY() - min.getY() >= -1 && middle.getY() - max.getY() <= 1))
                {
                    continue;
                }
                else
                {
                    listChilds.add(figure);
                }
            }
            drawing.setChildren(listChilds);
        }
    }

    public double getIndent()
    {
        return indent;
    }

    public boolean isDrowNow()
    {
        return isDrowNow;
    }

    public void setDrowNow(boolean drowNow)
    {
        isDrowNow = drowNow;
    }
}