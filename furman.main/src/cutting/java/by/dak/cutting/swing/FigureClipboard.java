package by.dak.cutting.swing;

import by.dak.cutting.swing.buffer.FigureListUpdater;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * User: akoyro
 * Date: 20.12.2010
 * Time: 23:31:01
 */
public class FigureClipboard
{
    private FigureListUpdater listUpdater = new FigureListUpdater();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public void exportData(ElementFigure elementFigure, CuttingDrawing source)
    {
        if (elementFigure != null && elementFigure.getParent() == null)
        {
            source.remove(elementFigure);
            listUpdater.add(elementFigure);
            pcs.firePropertyChange("index", null, listUpdater.getCount() - 1);
        }
    }

    public void importData(CuttingDrawing cuttingDrawing, ElementFigure elementFigure, DrawingView view)
    {
        cuttingDrawing.add(elementFigure);

        Point2D.Double currentFigurePoint = elementFigure.getStartPoint();
        SegmentFigure topFigure = cuttingDrawing.getTopFigure();

        double offsetTopSegmentWidth = topFigure.getBounds().getWidth();
        Point2D.Double point = new Point2D.Double((int) (2 * CuttingDrawing.OFFSET_X +
                offsetTopSegmentWidth), CuttingDrawing.OFFSET_Y);
        double offsetFree = calculateFreeOffset(point, cuttingDrawing);

        AffineTransform tx = new AffineTransform();
        tx.translate(-currentFigurePoint.getX() + point.getX(),
                -currentFigurePoint.getY() + point.getY() + offsetFree);

        elementFigure.willChange();
        elementFigure.transform(tx);
        elementFigure.changed();

//        view.addToSelection(elementFigure);
    }


    /**
     * метод для отсчёта свободной области в случае присутствия фигуры
     * на заданном участке(то есть если вытянута 1 фигура,
     * то при вытягивании второй метод рассчитает свободную область,чтобы не накладывались фигуры
     * друг на друга)
     *
     * @param oldPoint
     * @param cuttingDrawing
     * @return
     */
    private double calculateFreeOffset(Point2D.Double oldPoint, CuttingDrawing cuttingDrawing)
    {
        double offsetY = 0;
        Figure importedFigure = cuttingDrawing.findFigure(oldPoint);
        if (importedFigure != null)
        {
            double height = importedFigure.getBounds().getHeight();
            double y = oldPoint.getY() + height;
            offsetY = calculateFreeOffset(new Point2D.Double(oldPoint.getX(), y), cuttingDrawing) + height;

        }
        return offsetY;
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    public FigureListUpdater getListUpdater()
    {
        return listUpdater;
    }
}
