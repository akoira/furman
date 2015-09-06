package by.dak.cutting.facade;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.AOrderBoardDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: akoyro
 * Date: 27.09.11
 * Time: 11:49
 */
@Transactional
public interface AOrderBoardDetailFacade<E extends AOrderBoardDetail> extends AOrderDetailFacade<E>
{
    public List<TextureBoardDefPair> findUniquePairDefText(AOrder order);

    public List<E> findBy(AOrder order, TextureBoardDefPair pair);

    /**
     * возвращает кол-во деталей * кол-во orderItem
     *
     * @param order
     * @param pair
     * @return
     */
    public int getCountBy(AOrder order, TextureBoardDefPair pair);
}
