package by.dak.cutting.swing.table;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 25.04.2009
 * Time: 15:44:16
 */
public interface ComponentProvider<V>
{
    public void setTogglePopupAction(Action action);

    public void setCommitAction(Action action);

    public void setCancelAction(Action action);

    public Action getTogglePopupAction();

    public JComponent getCellComponent();

    public JComponent getPopupComponent();

    public void setCellContext(CellContext cellContext);

    public V getValue();

    public void init();
}
