package by.dak.furman.templateimport.swing;

import by.dak.furman.templateimport.Selectable;
import by.dak.furman.templateimport.SelectableCellRenderer;
import com.jidesoft.swing.JideScrollPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;

public abstract class ATreeTablePanel extends JXPanel
{
    private JXTreeTable treeTable = new JXTreeTable()
    {
        @Override
        public TableCellRenderer getCellRenderer(int row, int column)
        {
            if (isHierarchical(column))
                return super.getCellRenderer(row, column);

            Class cClass = getCellClass(row, column);
            TableCellRenderer renderer = getDefaultRenderer(cClass);
            if (renderer != null)
                return renderer;
            return super.getCellRenderer(row, column);
        }

        public TableCellEditor getCellEditor(int row, int column)
        {
            if (isHierarchical(column))
                return super.getCellEditor(row, column);

            Class cClass = getCellClass(row, column);
            TableCellEditor editor = getDefaultEditor(cClass);
            if (editor != null)
                return editor;
            return super.getCellEditor(row, column);
        }

        private Class getCellClass(int row, int column)
        {
            if (this.getTreeTableModel() instanceof TreeTableModel)
            {
                TreePath treePath = this.getPathForRow(row);
                return ((TreeTableModel) this.getTreeTableModel()).getColumnClass(treePath.getLastPathComponent(), column);
            }
            else
            {
                return getTreeTableModel().getColumnClass(column);
            }
        }
    };

    public ATreeTablePanel()
    {
        init();
    }

    private void init()
    {
        setLayout(new BorderLayout());
        treeTable.setScrollsOnExpand(true);
        treeTable.setExpandsSelectedPaths(true);
        treeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treeTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treeTable.setShowVerticalLines(true);
        treeTable.setShowHorizontalLines(true);
        treeTable.setColumnControlVisible(true);

        treeTable.addHighlighter(HighlighterFactory.createAlternateStriping());

        treeTable.setDefaultEditor(Selectable.class, new JXTable.BooleanEditor());
        treeTable.setDefaultRenderer(Selectable.class, new SelectableCellRenderer());

        JideScrollPane scrollPane = new JideScrollPane(treeTable);
        add(scrollPane, BorderLayout.CENTER);
    }


    public JXTreeTable getTreeTable()
    {
        return treeTable;
    }

    public Object getSelectedNode()
    {
        TreePath path = treeTable.getTreeSelectionModel().getLeadSelectionPath();
        if (path == null)
            return null;
        return path.getLastPathComponent();
    }
}
