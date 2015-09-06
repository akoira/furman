package by.dak.order.swing.tree;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.template.TemplateOrder;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.List;

/**
 * User: akoyro
 * Date: 15.03.11
 * Time: 14:25
 */
public class RootNode extends AOrderNode {
	private NodeBuilder nodeBuilder;

	@Override
	public void init() {
		super.init();
		nodeBuilder = new NodeBuilder();
		nodeBuilder.setContext(getContext());
		nodeBuilder.setOrder(getOrder());
        nodeBuilder.setTree(getTree());
    }

	@Override
	protected void initChildren() {
		List<OrderItem> orderItems = FacadeContext.getOrderItemFacade().findBy(getOrder(), OrderItemType.first);
		for (OrderItem orderItem : orderItems) {
			AOrderNode node = nodeBuilder.build(OrderItemNode.class, MapUtils.putAll(new HashMap<String, Object>(), new Object[]{"orderItem", orderItem}));
			add(node);
		}
		orderItems = FacadeContext.getOrderItemFacade().findBy(getOrder(), OrderItemType.common);
		for (OrderItem orderItem : orderItems) {
			if (orderItem.getSource() == null) {
				AOrderNode node = nodeBuilder.build(OrderItemNode.class, MapUtils.putAll(new HashMap<String, Object>(), new Object[]{"orderItem", orderItem}));
				add(node);
			}
		}


		if (getOrder() instanceof Order) {
			add(nodeBuilder.build(TemplatesNode.class, MapUtils.putAll(new HashMap<String, Object>(), new Object[]{"nodeBuilder", nodeBuilder})));
			add(nodeBuilder.build(AGTFacadeNode.class, MapUtils.EMPTY_MAP));
			add(nodeBuilder.build(ZFacadeNode.class, MapUtils.EMPTY_MAP));
		} else if (getOrder() instanceof TemplateOrder) {
			add(nodeBuilder.build(TemplateFacadeNode.class, MapUtils.EMPTY_MAP));
		}
	}

	public NodeBuilder getNodeBuilder() {
		return nodeBuilder;
	}

	public void setNodeBuilder(NodeBuilder nodeBuilder) {
		this.nodeBuilder = nodeBuilder;
	}
}

