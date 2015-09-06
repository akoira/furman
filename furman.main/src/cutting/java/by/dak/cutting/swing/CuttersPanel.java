/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

/*
 * CuttersPanel.java
 *
 * Created on 30.01.2009, 10:59:16
 */

package by.dak.cutting.swing;

import by.dak.cutting.cut.facade.ABoardStripsFacade;
import by.dak.cutting.facade.AOrderBoardDetailFacade;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.swing.TabIterator;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideTabbedPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

/**
 * @author admin
 */
public class CuttersPanel extends DPanel
{
    public static final String PROPERTY_STATE = "mainCuttingPanelState";
    public static final String PROPERTY_cuttingModel = "cuttingModel";
    /**
     * Event will be thrown when the cutting is finished succesfully. new value is true.
     * Need for start other cutting process or change editable other panels
     */
    public static final String PROPERTY_cuttingFinishedSuccessfully = "cuttingFinishedSuccessfully";

    /**
     * The event will be thrown when the cutting has been started. new value is true
     * Need to determine that some cutting is started
     */
    public static final String PROPERTY_cuttingStarted = "cuttingStarted";


    private JButton generateButton;
    private JButton stopButton;
    private JButton reloadButton;
    private com.jidesoft.swing.JideTabbedPane tabbedPaneCutting;
    private CuttingModel cuttingModel;

    private Controller controller = new Controller();


    private PropertyChangeListener cutterPanelListener;
    private ABoardStripsFacade stripsFacade;
    private AOrderBoardDetailFacade orderBoardDetailFacade;


    public static enum State
    {
        STARTED,
        STOPED
    }

    private State state = State.STOPED;


