package by.dak.swing;

import by.dak.cutting.swing.BaseTabPanel;
import org.jdesktop.swingx.JXTaskPane;

import javax.swing.*;
import java.awt.*;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.11.2010
 * Time: 20:17:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class AStatisticPanel<V> extends BaseTabPanel<V>
{
    private JXTaskPane panelFilter = new JXTaskPane();
    private JPanel panelFilterControl;
    private JPanel panelResult;

    public AStatisticPanel()
    {
        panelFilter.setAnimated(false);
        if (!Beans.isDesignTime())
        {
            setLayout(new BorderLayout());
            add(panelFilter, BorderLayout.NORTH);

            addPropertyChangeListener("panelFilterControl", new PropertyChangeListener()
            {
                @Override
                public void propertyChange(PropertyChangeEvent evt)
                {
                    JPanel old = (JPanel) evt.getOldValue();
                    JPanel newP = (JPanel) evt.getNewValue();
                    if (old != null)
                    {
                        panelFilter.getContentPane().remove(old);
                    }

                    if (newP != null)
                    {
                        panelFilter.getContentPane().add("Center", newP);
                    }
                }
            });
            addPropertyChangeListener("panelResult", new PropertyChangeListener()
            {
                @Override
                public void propertyChange(PropertyChangeEvent evt)
                {
                    JPanel old = (JPanel) evt.getOldValue();
                    JPanel newP = (JPanel) evt.getNewValue();
                    if (old != null)
                    {
                        remove(old);
                    }

                    if (newP != null)
                    {
                        add(newP, BorderLayout.CENTER);
                    }
                }
            });

            init();
        }

    }

    protected abstract void init();

    public JPanel getPanelFilterControl()
    {
        return panelFilterControl;
    }

    public void setPanelFilterControl(JPanel panelFilterControl)
    {
        JPanel old = this.panelFilterControl;
        this.panelFilterControl = panelFilterControl;
        firePropertyChange("panelFilterControl", old, panelFilterControl);
    }


    public JPanel getPanelResult()
    {
        return panelResult;
    }

    public void setPanelResult(JPanel panelResult)
    {
        JPanel old = this.panelResult;
        this.panelResult = panelResult;
        firePropertyChange("panelResult", old, panelResult);
    }

    public JXTaskPane getPanelFilter()
    {
        return panelFilter;
    }
}
