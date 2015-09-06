package by.dak.cutting.facade;

import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface OrderFacade extends AOrderFacade<Order>
{

    public Order copy(Order order, String suffix);

    public Order initNewOrderEntity(String namePrefix);

    public Long getLastOrderNumber();
//    public Date getReadyDateByLastOrder();

    List<Order> loadAllArchive();

    Long getLastNumberBy(Long from, Long till);

    List<Date> getCreatedDateBy(Customer customer);

    /**
     * Общая сумма заказов
     */
    //double getCommonCost(SearchFilter filter);
    public List<Order> findAllByStatus(OrderStatus orderStatus);

    String getOrdersStringBy(OrderGroup orderGroup);
}
