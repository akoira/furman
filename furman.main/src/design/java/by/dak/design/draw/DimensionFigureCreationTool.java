package by.dak.design.draw;

import by.dak.design.draw.components.Connectable;
import by.dak.design.draw.components.DimensionConnector;
import by.dak.design.draw.components.DimensionFigure;
import by.dak.draw.MoveScrollHandle;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.tool.CreationTool;

import javax.swing.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/22/11
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class DimensionFigureCreationTool extends CreationTool
{
    public static final double RADIUS_TO_SEARCH = 3;

    private boolean creationStarted = false;
    private boolean creationFinished = false;
    private DimensionConnector targetConnector;
    private DimensionConnector oldConnector = null;
    private MoveScrollHandle scrollHandle = new MoveScrollHandle();

    public DimensionFigureCreationTool(Figure prototype)
    {
        super(prototype);
    }

    @Override
    public void mouseReleased(MouseEvent evt)
    {
        if (createdFigure != null)
        {
            if (!creationStarted && !creationFinished)
            {
                creationStarted = true;
            }
            else if (creationStarted && !creationFinished)
            {
                Rectangle2D.Double bounds = createdFigure.getBounds();
                if (bounds.width == 0 && bounds.height == 0)
                {
                    creationFinished = false;
                    creationStarted = false;
                    getDrawing().remove(createdFigure);
                    if (isToolDoneAfterCreation())
                    {
                        fireToolDone();
                    }
                }
                else
                {
                    creationFinished = true;

                    final Figure addedFigure = createdFigure;
                    final Drawing addedDrawing = getDrawing();
                    getDrawing().fireUndoableEditHappened(new AbstractUndoableEdit()
                    {

                        @Override
                        public String getPresentationName()
                        {
                            return presentationName;
                        }

                        @Override
                        public void undo() throws CannotUndoException
                        {
                            super.undo();
                            addedDrawing.remove(addedFigure);
                        }

                        @Override
                        public void redo() throws CannotRedoException
                        {
                            super.redo();
                            addedDrawing.add(addedFigure);
                        }
                    });
                    Rectangle r = new Rectangle(anchor.x, anchor.y, 0, 0);
                    r.add(evt.getX(), evt.getY());
                    maybeFireBoundsInvalidated(r);
                }
            }
        }
        else
        {
            if (isToolDoneAfterCreation())
            {
                fireToolDone();
            }
        }

    }

    @Override
    public void mouseDragged(MouseEvent evt)
    {
        if (createdFigure != null)
        {
            Point2D.Double p = constrainPoint(new Point(evt.getX(), evt.getY()));
            createdFigure.willChange();
            createdFigure.setBounds(
                    createdFigure.getStartPoint(),
                    p);
            createdFigure.changed();
        }
        repaintConnectors(getView().viewToDrawing(evt.getPoint()));

        scrollHandle.setView(getView());
        scrollHandle.moveScroll(evt.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent evt)
    {
        if (!creationStarted && !creationFinished)
        {
            Point2D.Double p = constrainPoint(viewToDrawing(anchor));
            anchor.x = evt.getX();
            anchor.y = evt.getY();
            createdFigure.setBounds(p, p);
            getDrawing().add(createdFigure);
            connectStart(getView().viewToDrawing(evt.getPoint()));
        }
        else if (!creationFinished && createdFigure != null)
        {
            connectEnd(getView().viewToDrawing(evt.getPoint()));
        }
        else if (creationFinished && createdFigure != null)
        {
            creationFinished(createdFigure);
            createdFigure = null;
        }

    }

    private void connectStart(Point2D.Double point)
    {
        if (targetConnector != null)
        {
            DimensionConnector startConnector = targetConnector;
            Point2D.Double startPoint = startConnector.findStart(point);
            ((DimensionFigure) createdFigure).setStartConnector(startConnector);
            createdFigure.willChange();
            ((DimensionFigure) createdFigure).setStartPoint(startPoint);
            createdFigure.changed();

        }
    }

    private void connectEnd(Point2D.Double point)
    {
        if (targetConnector != null)
        {
            DimensionConnector endConnector = targetConnector;
            Point2D.Double endPoint = endConnector.findEnd(point, createdFigure.getStartPoint());
            ((DimensionFigure) createdFigure).setEndConnector(endConnector);
            createdFigure.willChange();
            ((DimensionFigure) createdFigure).setEndPoint(endPoint);
            createdFigure.changed();
        }
    }

    @Override
    protected void creationFinished(Figure createdFigure)
    {
        if (isToolDoneAfterCreation())
        {
            fireToolDone();
        }
        if (createdFigure.isSelectable())
        {
            getView().clearSelection();
        }
        creationFinished = false;
        creationStarted = false;
    }

    @Override
    public void draw(Graphics2D g)
    {
        Graphics2D gg = (Graphics2D) g.create();
        gg.transform(getView().getDrawingToViewTransform());
        if (targetConnector != null)
        {
            targetConnector.draw(gg);
        }
        gg.dispose();
    }


    @Override
    public void mouseMoved(MouseEvent evt)
    {
        if (createdFigure == null)
        {
            DrawingView view = editor.findView((Container) evt.getSource());
            view.requestFocus();
            anchor = new Point(evt.getX(), evt.getY());
            isWorking = true;
            fireToolStarted(view);
            getView().clearSelection();
            createdFigure = createFigure();
        }
        anchor = new Point(evt.getX(), evt.getY());
        if (creationStarted && !creationFinished && createdFigure != null)
        {
            Point2D.Double p = constrainPoint(new Point(evt.getX(), evt.getY()));
            createdFigure.willChange();
            createdFigure.setBounds(
                    createdFigure.getStartPoint(),
                    p);
            createdFigure.changed();
        }
        repaintConnectors(getView().viewToDrawing(evt.getPoint()));
        changeTipPosition(getView().viewToDrawing(evt.getPoint()));

        scrollHandle.setView(getView());
        scrollHandle.moveScroll(evt.getPoint());
    }


    /**
     * сначала происходит поиск лежит ли точка на фигуре, а потом поиск фигуры в радиусе точки
     *
     * @param point
     * @return
     */
    private Figure findFigureToConnect(Point2D.Double point)
    {
        Figure figure = null;
        figure = getDrawing().findFigureExcept(point, createdFigure);
        if (figure != null)
        {
            return figure;
        }
        Rectangle2D.Double areaToSearch = new Rectangle2D.Double(point.x - RADIUS_TO_SEARCH / getView().getScaleFactor(),
                point.y - RADIUS_TO_SEARCH / getView().getScaleFactor(),
                RADIUS_TO_SEARCH / getView().getScaleFactor() * 2, RADIUS_TO_SEARCH / getView().getScaleFactor() * 2);

        List<Figure> figuresInArea = getDrawing().findFigures(areaToSearch);
        if (!figuresInArea.isEmpty())
        {
            if (figuresInArea.get(0) instanceof Connectable)
            {
                DimensionConnector currentConnector = ((Connectable) figuresInArea.get(0)).getConnector(point);
                for (Figure f : figuresInArea)
                {
                    if (f instanceof Connectable)
                    {
                        DimensionConnector connector = ((Connectable) f).getConnector(point);
                        if (connector.findStart(point).distance(point) <=
                                currentConnector.findStart(point).distance(point))
                        {
                            currentConnector = connector;
                        }
                    }
                }
                return currentConnector.getOwner();
            }
        }

        return figure;
    }

    private void repaintConnectors(Point2D.Double point)
    {
        Figure figure = findFigureToConnect(point);

        if (figure != null && !creationFinished)
        {
            if (figure instanceof Connectable)
            {
                targetConnector = ((Connectable) figure).getConnector(point);
                targetConnector.setScale(getView().getScaleFactor());
                Ellipse2D.Double areaToSearch = new Ellipse2D.Double(targetConnector.findStart(point).x - RADIUS_TO_SEARCH / getView().getScaleFactor(),
                        targetConnector.findStart(point).y - RADIUS_TO_SEARCH / getView().getScaleFactor(),
                        (RADIUS_TO_SEARCH / getView().getScaleFactor()) * 2, (RADIUS_TO_SEARCH / getView().getScaleFactor()) * 2);
                if (areaToSearch.contains(point))
                {
                    if (!areaToSearch.contains(createdFigure.getStartPoint()))
                    {
                        try
                        {
                            Point pointToConvert = getView().drawingToView(targetConnector.findStart(point));
                            SwingUtilities.convertPointToScreen(pointToConvert, (Component) getView());
                            Robot robot = new Robot();
                            robot.mouseMove(pointToConvert.x, pointToConvert.y);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        else
        {
            targetConnector = null;
        }

        Rectangle2D.Double invalidArea = new Rectangle2D.Double();
        if (targetConnector != null)
        {
            invalidArea.add(targetConnector.getDrawingArea());
        }

        if (oldConnector != null)
        {
            invalidArea.add(oldConnector.getDrawingArea());
        }
        getView().getComponent().repaint(
                getView().drawingToView(invalidArea));
        oldConnector = targetConnector;
    }


    private void changeTipPosition(Point2D.Double point)
    {

        if (creationFinished && createdFigure != null)
        {
            Point2D.Double figureEndPoint = createdFigure.getEndPoint();
            Point2D.Double currentPoint = point;

            Line2D.Double line = new Line2D.Double(createdFigure.getStartPoint(), figureEndPoint);
            double distance = line.ptLineDist(currentPoint);

            createdFigure.willChange();

            double firstSide = createdFigure.getStartPoint().x - createdFigure.getEndPoint().x;
            double secondSide = createdFigure.getStartPoint().y - createdFigure.getEndPoint().y;
            double angle = Math.atan(secondSide / firstSide);

            if (Math.toDegrees(angle) >= 50 && Math.toDegrees(angle) <= 130)
            {

                double x = (createdFigure.getEndPoint().x - createdFigure.getStartPoint().x) *
                        (currentPoint.y - createdFigure.getStartPoint().y) /
                        (createdFigure.getEndPoint().y - createdFigure.getStartPoint().y);

                double result = x + createdFigure.getStartPoint().x;
                if (Math.abs(result) < currentPoint.x)
                {
                    ((DimensionFigure) createdFigure).setOffset(distance);
                }
                else
                {
                    ((DimensionFigure) createdFigure).setOffset(-distance);
                }

            }
            else if (Math.toDegrees(angle) >= -130 && Math.toDegrees(angle) <= -50)
            {

                double x = (createdFigure.getEndPoint().x - createdFigure.getStartPoint().x) *
                        (currentPoint.y - createdFigure.getStartPoint().y) /
                        (createdFigure.getEndPoint().y - createdFigure.getStartPoint().y);

                double result = x + createdFigure.getStartPoint().x;

                if (Math.abs(result) > currentPoint.x)
                {
                    ((DimensionFigure) createdFigure).setOffset(distance);
                }
                else
                {
                    ((DimensionFigure) createdFigure).setOffset(-distance);
                }

            }
            else
            {

                double y = (currentPoint.x - createdFigure.getStartPoint().x) *
                        (createdFigure.getEndPoint().y - createdFigure.getStartPoint().y) /
                        (createdFigure.getEndPoint().x - createdFigure.getStartPoint().x);
                double result = y + createdFigure.getStartPoint().y;

                if (Math.abs(result) > currentPoint.getY())
                {
                    ((DimensionFigure) createdFigure).setOffset(distance);
                }
                else
                {
                    ((DimensionFigure) createdFigure).setOffset(-distance);
                }

            }

            createdFigure.changed();
        }
    }
}
