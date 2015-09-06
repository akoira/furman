package by.dak.cutting.utils;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.order.data.Groove;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.OrderItem;
import by.dak.template.TemplateOrder;

import java.util.List;

public class ChangeGroove {

	private MainFacade mainFacade;


	public static void main(String[] args) {
		SpringConfiguration springConfiguration = new SpringConfiguration();

		ChangeGroove changeGroove = new ChangeGroove();
		changeGroove.setMainFacade(springConfiguration.getMainFacade());
		changeGroove.change();
	}

	public void change() {
		SearchFilter searchFilter = SearchFilter.instanceUnbound();
		searchFilter.isNotNull(Order.PROPERTY_orderItems + "." + OrderItem.PROPERTY_details + "." + "groove");
		List<TemplateOrder> orders = mainFacade.getTemplateOrderFacade().loadAll(searchFilter);
		for (TemplateOrder templateOrder : orders) {
			searchFilter = SearchFilter.instanceUnbound();
			searchFilter.isNotNull("groove");
			searchFilter.eq(templateOrder, OrderFurniture.PROPERTY_orderItem, OrderItem.PROPERTY_order);

			List<OrderFurniture> furnitures = mainFacade.getOrderFurnitureFacade().loadAll(searchFilter);
			for (OrderFurniture orderFurniture : furnitures) {
				Groove groove = (Groove) XstreamHelper.getInstance().fromXML(orderFurniture.getGroove());
				if (groove.isUp()) {
					groove.setDepthUp(10);
					groove.setDistanceUp(15);
				}

				if (groove.isDown()) {
					groove.setDepthDown(10);
					groove.setDistanceDown(15);
				}

				if (groove.isLeft()) {
					groove.setDepthLeft(10);
					groove.setDistanceLeft(15);
				}

				if (groove.isRight()) {
					groove.setDepthRight(10);
					groove.setDistanceRight(15);
				}
				orderFurniture.setGroove(XstreamHelper.getInstance().toXML(groove));
				mainFacade.getOrderFurnitureFacade().save(orderFurniture);
			}
		}
	}

	public MainFacade getMainFacade() {
		return mainFacade;
	}

	public void setMainFacade(MainFacade mainFacade) {
		this.mainFacade = mainFacade;
	}
}
