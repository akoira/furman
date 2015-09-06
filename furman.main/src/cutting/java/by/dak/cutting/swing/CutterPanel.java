/*
 * CutterFrame.java
 *
 * Created on 15. �nor 2008, 14:39
 */

package by.dak.cutting.swing;


import by.dak.cutting.DialogShowers;
import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.gui.cuttingApp.CSawyer;
import by.dak.cutting.cut.gui.cuttingApp.CVerticalSawyer;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.cut.gui.cuttingApp.Sawyer;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.measurement.utils.Watch;
import by.dak.cutting.cut.swing.NeedStop;
import by.dak.cutting.swing.buffer.CutterBufferController;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.cut.SheetDimentionItem;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Board;
import by.dak.swing.TabIterator;
import by.dak.swing.infonode.RootWindowProperty;
import by.dak.swing.table.ListNaviTable;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import com.jidesoft.swing.JideTabbedPane;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.View;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.event.FigureSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * @author Peca
 */
public class CutterPanel extends DPanel
{
    private RootWindow rootWindow;
    public static final String PROPERTY_STATE = "cutterPanelState";
    @Deprecated
    public static final String PROPERTY_boardChanged = "boardChanged";

    private State state = State.STOPED;
    private java.util.Timer timer = new java.util.Timer();

    private JProgressBar barWaste = new JProgressBar();
    private JProgressBar barProgress = new JProgressBar();
    private JideTabbedPane tabbedPane = new JideTabbedPane();

    private ListNaviTable<ElementFigure> bufferNaviTable;

    private JLabel labelWaste;
    private JLabel labelProgress;
    private TimerTask cuttingTimer;
    private Watch watch = new Watch();

    //not null
    private CuttingModel cuttingModel;

    //not null
    private TextureBoardDefPair textureBoardDefPair;

    private CSawyer sawyer = null;

    private StopStartHandler stopStartHandler = new StopStartHandler();
    private Controller controller = new Controller();

    private CutterBufferController cutterBufferController = new CutterBufferController(this);

