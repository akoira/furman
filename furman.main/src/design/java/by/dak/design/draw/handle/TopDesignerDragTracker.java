package by.dak.design.draw.handle;

import by.dak.design.draw.components.DimensionFigure;

import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 29.08.11
 * Time: 23:45
 * To change this template use File | Settings | File Templates.
 */
public class TopDesignerDragTracker extends ADesignerDragTracker
{


    /**
     * for topView not allowed to move figures
     *
     * @param evt
     */
    @Override
    public void mouseDragged(MouseEvent evt)
    {
        super.mouseDragged(evt);
        if (anchorFigure instanceof DimensionFigure)
        {
            DimensionPositionHandler dimensionPositionHandler = new DimensionPositionHandler((DimensionFigure) anchorFigure);
            dimensionPositionHandler.trackStep(viewToDrawing(evt.getPoint()));
        }
    }
}
