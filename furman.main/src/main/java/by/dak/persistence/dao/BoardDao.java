package by.dak.persistence.dao;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Denis Koyro
 * @version 0.1 07.12.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
@Repository
public interface BoardDao extends AStoreElementDao<Board>
{

    List<Board> findMinFreeRestBoardsBy(Dimension furnitureSize, TextureBoardDefPair pair, int maxCount);

    void deleteOrderBoards(Order order);
}
