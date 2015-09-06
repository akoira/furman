package by.dak.cutting.swing;

import com.jidesoft.swing.JideTabbedPane;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.09.11
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class MainCuttingPanel extends DPanel
{
    private JideTabbedPane cuttersPaneContainer;
    private CuttersPanel commonCuttersPanel;
    private CuttersPanel dspPlasticCuttersPanel;

    public MainCuttingPanel()
    {
        initComponents();
    }

    private void initComponents()
    {
        setLayout(new BorderLayout());

        cuttersPaneContainer = new JideTabbedPane();

        commonCuttersPanel = new CuttersPanel();
        cuttersPaneContainer.addTab(getResourceMap().getString("commonCuttersPanel.text"), commonCuttersPanel);
        add(cuttersPaneContainer);

        addPropertyChangeListener("editable", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                commonCuttersPanel.setEditable(isEditable());
            }
        });
    }

    public CuttersPanel getCommonCuttersPanel()
    {
        return commonCuttersPanel;
    }

    public CuttersPanel getDspPlasticCuttersPanel()
    {
        return dspPlasticCuttersPanel;
    }

    public void setDspPlasticCuttersPanel(CuttersPanel dspPlasticCuttersPanel)
    {
        CuttersPanel old = this.dspPlasticCuttersPanel;
        this.dspPlasticCuttersPanel = dspPlasticCuttersPanel;
        cuttersPaneContainer.remove(old);
        if (dspPlasticCuttersPanel != null)
        {
            cuttersPaneContainer.addTab(getResourceMap().getString("dspPlasticCuttersPanel.text"), dspPlasticCuttersPanel);
        }
        revalidate();
        repaint();
    }

    public void setSelectedComponent(CuttersPanel cuttersPanel)
    {
        cuttersPaneContainer.setSelectedComponent(cuttersPanel);
    }
}
