package by.dak.template.facade;

import by.dak.additional.Additional;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.OrderItem;
import by.dak.template.TemplateOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 16.03.11
 * Time: 16:06
 */
public class Order2TemplateOrderConverter
{
    private TemplateOrder templateOrder;
    private Order order;

    public void convert()
    {
        OrderItem orderItem = templateOrder.getOrderItems().get(0);
        FacadeContext.getTemplateOrderFacade().save(templateOrder);


        //добавляем board детали
        ArrayList<OrderFurniture> added = new ArrayList<OrderFurniture>();
        List<OrderFurniture> orderFurnitures = FacadeContext.getOrderFurnitureFacade().loadOrderedByNumber(order);
        for (OrderFurniture orderFurniture : orderFurnitures)
        {
            if (added.contains(orderFurniture))
            {
                continue;
            }
            OrderFurniture newOrderFurniture = (OrderFurniture) orderFurniture.clone();
            newOrderFurniture.setOrderItem(orderItem);
            FacadeContext.getOrderFurnitureFacade().save(newOrderFurniture);

            if (orderFurniture.getSecond() != null)
            {
                OrderFurniture newSecond = (OrderFurniture) orderFurniture.getSecond().clone();
                newOrderFurniture.setSecond(newSecond);
                newSecond.setSecond(newOrderFurniture);
                added.add(orderFurniture.getSecond());
                newSecond.setOrderItem(orderItem);
                FacadeContext.getOrderFurnitureFacade().save(newSecond);
                FacadeContext.getOrderFurnitureFacade().save(newOrderFurniture);
            }
        }

        //добавляем board фурнитуру
        List<FurnitureLink> furnitureLinks = FacadeContext.getFurnitureLinkFacade().loadAllBy(order);
        for (FurnitureLink furnitureLink : furnitureLinks)
        {
            FurnitureLink nfl = FurnitureLink.valueOf(furnitureLink);
            nfl.setOrderItem(orderItem);
            FacadeContext.getFurnitureLinkFacade().save(nfl);
        }

        //добавляем additional
        List<Additional> additionals = FacadeContext.getAdditionalFacade().loadAllBy(order);
        for (Additional additional : additionals)
        {
            Additional newAdditional = Additional.valueOf(additional);
            newAdditional.setOrderItem(orderItem);
            FacadeContext.getAdditionalFacade().save(newAdditional);
        }
    }

    public TemplateOrder getTemplateOrder()
    {
        return templateOrder;
    }

    public void setTemplateOrder(TemplateOrder templateOrder)
    {
        this.templateOrder = templateOrder;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

}
