package by.dak.swing.table;

import org.jdesktop.swingx.event.TableColumnModelExtListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import java.beans.PropertyChangeEvent;

/**
 * User: akoyro
 * Date: 14.04.11
 * Time: 19:47
 */
public abstract class TableColumnModelExtAdapter implements TableColumnModelExtListener
{
    @Override
    public void columnPropertyChange(PropertyChangeEvent event)
    {
    }

    @Override
    public void columnAdded(TableColumnModelEvent e)
    {
    }

    @Override
    public void columnRemoved(TableColumnModelEvent e)
    {
    }

    @Override
    public void columnMoved(TableColumnModelEvent e)
    {
    }

    @Override
    public void columnMarginChanged(ChangeEvent e)
    {
    }

    @Override
    public void columnSelectionChanged(ListSelectionEvent e)
    {
    }
}
