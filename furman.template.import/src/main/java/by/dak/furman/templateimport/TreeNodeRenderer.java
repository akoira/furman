package by.dak.furman.templateimport;

import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.tree.DefaultXTreeCellRenderer;

import javax.swing.*;
import java.awt.*;

public class TreeNodeRenderer extends DefaultXTreeCellRenderer {

	private ValueConverterAnnotationProcessor processor = new ValueConverterAnnotationProcessor();
	private ResourceMap resourceMap;

	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	public void setResourceMap(ResourceMap resourceMap) {
		this.resourceMap = resourceMap;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		render(value);
		return component;
	}

	private void render(Object value) {
		processor.setResourceMap(getResourceMap());
		IValueConverter converter = processor.getValueConverter(value.getClass());
		Icon icon = converter.getIcon(value);
		if (icon != null)
			setIcon(icon);
		String string = converter.getString(value);
		setText(string);
	}
}


