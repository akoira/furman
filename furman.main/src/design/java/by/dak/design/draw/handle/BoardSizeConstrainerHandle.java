package by.dak.design.draw.handle;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 6/30/11
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class BoardSizeConstrainerHandle
{
    private FrontDesignerDrawing drawing;
    private BoardFigure boardFigure;

    public BoardSizeConstrainerHandle(BoardFigure boardFigure)
    {
        this.boardFigure = boardFigure;
    }

    public FrontDesignerDrawing getDrawing()
    {
        return drawing;
    }

    public void setDrawing(FrontDesignerDrawing drawing)
    {
        this.drawing = drawing;
    }

    public void trackStart()
    {
    }

    public void trackStep()
    {
        CellFigure cellFigure = boardFigure.getParent();
        if (cellFigure != null)
        {
            double length = 0;
            double height = 0;
            double minX = 0;
            double minY = 0;

            switch (boardFigure.getOrientation())
            {
                case Horizontal:
                    minX = cellFigure.getStartPoint().x;
                    minY = boardFigure.getBounds().getMinY();
                    length = cellFigure.getBounds().getWidth();
                    height = boardFigure.getBoardElement().getBoardDef().getThickness();
                    break;
                case Vertical:
                    minX = boardFigure.getBounds().getMinX();
                    minY = cellFigure.getStartPoint().y;
                    height = cellFigure.getBounds().getHeight();
                    length = boardFigure.getBoardElement().getBoardDef().getThickness();
                    break;
                default:
                    break;
            }


            boardFigure.willChange();
            boardFigure.setBounds(new Point2D.Double(minX, minY), new Point2D.Double(minX + length, minY + height));
            boardFigure.changed();
        }
    }


    private void changeLocation(Point2D.Double point)
    {
        CellFigure cellFigure = drawing.findCellFigure(point);
        if (cellFigure != null)
        {
            if (cellFigure.equals(boardFigure.getParent()) || boardFigure.getParent() == null)
            {
                double length = 0;
                double height = 0;
                double minX = 0;
                double minY = 0;

                switch (boardFigure.getOrientation())
                {
                    case Horizontal:
                        minX = cellFigure.getStartPoint().x;
                        minY = point.getY() - boardFigure.getBoardElement().getBoardDef().getThickness() / 2;
                        length = cellFigure.getBounds().getWidth();
                        height = boardFigure.getBoardElement().getBoardDef().getThickness();
                        break;
                    case Vertical:
                        minX = point.getX() - boardFigure.getBoardElement().getBoardDef().getThickness() / 2;
                        minY = cellFigure.getStartPoint().y;
                        height = cellFigure.getBounds().getHeight();
                        length = boardFigure.getBoardElement().getBoardDef().getThickness();
                        break;
                    default:
                        break;
                }


                boardFigure.willChange();
                boardFigure.setBounds(new Point2D.Double(minX, minY), new Point2D.Double(minX + length, minY + height));
                boardFigure.changed();
            }
        }
    }

    public void trackEnd(Point2D.Double point)
    {
        changeLocation(point);
    }

}
