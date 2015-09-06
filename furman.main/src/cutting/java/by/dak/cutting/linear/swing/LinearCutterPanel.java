/*
 * CutterFrame.java
 *
 * Created on 15. �nor 2008, 14:39
 */

package by.dak.cutting.linear.swing;


import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.linear.FurnitureTypeCodePair;
import by.dak.cutting.swing.DPanel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author Peca
 */
public class LinearCutterPanel extends DPanel
{
    public static final String PROPERTY_STATE = "cutterPanelState";
    public static final String PROPERTY_PAIR = "furnitureTypeCodePair";

    private FurnitureTypeCodePair pair;
    private State state = State.STOPED;
    private JProgressBar barWaste = new JProgressBar();
    private JProgressBar barProgress = new JProgressBar();
    private LinearSegmentPanel linearSegmentPanel;
    private LinearCutterController controller;

    public LinearCutterPanel()
    {
        initComponents();
        initEnv();
    }

    public FurnitureTypeCodePair getPair()
    {
        return pair;
    }

    public void setPair(FurnitureTypeCodePair pair)
    {
        FurnitureTypeCodePair old = this.pair;
        this.pair = pair;
        firePropertyChange(PROPERTY_PAIR, old, pair);
    }

    private void initEnv()
    {
        addPropertyChangeListener("enabled", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                updateEnabled();
            }
        });

        addPropertyChangeListener("editable", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                updateEditable();
            }
        });

    }

    private void initComponents()
    {
        linearSegmentPanel = new LinearSegmentPanel();

        JLabel labelWaste = new JLabel();
        JLabel labelProgress = new JLabel();

        labelWaste.setText(getResourceMap().getString("label.waste.text"));
        labelProgress.setText(getResourceMap().getString("label.progress.text"));
        barWaste.setMaximum(100);
        barWaste.setMaximum(0);
        barWaste.setValue(0);
        setBorder(BorderFactory.createTitledBorder(getResourceMap().getString("panel.title.preview")));

        GroupLayout cuttingPanelLayout = new GroupLayout(this);
        this.setLayout(cuttingPanelLayout);


        cuttingPanelLayout.setVerticalGroup(
                cuttingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cuttingPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(cuttingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(barWaste, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelWaste))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(cuttingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(barProgress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelProgress))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(linearSegmentPanel, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                                .addContainerGap())
        );

        cuttingPanelLayout.setHorizontalGroup(
                cuttingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cuttingPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(cuttingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(linearSegmentPanel, GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                                        .addGroup(GroupLayout.Alignment.TRAILING, cuttingPanelLayout.createSequentialGroup()
                                                .addGroup(cuttingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(labelWaste, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(labelProgress, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(cuttingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(barWaste, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                                                        .addComponent(barProgress, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))))
                                .addContainerGap())
        );

    }


    public LinearSegmentPanel getLinearSegmentPanel()
    {
        return linearSegmentPanel;
    }


    public void updateBars(Strips strips)
    {
        int value = 0;
        if (pair != null)
        {
            if (strips != null)
            {
                value = 100 - (int) (strips.getWasteRatio() * 100);
            }
        }
        barWaste.setMaximum(100);
        barWaste.setMinimum(0);
        barWaste.setValue(value);
    }

    public void updateBarProgress(int durationInSeconds, int maxValue)
    {
        barProgress.setValue(durationInSeconds);
        barProgress.setMinimum(0);
        barProgress.setMaximum(maxValue);
    }

    public LinearCutterController getController()
    {
        return controller;
    }

    public void setController(LinearCutterController controller)
    {
        this.controller = controller;
    }

    public void updateEditable()
    {
        linearSegmentPanel.setEditable(isEditable());
    }

    public void updateEnabled()
    {
        linearSegmentPanel.setEnabled(isEnabled());
    }

    public synchronized State getState()
    {
        return state;
    }

    public synchronized void setState(State state)
    {
        State old = this.state;
        this.state = state;
        firePropertyChange(PROPERTY_STATE, old, this.state);
    }

    public static enum State
    {
        STOPED,
        STARTED
    }


}
