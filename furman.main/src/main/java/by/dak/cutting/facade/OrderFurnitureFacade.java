package by.dak.cutting.facade;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.OrderItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrderFurnitureFacade extends AOrderBoardDetailFacade<OrderFurniture>
{
    public List<OrderFurniture> findWithCutoffBy(AOrder order);

    public List<OrderFurniture> findWithMillingBy(AOrder order);

    List<OrderFurniture> loadOrderedByNumber(AOrder order);

    List<OrderFurniture> loadOrderedByNumber(OrderItem orderItem);

    List<OrderFurniture> findOrderedComplex(AOrder order, Boolean primary);

    public List<OrderFurniture> findOrderedWithGlueing(AOrder order, Boolean primary);

    /**
     * Возвращает детали начиная с минимальными размерами в данном ордере для данного материала.
     * Используется для поиска подходящих остатков
     *
     * @param pair
     * @param order
     * @return
     */
    List<OrderFurniture> loadOrderedBySize(AOrder order, TextureBoardDefPair pair, int maxCount);

    int getCountBy(AOrder order, List<Long> boardDefIds);
}
