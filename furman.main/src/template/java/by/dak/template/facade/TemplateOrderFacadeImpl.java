package by.dak.template.facade;

import by.dak.category.Category;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.Callback;
import by.dak.cutting.facade.CustomerFacade;
import by.dak.cutting.facade.DailysheetFacade;
import by.dak.cutting.facade.OrderItemFacade;
import by.dak.cutting.facade.impl.AOrderFacadeImpl;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.template.TemplateOrder;
import by.dak.utils.convert.StringValueAnnotationProcessor;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * User: akoyro
 * Date: 16.03.11
 * Time: 16:03
 */

public class TemplateOrderFacadeImpl extends AOrderFacadeImpl<TemplateOrder> implements TemplateOrderFacade
{

    private OrderItemFacade orderItemFacade;

    private String templateNumber = "";

    private String customerName = "";

    private DailysheetFacade dailysheetFacade;
    private CustomerFacade customerFacade;

    @Override
    public String parseOrderNumber(TemplateOrder order)
    {
        return order.getName();
    }

    public String getTemplateNumber()
    {
        return templateNumber;
    }

    public void setTemplateNumber(String templateNumber)
    {
        this.templateNumber = templateNumber;
    }


    @Override
    public void copy(Order order, TemplateOrder templateOrder, Callback<OrderItem> callback)
    {
        Order2TemplateOrderConverter converter = new Order2TemplateOrderConverter();
        converter.setOrder(order);
        converter.setTemplateOrder(templateOrder);
        converter.convert();
    }

    @Override
    public void copy(OrderItem sourceItem, OrderItem orderItem, Callback<OrderItem> callback)
    {
        TemplateOrder2OrderItemConverter converter = new TemplateOrder2OrderItemConverter();
        converter.setSourceItem(sourceItem);
        converter.setOrderItem(orderItem);
        converter.convert();
        if (callback != null)
            callback.callback(orderItem);
    }

    @Override
    public void copy(TemplateOrder source, AOrder target, Callback<OrderItem> callback)
    {
        List<OrderItem> orderItems = FacadeContext.getOrderItemFacade().loadBy(source);
        for (OrderItem sourceItem : orderItems)
        {
            if (sourceItem.getType() == OrderItemType.common)
            {
                OrderItem orderItem = new OrderItem();
                orderItem.setName(sourceItem.getName());
                orderItem.setType(OrderItemType.common);
                orderItem.setOrder(target);
                orderItem.setSource(sourceItem);
                orderItemFacade.save(orderItem);
                copy(sourceItem, orderItem, callback);
            }
        }

    }

    @Override
    public TemplateOrder initNewOrderEntity(String namePrefix)
    {
        TemplateOrder order = new TemplateOrder();
        order.setOrderNumber(0L);
        order.setName(namePrefix);

        OrderItem orderItem = new OrderItem();
        orderItem.setName(StringValueAnnotationProcessor.getProcessor().convert(OrderItemType.first));
        orderItem.setType(OrderItemType.first);
        order.addOrderItem(orderItem);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        order.setReadyDate(new Date(calendar.getTime().getTime()));
        order.setCreatedDailySheet(dailysheetFacade.loadCurrentDailysheet());
        order.setCustomer(customerFacade.getCurrentCustomer());
        return order;
    }

    @Override
    public void removeFrom(Category category)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(TemplateOrder.PROPERTY_category, category);

        List<TemplateOrder> list = loadAll(searchFilter);
        for (TemplateOrder templateOrder : list)
        {
            templateOrder.setCategory(null);
            save(templateOrder);
        }
    }

    @Override
    public List<TemplateOrder> loadAllBy(Category category)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(TemplateOrder.PROPERTY_category, category);
        return loadAll(searchFilter);
    }

    @Override
    public void save(TemplateOrder entity)
    {
        super.save(entity);
    }

    public void recalculate(TemplateOrder entity)
    {
        if (entity.getDialerCost() != null && entity.getSaleFactor() != null)
        {
            entity.setSalePrice(entity.getDialerCost() * entity.getSaleFactor());
        }
    }

    public DailysheetFacade getDailysheetFacade()
    {
        return dailysheetFacade;
    }

    public void setDailysheetFacade(DailysheetFacade dailysheetFacade)
    {
        this.dailysheetFacade = dailysheetFacade;
    }

    public CustomerFacade getCustomerFacade()
    {
        return customerFacade;
    }

    public void setCustomerFacade(CustomerFacade customerFacade)
    {
        this.customerFacade = customerFacade;
    }

    public OrderItemFacade getOrderItemFacade()
    {
        return orderItemFacade;
    }

    public void setOrderItemFacade(OrderItemFacade orderItemFacade)
    {
        this.orderItemFacade = orderItemFacade;
    }
}
