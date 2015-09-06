package by.dak.template.facade;

import by.dak.additional.Additional;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 16.03.11
 * Time: 16:06
 */
public class TemplateOrder2OrderItemConverter {
	private OrderItem sourceItem;
	private OrderItem orderItem;

	public void convert() {

		//добавляем board детали
		ArrayList<OrderFurniture> added = new ArrayList<>();
		List<OrderFurniture> orderFurnitures = FacadeContext.getOrderFurnitureFacade().loadOrderedByNumber(sourceItem);
		for (OrderFurniture orderFurniture : orderFurnitures) {
			if (added.contains(orderFurniture)) {
				continue;
			}
			OrderFurniture newOrderFurniture = (OrderFurniture) orderFurniture.clone();
			newOrderFurniture.setOrderItem(getOrderItem());
			FacadeContext.getOrderFurnitureFacade().save(newOrderFurniture);

			if (orderFurniture.getSecond() != null) {
				OrderFurniture newSecond = (OrderFurniture) orderFurniture.getSecond().clone();
				newOrderFurniture.setSecond(newSecond);
				newSecond.setSecond(newOrderFurniture);
				added.add(orderFurniture.getSecond());
				newSecond.setOrderItem(getOrderItem());
				FacadeContext.getOrderFurnitureFacade().save(newSecond);
				FacadeContext.getOrderFurnitureFacade().save(newOrderFurniture);
			}
		}

		//добавляем board фурнитуру
		List<FurnitureLink> furnitureLinks = FacadeContext.getFurnitureLinkFacade().loadAllBy(sourceItem);
		for (FurnitureLink furnitureLink : furnitureLinks) {
			FurnitureLink nfl = FurnitureLink.valueOf(furnitureLink);
			nfl.setOrderItem(getOrderItem());
			FacadeContext.getFurnitureLinkFacade().save(nfl);
		}

		//добавляем additional
		List<Additional> additionals = FacadeContext.getAdditionalFacade().loadAllBy(sourceItem);
		for (Additional additional : additionals) {
			Additional newAdditional = Additional.valueOf(additional);
			newAdditional.setOrderItem(getOrderItem());
			FacadeContext.getAdditionalFacade().save(newAdditional);
		}
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public OrderItem getSourceItem() {
		return sourceItem;
	}

	public void setSourceItem(OrderItem sourceItem) {
		this.sourceItem = sourceItem;
	}
}
