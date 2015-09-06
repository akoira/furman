package by.dak.design.draw.handle;

import by.dak.design.draw.components.BoardFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.tool.DefaultDragTracker;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 29.08.11
 * Time: 23:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class ADesignerDragTracker extends DefaultDragTracker
{
    private TextFigure currentTextFigure;
    public static int TOOL_TIP_OFFSET = 12;

    protected ADesignerDragTracker(Figure figure)
    {
        super(figure);
    }

    protected ADesignerDragTracker()
    {
    }

    @Override
    public void mousePressed(MouseEvent evt)
    {
        super.mousePressed(evt);
        if (anchorFigure instanceof BoardFigure)
        {
            enableToolTip(true, evt.getPoint());
        }
    }

    @Override
    public void mouseDragged(MouseEvent evt)
    {
        if (anchorFigure.isTransformable() && getView().getSelectionCount() == 1)
        {
            super.mouseDragged(evt);
        }
        enableToolTip(true, evt.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent evt)
    {
        super.mouseReleased(evt);
        enableToolTip(false, evt.getPoint());
    }

    private void enableToolTip(boolean enable, Point lead)
    {
        if (enable)
        {
            getView().getDrawing().remove(currentTextFigure);

            String toolTip = anchorFigure.getToolTipText(getView().viewToDrawing(lead));

            TextFigure textFigure = new TextFigure(toolTip);
            textFigure.setFontSize((float) (textFigure.getFontSize() * (1 / getView().getScaleFactor())));

            Point2D.Double anchorPoint = new Point2D.Double(getView().viewToDrawing(lead).x +
                    TOOL_TIP_OFFSET * (1 / getView().getScaleFactor()),
                    getView().viewToDrawing(lead).y);
            textFigure.setBounds(anchorPoint, anchorPoint);
            textFigure.setSelectable(false);
            getView().getDrawing().add(textFigure);
            currentTextFigure = textFigure;
        }
        else
        {
            getView().getDrawing().remove(currentTextFigure);
        }
    }

}
