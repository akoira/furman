package by.dak.persistence.dao.impl;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.facade.FurnitureLinkFacade;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderItem;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.03.2010
 * Time: 11:32:26
 * To change this template use File | Settings | File Templates.
 */
public class TFurnitureLinkFacade
{
    public static void main(String[] args)
    {
        new SpringConfiguration();

        FurnitureLinkFacade facade = FacadeContext.getFurnitureLinkFacade();

        FurnitureLink linkFurniture = new FurnitureLink();
        linkFurniture.setSize(100d);
        linkFurniture.setFurnitureCode(FacadeContext.getFurnitureCodeFacade().loadAll().get(0));
        linkFurniture.setFurnitureType(FacadeContext.getFurnitureTypeFacade().loadAll().get(0));
        Order order = FacadeContext.getOrderFacade().loadAll().get(0);
        List<OrderItem> items = FacadeContext.getOrderItemFacade().loadBy(order);
        linkFurniture.setOrderItem(items.get(0));
        facade.save(linkFurniture);

        FurnitureLink linkFurniture2 = FacadeContext.getFurnitureLinkFacade().loadAll().get(0);
        System.out.println(linkFurniture2.getSize());
        System.out.println(linkFurniture2.getFurnitureCode().getName());
        System.out.println(linkFurniture2.getFurnitureType().getName());
        System.out.println(linkFurniture2.getOrderItem().getName());
    }
}
