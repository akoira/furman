package by.dak.design.draw.components.convert;

import by.dak.design.draw.components.BoardElement;
import by.dak.persistence.entities.Board;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 22.08.11
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class DElement2BoardConverter
{
    private BoardElement boardElement;

    public DElement2BoardConverter(BoardElement boardElement)
    {
        this.boardElement = boardElement;
    }

    private Board convert()
    {
        Board board = new Board();
        board.setLength((long) boardElement.getLength());
        board.setWidth((long) boardElement.getWidth());
        board.setTexture(boardElement.getTexture());
        board.setBoardDef(boardElement.getBoardDef());

        return board;
    }
}
