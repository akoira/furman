package by.dak.cutting.facade.impl.helper;

import by.dak.cutting.facade.BoardFacade;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.predefined.StoreElementStatus;

import java.util.List;

/**
 * Ищет целые листы на складе и добавляет их вместо ordered
 */
public class BoardOrderStatusRefactorer
{
    private Order order;
    private BoardFacade boardFacade;

    public BoardOrderStatusRefactorer(Order order, BoardFacade boardFacade)
    {
        this.order = order;
        this.boardFacade = boardFacade;
    }

    public void refactor()
    {
        List<Board> boards = boardFacade.findAllBy(order, StoreElementStatus.order);
        for (Board board : boards)
        {
            Board template = new Board();
            template.setAmount(null);
            template.setPrice(null);
            template.setPair(board.getPair());
            template.setStatus(StoreElementStatus.exist);
            template.setLength(board.getLength());
            template.setWidth(board.getWidth());
            List<Board> boardsR = boardFacade.findAllBy(template);
            for (Board boardR : boardsR)
            {
                refactorBoard(board, boardR);
                if (board.getStatus() == StoreElementStatus.used)
                    break;
            }
        }
    }

    private void refactorBoard(Board board, Board boardR)
    {
        int diff = boardR.getAmount() - board.getAmount();
        if (diff > 0)
        {
            boardR.setAmount(diff);
            boardFacade.save(boardR);
            refactorBoard(board, boardR, board.getAmount());
            if (diff == 0)
            {
                boardR.setStatus(StoreElementStatus.used);
                FacadeContext.getBoardFacade().save(boardR);
            }
        }
        else
        {
            boardR.setStatus(StoreElementStatus.used);
            FacadeContext.getBoardFacade().save(boardR);
            Board boardC = board.clone();
            refactorBoard(boardC, boardR, boardR.getAmount());
            board.setAmount(Math.abs(diff));
            FacadeContext.getBoardFacade().save(board);
        }
    }


    private void refactorBoard(Board board, Board boardR, int count)
    {
        board.setProvider(boardR.getProvider());
        board.setPrice(boardR.getPrice());
        board.setDelivery(boardR.getDelivery());
        board.setStatus(StoreElementStatus.used);
        board.setAmount(count);
        boardFacade.save(board);
    }


}
