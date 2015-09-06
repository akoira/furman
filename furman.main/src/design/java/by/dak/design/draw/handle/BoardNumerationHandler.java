package by.dak.design.draw.handle;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardFigure;
import org.jhotdraw.draw.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 17.09.11
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
public class BoardNumerationHandler
{
    private FrontDesignerDrawing frontDesignerDrawing;

    public BoardNumerationHandler(FrontDesignerDrawing frontDesignerDrawing)
    {
        this.frontDesignerDrawing = frontDesignerDrawing;
    }

    public void initNumeration(BoardFigure boardFigure)
    {
        int numeration = calcBoardsQuantity();
        boardFigure.willChange();
        boardFigure.getBoardElement().setNumeration(numeration);
        boardFigure.changed();
    }

    /**
     * кол-во считается по всему drawing, потому что борд может быть добавлен не сразу в ячейку
     *
     * @return
     */
    private int calcBoardsQuantity()
    {
        int result = 0;
        for (Figure figure : frontDesignerDrawing.getChildren())
        {
            if (figure instanceof BoardFigure)
            {
                result++;
            }
        }
        return result;
    }

    public void recalcNumeration(BoardFigure currentBoardFigure)
    {
        List<BoardFigure> boardFigures = findBoardsExcept(currentBoardFigure);

        int currentNumeration = currentBoardFigure.getBoardElement().getNumeration();
        for (BoardFigure boardFigure : boardFigures)
        {
            if (currentNumeration <= boardFigure.getBoardElement().getNumeration())
            {
                currentNumeration = boardFigure.getBoardElement().getNumeration();
                boardFigure.willChange();
                boardFigure.getBoardElement().setNumeration(currentNumeration - 1);
                boardFigure.changed();
            }
        }
    }

    private List<BoardFigure> findBoardsExcept(BoardFigure except)
    {
        List<BoardFigure> boardFigures = new ArrayList<BoardFigure>();
        for (Figure figure : frontDesignerDrawing.getChildren())
        {
            if (figure instanceof BoardFigure && !figure.equals(except))
            {
                boardFigures.add((BoardFigure) figure);
            }
        }
        return boardFigures;
    }
}
