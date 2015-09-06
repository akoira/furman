package by.dak.furman.templateimport.swing;

import by.dak.furman.templateimport.swing.nodes.AValueNode;
import by.dak.furman.templateimport.swing.nodes.NodeFactory;
import by.dak.furman.templateimport.swing.nodes.RootNode;
import by.dak.furman.templateimport.values.AValue;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import javax.swing.*;
import java.util.List;

/**
 * User: akoyro
 * Date: 9/23/13
 * Time: 9:40 PM
 */
public class ANodeAction {
	private DefaultTreeTableModel model;
	private NodeFactory nodeFactory;

	public <V extends AValue> void addChildren(List<V> values,
											   final AValueNode parent) {
		for (V value : values) {
			final AValueNode<V> node = nodeFactory.buildNode(value);
			Runnable runnable = new Runnable() {
				public void run() {
					getModel().insertNodeInto(node, parent, parent.getChildCount());
				}
			};
			SwingUtilities.invokeLater(runnable);

			addChildren(value.getMessages(), node);
			node.setValid(value.getAllMessages().isEmpty());

			addChildren(value.getChildren(), node);
		}
	}

	public DefaultTreeTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTreeTableModel model) {
		this.model = model;
	}

	public NodeFactory getNodeFactory() {
		return nodeFactory;
	}

	public void setNodeFactory(NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
	}

	public RootNode getRootNode() {
		return (RootNode) getModel().getRoot();
	}
}
