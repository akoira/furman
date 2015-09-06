package by.dak.persistence.dao;

import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDao extends GenericDao<OrderItem>
{
    public List<OrderItem> findBy(AOrder order);
}
