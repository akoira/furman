package by.dak.template.facade;

import by.dak.category.Category;
import by.dak.cutting.facade.AOrderFacade;
import by.dak.cutting.facade.Callback;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderItem;
import by.dak.template.TemplateOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: akoyro
 * Date: 09.03.11
 * Time: 14:55
 */
@Transactional
public interface TemplateOrderFacade extends AOrderFacade<TemplateOrder>
{
    public void copy(Order order, TemplateOrder templateOrder, Callback<OrderItem> callback);

    public void copy(OrderItem sourceItem, OrderItem orderItem, Callback<OrderItem> callback);

    public void copy(TemplateOrder source, AOrder target, Callback<OrderItem> callback);

    public void removeFrom(Category category);

    public List<TemplateOrder> loadAllBy(Category category);
}
