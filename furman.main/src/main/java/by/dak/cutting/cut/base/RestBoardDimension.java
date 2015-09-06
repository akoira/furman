package by.dak.cutting.cut.base;

import by.dak.persistence.entities.Board;

/**
 * класс нужен для того что бы создать остатки
 */
public class RestBoardDimension extends Dimension
{
    private Board board;

    public RestBoardDimension(int free, Integer width, Board board)
    {
        super(free, width);
        this.board = board;
    }

    public Board getBoard()
    {
        return board;
    }
}
