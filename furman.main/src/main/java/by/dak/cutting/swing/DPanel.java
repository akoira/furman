package by.dak.cutting.swing;

import by.dak.swing.Titled;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.*;

/**
 * @author admin
 * @version 0.1 11.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class DPanel extends JPanel implements Titled
{
    private ResourceMap resourceMap = (getClass() == DPanel.class) ? Application.getInstance().getContext().getResourceMap(DPanel.class) :
            Application.getInstance().getContext().getResourceMap(getClass(), DPanel.class);

    private ActionMap actionMap = Application.getInstance().getContext().getActionMap(this.getClass(), this);

    private boolean editable = true;


    public DPanel(LayoutManager layout, boolean isDoubleBuffered)
    {
        super(layout, isDoubleBuffered);
        AnnotationProcessor.process(this);
        setName(this.getClass().getSimpleName());
    }

    public DPanel(LayoutManager layout)
    {
        super(layout);
        AnnotationProcessor.process(this);
        setName(this.getClass().getSimpleName());
    }

    public DPanel(boolean isDoubleBuffered)
    {
        super(isDoubleBuffered);
        AnnotationProcessor.process(this);
        setName(this.getClass().getSimpleName());
    }

    public DPanel()
    {
        super();
        AnnotationProcessor.process(this);
        setName(this.getClass().getSimpleName());
    }

    @Override
    public String getTitle()
    {
        String value = resourceMap.getString("panel.title");
        if (value == null)
        {
            value = getClass().getSimpleName();
        }
        return value;
    }

    public ImageIcon getIcon()
    {
        return resourceMap.getImageIcon("panel.icon");
    }

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable(final boolean editable)
    {
        boolean old = this.editable;
        this.editable = editable;
        firePropertyChange("editable", old, editable);
    }


    public ActionMap getAppActionMap()
    {
        return actionMap;
    }

    public void setAppActionMap(ActionMap actionMap)
    {
        this.actionMap = actionMap;
    }


}
