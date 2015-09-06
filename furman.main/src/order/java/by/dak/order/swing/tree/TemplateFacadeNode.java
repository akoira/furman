package by.dak.order.swing.tree;

import by.dak.persistence.entities.predefined.OrderItemType;

/**
 * User: akoyro
 * Date: 11/15/13
 * Time: 5:48 AM
 */
public class TemplateFacadeNode extends AOrderNode {

	protected TemplateFacadeNode() {
		setUserObject(OrderItemType.templateFacade);
	}

	@Override
	protected void initChildren() {
	}
}
