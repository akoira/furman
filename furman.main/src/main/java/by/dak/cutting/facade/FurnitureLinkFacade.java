package by.dak.cutting.facade;

import by.dak.cutting.linear.FurnitureTypeCodePair;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.persistence.entities.predefined.Unit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FurnitureLinkFacade extends AOrderDetailFacade<FurnitureLink>
{
    public List<FurnitureLink> loadAllBy(AOrder order, List<OrderItemType> types);

    public List<FurnitureLink> loadAllBy(AOrder order, List<OrderItemType> types, String discriminator);

    public List<FurnitureLink> findAllBy(Furniture child);

    public List<FurnitureLink> loadAllBy(OrderGroup orderGroup, FurnitureTypeCodePair pair, Unit unit);

    public int getCountBy(OrderGroup orderGroup, FurnitureTypeCodePair pair);

    public List<FurnitureTypeCodePair> loadUniquePairsBy(OrderGroup orderGroup, Unit unit);
}
