package by.dak.design.draw;

import by.dak.design.draw.components.CellFigure;
import by.dak.design.draw.components.DimensionConnector;
import by.dak.design.draw.components.DimensionFigure;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.Figure;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 29.08.11
 * Time: 2:05
 * To change this template use File | Settings | File Templates.
 */
public abstract class ADesignerDrawing extends DefaultDrawing
{
    public static String PROPERTY_topFigure = "topFigure";
    protected CellFigure topFigure;

    public ADesignerDrawing()
    {
        addPropertyChangeListener(PROPERTY_topFigure, new TopFigureChangeListener());
    }

    public void setTopFigure(CellFigure topFigure)
    {
        this.topFigure = topFigure;
        firePropertyChange(PROPERTY_topFigure, null, topFigure);
    }

    public CellFigure getTopFigure()
    {
        return topFigure;
    }

    private void topFigureChanged()
    {
        removeAllChildren();
        add(topFigure);
    }


    private class TopFigureChangeListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            topFigureChanged();
        }
    }

    //todo плохая реализация: проверка должна быть лучше
    public void moveTopFigure(double offsetX, double offsetY)
    {

        if (topFigure.getStartPoint().x == 0 &&
                topFigure.getStartPoint().y == 0)
        {
            topFigure.willChange();
            topFigure.setBounds(new Point2D.Double(offsetX, offsetY),
                    new Point2D.Double(topFigure.getBounds().getWidth() + offsetX,
                            topFigure.getBounds().getHeight() + offsetY));
            topFigure.changed();
            move(topFigure, offsetX, offsetY);
        }
    }

    private void move(CellFigure cellFigure, double offsetX, double offsetY)
    {
        for (Figure figure : cellFigure.getChildren())
        {
            figure.willChange();
            figure.setBounds(new Point2D.Double(offsetX, offsetY),
                    new Point2D.Double(topFigure.getBounds().getWidth() + offsetX,
                            topFigure.getBounds().getHeight() + offsetY));
            figure.changed();
            move((CellFigure) figure, offsetX, offsetY);
        }
    }

    //todo плохая реализация проверки когда добавлять dimension
    public void addDimensionsTo(CellFigure topFigure, int offset)
    {
        if (getChildCount() > 1)
        {
            return;
        }

        double minX = topFigure.getBounds().getMinX();
        double maxX = topFigure.getBounds().getMaxX();
        double minY = topFigure.getBounds().getMinY();
        double maxY = topFigure.getBounds().getMaxY();


        DimensionFigure rightDimension = new DimensionFigure();
        rightDimension.setStartConnector(new DimensionConnector(topFigure));
        rightDimension.setEndConnector(new DimensionConnector(topFigure));
        rightDimension.setStartPoint(new Point2D.Double(maxX, minY));
        rightDimension.setEndPoint(new Point2D.Double(maxX, maxY));
        rightDimension.setOffset(-offset);

        DimensionFigure bottomDimension = new DimensionFigure();
        bottomDimension.setStartConnector(new DimensionConnector(topFigure));
        bottomDimension.setEndConnector(new DimensionConnector(topFigure));
        bottomDimension.setStartPoint(new Point2D.Double(minX, maxY));
        bottomDimension.setEndPoint(new Point2D.Double(maxX, maxY));
        bottomDimension.setOffset(-offset);

        add(rightDimension);
        add(bottomDimension);
    }

    @Override
    public Rectangle2D.Double getDrawingArea()
    {
        if (cachedDrawingArea == null)
        {
            if (getChildCount() == 0)
            {
                cachedDrawingArea = new Rectangle2D.Double();
            }
            else
            {
                for (Figure f : children)
                {
                    if (cachedDrawingArea == null)
                    {
                        cachedDrawingArea = f.getDrawingArea();
                    }
                    else
                    {
                        cachedDrawingArea.add(f.getDrawingArea());
                    }
                }
            }
            //на сколько был офсет на столько и увиличивается drawingArea, чтобы была пропорция между расстояниями в view
            if (topFigure != null)
            {
                double width = topFigure.getStartPoint().x;
                double height = topFigure.getStartPoint().y;
                cachedDrawingArea.add(new Rectangle2D.Double(0, 0, topFigure.getEndPoint().x + width,
                        topFigure.getEndPoint().y + height));
            }

        }
        return (Rectangle2D.Double) cachedDrawingArea.clone();
    }
}
