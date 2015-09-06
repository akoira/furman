package by.dak.order.copy.template.swing;

import org.jdesktop.application.ApplicationContext;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 11/23/13
 * Time: 9:52 AM
 */
public abstract class AStep implements IStep
{
    private ApplicationContext context;

    private String name;
    private String description;
    private JComponent view;


    public void init()
    {
        setName(this.getClass().getSimpleName());
        setDescription(getContext().getResourceMap(PrepareFacade.class).getString("description"));
        setView(new JXPanel());
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public JComponent getView()
    {
        return view;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setView(JComponent view)
    {
        this.view = view;
    }

    public ApplicationContext getContext()
    {
        return context;
    }

    public void setContext(ApplicationContext context)
    {
        this.context = context;
    }
}
