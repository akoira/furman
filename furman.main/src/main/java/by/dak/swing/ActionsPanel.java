package by.dak.swing;

import by.dak.cutting.swing.DPanel;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 16.05.2010
 * Time: 19:26:14
 */
public class ActionsPanel<C extends JComponent> extends DPanel implements FocusToFirstComponent
{
    private JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
    private C contentComponent;


    private ActionMap sourceActionMap;
    private String[] actions;

    public ActionsPanel()
    {
    }


    public ActionsPanel(C contentComponent, ActionMap sourceActionMap, String... actions)
    {
        this.contentComponent = contentComponent;
        this.sourceActionMap = sourceActionMap;
        this.actions = actions;
    }


    public void init()
    {
        actionPanel.removeAll();
        this.removeAll();
        setLayout(new BorderLayout());
        add(getContentComponent(), BorderLayout.CENTER);
        if (actions != null)
        {
            for (String action : actions)
            {
                actionPanel.add(new JButton(getSourceActionMap().get(action)));
            }
            add(actionPanel, BorderLayout.SOUTH);
        }
    }

    public C getContentComponent()
    {
        return contentComponent;
    }

    public void setContentComponent(C contentComponent)
    {
        this.contentComponent = contentComponent;
    }

    @Override
    public void setFocusToFirstComponent()
    {
        if (getContentComponent() instanceof FocusToFirstComponent)
        {
            ((FocusToFirstComponent) getContentComponent()).setFocusToFirstComponent();
        }

    }

    public String[] getActions()
    {
        return actions;
    }

    public void setActions(String... actions)
    {
        this.actions = actions;
    }

    public ActionMap getSourceActionMap()
    {
        return sourceActionMap;
    }

    public void setSourceActionMap(ActionMap sourceActionMap)
    {
        this.sourceActionMap = sourceActionMap;
    }

}
