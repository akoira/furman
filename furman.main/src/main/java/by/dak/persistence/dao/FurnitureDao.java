package by.dak.persistence.dao;

import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FurnitureDao extends AStoreElementDao<Furniture>
{
    public List<Furniture> loadAllByOrder(Order order);
}
