package by.dak.design.draw.handle;

import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.RectangleFigure;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/13/11
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AConstrainerHandle
{
    /**
     * собирает область ограниченную чайлдами
     *
     * @param cellFigure
     * @param boardFigure
     * @return
     */
    protected RectangleFigure findConstrainRectangle(CellFigure cellFigure, BoardFigure boardFigure)
    {
        CellFigure child1 = null;
        CellFigure child2 = null;

        Point2D.Double startPoint = null;
        Point2D.Double endPoint = null;

        switch (boardFigure.getOrientation())
        {
            case Vertical:
                child1 = cellFigure.findChild(CellFigure.Location.Left, cellFigure);
                child2 = cellFigure.findChild(CellFigure.Location.Right, cellFigure);

                startPoint = findLeftConstrain(child1);
                endPoint = findRightConstrain(child2);
                break;
            case Horizontal:
                child1 = cellFigure.findChild(CellFigure.Location.Top, cellFigure);
                child2 = cellFigure.findChild(CellFigure.Location.Bottom, cellFigure);
                startPoint = findTopConstrain(child1);
                endPoint = findBottomConstrain(child2);
                break;
            default:
                break;
        }

        if (startPoint == null)
        {
            startPoint = cellFigure.getStartPoint();
        }

        if (endPoint == null)
        {
            endPoint = cellFigure.getEndPoint();
        }

        RectangleFigure rectangleFigure = new RectangleFigure();
        rectangleFigure.setBounds(startPoint, endPoint);

        return rectangleFigure;
    }

    private Point2D.Double findRightConstrain(CellFigure cellFigure)
    {
        List<BoardFigure> boardFigures = new ArrayList<BoardFigure>();
        findVerticalBoards(boardFigures, cellFigure);

        if (boardFigures.size() == 0)
        {
            return null;
        }
        else
        {
            BoardFigure minBoard = boardFigures.get(0);
            for (int count = 0; count < boardFigures.size(); count++)
            {
                if (boardFigures.get(count).getBounds().getMinX() < minBoard.getBounds().getMinX())
                {
                    minBoard = boardFigures.get(count);
                }
            }
            return new Point2D.Double(minBoard.getBounds().getMinX(), cellFigure.getBounds().getMaxY());
        }

    }

    private Point2D.Double findLeftConstrain(CellFigure cellFigure)
    {
        List<BoardFigure> boardFigures = new ArrayList<BoardFigure>();
        findVerticalBoards(boardFigures, cellFigure);

        if (boardFigures.size() == 0)
        {
            return null;
        }
        else
        {
            BoardFigure maxBoard = boardFigures.get(0);
            for (int count = 0; count < boardFigures.size(); count++)
            {
                if (boardFigures.get(count).getBounds().getMaxX() > maxBoard.getBounds().getMaxX())
                {
                    maxBoard = boardFigures.get(count);
                }
            }
            return new Point2D.Double(maxBoard.getBounds().getMaxX(), cellFigure.getBounds().getMinY());
        }

    }

    private Point2D.Double findBottomConstrain(CellFigure cellFigure)
    {
        List<BoardFigure> boardFigures = new ArrayList<BoardFigure>();
        findHorizontalBoards(boardFigures, cellFigure);

        if (boardFigures.size() == 0)
        {
            return null;
        }
        else
        {
            BoardFigure minBoard = boardFigures.get(0);
            for (int count = 0; count < boardFigures.size(); count++)
            {
                if (boardFigures.get(count).getBounds().getMinY() < minBoard.getBounds().getMinY())
                {
                    minBoard = boardFigures.get(count);
                }
            }
            return new Point2D.Double(cellFigure.getBounds().getMaxX(), minBoard.getBounds().getMinY());
        }

    }

    private Point2D.Double findTopConstrain(CellFigure cellFigure)
    {
        List<BoardFigure> boardFigures = new ArrayList<BoardFigure>();
        findHorizontalBoards(boardFigures, cellFigure);

        if (boardFigures.size() == 0)
        {
            return null;
        }
        else
        {
            BoardFigure maxBoard = boardFigures.get(0);
            for (int count = 0; count < boardFigures.size(); count++)
            {
                if (boardFigures.get(count).getBounds().getMaxY() > maxBoard.getBounds().getMaxY())
                {
                    maxBoard = boardFigures.get(count);
                }
            }
            return new Point2D.Double(cellFigure.getBounds().getMinX(), maxBoard.getBounds().getMaxY());
        }

    }

    private void findVerticalBoards(List<BoardFigure> boardFigures, CellFigure cellFigure)
    {
        BoardFigure boardFigure = cellFigure.getBoardFigure();
        if (boardFigure != null)
        {
            if (boardFigure.getOrientation() == BoardFigure.Orientation.Vertical)
            {
                boardFigures.add(boardFigure);
            }

            for (Figure figure : cellFigure.getChildren())
            {
                if (figure instanceof CellFigure)
                {
                    findVerticalBoards(boardFigures, (CellFigure) figure);
                }

            }
        }
    }

    private void findHorizontalBoards(List<BoardFigure> boardFigures, CellFigure cellFigure)
    {
        BoardFigure boardFigure = cellFigure.getBoardFigure();
        if (boardFigure != null)
        {
            if (boardFigure.getOrientation() == BoardFigure.Orientation.Horizontal)
            {
                boardFigures.add(boardFigure);
            }

            for (Figure figure : cellFigure.getChildren())
            {
                if (figure instanceof CellFigure)
                {
                    findHorizontalBoards(boardFigures, (CellFigure) figure);
                }

            }
        }
    }
}
