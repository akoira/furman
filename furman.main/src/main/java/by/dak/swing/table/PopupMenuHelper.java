package by.dak.swing.table;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 10.02.2010
 * Time: 20:41:19
 * To change this template use File | Settings | File Templates.
 */
public class PopupMenuHelper
{
    public static final String SEPARATOR_KEY = "SEPARATOR";


    private JComponent component;

    private String[] actions;
    private PopupMenuTrigger popupMenuTrigger;
    private ActionMap actionMap;

    private JPopupMenu popupMenu = new JPopupMenu();

    public PopupMenuHelper(JComponent component)
    {
        this.component = component;
    }

    public String[] getActions()
    {
        return actions;
    }

    public void setActions(String... actions)
    {
        this.actions = actions;
    }

    public PopupMenuTrigger getPopupMenuTrigger()
    {
        return popupMenuTrigger;
    }

    public void setPopupMenuTrigger(PopupMenuTrigger popupMenuTrigger)
    {
        this.popupMenuTrigger = popupMenuTrigger;
    }

    public ActionMap getActionMap()
    {
        return actionMap;
    }

    public void setActionMap(ActionMap actionMap)
    {
        this.actionMap = actionMap;
    }

    public void init()
    {
        popupMenu.removeAll();
        if (actions != null && actions.length > 0)
        {
            for (String actionKey : actions)
            {
                if (actionKey == SEPARATOR_KEY)
                {
                    popupMenu.addSeparator();
                }
                else
                {
                    Action action = this.actionMap.get(actionKey);
                    if (action != null)
                        popupMenu.add(action);
                    else
                        throw new IllegalArgumentException();
                }
            }
            component.addMouseListener(new PopupListener());
        }
    }


    class PopupListener extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            showPopup(e);
        }

        public void mouseReleased(MouseEvent e)
        {
            showPopup(e);
        }

        private void showPopup(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                if (popupMenuTrigger == null || popupMenuTrigger.showPopupMenu(e))
                {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }
    }

    public JComponent getComponent()
    {
        return component;
    }


    public interface PopupMenuTrigger
    {
        boolean showPopupMenu(MouseEvent e);
    }

}