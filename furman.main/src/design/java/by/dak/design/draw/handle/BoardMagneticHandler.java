package by.dak.design.draw.handle;

import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import org.jhotdraw.draw.RectangleFigure;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/13/11
 * Time: 1:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class BoardMagneticHandler extends AConstrainerHandle
{
    public static double SIZE_TO_MAGNETIZE = 18;

    private CellFigure currentCell;
    private BoardFigure boardToDrop;
    private CellFigure topCell;

    public BoardMagneticHandler(CellFigure currentCell, BoardFigure boardToDrop, CellFigure topCell)
    {
        this.currentCell = currentCell;
        this.boardToDrop = boardToDrop;
        this.topCell = topCell;
    }

    public void magnetize()
    {
        RectangleFigure rectangleFigure = findConstrainRectangle(currentCell, boardToDrop);

        switch (boardToDrop.getOrientation())
        {
            case Horizontal:
                magnetizeHorizontal(rectangleFigure);
                break;
            case Vertical:
                magnetizeVertical(rectangleFigure);
                break;
            default:
                break;
        }

    }

    private boolean isMagnetizeToTopCell()
    {
        double minX = topCell.getBounds().getMinX();
        double maxX = topCell.getBounds().getMaxX();
        double minY = topCell.getBounds().getMinY();
        double maxY = topCell.getBounds().getMaxY();

        double boardMinX = boardToDrop.getBounds().getMinX();
        double boardMaxX = boardToDrop.getBounds().getMaxX();
        double boardMinY = boardToDrop.getBounds().getMinY();
        double boardMaxY = boardToDrop.getBounds().getMaxY();


        if (minX == boardMinX && minY == boardMinY && maxY == boardMaxY)
        {
            return true;
        }
        if (maxX == boardMaxX && maxY == boardMaxY && minY == boardMinY)
        {
            return true;
        }
        if (minX == boardMinX && minY == boardMinY && maxX == boardMaxX)
        {
            return true;
        }
        if (minX == boardMinX && maxY == boardMaxY && maxX == boardMaxX)
        {
            return true;
        }

        return false;
    }

    private void magnetizeVertical(RectangleFigure constrainerRect)
    {
        Point2D.Double boardLeftStartPoint = boardToDrop.getStartPoint();
        Point2D.Double constrainLeftStartPoint = constrainerRect.getStartPoint();

        double distanceLeft = boardLeftStartPoint.distance(constrainLeftStartPoint);

        if (distanceLeft <= SIZE_TO_MAGNETIZE)
        {
            boardToDrop.setBounds(constrainLeftStartPoint,
                    new Point2D.Double(constrainLeftStartPoint.x + boardToDrop.getBoardElement().getBoardDef().getThickness(),
                            boardLeftStartPoint.y + boardToDrop.getBoardElement().getLength()));
//            if (isMagnetizeToTopCell())
//            {
//                boardToDrop.getBoardElement().setLocation(BoardElement.Location.left);
//            }
        }

        Point2D.Double boardRightStartPoint = new Point2D.Double(boardToDrop.getBounds().getMaxX(),
                boardToDrop.getBounds().getMinY());
        Point2D.Double constrainRightStartPoint = new Point2D.Double(constrainerRect.getBounds().getMaxX(),
                constrainerRect.getBounds().getMinY());

        double distanceRight = boardRightStartPoint.distance(constrainRightStartPoint);
        if (distanceRight <= SIZE_TO_MAGNETIZE)
        {
            boardToDrop.setBounds(new Point2D.Double(constrainRightStartPoint.x - boardToDrop.getBoardElement().getBoardDef().getThickness(),
                    constrainRightStartPoint.y), new Point2D.Double(constrainRightStartPoint.x,
                    constrainRightStartPoint.y + boardToDrop.getBoardElement().getLength()));
//            if (isMagnetizeToTopCell())
//            {
//                boardToDrop.getBoardElement().setLocation(BoardElement.Location.right);
//            }
        }


    }

    private void magnetizeHorizontal(RectangleFigure constrainerRect)
    {
        Point2D.Double boardTopStartPoint = boardToDrop.getStartPoint();
        Point2D.Double constrainTopStartPoint = constrainerRect.getStartPoint();

        double distanceTop = boardTopStartPoint.distance(constrainTopStartPoint);
        if (distanceTop <= SIZE_TO_MAGNETIZE)
        {
            boardToDrop.setBounds(constrainTopStartPoint,
                    new Point2D.Double(constrainTopStartPoint.x + boardToDrop.getBoardElement().getLength(),
                            constrainTopStartPoint.y + boardToDrop.getBoardElement().getBoardDef().getThickness()));
//            if (isMagnetizeToTopCell())
//            {
//                boardToDrop.getBoardElement().setLocation(BoardElement.Location.top);
//            }

        }

        Point2D.Double boardBottomStartPoint = new Point2D.Double(boardToDrop.getBounds().getMinX(),
                boardToDrop.getBounds().getMaxY());
        Point2D.Double constrainBottomStartPoint = new Point2D.Double(constrainerRect.getBounds().getMinX(),
                constrainerRect.getBounds().getMaxY());

        double distanceBottom = boardBottomStartPoint.distance(constrainBottomStartPoint);
        if (distanceBottom <= SIZE_TO_MAGNETIZE)
        {
            boardToDrop.setBounds(constrainBottomStartPoint,
                    new Point2D.Double(constrainBottomStartPoint.x + boardToDrop.getBoardElement().getLength(),
                            constrainBottomStartPoint.y - boardToDrop.getBoardElement().getBoardDef().getThickness()));
//            if (isMagnetizeToTopCell())
//            {
//                boardToDrop.getBoardElement().setLocation(BoardElement.Location.bottom);
//            }
        }
    }
}
