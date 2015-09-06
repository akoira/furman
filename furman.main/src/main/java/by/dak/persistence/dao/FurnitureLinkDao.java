package by.dak.persistence.dao;

import by.dak.cutting.linear.FurnitureTypeCodePair;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.predefined.Unit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FurnitureLinkDao extends AOrderDetailDao<FurnitureLink>
{
    public List<FurnitureLink> findAllBy(OrderItem orderItem);

    List<Object[]> loadUniquePairsBy(OrderGroup orderGroup, Unit unit);

    Long getCountBy(OrderGroup orderGroup, FurnitureTypeCodePair pair);
}
