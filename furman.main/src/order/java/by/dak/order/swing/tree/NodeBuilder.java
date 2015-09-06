package by.dak.order.swing.tree;

import by.dak.persistence.entities.AOrder;
import org.apache.commons.beanutils.BeanUtils;
import org.jdesktop.application.ApplicationContext;

import javax.swing.*;
import java.util.Map;

public class NodeBuilder {
	private AOrder order;
	private ApplicationContext context;
    private JTree tree;

    public <T extends AOrderNode> T build(Class<T> nodeClass, Map<String, Object> properties) {
		try {
			T result = nodeClass.newInstance();
			result.setOrder(order);
			result.setContext(context);
            result.setTree(tree);
            BeanUtils.populate(result, properties);
			result.init();
			return result;
		} catch (Throwable e) {
			throw new IllegalArgumentException(e);
		}
	}

	public AOrder getOrder() {
		return order;
	}

	public void setOrder(AOrder order) {
		this.order = order;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

    public JTree getTree()
    {
        return tree;
    }

    public void setTree(JTree tree)
    {
        this.tree = tree;
    }
}
