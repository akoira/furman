package by.dak.order.swing;

import by.dak.cutting.swing.DPanel;
import by.dak.cutting.swing.order.OrderDetailsTab;
import by.dak.cutting.swing.order.OrderItemTabFactory;
import by.dak.cutting.swing.order.wizard.ClearNextStepObserver;
import by.dak.order.swing.tree.AOrderNode;
import by.dak.order.swing.tree.RootNode;
import by.dak.persistence.entities.AOrder;
import by.dak.swing.explorer.AExplorerPanel;
import by.dak.utils.StateUtils;
import org.jdesktop.application.Application;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: akoyro
 * Date: 10.03.11
 * Time: 16:33
 */
public class OrderItemsExplorer extends AExplorerPanel implements ClearNextStepObserver {
	private AOrder order;

	private TreeModelListener treeModelListener = new TreeModelListener() {
		@Override
		public void treeNodesChanged(TreeModelEvent e) {
			firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, ClearNextStepObserver.PROPERTY_clearNextStep);
		}

		@Override
		public void treeNodesInserted(TreeModelEvent e) {
			firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, ClearNextStepObserver.PROPERTY_clearNextStep);
		}

		@Override
		public void treeNodesRemoved(TreeModelEvent e) {
			firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, ClearNextStepObserver.PROPERTY_clearNextStep);
		}

		@Override
		public void treeStructureChanged(TreeModelEvent e) {
			firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, ClearNextStepObserver.PROPERTY_clearNextStep);
		}
	};


	private PropertyChangeListener clearNextStepListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, ClearNextStepObserver.PROPERTY_clearNextStep);
		}
	};

	@Override
	protected void treeSelectedChanged(TreePath treePath) {
		Object object = treePath.getLastPathComponent();
		if (object instanceof AOrderNode) {
			JComponent component = OrderItemTabFactory.ORDER_ITEM_TAB_FACTORY.getComponentBy((AOrderNode) object);
			if (component instanceof DPanel) {
				((DPanel) component).setEditable(isEditable());
			}
			if (component != null) {
				setShowData(true);
				getActionsPanel().setContentComponent(component);
				getActionsPanel().init();
				getActionsPanel().setVisible(true);
			}
			if (component instanceof ClearNextStepObserver) {
				((ClearNextStepObserver) component).addClearNextStepListener(clearNextStepListener);
			}
		}
	}

	@Override
	protected void initEnvironment() {
		super.initEnvironment();
		addPropertyChangeListener("order", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				orderChanged();
			}
		});
	}

	@Override
	protected void loadSessionState() {
		if (getActionsPanel().getContentComponent() != null) {
			StateUtils.loadSessionState(getActionsPanel().getContentComponent(), getActionsPanel().getContentComponent().getName());
		}
	}

	@Override
	protected void saveSessionState() {
		if (getActionsPanel().getContentComponent() != null) {
			StateUtils.saveSessionState(getActionsPanel().getContentComponent(), getActionsPanel().getContentComponent().getName());
		}
	}

	private void orderChanged() {
		final RootNode rootNode = new RootNode();
		rootNode.setOrder(order);
		rootNode.setContext(Application.getInstance().getContext());
		rootNode.setTree(getTreePanel().getTree());
        rootNode.init();

        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(treeModelListener);
		getTreePanel().getTree().setModel(treeModel);
		Runnable runnable = new Runnable() {
			public void run() {
				TreePath treePath = hasFirstNode(rootNode);
				getTreePanel().getTree().getSelectionModel().setSelectionPath(treePath);
			}
		};
		SwingUtilities.invokeLater(runnable);
	}

	private TreePath hasFirstNode(RootNode rootNode) {
		if (rootNode.getChildCount() > 0) {
			return new TreePath(new Object[]{rootNode, rootNode.getChildAt(0)});
		}
		return null;
	}

	public AOrder getOrder() {
		return order;
	}

	public void setOrder(AOrder order) {
		AOrder old = this.order;
		this.order = order;
		firePropertyChange("order", old, order);
	}

	public boolean validateGui() {
		return validateCurrentContent();
	}

	public void save() {
		if (getActionsPanel().getContentComponent() instanceof OrderDetailsTab) {
			((OrderDetailsTab) getActionsPanel().getContentComponent()).getOrderDetailsControl().saveAll();
		}
	}

	@Override
	protected boolean validateCurrentContent() {
		if (getActionsPanel().getContentComponent() instanceof OrderDetailsTab) {
			return ((OrderDetailsTab) getActionsPanel().getContentComponent()).validateGUI();
		}
		return true;
	}

	@Override
	public void addClearNextStepListener(PropertyChangeListener listener) {
		addPropertyChangeListener(ClearNextStepObserver.PROPERTY_clearNextStep, listener);
	}

	@Override
	public void removeClearNextStepListener(PropertyChangeListener listener) {
		removePropertyChangeListener(ClearNextStepObserver.PROPERTY_clearNextStep, listener);
	}

}
