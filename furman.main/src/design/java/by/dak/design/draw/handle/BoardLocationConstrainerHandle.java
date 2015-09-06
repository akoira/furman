package by.dak.design.draw.handle;

import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import org.jhotdraw.draw.RectangleFigure;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/12/11
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * не позволяет выходить борду за пределы ячейки
 */

public class BoardLocationConstrainerHandle extends AConstrainerHandle
{
    private CellFigure cellFigure;

    public BoardLocationConstrainerHandle(CellFigure cellFigure)
    {
        this.cellFigure = cellFigure;
    }

    public void constrainDraggedFigure(BoardFigure boardFigure)
    {
        if (cellFigure != null)
        {
            RectangleFigure constrainRectangle = findConstrainRectangle(cellFigure, boardFigure);


            double minX = constrainRectangle.getBounds().getMinX();
            double maxX = constrainRectangle.getBounds().getMaxX();
            double minY = constrainRectangle.getBounds().getMinY();
            double maxY = constrainRectangle.getBounds().getMaxY();

            double boardMinX = boardFigure.getBounds().getMinX();
            double boardMaxX = boardFigure.getBounds().getMaxX();
            double boardMinY = boardFigure.getBounds().getMinY();
            double boardMaxY = boardFigure.getBounds().getMaxY();

            switch ((boardFigure).getOrientation())
            {
                case Horizontal:
                    if (minY - boardMinY >= 0)
                    {
                        boardFigure.willChange();
                        boardFigure.setBounds(new Point2D.Double(minX, minY),
                                new Point2D.Double(minX + boardFigure.getBounds().getWidth(), minY + boardFigure.getBounds().getHeight()));
                        boardFigure.changed();
                    }
                    else if (maxY - boardMaxY <= 0)
                    {
                        boardFigure.willChange();
                        boardFigure.setBounds(new Point2D.Double(minX, maxY - boardFigure.getBounds().getHeight()),
                                new Point2D.Double(minX + boardFigure.getBounds().getWidth(), maxY));
                        boardFigure.changed();
                    }
                    else if (minX - boardMinX >= 0)
                    {
                        boardFigure.willChange();
                        boardFigure.setBounds(new Point2D.Double(minX, boardMinY),
                                new Point2D.Double(minX + boardFigure.getBounds().getWidth(), boardMinY + boardFigure.getBounds().getHeight()));
                        boardFigure.changed();
                    }
                    else if (maxX - boardMaxX <= 0)
                    {
                        boardFigure.willChange();
                        boardFigure.setBounds(new Point2D.Double(maxX - boardFigure.getBounds().getWidth(), boardMinY),
                                new Point2D.Double(maxX, boardMinY + boardFigure.getBounds().getHeight()));
                        boardFigure.changed();
                    }
                    break;
                case Vertical:
                    if (minX - boardMinX >= 0)
                    {
                        boardFigure.willChange();
                        boardFigure.setBounds(new Point2D.Double(minX, minY),
                                new Point2D.Double(minX + boardFigure.getBounds().getWidth(), minY + boardFigure.getBounds().getHeight()));
                        boardFigure.changed();
                    }
                    else if (maxX - boardMaxX <= 0)
                    {
                        boardFigure.willChange();
                        boardFigure.setBounds(new Point2D.Double(maxX - boardFigure.getBounds().getWidth(), minY),
                                new Point2D.Double(maxX, minY + boardFigure.getBounds().getHeight()));
                        boardFigure.changed();
                    }
                    else if (minY - boardMinY >= 0)
                    {
                        boardFigure.willChange();
                        boardFigure.setBounds(new Point2D.Double(boardMinX, minY),
                                new Point2D.Double(boardMinX + boardFigure.getBounds().getWidth(), minY + boardFigure.getBounds().getHeight()));
                        boardFigure.changed();
                    }
                    else if (maxY - boardMaxY <= 0)
                    {
                        boardFigure.willChange();
                        boardFigure.setBounds(new Point2D.Double(boardMinX, maxY - boardFigure.getBounds().getHeight()),
                                new Point2D.Double(boardMinX + boardFigure.getBounds().getWidth(), maxY));
                        boardFigure.changed();
                    }
                    break;
                default:
                    break;
            }
        }

    }


}
