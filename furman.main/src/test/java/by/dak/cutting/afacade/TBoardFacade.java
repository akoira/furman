package by.dak.cutting.afacade;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.predefined.StoreElementStatus;

/**
 * User: akoyro
 * Date: 22.08.2009
 * Time: 19:51:30
 */
public class TBoardFacade
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();

        Order order = FacadeContext.getOrderFacade().loadAll().get(0);

        Board board = new Board();
        board.setOrder(order);
        board.setStatus(StoreElementStatus.order);
        board.setLength(10L);
        board.setWidth(10l);
        board.setBoardDef(FacadeContext.getBoardDefFacade().loadAll().get(0));
        board.setTexture(FacadeContext.getTextureFacade().loadAll().get(0));
        FacadeContext.getBoardFacade().save(board);

        order = FacadeContext.getOrderFacade().loadAll().get(0);
        board = new Board();
        board.setOrder(order);
        board.setStatus(StoreElementStatus.order);
        board.setLength(10L);
        board.setWidth(10l);
        board.setBoardDef(FacadeContext.getBoardDefFacade().loadAll().get(0));
        board.setTexture(FacadeContext.getTextureFacade().loadAll().get(0));
        FacadeContext.getBoardFacade().save(board);

    }
}
