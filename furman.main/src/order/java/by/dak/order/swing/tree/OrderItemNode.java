package by.dak.order.swing.tree;

import by.dak.persistence.entities.OrderItem;

/**
 * User: akoyro
 * Date: 11/15/13
 * Time: 5:55 AM
 */
public class OrderItemNode extends AOrderNode {
	private OrderItem orderItem;

	@Override
	public void init() {
		super.init();
		setUserObject(orderItem.getName() == null ? orderItem.getType() : orderItem.getName());
		setOrder(orderItem.getOrder());

	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	@Override
	protected void initChildren() {
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
}