    private PropertyChangeListener stripsChangedListener = new PropertyChangeListener()
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            if (evt.getNewValue().equals(getTextureBoardDefPair()))
            {
                controller.stripsChanged();
            }
        }
    };


    public CutterPanel()
    {
        initNaviTable();
        initComponents();
        initEnv();
    }


    private void initEnv()
    {
        addHierarchyListener(new HierarchyListener()
        {
            @Override
            public void hierarchyChanged(HierarchyEvent e)
            {
                if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) > 1 && isShowing())
                {
                    RootWindowProperty.restore(rootWindow);
                }
            }
        });

        addPropertyChangeListener("enabled", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                controller.updateEnabled();
            }
        });

        addPropertyChangeListener("editable", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                controller.updateEditable();
            }
        });

        addPropertyChangeListener("cuttingModel", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                controller.cuttingModelChanged(evt);
            }
        });

        addPropertyChangeListener("textureBoardDefPair", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                controller.stripsChanged();
            }
        });

        cutterBufferController.getFigureClipboard().addPropertyChangeListener("index", new NaviTableIndexListener());
        tabbedPane.addChangeListener(cutterBufferController.getTabChangedListener());
    }

    public ListNaviTable<ElementFigure> getBufferNaviTable()
    {
        return bufferNaviTable;
    }

    private void initNaviTable()
    {
        bufferNaviTable = new ListNaviTable<ElementFigure>();
        bufferNaviTable.setListUpdater(cutterBufferController.getFigureClipboard().getListUpdater());
        bufferNaviTable.setPreferredSize(new Dimension(150, 400));
        bufferNaviTable.setShowFilterHeader(false);
        bufferNaviTable.setShowNaviControls(false);
        bufferNaviTable.setSortable(false);
        bufferNaviTable.init();
    }

    private void initComponents()
    {
        ViewMap viewMap = new ViewMap(initViews());

        rootWindow = DockingUtil.createRootWindow(viewMap, true);
        rootWindow.setName(CutterPanel.class.getSimpleName());
        layoutViews(viewMap, rootWindow);

        setLayout(new BorderLayout());
        add(rootWindow);
    }

    private void layoutViews(ViewMap viewMap, RootWindow rootWindow)
    {
        rootWindow.setWindow(new SplitWindow(true, 0.8f, viewMap.getView(0), viewMap.getView(1)));
        rootWindow.getWindowBar(Direction.RIGHT).setEnabled(true);
    }

    private View[] initViews()
    {
        View[] views = new View[2];

        views[0] = initCuttingView();
        views[1] = initBufferNaviView();

        disableHighlightedButtons(views);

        return views;
    }

    private View initCuttingView()
    {
        JPanel cuttingPanel = initCuttingPanel();
        View cuttingView = new View(getResourceMap().getString("cuttingPanel.title"),
                getResourceMap().getIcon("cuttingPanel.icon"), cuttingPanel);

        cuttingView.getWindowProperties().setCloseEnabled(false);
        cuttingView.getWindowProperties().setDockEnabled(false);
        cuttingView.getWindowProperties().setUndockEnabled(false);
        cuttingView.getWindowProperties().setDragEnabled(false);
        cuttingView.getWindowProperties().setMinimizeEnabled(false);

        return cuttingView;
    }

    private View initBufferNaviView()
    {
        View bufferNaviView = new View(getResourceMap().getString("bufferNaviTable.title"),
                getResourceMap().getIcon("bufferNaviTable.icon"), bufferNaviTable);

        bufferNaviView.getWindowProperties().setCloseEnabled(false);
        bufferNaviView.getWindowProperties().setDockEnabled(false);
        bufferNaviView.getWindowProperties().setUndockEnabled(false);

        return bufferNaviView;
    }

    private void disableHighlightedButtons(View[] views)
    {
        for (View view : views)
        {
            view.getWindowProperties().getTabProperties().getHighlightedButtonProperties().
                    getDockButtonProperties().setVisible(false);
            view.getWindowProperties().getTabProperties().getHighlightedButtonProperties().
                    getMinimizeButtonProperties().setVisible(false);
            view.getWindowProperties().getTabProperties().getHighlightedButtonProperties().
                    getRestoreButtonProperties().setVisible(false);
            view.getWindowProperties().getTabProperties().getHighlightedButtonProperties().
                    getUndockButtonProperties().setVisible(false);
        }
    }

    private JPanel initCuttingPanel()
    {
        JPanel cuttingPanel = new JPanel();

        tabbedPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        labelWaste = new JLabel();
        labelProgress = new JLabel();

        labelWaste.setText(getResourceMap().getString("label.waste.text"));
        labelProgress.setText(getResourceMap().getString("label.progress.text"));
        barWaste.setMaximum(100);
        barWaste.setMaximum(0);
        barWaste.setValue(0);

        javax.swing.GroupLayout cuttingPanelLayout = new javax.swing.GroupLayout(cuttingPanel);
        cuttingPanel.setLayout(cuttingPanelLayout);


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
                                .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                                .addContainerGap())
        );

        cuttingPanelLayout.setHorizontalGroup(
                cuttingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cuttingPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(cuttingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
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

        return cuttingPanel;
    }

    public synchronized State getState()
    {
        return state;
    }

    private synchronized void setState(State state)
    {
        State old = this.state;
        this.state = state;
        firePropertyChange(PROPERTY_STATE, old, this.state);
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

    public boolean isValidCutter()
    {
        if (cutterBufferController.hasBufferedElements())
        {
            return false;
        }
        else
        {
            final boolean result[] = new boolean[1];
            result[0] = true;

            TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(tabbedPane)
            {
                @Override
                protected Boolean iterate(Component tab)
                {
                    TopSegmentPanel topSegmentPanel = (TopSegmentPanel) tab;
                    if (topSegmentPanel.getCuttingDrawing().hasUnboundElements())
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
            return result[0];
        }
    }

    public TextureBoardDefPair getTextureBoardDefPair()
    {
        return textureBoardDefPair;
    }

    public void setTextureBoardDefPair(TextureBoardDefPair textureBoardDefPair)
    {
        TextureBoardDefPair old = this.textureBoardDefPair;
        this.textureBoardDefPair = textureBoardDefPair;
        firePropertyChange("textureBoardDefPair", old, textureBoardDefPair);

    }

    @Deprecated
    public void addBoardChangedListener(PropertyChangeListener listener)
    {
        this.addPropertyChangeListener(PROPERTY_boardChanged, listener);
    }

    @Deprecated
    public void removeBoardChangedListener(PropertyChangeListener listener)
    {
        this.removePropertyChangeListener(PROPERTY_boardChanged, listener);
    }

    public Strips getStrips()
    {
        return getCuttingModel().getStrips(getTextureBoardDefPair());
    }

    public StopStartHandler getStopStartHandler()
    {
        return stopStartHandler;
    }

    public void reload()
    {
        cutterBufferController.getFigureClipboard().getListUpdater().getList().clear();
        controller.reload();
    }

    public void apply()
    {
        controller.apply();
    }

    public class Controller
    {
        public void sortRedSegments()
        {
            final List<Segment> segments = FacadeContext.getBoardFacade().getWholeGraySegmentsBy(getStrips());
            if (segments.size() < 2)
            {
                return;
            }
            SwingWorker swingWorker = new SwingWorker()
            {
                @Override
                protected Object doInBackground() throws Exception
                {
                    final CVerticalSawyer sawyer = new CVerticalSawyer(FacadeContext.getBoardFacade().getWholeRedSegmentsBy(getStrips()),
                            segments, getStrips().getSawWidth());

                    sawyer.start();
                    sawyer.setNewSolutionListener(new Sawyer.INewSolutionListener()
                    {
                        @Override
                        public void newSolutionFound(Strips strips)
                        {
                            final Strips result = getStrips();
                            List<Segment> wholeGraySegments = FacadeContext.getBoardFacade().getWholeGraySegmentsBy(result);

                            while (wholeGraySegments.size() > strips.getSegmentsCount())
                            {
                                Segment segment = wholeGraySegments.get(strips.getSegmentsCount());
                                segment.clear(false);
                                getStrips().removeSegment(segment);
                                wholeGraySegments.remove(segment);
                            }

                            for (int i = 0; i < strips.getSegmentsCount(); i++)
                            {
                                Segment segment = strips.getSegment(i);
                                Element[] elements = segment.getElements();
                                Segment resultSegment = wholeGraySegments.get(i);
                                resultSegment.clear(false);
                                for (Element element : elements)
                                {
                                    resultSegment.addSegment(((Segment) element.getId()).clone());
                                }
                            }

                            result.sort();
                            Runnable runnable = new Runnable()
                            {
                                public void run()
                                {
                                    getCuttingModel().putStrips(getTextureBoardDefPair(), result);
                                }
                            };
                            SwingUtilities.invokeLater(runnable);
                        }
                    });
                    int count = getStrips().getSegmentsCount();
                    long stopTime = System.currentTimeMillis() + count * 1000;
                    while (sawyer.getState() != Sawyer.States.STOPPED)
                    {
                        if (System.currentTimeMillis() >= stopTime)
                        {
                            sawyer.stop();
                        }
                        try
                        {
                            Thread.sleep(10);
                        }
                        catch (InterruptedException ex)
                        {
                            ex.printStackTrace();
                            break;
                        }
                    }
                    return null;
                }
            };
            DialogShowers.showWaitDialog(swingWorker, CutterPanel.this);

        }


        private String calcTitleBy(int index)
        {
            Board board = getBoard(index);
            String title;
            if (board == null || (textureBoardDefPair.getBoardDef().equals(board.getBoardDef()) &&
                    textureBoardDefPair.getTexture().equals(board.getTexture())))
                title = getResourceMap().getString("tab.name", index + 1);
            else
                title = getResourceMap().getString("tab.name.board", index + 1,
                        StringValueAnnotationProcessor.getProcessor().convert(board.getTexture()),
                        StringValueAnnotationProcessor.getProcessor().convert(board.getBoardDef()));
            return title;
        }

        private Board getBoard(int index)
        {
            Strips strips = getCuttingModel().getStrips(getTextureBoardDefPair());
            Segment segment = strips.getSegments().get(index);
            if (segment != null)
            {
                DimensionItem dimensionItem = segment.getDimensionItem();
                if (dimensionItem instanceof SheetDimentionItem)
                {
                    SheetDimentionItem sheetDimentionItem = (SheetDimentionItem) dimensionItem;
                    return sheetDimentionItem.getBoard();
                }
            }
            return null;
        }


        private void stripsChanged()
        {
            if (!cutterBufferController.hasBufferedElements())
            {
                tabbedPane.removeAll();
                if (getCuttingModel() != null && getTextureBoardDefPair() != null)
                {
                    Strips strips = getCuttingModel().getStrips(getTextureBoardDefPair());
                    if (strips != null)
                    {
                        int i = 0;
                        for (Segment segment : strips.getSegments())
                        {
                            if (segment.getElements() != null)
                            {
                                TopSegmentPanel topSegmentPanel = createTopSegmentPanel();
                                topSegmentPanel.setSegment(segment);
                                String title = calcTitleBy(i);
                                tabbedPane.addTab(title, topSegmentPanel);
                                i++;
                            }
                        }
                        updateBars();
                    }
                }
            }
            else
            {
                if (getCuttingModel() != null)
                {
                    int lastSegmentndex = getCuttingModel().getStrips(getTextureBoardDefPair()).getSegmentsCount() - 1;
                    if (getCuttingModel().getStrips(getTextureBoardDefPair()).getSegmentsCount() != tabbedPane.getTabCount())
                    {
                        String title = calcTitleBy(lastSegmentndex);
                        TopSegmentPanel topSegmentPanel = createTopSegmentPanel();
                        topSegmentPanel.setSegment(getCuttingModel().getStrips(getTextureBoardDefPair()).getSegment(lastSegmentndex));
                        tabbedPane.addTab(title, topSegmentPanel);
                    }
                    updateBars();
                }
            }
        }

        private TopSegmentPanel createTopSegmentPanel()
        {
            TopSegmentPanel topSegmentPanel = new TopSegmentPanel();
            topSegmentPanel.setCutter(getTextureBoardDefPair().getBoardDef().getCutter());
            topSegmentPanel.setParentCutterPanel(CutterPanel.this);
            topSegmentPanel.setEnabled(isEnabled());
            topSegmentPanel.setEditable(isEditable());
            topSegmentPanel.setFigureClipboard(cutterBufferController.getFigureClipboard());
            return topSegmentPanel;
        }


        public void updateBars()
        {
            int value = 0;
            if (getTextureBoardDefPair() != null)
            {
                Strips strips = getCuttingModel().getStrips(getTextureBoardDefPair());
                if (strips != null)
                {
                    value = 100 - (int) (strips.getWasteRatio() * 100);
                }
            }
            barWaste.setMaximum(100);
            barWaste.setMinimum(0);
            barWaste.setValue(value);
        }

        private void cuttingModelChanged(PropertyChangeEvent evt)
        {
            if (evt.getOldValue() != null && evt.getOldValue() instanceof CuttingModel)
            {
                ((CuttingModel) evt.getOldValue()).removeStripsChangedListener(stripsChangedListener);
            }

            if (getCuttingModel() != null)
            {
                getCuttingModel().addStripsChangedListener(stripsChangedListener);
            }
            stripsChanged();
        }

        private void initSawyer()
        {

            sawyer = new CSawyer();
            sawyer.setForceBestFit(true);
            sawyer.setForceMinimumRatio(0.2f);
            sawyer.setForceMigrationRatio(0.5f);

            sawyer.setNewSolutionListener(new Sawyer.INewSolutionListener()
            {

                public void newSolutionFound(final Strips strips)
                {
                    Runnable runnable = new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            getCuttingModel().putStrips(getTextureBoardDefPair(), strips);
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            });

            sawyer.setCutSettings(getCuttingModel().getCutSettings(getTextureBoardDefPair()));
        }

        private void updateEnabled()
        {
            TabIterator tabIterator = new TabIterator(tabbedPane)
            {
                @Override
                protected Object iterate(Component tab)
                {
                    TopSegmentPanel topSegmentPanel = (TopSegmentPanel) tab;
                    topSegmentPanel.setEnabled(isEnabled());
                    return null;
                }
            };
            tabIterator.iterate();
        }


        private void updateEditable()
        {
            TabIterator tabIterator = new TabIterator(tabbedPane)
            {
                @Override
                protected Object iterate(Component tab)
                {
                    TopSegmentPanel topSegmentPanel = (TopSegmentPanel) tab;
                    topSegmentPanel.setEditable(isEditable());
                    return null;
                }
            };
            tabIterator.iterate();
        }

        public void reload()
        {
            TabIterator tabIterator = new TabIterator(tabbedPane)
            {
                @Override
                protected Object iterate(Component tab)
                {
                    TopSegmentPanel topSegmentPanel = (TopSegmentPanel) tab;
                    topSegmentPanel.reload();
                    return null;
                }
            };
            tabIterator.iterate();
        }

        public void apply()
        {
            final List<TopSegmentPanel> removed = new ArrayList<TopSegmentPanel>();
            TabIterator tabIterator = new TabIterator(tabbedPane)
            {
                @Override
                protected Object iterate(Component tab)
                {
                    TopSegmentPanel topSegmentPanel = (TopSegmentPanel) tab;

                    Strips strips = getCuttingModel().getStrips(getTextureBoardDefPair());
                    int index = strips.getSegments().indexOf(topSegmentPanel.getSegment());

                    Segment newSegment = topSegmentPanel.apply();
                    if (newSegment != null)
                    {
                        strips.replaceSegment(index, newSegment);
                        topSegmentPanel.setSegment(newSegment);
                        getCuttingModel().fireRefresh();
                    }

                    Segment segment = strips.getSegment(index);
                    if (segment.getItemsCount() == 0)
                    {
                        strips.removeSegment(index);
                        FacadeContext.getBoardFacade().releaseBy(segment);
                        removed.add(topSegmentPanel);
                        getCuttingModel().fireRefresh();
                    }
                    return null;
                }
            };
            tabIterator.iterate();

            for (TopSegmentPanel topSegmentPanel : removed)
            {
                int index = tabbedPane.indexOfComponent(topSegmentPanel);
                tabbedPane.removeTabAt(index);
            }
        }
    }

    public void addCuttingDrawingViewHandler(FigureSelectionListener cuttingDrawingViewHandler)
    {
        for (Component component : tabbedPane.getComponents())
        {
            if (component instanceof TopSegmentPanel)
            {
                DrawingView drawingView = ((TopSegmentPanel) component).getView();
                drawingView.addFigureSelectionListener(cuttingDrawingViewHandler);
            }
        }
    }

    public void removeCuttingDrawingViewHandler(FigureSelectionListener cuttingDrawingViewHandler)
    {
        for (Component component : tabbedPane.getComponents())
        {
            if (component instanceof TopSegmentPanel)
            {
                DrawingView drawingView = ((TopSegmentPanel) component).getView();
                drawingView.removeFigureSelectionListener(cuttingDrawingViewHandler);
            }
        }
    }

    public class StopStartHandler
    {
        public void stop()
        {
            if (sawyer != null)
            {
                sawyer.stop();

                SwingWorker swingWorker = new SwingWorker()
                {
                    @Override
                    protected Object doInBackground() throws Exception
                    {
                        while (sawyer.getState() != Sawyer.States.STOPPED)
                        {
                            try
                            {
                                Thread.sleep(10);
                            }
                            catch (InterruptedException ex)
                            {
                                ex.printStackTrace();
                                break;
                            }
                        }
                        return null;
                    }
                };
                DialogShowers.showWaitDialog(swingWorker, CutterPanel.this);


                watch.stop();
                //сортируем по вертикали
                CutterPanel.this.controller.sortRedSegments();
                cuttingTimer.cancel();
            }
            setState(State.STOPED);
        }

        public void generate()
        {
            //очищаем содержимое буфера
            cutterBufferController.getFigureClipboard().getListUpdater().getList().clear();
            controller.initSawyer();
            if (sawyer == null)
                return;


            sawyer.start();
            watch.reset();
            watch.start();
            cuttingTimer = new TimerTask()
            {
                @Override
                public void run()
                {
                    Runnable runnable = new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Strips strips = getCuttingModel().getStrips(getTextureBoardDefPair());
                            if (strips != null)
                            {
                                barProgress.setValue((int) watch.getDurationInSeconds());
                                barProgress.setMinimum(0);
                                barProgress.setMaximum((int) (strips.getSegmentsCount() * by.dak.cutting.configuration.Constants.TIME_FOR_STRIP));
                                NeedStop needStop = new NeedStop(watch.getDurationInSeconds(), strips);
                                if (needStop.need() &&
                                        sawyer.getState() != Sawyer.States.STOPPED &&
                                        sawyer.getState() != Sawyer.States.STOPPING
                                        )
                                {
                                    stop();
                                }
                            }
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            };
            setState(State.STARTED);
            timer.schedule(cuttingTimer, 0, 1000);
        }
    }

    public static enum State
    {
        STOPED,
        STARTED
    }

    private class NaviTableIndexListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            int index = (Integer) evt.getNewValue();
            bufferNaviTable.getTable().setRowSelectionInterval(index, index);
        }
    }


}
