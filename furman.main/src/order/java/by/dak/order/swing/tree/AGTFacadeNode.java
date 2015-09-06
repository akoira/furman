package by.dak.order.swing.tree;

import by.dak.persistence.entities.predefined.OrderItemType;

/**
 * User: akoyro
 * Date: 15.03.11
 * Time: 15:42
 */
public class AGTFacadeNode extends AOrderNode {
	protected AGTFacadeNode() {
		setUserObject(OrderItemType.agtfacade);
		setAllowsChildren(false);
	}

	@Override
	protected void initChildren() {
	}
}
