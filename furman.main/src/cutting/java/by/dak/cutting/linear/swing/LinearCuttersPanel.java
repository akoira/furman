/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

/*
 * CuttersPanel.java
 *
 * Created on 30.01.2009, 10:59:16
 */

package by.dak.cutting.linear.swing;

import by.dak.cutting.linear.FurnitureTypeCodePair;
import by.dak.cutting.linear.LinearCuttingModel;
import by.dak.cutting.linear.LinearCuttingModelCreator;
import by.dak.cutting.swing.DPanel;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.swing.TabIterator;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import com.jidesoft.swing.JideButton;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * @author admin
 */
public class LinearCuttersPanel extends DPanel
{
    public static final String PROPERTY_STATE = "mainCuttingPanelState";

    private JButton generateButton;
    private JButton stopButton;
    private JButton reloadButton;
    private com.jidesoft.swing.JideTabbedPane tabbedPaneCutting;
    private LinearCuttingModel cuttingModel;

    private Controller controller = new Controller();


    public static enum State
    {
        STARTED,
        STOPED
    }

    private State state = State.STOPED;


    public LinearCuttersPanel()
    {
        initComponents();
        initEnv();
    }

    private void initEnv()
    {

        addPropertyChangeListener("cuttingModel", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                controller.cuttingModelChanged();
            }
        });

        addPropertyChangeListener("editable", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                generateButton.setEnabled(isEditable());
                reloadButton.setEnabled(isEditable());
                stopButton.setEnabled(isEditable());

                TabIterator tabIterator = new TabIterator(tabbedPaneCutting)
                {
                    @Override
                    protected Object iterate(Component tab)
                    {
                        LinearCutterPanel cutterPanel = (LinearCutterPanel) tab;
                        cutterPanel.setEditable(isEditable());
                        return null;
                    }
                };
                tabIterator.iterate();
            }
        });

        addPropertyChangeListener("enabled", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                generateButton.setEnabled(isEnabled());
                reloadButton.setEnabled(isEnabled());

                TabIterator tabIterator = new TabIterator(tabbedPaneCutting)
                {
                    @Override
                    protected Object iterate(Component tab)
                    {
                        LinearCutterPanel cutterPanel = (LinearCutterPanel) tab;
                        cutterPanel.setEnabled(isEnabled());
                        return null;
                    }
                };
                tabIterator.iterate();
            }
        });
    }


    public synchronized State getState()
    {
        return state;
    }

    synchronized void setState(State state)
    {
        State old = this.state;
        this.state = state;
        firePropertyChange(PROPERTY_STATE, old, this.state);
    }


    @SuppressWarnings("unchecked")

    private void initComponents()
    {

        tabbedPaneCutting = new com.jidesoft.swing.JideTabbedPane();
        generateButton = new JideButton();
        generateButton.setFocusable(false);
        stopButton = new JideButton();
        stopButton.setFocusable(false);
        reloadButton = new JideButton();
        reloadButton.setFocusable(false);

        setName("CuttersPanel"); // NOI18N

        tabbedPaneCutting.setBorder(BorderFactory.createEtchedBorder());
        tabbedPaneCutting.setName("tabbedPaneCutting"); // NOI18N

        ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(LinearCuttersPanel.class, this);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(LinearCuttersPanel.class);

        generateButton.setAction(actionMap.get("generate")); // NOI18N

        stopButton.setAction(actionMap.get("stop")); // NOI18N

        reloadButton.setAction(actionMap.get("reload")); // NOI18N

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tabbedPaneCutting, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(generateButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(stopButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(reloadButton)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(generateButton)
                                        .addComponent(stopButton)
                                        .addComponent(reloadButton)
                                )
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tabbedPaneCutting, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }


    public LinearCuttingModel getCuttingModel()
    {
        return cuttingModel;
    }

    public void setCuttingModel(LinearCuttingModel cuttingModel)
    {
        LinearCuttingModel old = this.cuttingModel;
        this.cuttingModel = cuttingModel;
        firePropertyChange("cuttingModel", old, cuttingModel);
    }


    @org.jdesktop.application.Action
    public void generate()
    {
        controller.generate();
    }

    @org.jdesktop.application.Action
    public void stop()
    {
        controller.stop();
    }

    @org.jdesktop.application.Action
    public void reload()
    {
    }

    @org.jdesktop.application.Action
    public void apply()
    {
    }

    public boolean validateModel()
    {
        return controller.validateModel();
    }


    private class Controller
    {
        private CutterPanelListener cutterPanelListener = new CutterPanelListener();

        private void checkStop()
        {
            final boolean result[] = new boolean[1];
            result[0] = true;

            TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(tabbedPaneCutting)
            {
                @Override
                protected Boolean iterate(Component tab)
                {
                    if (((LinearCutterPanel) tab).getState() != LinearCutterPanel.State.STOPED)
                    {
                        result[0] = false;
                        return false;
                    }
                    return true;
                }

                @Override
                public boolean canNext(Boolean previosResult)
                {
                    return previosResult;
                }
            };
            tabIterator.iterate();
            if (result[0])
            {
                setEnabled(true);
                setState(State.STOPED);
                if (validateModel())
                {
                    FacadeContext.getLinearStripsFacade().saveCuttingModel(getCuttingModel());
                }
            }
        }

        private void cuttingModelChanged()
        {
            List<FurnitureTypeCodePair> pairs = getCuttingModel().getPairs();
            tabbedPaneCutting.removeAll();
            for (FurnitureTypeCodePair pair : pairs)
            {
                LinearCutterController cutterController = new LinearCutterController();
                cutterController.setCuttingModel(getCuttingModel());
                getCuttingModel().addStripsChangedListener(cutterController.getStripsChangedListener());

                LinearCutterPanel cutterPanel = new LinearCutterPanel();
                cutterPanel.setEditable(isEditable());
                cutterPanel.setEnabled(isEnabled());
                cutterPanel.addPropertyChangeListener(LinearCutterPanel.PROPERTY_STATE, cutterPanelListener);
                cutterPanel.addPropertyChangeListener(LinearCutterPanel.PROPERTY_PAIR, cutterController.getStripsChangedListener());
                cutterPanel.setController(cutterController);

                cutterController.setLinearCutterPanel(cutterPanel);

                cutterPanel.setPair(pair);

                tabbedPaneCutting.addTab(StringValueAnnotationProcessor.getProcessor().convert(pair),
                        cutterPanel);
            }
        }

        /**
         * удаляет старую модуль и ставит новую
         */
        private void reattachCuttingModel()
        {
            OrderGroup orderGroup = getCuttingModel().getOrderGroup();
            FacadeContext.getLinearStripsFacade().deleteAllBy(orderGroup);

            LinearCuttingModelCreator cuttingModelCreator = new LinearCuttingModelCreator();
            LinearCuttingModel newModel = cuttingModelCreator.createCuttingModel(orderGroup);
            setCuttingModel(newModel);
        }

        public void generate()
        {
            if (isEditable() && getCuttingModel().getPairs().size() > 0)
            {
                setState(State.STARTED);
                setEnabled(false);

                reattachCuttingModel();

                Runnable runnable = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        TabIterator tabIterator = new TabIterator(tabbedPaneCutting)
                        {
                            @Override
                            protected Object iterate(Component tab)
                            {
                                ((LinearCutterPanel) tab).getController().getStopStartHandler().generate();
                                return null;
                            }
                        };
                        tabIterator.iterate();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        }

        public void stop()
        {
            if (isEditable())
            {
                checkStop();

                Runnable runnable = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        TabIterator tabIterator = new TabIterator(tabbedPaneCutting)
                        {
                            @Override
                            protected Object iterate(Component tab)
                            {
                                ((LinearCutterPanel) tab).getController().getStopStartHandler().stop();
                                return null;
                            }
                        };
                        tabIterator.iterate();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        }

        public boolean validateModel()
        {
            if (isEnabled())
            {
                LinearCuttingModelValidator validator = new LinearCuttingModelValidator();
                ValidationResult validationResult = validator.validate(getCuttingModel());
                if (validationResult.hasErrors())
                {
                    String message = validationResult.getErrors().get(0).formattedText();
                    JOptionPane.showMessageDialog(LinearCuttersPanel.this, validationResult.getErrors().get(0).formattedText(), message,
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                else
                {
                    return true;
                }
            }
            return false;
        }

        private class CutterPanelListener implements PropertyChangeListener
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                controller.checkStop();
            }
        }
    }
}
