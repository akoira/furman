package by.dak.design.draw;

import by.dak.design.draw.components.BoardElement;
import by.dak.design.draw.components.BoardFigure;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;

import java.util.HashMap;
import java.util.Map;

/**
 * User: akoyro
 * Date: 16.09.11
 * Time: 15:07
 */
public class BoardElementFacade
{
    public static final double DEFAULT_length = 100;
    public static final double DEFAULT_width = 100;

    private final static BoardElementFacade INSTANCE = new BoardElementFacade();

    private Map<BoardElement, BoardElementBinder> boardElementBinders = new HashMap<BoardElement, BoardElementBinder>();


    public static BoardElementFacade getInstance()
    {
        return INSTANCE;
    }

    public BoardElement createBoardElement()
    {
        BoardElement element = new BoardElement();
        BoardDef defaultBoardDef = FacadeContext.getBoardDefFacade().findDefaultBoardDef();
        element.setBoardDef(defaultBoardDef);
        element.setLength(DEFAULT_length);
        element.setWidth(DEFAULT_width);
        return element;
    }

    public BoardElementBinder bind(BoardElement boardElement, BoardFigure boardFigure)
    {
        BoardElementBinder boardElementBinder = new BoardElementBinder();
        boardElementBinder.setBoardElement(boardElement);
        boardElementBinder.setBoardFigure(boardFigure);
        boardElementBinder.bind();
        boardElementBinders.put(boardElement, boardElementBinder);
        return boardElementBinder;
    }


    public BoardElementBinder unbind(BoardElement boardElement)
    {
        BoardElementBinder boardElementBinder = boardElementBinders.get(boardElement);
        boardElementBinder.unbind();
        return boardElementBinder;
    }
}
