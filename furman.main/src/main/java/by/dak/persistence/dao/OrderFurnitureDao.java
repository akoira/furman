package by.dak.persistence.dao;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.TextureEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderFurnitureDao extends AOrderBoardDetailDao<OrderFurniture>
{
    void deleteSecond(OrderFurniture first);

    public List<OrderFurniture> findBy(AOrder order, TextureEntity texture, BoardDef boardDef);

    public List<OrderFurniture> findWithCutoffBy(AOrder order);

    public List<OrderFurniture> findWithMillingBy(AOrder order);

    List<OrderFurniture> findOrderedWithGlueing(AOrder order, Boolean primary);

    List<OrderFurniture> findOrderedComplex(AOrder order, Boolean primary);

    List<OrderFurniture> findOrderedBySize(AOrder order, TextureBoardDefPair pair, int maxCount);

    public Long getCountBy(AOrder order, TextureBoardDefPair pair);


}
