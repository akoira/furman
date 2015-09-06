package by.dak.order.swing.tree;

import by.dak.persistence.entities.AOrder;
import by.dak.swing.tree.ATreeNode;

/**
 * User: akoyro
 * Date: 16.03.11
 * Time: 12:49
 */
public abstract class AOrderNode extends ATreeNode {
	private AOrder order;

	public AOrder getOrder() {
		return order;
	}

	public void setOrder(AOrder order) {
		this.order = order;
	}
}
