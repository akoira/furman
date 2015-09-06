package by.dak.furman.templateimport.swing;

import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.gui.DockController;
import bibliothek.gui.dock.DefaultDockable;
import bibliothek.gui.dock.SplitDockStation;
import bibliothek.gui.dock.ToolbarDockStation;
import bibliothek.gui.dock.ToolbarItemDockable;
import bibliothek.gui.dock.station.split.SplitDockGrid;
import bibliothek.gui.dock.toolbar.expand.ExpandedState;
import by.dak.furman.templateimport.swing.category.CategoryPanel;
import by.dak.furman.templateimport.swing.template.TemplatePanel;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JXPanel
{

    private ResourceMap resourceMap;
    private CategoryPanel categoryPanel = new CategoryPanel();
    private TemplatePanel templatePanel = new TemplatePanel();
    private DockController dockController = new DockController();
    private ToolbarDockStation toolbarStation;

    public void init()
    {
        setLayout(new BorderLayout());
        initDockController();
        initToolBar();
    }

    private void initDockController()
    {
        dockController.setTheme(new EclipseTheme());
        SplitDockGrid splitDockGrid = new SplitDockGrid();


        splitDockGrid.addDockable(0, 0, 1, 1, new DefaultDockable(categoryPanel, getResourceMap().getString("title.categories")));
        splitDockGrid.addDockable(1, 0, 3, 3, new DefaultDockable(templatePanel, getResourceMap().getString("title.templates")));
        SplitDockStation splitDockStation = new SplitDockStation();
        splitDockStation.dropTree(splitDockGrid.toTree());

        dockController.add(splitDockStation);
        add(splitDockStation.getComponent(), BorderLayout.CENTER);

    }

    private void initToolBar()
    {
        toolbarStation = new ToolbarDockStation();
        toolbarStation.setExpandedState(ExpandedState.SHRUNK);
        dockController.add(toolbarStation);
        categoryPanel.add(toolbarStation.getComponent(), BorderLayout.NORTH);
    }

    public CategoryPanel getCategoryPanel()
    {
        return categoryPanel;
    }

    public TemplatePanel getTemplatePanel()
    {
        return templatePanel;
    }

    public DockController getDockController()
    {
        return dockController;
    }

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }

    public void addAction(Action action)
    {
        ToolbarItemDockable item = new ToolbarItemDockable();

        JXButton button = new JXButton(action);
        button.setBorderPainted(false);
        button.setFocusable(false);

        button.setPreferredSize(new Dimension(18, 18));
        item.setComponent(button, ExpandedState.SHRUNK);
        toolbarStation.drop(item);
    }

    public ToolbarDockStation getToolbarStation()
    {
        return toolbarStation;
    }
}
