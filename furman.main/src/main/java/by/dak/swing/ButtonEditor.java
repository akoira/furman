package by.dak.swing;

import by.dak.cutting.swing.table.CellContext;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: akoyro
 * Date: 15.07.2009
 * Time: 22:40:43
 */
public class ButtonEditor extends AbstractCellEditor implements TableCellEditor
{
    protected JButton button = new JButton();


    private ReturnValue returnValue = new DefaultReturnValue();

    private ActionListener actionListener;

    protected ButtonEditor()
    {
        button.setOpaque(true);
    }

    public ButtonEditor(ActionListener actionListener, final ReturnValue returnValue)
    {
        setActionListener(actionListener);
        setResultValue(returnValue);
    }

    public void setResultValue(final ReturnValue returnValue)
    {
        returnValue.setCommitAction(new AbstractAction("Ok")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                stopCellEditing();
                hidePopup(returnValue);
            }
        });

        returnValue.setCancelAction(new AbstractAction("Cancel")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cancelCellEditing();
                hidePopup(returnValue);
            }
        });
        this.returnValue = returnValue;
    }

    private void hidePopup(ReturnValue returnValue)
    {
        if (returnValue != null)
        {
            returnValue.hidePopup();
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column)
    {
        CellContext cellContext = new CellContext(table, value, isSelected, row, column);
        if (returnValue != null)
            returnValue.setCellContext(cellContext);

        if (isSelected)
        {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        }
        else
        {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        return button;
    }

    public Object getCellEditorValue()
    {
        return returnValue.getValue();
    }

    public boolean stopCellEditing()
    {
        return super.stopCellEditing();
    }

    protected void fireEditingStopped()
    {
        super.fireEditingStopped();
    }

    public ActionListener getActionListener()
    {
        return actionListener;
    }

    public void setActionListener(ActionListener actionListener)
    {
        ActionListener old = this.actionListener;
        this.actionListener = actionListener;
        if (old != null)
        {
            button.removeActionListener(old);
        }
        if (actionListener != null)
        {
            button.addActionListener(actionListener);
        }
    }

    public ReturnValue getReturnValue()
    {
        return returnValue;
    }


    public static interface ReturnValue
    {
        public Object getValue();

        public void setCommitAction(Action action);

        public void setCancelAction(Action action);

        public void setCellContext(CellContext cellContext);

        public void hidePopup();

    }


    public static final class DefaultReturnValue implements ReturnValue
    {
        private CellContext cellContext;

        @Override
        public Object getValue()
        {
            return cellContext.getValue();
        }

        @Override
        public void setCommitAction(Action action)
        {
        }

        @Override
        public void setCancelAction(Action action)
        {
        }

        @Override
        public void setCellContext(CellContext cellContext)
        {
            this.cellContext = cellContext;
        }

        @Override
        public void hidePopup()
        {
        }
    }
}
