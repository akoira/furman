package by.dak.design.draw.handle;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/5/11
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class CellFigureSizeHandler
{
    private CellFigure cellFigure;
    private FrontDesignerDrawing drawing;

    public CellFigureSizeHandler(CellFigure cellFigure)
    {
        this.cellFigure = cellFigure;
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

    public void trackStep(BoardFigure boardFigure)
    {
        changeLocation(boardFigure);
    }

    public void trackEnd(BoardFigure boardFigure)
    {
        changeLocation(boardFigure);
    }


    private void changeLocation(BoardFigure boardFigure)
    {
        if (cellFigure.getChildCount() > 0)
        {
            CellFigure childFigure1 = null;
            CellFigure childFigure2 = null;

            BoardSizeConstrainerHandle boardSizeConstrainerHandle = new BoardSizeConstrainerHandle(boardFigure);
            boardSizeConstrainerHandle.setDrawing(getDrawing());
            boardSizeConstrainerHandle.trackStep();


            switch (boardFigure.getOrientation())
            {
                case Vertical:
                    childFigure1 = cellFigure.findChild(CellFigure.Location.Left, cellFigure);
                    childFigure2 = cellFigure.findChild(CellFigure.Location.Right, cellFigure);
                    break;
                case Horizontal:
                    childFigure1 = cellFigure.findChild(CellFigure.Location.Top, cellFigure);
                    childFigure2 = cellFigure.findChild(CellFigure.Location.Bottom, cellFigure);
                    break;
                default:
                    break;
            }

            if (childFigure1 != null && childFigure2 != null)
            {
                Point2D.Double startPoint1 = null;
                Point2D.Double startPoint2 = null;
                Point2D.Double endPoint1 = null;
                Point2D.Double endPoint2 = null;

                switch (boardFigure.getOrientation())
                {
                    case Horizontal:
                        startPoint1 = cellFigure.getStartPoint();
                        startPoint2 = new Point2D.Double(boardFigure.getStartPoint().x,
                                boardFigure.getStartPoint().y + boardFigure.getBoardElement().getBoardDef().getThickness());

                        endPoint1 = new Point2D.Double(boardFigure.getEndPoint().x,
                                boardFigure.getStartPoint().y);
                        endPoint2 = new Point2D.Double(cellFigure.getEndPoint().x,
                                cellFigure.getEndPoint().y);
                        break;
                    case Vertical:
                        startPoint1 = cellFigure.getStartPoint();
                        startPoint2 = new Point2D.Double(boardFigure.getStartPoint().x +
                                boardFigure.getBoardElement().getBoardDef().getThickness(), boardFigure.getStartPoint().y);
                        endPoint1 = new Point2D.Double(boardFigure.getStartPoint().x,
                                boardFigure.getEndPoint().y);
                        endPoint2 = cellFigure.getEndPoint();
                        break;
                    default:
                        break;
                }

                childFigure1.willChange();
                childFigure1.setBounds(startPoint1, endPoint1);
                childFigure1.changed();

                childFigure2.willChange();
                childFigure2.setBounds(startPoint2, endPoint2);
                childFigure2.changed();
            }
        }
    }
}
