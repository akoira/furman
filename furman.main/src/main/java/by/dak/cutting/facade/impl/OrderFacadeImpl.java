package by.dak.cutting.facade.impl;

import by.dak.additional.Additional;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.OrderFacade;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.dao.OrderDao;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.utils.convert.StringValueAnnotationProcessor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderFacadeImpl extends AOrderFacadeImpl<Order> implements OrderFacade
{
    public Order copy(Order order, String suffix)
    {
        Order newOrder = Order.valueOf(order);
        newOrder.setStatus(OrderStatus.miscalculation);
        newOrder.setName(order.getName() + "-" + suffix);
        OrderItem orderItem = new OrderItem();
        orderItem.setType(OrderItemType.first);
        orderItem.setName(OrderItem.DEFAULT_NAME);
        newOrder.addOrderItem(orderItem);
        newOrder.setCreatedDailySheet(order.getCreatedDailySheet());


        OrderItem sOrderItem = FacadeContext.getOrderItemFacade().findBy(order, OrderItemType.first).get(0);

        //добавляем детали
        ArrayList<OrderFurniture> added = new ArrayList<OrderFurniture>();
        List<OrderFurniture> orderFurnitures = FacadeContext.getOrderFurnitureFacade().loadOrderedByNumber(sOrderItem);
        for (OrderFurniture orderFurniture : orderFurnitures)
        {
            if (added.contains(orderFurniture))
            {
                continue;
            }
            OrderFurniture newOrderFurniture = (OrderFurniture) orderFurniture.clone();
            orderItem.addDetail(newOrderFurniture);

            if (orderFurniture.getSecond() != null)
            {
                OrderFurniture newSecond = (OrderFurniture) orderFurniture.getSecond().clone();
                newOrderFurniture.setSecond(newSecond);
                newSecond.setSecond(newOrderFurniture);
                added.add(orderFurniture.getSecond());
                orderItem.addDetail(newSecond);
            }
        }

        List<FurnitureLink> furnitureLinks = FacadeContext.getFurnitureLinkFacade().loadAllBy(sOrderItem);
//
//        //добовляем фурнитуру
        for (FurnitureLink furnitureLink : furnitureLinks)
        {
            FurnitureLink nfl = FurnitureLink.valueOf(furnitureLink);
            orderItem.addDetail(nfl);
        }

        List<Additional> additionals = FacadeContext.getAdditionalFacade().loadAllBy(sOrderItem);
        for (Additional additional : additionals)
        {
            Additional dAdditional = Additional.valueOf(additional);
            orderItem.addDetail(dAdditional);
        }


        newOrder.setOrderNumber(getLastOrderNumber() + 1);
        save(newOrder);
        return newOrder;
    }
    /*@Override
    public Date getReadyDateByLastOrder()
    {
        return (( OrderDao ) dao).getReadyDateByLastOrder();
    }*/

    @Override
    public Order initNewOrderEntity(String namePrefix)
    {
        Order order = new Order();
        Long lastNumber = getLastOrderNumber() + 1;
        order.setOrderNumber(lastNumber);
        /* Date readyDate = getReadyDateByLastOrder();
        if (readyDate != null)
        {
            order.setReadyDate(readyDate);
        }*/
        Dailysheet dailysheet = FacadeContext.getDailysheetFacade().loadCurrentDailysheet();
        if (dailysheet == null)
        {
            throw new IllegalStateException("Current dailysheet is not set");
        }
        order.setCreatedDailySheet(dailysheet);
        order.setName(new StringBuffer(namePrefix)
                .append(" ")
                .append(parseOrderNumber(order)).toString());
        Date timestamp = dailysheet.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        order.setReadyDate(new Date(calendar.getTime().getTime()));
        return order;
    }

    public String parseOrderNumber(Order order)
    {
        return order.getNumber().getStringValue();
    }

    public Long getLastOrderNumber()
    {
        return ((OrderDao) dao).getLastOrderNumber();
    }

    @Override
    public void save(Order entity)
    {
        super.save(entity);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public List<Order> loadAllArchive()
    {
        return ((OrderDao) dao).findAllArchive();
    }

    @Override
    public Long getLastNumberBy(Long from, Long till)
    {
        return ((OrderDao) dao).getLastNumberBy(from, till);
    }

    @Override
    public List<java.util.Date> getCreatedDateBy(Customer customer)
    {
        return ((OrderDao) dao).getDateBy(customer);
    }

    @Override
    public List<Order> findAllByStatus(OrderStatus orderStatus)
    {
        return ((OrderDao) dao).findAllByStatus(orderStatus);
    }


    public static SearchFilter getNotGroupedFilter()
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(Order.PROPERTY_orderStatus, OrderStatus.production);
        filter.isNull(Order.PROPERTY_orderGroup);
        filter.addDescOrder(Order.PROPERTY_readyDate);
        return filter;
    }

    @Override
    public String getOrdersStringBy(OrderGroup orderGroup)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(Order.PROPERTY_orderGroup, orderGroup);
        filter.addAscOrder(Order.PROPERTY_readyDate);
        List<Order> list = loadAll(filter);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++)
        {
            Order order = list.get(i);

            buffer.append(StringValueAnnotationProcessor.getProcessor().convert(order));
            if (i < list.size() - 1)
            {
                buffer.append(", ");
            }
        }
        return buffer.toString();

    }
}
