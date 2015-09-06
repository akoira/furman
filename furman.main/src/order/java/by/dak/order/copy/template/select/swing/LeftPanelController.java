package by.dak.order.copy.template.select.swing;

import by.dak.order.copy.template.select.swing.tree.RootNode;
import by.dak.order.copy.template.select.swing.tree.TreeRenderer;
import by.dak.order.copy.template.select.swing.tree.action.ActionSearchTemplates;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeftPanelController extends ASelectController<LeftPanel>
{

    private SelectPanelController selectPanelController;
    private DefaultTreeTableModel model;
    private RootNode rootNode;
    private LeftPanel leftPanel;

    @Override
    public LeftPanel getComponent()
    {
        return leftPanel;
    }

    @Override
    public void init()
    {
        leftPanel = new LeftPanel();
        leftPanel.setResourceMap(getApplicationContext().getResourceMap(LeftPanel.class));
        leftPanel.init();
        leftPanel.getSearchField().setFindAction(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String text = leftPanel.getSearchField().getText();
                ActionSearchTemplates actionSearchTemplates = new ActionSearchTemplates();
                actionSearchTemplates.setSelectPanelController(selectPanelController);
                actionSearchTemplates.setSearchText(text);
                actionSearchTemplates.execute();

            }
        });

        rootNode = new RootNode();
        model = new DefaultTreeTableModel(rootNode);
        leftPanel.getTreeTable().setTreeTableModel(model);

        TreeRenderer treeRenderer = new TreeRenderer();
        treeRenderer.setResourceMap(getApplicationContext().getResourceMap(TreeRenderer.class));
        leftPanel.getTreeTable().setTreeCellRenderer(treeRenderer);
    }

    public SelectPanelController getSelectPanelController()
    {
        return selectPanelController;
    }

    public void setSelectPanelController(SelectPanelController selectPanelController)
    {
        this.selectPanelController = selectPanelController;
    }
}
