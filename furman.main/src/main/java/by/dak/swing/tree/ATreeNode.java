package by.dak.swing.tree;

import com.jidesoft.plaf.basic.LazyMutableTreeNode;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 26.11.2009
 * Time: 20:18:56
 */

@Deprecated //need to be adjusted to exclude to use static variables (resourceMap and actionMap)
public abstract class ATreeNode extends LazyMutableTreeNode {
	public static final String PROPERTY_leafIcon = "leafIcon";
	public static final String PROPERTY_openIcon = "openIcon";
	public static final String PROPERTY_closedIcon = "closedIcon";

	private ApplicationContext context;

	protected ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(this.getClass());

	private ActionMap actionMap = Application.getInstance().getContext().getActionMap(ATreeNode.class, this);

	private JTree tree;

	private Icon leafIcon;
	private Icon closedIcon;
	private Icon openIcon;

	private String[] actions;


	protected ATreeNode() {
	}

	protected ATreeNode(Object userObject) {
		super(userObject);
	}

	protected ATreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public void init() {
		resourceMap = context.getResourceMap(this.getClass(), ATreeNode.class);
		setClosedIcon(resourceMap.getIcon(PROPERTY_closedIcon));
		setOpenIcon(resourceMap.getIcon(PROPERTY_openIcon));
		setLeafIcon(resourceMap.getIcon(PROPERTY_leafIcon));
	}


	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	public String[] getActions() {
		return actions;
	}

	public void setActions(String... actions) {
		this.actions = actions;
	}


	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public Icon getLeafIcon() {
		return leafIcon;
	}

	public void setLeafIcon(Icon leafIcon) {
		this.leafIcon = leafIcon;
	}

	public Icon getClosedIcon() {
		return closedIcon;
	}

	public void setClosedIcon(Icon closedIcon) {
		this.closedIcon = closedIcon;
	}

	public Icon getOpenIcon() {
		return openIcon;
	}

	public void setOpenIcon(Icon openIcon) {
		this.openIcon = openIcon;
	}

	public ActionMap getActionMap() {
		return actionMap;
	}

	public void setActionMap(ActionMap actionMap) {
		this.actionMap = actionMap;
	}


	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public ApplicationContext getContext() {
		return context;
	}
}
