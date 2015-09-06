package by.dak.design.swing.action;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import org.jhotdraw.draw.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 17.09.11
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class BoardNumerationVisibleHandle
{
    private FrontDesignerDrawing frontDesignerDrawing;

    public BoardNumerationVisibleHandle(FrontDesignerDrawing frontDesignerDrawing)
    {
        this.frontDesignerDrawing = frontDesignerDrawing;
    }

    public void setVisible(boolean visible)
    {
        List<BoardFigure> boardFigures = new ArrayList<BoardFigure>();
        findBoards(frontDesignerDrawing.getTopFigure(), boardFigures);
        for (BoardFigure boardFigure : boardFigures)
        {
            boardFigure.willChange();
            boardFigure.getNumerationTip().setVisible(visible);
            boardFigure.changed();
        }
    }

    private void findBoards(CellFigure topCellFigure, List<BoardFigure> boardFigures)
    {
        BoardFigure frontBoard = (topCellFigure).getBoardFigure();
        if (frontBoard != null)
        {
            boardFigures.add(frontBoard);
            for (Figure child : topCellFigure.getChildren())
            {
                findBoards((CellFigure) child, boardFigures);
            }
        }
    }
}
