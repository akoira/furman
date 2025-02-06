package by.dak.persistence.dao;

import by.dak.persistence.entities.ServiceLink;
import by.dak.persistence.entities.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceLinkDao extends AOrderDetailDao<ServiceLink>
{
    public List<ServiceLink> findAllBy(OrderItem orderItem);
}
