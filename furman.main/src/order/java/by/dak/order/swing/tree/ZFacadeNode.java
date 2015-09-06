package by.dak.order.swing.tree;

import by.dak.persistence.entities.predefined.OrderItemType;

/**
 * User: akoyro
 * Date: 16.03.11
 * Time: 12:32
 */
public class ZFacadeNode extends AOrderNode {
	protected ZFacadeNode() {
		setUserObject(OrderItemType.zfacade);
	}

	@Override
	protected void initChildren() {
	}
}
