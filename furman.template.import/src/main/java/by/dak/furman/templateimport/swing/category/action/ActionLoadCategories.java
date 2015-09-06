package by.dak.furman.templateimport.swing.category.action;

import by.dak.furman.templateimport.parser.category.CategoryParser;
import by.dak.furman.templateimport.swing.ANodeAction;
import by.dak.furman.templateimport.swing.nodes.AValueNode;
import by.dak.furman.templateimport.values.ACategory;
import by.dak.furman.templateimport.values.AValue;
import by.dak.furman.templateimport.values.Message;
import by.dak.furman.templateimport.values.ValueUtils;

import java.io.File;
import java.util.Collections;

public class ActionLoadCategories extends ANodeAction {
	private String baseDirPath;

	public void action() {
		CategoryParser parser = new CategoryParser();
		parser.setBaseDir(new File(baseDirPath));
		parser.setDelegate(new CategoryParser.Delegate() {
			@Override
			public void categoryAdded(ACategory added) {
				addChildren(Collections.singletonList(added), getRootNode());
			}

			@Override
			public void messageAdded(ACategory category, Message message) {
			}
		});
		parser.parse();


	}

	private AValueNode getNode(AValue value) {
		if (value == null)
			return (AValueNode) getModel().getRoot();

		AValue[] path = ValueUtils.getPath(value);
		AValueNode node = (AValueNode) getModel().getRoot();
		for (AValue item : path) {
			node = node.getChildBy(item);
		}
		return node;
	}

	public String getBaseDirPath() {
		return baseDirPath;
	}

	public void setBaseDirPath(String baseDirPath) {
		this.baseDirPath = baseDirPath;
	}

}
