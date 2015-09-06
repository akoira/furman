package by.dak.order.copy.template.select.swing;

import by.dak.order.copy.template.select.swing.tree.RootNode;
import by.dak.order.copy.template.select.swing.tree.TreeRenderer;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class SelectPanelController extends ASelectController<SelectPanel>
{
    private DefaultTreeTableModel model;
    private RootNode rootNode;
    private SelectPanel selectPanel;
    private LeftPanelController leftPanelController;

    @Override
    public void init()
    {


        leftPanelController = new LeftPanelController();
        leftPanelController.setMainFacade(getMainFacade());
        leftPanelController.setApplicationContext(getApplicationContext());
        leftPanelController.setSelectPanelController(this);
        leftPanelController.init();


        selectPanel = new SelectPanel();
        selectPanel.setResourceMap(applicationContext.getResourceMap(SelectPanel.class));
        selectPanel.setLeftPanel(leftPanelController.getComponent());
        selectPanel.init();


        rootNode = new RootNode();
        model = new DefaultTreeTableModel(rootNode);

        selectPanel.getTreeTable().setTreeTableModel(model);

        TreeRenderer treeRenderer = new TreeRenderer();
        treeRenderer.setResourceMap(getApplicationContext().getResourceMap(TreeRenderer.class));
        selectPanel.getTreeTable().setTreeCellRenderer(treeRenderer);

    }

    @Override
    public SelectPanel getComponent()
    {
        return selectPanel;
    }

    public DefaultTreeTableModel getModel()
    {
        return model;
    }

    public RootNode getRootNode()
    {
        return rootNode;
    }

}

