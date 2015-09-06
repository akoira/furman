package by.dak.furman.templateimport.swing.template;

import by.dak.furman.templateimport.AbstractController;
import by.dak.furman.templateimport.TreeNodeRenderer;
import by.dak.furman.templateimport.swing.nodes.RootNode;
import by.dak.furman.templateimport.swing.template.action.ActionLoadTemplates;
import by.dak.furman.templateimport.values.TemplateCategory;

public class TemplatePanelController extends AbstractController<TemplatePanel>
{
    private String baseDirPath;

    public void init()
    {
        TreeNodeRenderer treeNodeRenderer = new TreeNodeRenderer();
        treeNodeRenderer.setResourceMap(getResourceMap());

        getView().getTreeTable().setTreeCellRenderer(treeNodeRenderer);
    }

    public void showTemplates(TemplateCategory category)
    {

        RootNode rootNode = new RootNode();
        getModel().setRoot(rootNode);

        ActionLoadTemplates actionLoadTemplates = new ActionLoadTemplates();
        actionLoadTemplates.setCategory(category);
        actionLoadTemplates.setModel(getModel());
        actionLoadTemplates.setNodeFactory(getNodeFactory());
        actionLoadTemplates.action();
    }


}
