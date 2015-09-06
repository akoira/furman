package by.dak.persistence.dao;

import by.dak.persistence.FinderException;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends GenericDao<Order>
{

    public static final String PROP_ORDER_NUMBER = "orderNumber";

    Order findOrderByNumber(Long orderIdentifier) throws FinderException;

    public Long getLastOrderNumber();
//    public Date getReadyDateByLastOrder();

    List<Order> findAllArchive();

    Long getLastNumberBy(Long from, Long till);

    List<java.util.Date> getDateBy(Customer customer);

    public List<Order> findAllByStatus(OrderStatus orderStatus);
}
