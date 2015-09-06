package by.dak.buffer.dao;

import by.dak.buffer.entity.DilerOrder;
import by.dak.persistence.dao.GenericDao;
import by.dak.persistence.entities.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DilerOrderDao extends GenericDao<DilerOrder>
{
    public List<DilerOrder> getDilerOrderBy(Customer customer);
}
