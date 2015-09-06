package by.dak.furman.templateimport.swing.category;

import by.dak.furman.templateimport.AbstractController;
import by.dak.furman.templateimport.TreeNodeRenderer;
import by.dak.furman.templateimport.swing.category.action.ActionLoadCategories;
import by.dak.furman.templateimport.swing.category.action.ActionOpenFile;
import by.dak.furman.templateimport.swing.nodes.AValueNode;
import by.dak.furman.templateimport.swing.nodes.MessageNode;
import by.dak.furman.templateimport.swing.nodes.RootNode;
import by.dak.furman.templateimport.swing.template.TemplatePanelController;
import by.dak.furman.templateimport.values.IFileAware;
import by.dak.furman.templateimport.values.TemplateCategory;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoryPanelController extends AbstractController<CategoryPanel>
{
    private TemplatePanelController templatePanelController;

    public void init()
    {
        TreeNodeRenderer treeNodeRenderer = new TreeNodeRenderer();
        treeNodeRenderer.setResourceMap(getResourceMap());
        getView().getTreeTable().setTreeCellRenderer(treeNodeRenderer);
        getView().getTreeTable().setTreeTableModel(getModel());
        getView().getTreeTable().addTreeSelectionListener(new TreeSelectionListener()
        {
            @Override
            public void valueChanged(TreeSelectionEvent e)
            {
                if (e.getNewLeadSelectionPath() != null)
                {
                    AValueNode node = (AValueNode) e.getNewLeadSelectionPath().getLastPathComponent();
                    if (node.getValue() instanceof TemplateCategory)
                        templatePanelController.showTemplates((TemplateCategory) node.getValue());
                }
            }
        }
        );

        getView().getTreeTable().addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() > 1)
                {
                    Object node = getView().getSelectedNode();
                    if (node instanceof AValueNode)
                    {
                        AValueNode valueNode = (AValueNode) node;
                        if (valueNode instanceof MessageNode)
                            valueNode = (AValueNode) valueNode.getParent();
                        if (valueNode.getValue() instanceof IFileAware)
                        {
                            ActionOpenFile action = new ActionOpenFile();
                            action.setFilePath(((IFileAware) valueNode.getValue()).getFile().getAbsolutePath());
                            action.action();
                        }
                    }
                }
            }
        });
    }

    public void loadCategories()
    {
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                RootNode rootNode = new RootNode();
                getModel().setRoot(rootNode);
            }
        };
        SwingUtilities.invokeLater(runnable);

        ActionLoadCategories action = new ActionLoadCategories();
        action.setBaseDirPath(getBaseDirPath());
        action.setModel(getModel());
        action.setNodeFactory(getNodeFactory());
        action.action();
    }

    public void selectCategory(TemplateCategory category)
    {
        templatePanelController.showTemplates(category);
    }

    public TemplatePanelController getTemplatePanelController()
    {
        return templatePanelController;
    }

    public void setTemplatePanelController(TemplatePanelController templatePanelController)
    {
        this.templatePanelController = templatePanelController;
    }
}
