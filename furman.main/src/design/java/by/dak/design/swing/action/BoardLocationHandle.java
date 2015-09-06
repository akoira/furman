package by.dak.design.swing.action;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardElement;
import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.handle.AConstrainerHandle;
import by.dak.design.draw.handle.CellFigureSizeHandler;
import org.jhotdraw.draw.RectangleFigure;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 08.09.11
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
public class BoardLocationHandle extends AConstrainerHandle
{
    private BoardFigure boardFigure;
    private BoardElement.Location location;
    private FrontDesignerDrawing drawing;

    public BoardLocationHandle(BoardFigure boardFigure, BoardElement.Location location, FrontDesignerDrawing drawing)
    {
        this.boardFigure = boardFigure;
        this.location = location;
        this.drawing = drawing;
    }

    public void magnetize()
    {
        RectangleFigure constrainedRectangle = findConstrainRectangle(boardFigure.getParent(), boardFigure);
        Point2D.Double startPoint = null;
        Point2D.Double endPoint = null;

        switch (location)
        {
            case top:
                if (boardFigure.getOrientation().equals(BoardFigure.Orientation.Horizontal))
                {
                    startPoint = constrainedRectangle.getStartPoint();
                    endPoint = new Point2D.Double(startPoint.x + boardFigure.getBoardElement().getLength(),
                            startPoint.y + boardFigure.getBoardElement().getBoardDef().getThickness());
                }
                break;
            case bottom:
                if (boardFigure.getOrientation().equals(BoardFigure.Orientation.Horizontal))
                {
                    startPoint = new Point2D.Double(constrainedRectangle.getStartPoint().x,
                            constrainedRectangle.getEndPoint().y - boardFigure.getBoardElement().getBoardDef().getThickness());
                    endPoint = new Point2D.Double(startPoint.x + boardFigure.getBoardElement().getLength(),
                            constrainedRectangle.getEndPoint().y);
                }
                break;
            case left:
                if (boardFigure.getOrientation().equals(BoardFigure.Orientation.Vertical))
                {
                    startPoint = constrainedRectangle.getStartPoint();
                    endPoint = new Point2D.Double(startPoint.x + boardFigure.getBoardElement().getBoardDef().getThickness(),
                            startPoint.y + boardFigure.getBoardElement().getLength());
                }
                break;
            case right:
                if (boardFigure.getOrientation().equals(BoardFigure.Orientation.Vertical))
                {
                    startPoint = new Point2D.Double(constrainedRectangle.getEndPoint().x -
                            boardFigure.getBoardElement().getBoardDef().getThickness(),
                            constrainedRectangle.getStartPoint().y);
                    endPoint = new Point2D.Double(constrainedRectangle.getEndPoint().x,
                            constrainedRectangle.getEndPoint().y);
                }
                break;
        }
        if (startPoint != null)
        {
            boardFigure.willChange();
            boardFigure.setBounds(startPoint, endPoint);
            boardFigure.changed();
            CellFigureSizeHandler cellFigureSizeHandler = new CellFigureSizeHandler(boardFigure.getParent());
            cellFigureSizeHandler.setDrawing(drawing);
            cellFigureSizeHandler.trackEnd(boardFigure);
        }
    }
}
