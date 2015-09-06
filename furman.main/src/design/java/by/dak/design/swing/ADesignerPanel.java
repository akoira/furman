package by.dak.design.swing;

import by.dak.design.draw.DimensionFigureCreationTool;
import by.dak.design.draw.components.DimensionFigure;
import by.dak.draw.swing.ADrawPanel;
import org.jhotdraw.draw.GridConstrainer;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.action.ZoomAction;
import org.jhotdraw.draw.tool.DelegationSelectionTool;
import org.jhotdraw.gui.JPopupButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 29.08.11
 * Time: 1:52
 * To change this template use File | Settings | File Templates.
 */
public abstract class ADesignerPanel extends ADrawPanel
{
    protected DelegationSelectionTool selectionTool;
    protected JToggleButton defaultSelectionButton;
    private ButtonGroup zoomGroup;

    public static final Double[] zoomArray = new Double[]{
            0.1,
            0.25,
            0.5,
            0.75,
            1.0,
            1.25,
            1.5,
            2.0,
            3.0
    };
    public static final int DEFAULT_GRID_STEP = 1;

    public ButtonGroup getZoomGroup()
    {
        return zoomGroup;
    }

    @Override
    protected void fillToolBarAtributes()
    {
        getToolBarAttributes().add(getMenuZoom());
    }

    private JPopupButton getMenuZoom()
    {
        JPopupButton pb = new JPopupButton();
        pb.setItemFont(UIManager.getFont("MenuItem.font"));
        getResourceBundleUtil().configureToolBarButton(pb, "actions");

        JRadioButtonMenuItem rbmi;
        zoomGroup = new ButtonGroup();

        for (Double zoom : zoomArray)
        {
            pb.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), zoom, null)));
            rbmi.addItemListener(new ZoomButtonListener());
            zoomGroup.add(rbmi);
        }

        return pb;
    }


    @Override
    protected void fillToolBarFigures()
    {
        ButtonFactory.addToolTo(getToolBarFigures(), getDrawingEditor(), new DimensionFigureCreationTool(new DimensionFigure()),
                "edit.createDistanceLine", getResourceBundleUtil());
    }

    @Override
    protected abstract JToolBar getToolBarFigures();


    @Override
    protected JPanel getControlPanel()
    {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints;
        JToolBar toolBarFigures = getToolBarFigures();
        JToolBar attributesToolBar = getToolBarAttributes();

        toolBarFigures.setFloatable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        controlPanel.add(toolBarFigures, gridBagConstraints);

        attributesToolBar.setFloatable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        controlPanel.add(attributesToolBar, gridBagConstraints);
        return controlPanel;
    }

    private class ZoomButtonListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        drawingView.setVisibleConstrainer(new GridConstrainer(DEFAULT_GRID_STEP /
                                drawingView.getScaleFactor(),
                                DEFAULT_GRID_STEP /
                                        drawingView.getScaleFactor()));
                        ((JComponent) getDrawingView()).revalidate();
                        ((JComponent) getDrawingView()).repaint();
                    }
                });

            }
        }
    }

}
