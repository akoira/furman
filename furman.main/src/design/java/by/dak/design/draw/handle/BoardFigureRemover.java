package by.dak.design.draw.handle;

import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jhotdraw.draw.Figure;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 19.09.11
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
public class BoardFigureRemover
{
    private BoardFigure boardFigure;
    private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(getClass());

    public BoardFigureRemover(BoardFigure boardFigure)
    {
        this.boardFigure = boardFigure;
    }

    public boolean remove()
    {
        CellFigure cellFigure = boardFigure.getParent();

        if (isBoardRemovable(boardFigure))
        {
            if (cellFigure != null)
            {
                cellFigure.setBoardFigure(null);
                cellFigure.removeAllChildren();
            }
            return true;
        }
        else
        {
            List<BoardFigure> removableBoardFigures = selectRemovableBoardFigures();
            StringBuilder stringBuilder = new StringBuilder();

            for (int count = 1; count <= removableBoardFigures.size(); count++)
            {
                if (count != removableBoardFigures.size())
                {
                    stringBuilder.append(removableBoardFigures.get(count - 1).getBoardElement().getNumeration()).append(",");
                }
                else
                {
                    stringBuilder.append(removableBoardFigures.get(count - 1).getBoardElement().getNumeration());
                }
            }

            JOptionPane.showMessageDialog(null, resourceMap.getString("remove.text") + stringBuilder.toString(),
                    resourceMap.getString("remove.title"), JOptionPane.INFORMATION_MESSAGE);


            return false;
        }
    }

    private CellFigure findTopCellFgiure(CellFigure currentCellFigure)
    {
        if (currentCellFigure.getParent() != null)
        {
            return findTopCellFgiure(currentCellFigure.getParent());
        }
        return currentCellFigure;
    }

    private boolean isBoardRemovable(BoardFigure boardF)
    {
        CellFigure cellFigure = boardF.getParent();
        if (cellFigure != null)
        {
            List<Figure> figures = cellFigure.getChildren();
            for (Figure f : figures)
            {
                if (f instanceof CellFigure)
                {
                    if (((CellFigure) f).getChildCount() > 0)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private List<BoardFigure> selectRemovableBoardFigures()
    {
        List<BoardFigure> boardsToRemove = new ArrayList<BoardFigure>();
        List<BoardFigure> boards = new ArrayList<BoardFigure>();

        loadBoardsFromCell(findTopCellFgiure(boardFigure.getParent()), boards);
        for (BoardFigure boardFigure : boards)
        {
            if (isBoardRemovable(boardFigure))
            {
                boardsToRemove.add(boardFigure);
            }
        }

        return boardsToRemove;
    }

    private void loadBoardsFromCell(CellFigure cellFigure, List<BoardFigure> boardFigures)
    {
        if (cellFigure.getBoardFigure() != null)
        {
            boardFigures.add(cellFigure.getBoardFigure());
        }
        for (Figure figure : cellFigure.getChildren())
        {
            loadBoardsFromCell((CellFigure) figure, boardFigures);
        }
    }
}
