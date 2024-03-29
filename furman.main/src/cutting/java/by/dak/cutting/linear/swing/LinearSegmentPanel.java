package by.dak.cutting.linear.swing;

import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.DPanel;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.MillingFigureFactory;
import by.dak.draw.Graphics2DUtils;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.GridConstrainer;
import org.jhotdraw.draw.action.ZoomAction;
import org.jhotdraw.draw.io.DOMStorableInputOutputFormat;
import org.jhotdraw.draw.io.InputFormat;
import org.jhotdraw.draw.io.OutputFormat;
import org.jhotdraw.draw.io.SerializationInputOutputFormat;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 16.02.11
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class LinearSegmentPanel extends DPanel
{
    public static final int DEFAULT_GRID_STEP = 1;
    private static final Double defaultZoom = 0.25;
    private static final Double[] zoom = new Double[]
            {
                    0.1,
                    0.15,
                    0.2,
                    0.25,
                    0.3,
                    0.35,
                    0.5,
                    0.65,
                    0.85,
                    1.00
            };

    private LinearCuttingDrawing cuttingDrawing;
    private DrawingEditor editor;
    private Strips strips;

    /**
     * Creates new instance.
     */
    public LinearSegmentPanel()
    {
        initComponents();
        initEnv();
    }


    private void initEnv()
    {
        getEditor().add(view);

        cuttingDrawing = new LinearCuttingDrawing();

        JPopupButton pb = createPopup(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.labels);
        creationToolbar.add(pb);

        view.setDrawing(cuttingDrawing);

        DOMStorableInputOutputFormat ioFormat = new DOMStorableInputOutputFormat(new MillingFigureFactory());
        SerializationInputOutputFormat sFormat = new SerializationInputOutputFormat();
        LinkedList<InputFormat> inputFormats = new LinkedList<InputFormat>();
        LinkedList<OutputFormat> outputFormats = new LinkedList<OutputFormat>();
        inputFormats.add(ioFormat);
        inputFormats.add(sFormat);
        outputFormats.add(ioFormat);
        outputFormats.add(sFormat);

        cuttingDrawing.setInputFormats(inputFormats);
        cuttingDrawing.setOutputFormats(outputFormats);

        GridConstrainer constrainer = new GridConstrainer(DEFAULT_GRID_STEP, DEFAULT_GRID_STEP);
        view.setVisibleConstrainer(constrainer);
        view.setConstrainerVisible(true);
        view.setAutoscrolls(true);

        view.setScaleFactor(defaultZoom);

        addPropertyChangeListener("enabled", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                getView().setEnabled(isEnabled());
            }
        });

        addPropertyChangeListener("editable", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                getView().setEnabled(isEditable());
            }
        });

        addPropertyChangeListener("strips", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                cuttingDrawing.setStrips(strips);
                getView().revalidate();
                getView().repaint();
            }
        });

    }

    private JPopupButton createPopup(ResourceBundleUtil labels)
    {
        JPopupButton pb = new JPopupButton();

        pb.setItemFont(UIManager.getFont("MenuItem.font"));
        labels.configureToolBarButton(pb, "actions");

        JRadioButtonMenuItem rbmi;
        ButtonGroup group = new ButtonGroup();

        for (Double z : zoom)
        {
            pb.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getEditor(), z, null)));
            rbmi.addItemListener(new ZoomButtonListener());
            if (z.equals(defaultZoom))
            {
                rbmi.setSelected(true);
            }
            group.add(rbmi);
        }

        return pb;
    }

    public DefaultDrawingView getView()
    {
        return view;
    }


    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <draw-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        GridBagConstraints gridBagConstraints;

        scrollPane = new JScrollPane();
        view = new DefaultDrawingView();
        view.setBorder(BorderFactory.createEmptyBorder(0, 0, Graphics2DUtils.OFFSET, Graphics2DUtils.OFFSET));
        jPanel1 = new JPanel();
        creationToolbar = new JToolBar();

        setLayout(new BorderLayout());

        scrollPane.setViewportView(view);

        add(scrollPane, BorderLayout.CENTER);

        jPanel1.setLayout(new GridBagLayout());

        creationToolbar.setFloatable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(creationToolbar, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;

        add(jPanel1, BorderLayout.NORTH);

    }// </draw-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JToolBar creationToolbar;
    private JPanel jPanel1;
    private JScrollPane scrollPane;
    private DefaultDrawingView view;
    // End of variables declaration//GEN-END:variables


    public DrawingEditor getEditor()
    {
        if (editor == null)
        {
            editor = new DefaultDrawingEditor();
        }
        return editor;
    }

    public Strips getStrips()
    {
        return strips;
    }

    public void setStrips(Strips strips)
    {
        Strips old = this.strips;
        this.strips = strips;
        firePropertyChange("strips", old, strips);
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
                        getView().setVisibleConstrainer(new GridConstrainer(DEFAULT_GRID_STEP /
                                getView().getScaleFactor(),
                                DEFAULT_GRID_STEP /
                                        getView().getScaleFactor()));
                        ((JComponent) getView()).revalidate();
                        ((JComponent) getView()).repaint();
                    }
                });

            }
        }
    }
}