    public CuttersPanel()
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
                        CutterPanel cutterPanel = (CutterPanel) tab;
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
                        CutterPanel cutterPanel = (CutterPanel) tab;
                        cutterPanel.setEnabled(isEnabled());
                        return null;
                    }
                };
                tabIterator.iterate();
            }
        });


        cutterPanelListener = new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                controller.checkStop();
            }
        };
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

        ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(CuttersPanel.class, this);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(CuttersPanel.class);

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


    public CuttingModel getCuttingModel()
    {
        return cuttingModel;
    }

    public void setCuttingModel(CuttingModel cuttingModel)
    {
        CuttingModel old = this.cuttingModel;
        this.cuttingModel = cuttingModel;
        firePropertyChange("cuttingModel", old, cuttingModel);
    }

    public JideTabbedPane getTabbedPaneCutting()
    {
        return tabbedPaneCutting;
    }

    public void removeAllCutter()
    {
        TabIterator iterator = new TabIterator(this.tabbedPaneCutting)
        {
            @Override
            protected Object iterate(Component tab)
            {
                tab.removePropertyChangeListener("state", cutterPanelListener);
                return null;
            }
        };
        iterator.iterate();
        tabbedPaneCutting.removeAll();
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
        controller.reload();
    }

    @org.jdesktop.application.Action
    public void apply()
    {
        controller.apply();
    }


    public boolean validateModel()
    {
        return controller.validate();
    }


    public void addCutterPanelBy(TextureBoardDefPair pair)
    {
        CutterPanel cutterPanel = new CutterPanel();
        cutterPanel.setEditable(isEditable());
        cutterPanel.setEnabled(isEnabled());

        cutterPanel.setCuttingModel(getCuttingModel());
        cutterPanel.setTextureBoardDefPair(pair);

        cutterPanel.addPropertyChangeListener(CutterPanel.PROPERTY_STATE, cutterPanelListener);

        tabbedPaneCutting.addTab(StringValueAnnotationProcessor.getProcessor().convert(pair),
                cutterPanel);
    }

    public class Controller
    {
        private void checkStop()
        {
            final boolean result[] = new boolean[1];
            result[0] = true;

            TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(tabbedPaneCutting)
            {
                @Override
                protected Boolean iterate(Component tab)
                {
                    if (((CutterPanel) tab).getState() != CutterPanel.State.STOPED)
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
                validateModel();
            }
        }

        /**
         * проверяет валидацию для нажатия next в orderWizard.
         * проходит по всем tab'ам и проверяет валидность раскроя каждого таба
         * если в буффере есть элемент или на раскрое остался элемент не привязанный к сегменту, то не валидно
         *
         * @return
         */
        private boolean validateBuffer()
        {
            final boolean result[] = new boolean[1];
            result[0] = true;

            TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(tabbedPaneCutting)
            {
                @Override
                protected Boolean iterate(Component tab)
                {
                    if (!((CutterPanel) tab).isValidCutter())
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
            if (!result[0])
            {
                ResourceMap map = Application.getInstance().getContext().getResourceMap(CuttingModelValidator.class);
                String message = map.getString("validator.size");
                String title = map.getString("validator.size");
                JOptionPane.showMessageDialog(CuttersPanel.this, message, title,
                        JOptionPane.ERROR_MESSAGE);
            }
            return result[0];
        }

        /**
         * валидация буффера и модели
         *
         * @return
         */
        private boolean validate()
        {
            if (isEditable())
            {
                if (getState() == CuttersPanel.State.STOPED &&
                        validateBuffer())
                {
                    apply();
                    return validateModel();
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return true;
            }

        }

        private boolean validateModel()
        {
            CuttingModelValidator cuttingModelValidator = new CuttingModelValidator();
            cuttingModelValidator.setBoardDetailFacade(getOrderBoardDetailFacade());
            ValidationResult validationResult = cuttingModelValidator.validate(getCuttingModel());
            if (validationResult.hasErrors())
            {
                String message = validationResult.getErrors().get(0).formattedText();
                JOptionPane.showMessageDialog(CuttersPanel.this, validationResult.getErrors().get(0).formattedText(), message,
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            else
            {
                getStripsFacade().saveAll(getCuttingModel());
                firePropertyChange(PROPERTY_cuttingFinishedSuccessfully, null, Boolean.TRUE);
                return true;
            }
        }

        private void cuttingModelChanged()
        {
            removeAllCutter();
            List<TextureBoardDefPair> pairs = getCuttingModel().getPairs();
            for (TextureBoardDefPair pair : pairs)
            {
                addCutterPanelBy(pair);
            }
        }


        @org.jdesktop.application.Action
        public void generate()
        {
            if (isEditable())
            {
                setState(State.STARTED);
                setEnabled(false);

                getStripsFacade().deleteAll(getCuttingModel().getOrder());

                getCuttingModel().setSettingsMap(getStripsFacade().loadCutSettings(cuttingModel.getOrder(), cuttingModel.getPairs()));
                getCuttingModel().setStripsMap(Collections.EMPTY_MAP);

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
                                ((CutterPanel) tab).getStopStartHandler().generate();
                                return null;
                            }
                        };
                        tabIterator.iterate();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
            firePropertyChange(PROPERTY_cuttingStarted, null, Boolean.TRUE);
        }

        @org.jdesktop.application.Action
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
                                ((CutterPanel) tab).getStopStartHandler().stop();
                                return null;
                            }
                        };
                        tabIterator.iterate();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        }

        @org.jdesktop.application.Action
        public void reload()
        {
            if (isEditable() && isEnabled() && getState() == State.STOPED)
            {
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
                                ((CutterPanel) tab).reload();
                                return null;
                            }
                        };
                        tabIterator.iterate();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        }

        @org.jdesktop.application.Action
        public void apply()
        {
            if (isEditable() && isEnabled() && getState() == State.STOPED)
            {
                TabIterator tabIterator = new TabIterator(tabbedPaneCutting)
                {
                    @Override
                    protected Object iterate(Component tab)
                    {
                        ((CutterPanel) tab).apply();
                        return null;
                    }
                };
                tabIterator.iterate();
            }
        }

    }

    public ABoardStripsFacade getStripsFacade()
    {
        return stripsFacade;
    }

    public void setStripsFacade(ABoardStripsFacade stripsFacade)
    {
        this.stripsFacade = stripsFacade;
    }

    public AOrderBoardDetailFacade getOrderBoardDetailFacade()
    {
        return orderBoardDetailFacade;
    }

    public void setOrderBoardDetailFacade(AOrderBoardDetailFacade orderBoardDetailFacade)
    {
        this.orderBoardDetailFacade = orderBoardDetailFacade;
    }


}
