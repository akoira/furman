package by.dak.swing;

import org.jdesktop.beans.AbstractBean;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 10.11.2010
 * Time: 16:26:42
 */
public abstract class ActionsContext<E> extends AbstractBean
{
    /**
     * Компонент связагнный с этими actions
     */
    private JComponent relatedComponent;

    private ActionMap actionMap;

    private E selectedElement;

    private String[] actions;

    public ActionMap getActionMap()
    {
        return actionMap;
    }

    public void setActionMap(ActionMap actionMap)
    {
        this.actionMap = actionMap;
    }


    public void setSelectedElement(E selectedElement)
    {
        E old = this.selectedElement;
        this.selectedElement = selectedElement;
        firePropertyChange("selectedElement", old, selectedElement);
    }

    public E getSelectedElement()
    {
        return selectedElement;
    }

    public String[] getActionNames()
    {
        return actions;
    }

    public void setActions(String[] actions)
    {
        this.actions = actions;
    }

    public JComponent getRelatedComponent()
    {
        return relatedComponent;
    }

    public void setRelatedComponent(JComponent relatedComponent)
    {
        this.relatedComponent = relatedComponent;
    }
}
