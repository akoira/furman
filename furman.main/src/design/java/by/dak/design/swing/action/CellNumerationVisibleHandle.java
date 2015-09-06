package by.dak.design.swing.action;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.CellFigure;
import org.jhotdraw.draw.Figure;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 17.09.11
 * Time: 13:21
 * To change this template use File | Settings | File Templates.
 */
public class CellNumerationVisibleHandle
{
    private FrontDesignerDrawing frontDesignerDrawing;

    public CellNumerationVisibleHandle(FrontDesignerDrawing frontDesignerDrawing)
    {
        this.frontDesignerDrawing = frontDesignerDrawing;
    }

    public void setVisible(boolean visible)
    {
        setNumericVisible(frontDesignerDrawing.getTopFigure(), visible);
    }

    private void setNumericVisible(CellFigure cellFigure, boolean visible)
    {
        cellFigure.willChange();
        cellFigure.getNumerationTip().setVisible(visible);
        cellFigure.changed();
        for (Figure figure : cellFigure.getChildren())
        {
            setNumericVisible((CellFigure) figure, visible);
        }
    }
}
