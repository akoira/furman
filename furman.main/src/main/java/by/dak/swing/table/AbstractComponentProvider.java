package by.dak.swing.table;

import by.dak.cutting.swing.table.ComponentProvider;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 13.07.2009
 * Time: 11:42:50
 */
public abstract class AbstractComponentProvider<V> implements ComponentProvider<V>
{
    private Action togglePopupAction;
    private Action commitAction;
    private JButton button;

    @Override
    public void setTogglePopupAction(Action action)
    {
        togglePopupAction = action;
    }

    @Override
    public Action getTogglePopupAction()
    {
        return togglePopupAction;
    }

    @Override
    public JComponent getCellComponent()
    {
        return getButton();
    }

    @Override
    public void setCommitAction(Action action)
    {
        commitAction = action;
    }

    @Override
    public void setCancelAction(Action action)
    {
    }


    protected void createButton()
    {
        button = new JButton();
    }

    public JButton getButton()
    {
        return button;
    }

    public Action getCommitAction()
    {
        return commitAction;
    }

    public void init()
    {
        if (getButton() != null)
        {
            getButton().setAction(null);
        }
        createButton();
        getButton().setAction(getTogglePopupAction());
    }
}
