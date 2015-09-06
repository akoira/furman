package by.dak.order.copy.template.select.swing;

import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.gui.DockController;
import bibliothek.gui.dock.DefaultDockable;
import bibliothek.gui.dock.SplitDockStation;
import bibliothek.gui.dock.station.split.SplitDockGrid;
import by.dak.common.swing.NeedInit;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.swing.*;
import java.awt.*;

public class SelectPanel extends JXPanel implements NeedInit
{
    private ResourceMap resourceMap;
    private DockController dockController = new DockController();
    private JXTreeTable treeTable;
    private JXPanel rightPanel;
    private LeftPanel leftPanel;

    public void init()
    {
        setLayout(new BorderLayout());

        dockController.setTheme(new EclipseTheme());

        rightPanel = new JXPanel(new BorderLayout());

        treeTable = new JXTreeTable();
        treeTable.setScrollsOnExpand(true);
        treeTable.setExpandsSelectedPaths(true);
        treeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treeTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treeTable.setShowVerticalLines(true);
        treeTable.setShowHorizontalLines(true);
        treeTable.setColumnControlVisible(true);
        treeTable.addHighlighter(HighlighterFactory.createAlternateStriping());

        JScrollPane scrollPane = new JScrollPane(treeTable);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        SplitDockGrid splitDockGrid = new SplitDockGrid();

        splitDockGrid.addDockable(0, 0, 1, 1, new DefaultDockable(leftPanel, getResourceMap().getString("title.leftPanel"), getResourceMap().getIcon("icon.leftPanel")));
        splitDockGrid.addDockable(1, 0, 3, 3, new DefaultDockable(rightPanel, getResourceMap().getString("title.rightPanel"), getResourceMap().getIcon("icon.rightPanel")));
        SplitDockStation splitDockStation = new SplitDockStation();
        splitDockStation.dropTree(splitDockGrid.toTree());

        dockController.add(splitDockStation);

        add(splitDockStation.getComponent(), BorderLayout.CENTER);
    }

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }

    public JXTreeTable getTreeTable()
    {
        return treeTable;
    }

    public JXPanel getRightPanel()
    {
        return rightPanel;
    }

    public LeftPanel getLeftPanel()
    {
        return leftPanel;
    }

    public void setLeftPanel(LeftPanel leftPanel)
    {
        this.leftPanel = leftPanel;
    }
}

