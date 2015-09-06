package by.dak.order.copy.template.select.swing;

import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.swing.*;

public class LeftPanel extends JXPanel
{
    private ResourceMap resourceMap;
    private JXSearchField searchField;
    private JXTreeTable treeTable;

    public void init()
    {
        treeTable = new JXTreeTable();
        treeTable.setScrollsOnExpand(true);
        treeTable.setExpandsSelectedPaths(true);
        treeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treeTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treeTable.setShowVerticalLines(true);
        treeTable.setShowHorizontalLines(true);
        treeTable.setColumnControlVisible(true);
        treeTable.addHighlighter(HighlighterFactory.createAlternateStriping());

        searchField = new JXSearchField();

        MigLayout layout = new MigLayout("debug, wrap 1", "0[grow,fill]0", "0[fill]0");
        setLayout(layout);
        add(new JScrollPane(treeTable));
        add(searchField);
    }

    public JXSearchField getSearchField()
    {
        return searchField;
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

}
