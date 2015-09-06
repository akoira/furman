package by.dak.design.settings;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardFigure;
import by.dak.design.swing.action.BoardNumerationVisibleHandle;
import by.dak.design.swing.action.CellNumerationVisibleHandle;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.event.CompositeFigureEvent;
import org.jhotdraw.draw.event.CompositeFigureListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 17.09.11
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
public class SettingsController
{
    private FrontDesignerDrawing frontDesignerDrawing;
    private Settings settings;
    private BoardNumerationChangedHandler boardNumerationChangedHandler = new BoardNumerationChangedHandler();
    private CellNumerationChangedHandler cellNumerationChangedHandler = new CellNumerationChangedHandler();
    private FrontDesignerDrawingChangedHandler frontDesignerDrawingChangedHandler = new FrontDesignerDrawingChangedHandler();

    public SettingsController(FrontDesignerDrawing frontDesignerDrawing, Settings settings)
    {
        this.frontDesignerDrawing = frontDesignerDrawing;
        this.settings = settings;
    }

    public void bindSettings()
    {
        settings.addPropertyChangeListener(Settings.PROPERTY_boardNumeration, boardNumerationChangedHandler);
        settings.addPropertyChangeListener(Settings.PROPERTY_cellNumeration, cellNumerationChangedHandler);
    }

    public void bindFrontDesignerDrawing()
    {
        frontDesignerDrawing.addCompositeFigureListener(frontDesignerDrawingChangedHandler);
    }

    public void unbindSettings()
    {
        settings.removePropertyChangeListener(Settings.PROPERTY_boardNumeration, boardNumerationChangedHandler);
        settings.removePropertyChangeListener(Settings.PROPERTY_cellNumeration, cellNumerationChangedHandler);
    }

    public void unbindFrontDesignerDrawing()
    {
        frontDesignerDrawing.removeCompositeFigureListener(frontDesignerDrawingChangedHandler);
    }


    private class BoardNumerationChangedHandler implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            new BoardNumerationVisibleHandle(frontDesignerDrawing).setVisible((Boolean) evt.getNewValue());
        }
    }

    private class CellNumerationChangedHandler implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            new CellNumerationVisibleHandle(frontDesignerDrawing).setVisible((Boolean) evt.getNewValue());
        }
    }

    private class FrontDesignerDrawingChangedHandler implements CompositeFigureListener
    {

        @Override
        public void figureAdded(CompositeFigureEvent e)
        {
            Figure addedFigure = e.getChildFigure();
            if (addedFigure instanceof BoardFigure)
            {
                ((BoardFigure) addedFigure).getNumerationTip().setVisible(settings.isBoardNumeration());
            }
        }

        @Override
        public void figureRemoved(CompositeFigureEvent e)
        {
        }
    }
}
