package by.dak.design.draw;

import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import by.dak.design.draw.handle.BoardFigureRemover;
import by.dak.design.draw.handle.BoardNumerationHandler;
import by.dak.design.draw.handle.CellNumerationHandler;
import by.dak.design.entity.converter.DesignerDrawingXConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.jhotdraw.draw.Figure;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 6/27/11
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
@XStreamConverter(value = DesignerDrawingXConverter.class)
@XStreamAlias(value = "FrontDesignerDrawing")
public class FrontDesignerDrawing extends ADesignerDrawing
{
    public FrontDesignerDrawing()
    {
        super();
    }

    /**
     * ищет cell который лежит под выбранным board
     *
     * @param point
     * @return
     */
    public CellFigure findCellFigure(Point2D.Double point)
    {
        if (topFigure.contains(point))
        {
            Figure f = topFigure.findFrontCellFigure(point, topFigure);
            if (f != null)
            {
                if (f.isVisible() && f.contains(point))
                {
                    return (CellFigure) f;
                }
            }
            return topFigure;
        }

        return null;
    }

    @Override
    public boolean remove(Figure figure)
    {
        if (figure instanceof BoardFigure)
        {
            BoardFigureRemover boardFigureRemover = new BoardFigureRemover((BoardFigure) figure);

            boolean result = false;
            if (boardFigureRemover.remove())
            {
                result = super.remove(figure);
            }
            if (result)
            {
                firePropertyChange("boardRemoved", null, figure);
                CellNumerationHandler cellNumerationHandler = new CellNumerationHandler(getTopFigure());
                cellNumerationHandler.recalcNumeration();

                BoardNumerationHandler boardNumerationHandler = new BoardNumerationHandler(this);
                boardNumerationHandler.recalcNumeration((BoardFigure) figure);
            }
            return result;
        }
        return super.remove(figure);
    }
}