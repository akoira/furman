package by.dak.swing.table;

import net.coderazzi.filters.gui.editor.FilterEditor;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * User: akoyro
 * Date: 05.11.2010
 * Time: 16:30:18
 */
public class FilterContentHandler implements TableModelListener
{
    private TableModel tableModel;
    private FilterEditor filterEditor;

    public FilterContentHandler(TableModel tableModel, FilterEditor filterEditor)
    {
        this.tableModel = tableModel;
        this.filterEditor = filterEditor;

        tableModel.addTableModelListener(this);
    }

    @Override
    public void tableChanged(TableModelEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
