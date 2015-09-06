package by.dak.draw.swing;

import by.dak.cutting.draw.Constants;
import by.dak.cutting.swing.DPanel;
import by.dak.draw.Graphics2DUtils;
import by.dak.draw.ScrollDelegationSelectionTool;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.action.ZoomAction;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.undo.UndoRedoManager;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 12.09.2009
 * Time: 16:24:36
 */
public abstract class ADrawPanel extends DPanel
{

    protected DrawingView drawingView;
    protected DrawingEditor drawingEditor;
    private UndoRedoManager undoRedoManager;

    private ResourceBundleUtil resourceBundleUtil;
    private JMenu menuZoom;
    protected JToolBar toolBarAttributes;
    protected JToolBar toolBarFigures;


    public ADrawPanel()
    {
        setLayout(new BorderLayout());
        initComponents();
    }

    protected void initComponents()
    {
        DrawingView drawingView = getDrawingView();
        JScrollPane scrollPane = new JScrollPane((JComponent) drawingView);

        JPanel controlPanel = getControlPanel();

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }


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
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        controlPanel.add(toolBarFigures, gridBagConstraints);

        attributesToolBar.setFloatable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        controlPanel.add(attributesToolBar, gridBagConstraints);
        return controlPanel;
    }

    public DrawingView getDrawingView()
    {
        if (drawingView == null)
        {
            drawingView = new DefaultDrawingView();
            ((DefaultDrawingView) drawingView).setBorder(BorderFactory.createEmptyBorder(0, 0, Graphics2DUtils.OFFSET, Graphics2DUtils.OFFSET));

            drawingView.setVisibleConstrainer(Constants.DEFAULT_GRID_CONSTRAINER);
            drawingView.setConstrainerVisible(true);
            ((DefaultDrawingView) drawingView).setAutoscrolls(true);
        }
        return drawingView;
    }

    public DrawingEditor getDrawingEditor()
    {
        if (drawingEditor == null)
        {
            drawingEditor = new DefaultDrawingEditor();
            drawingEditor.add(getDrawingView());
            //drawingEditor.setActiveView(getDrawingView());
            drawingEditor.setTool(new ScrollDelegationSelectionTool());

        }
        return drawingEditor;
    }

    protected JToolBar getToolBarFigures()
    {
        if (toolBarFigures == null)
        {
            toolBarFigures = createToolBar();
            ButtonFactory.addSelectionToolTo(toolBarFigures, getDrawingEditor());
            fillToolBarFigures();
        }
        return toolBarFigures;
    }

    public JToolBar getToolBarAttributes()
    {
        if (toolBarAttributes == null)
        {
            toolBarAttributes = createToolBar();
            fillToolBarAtributes();
        }
        return toolBarAttributes;
    }

    private JToolBar createToolBar()
    {
        JToolBar toolBar = new JToolBar();
        ButtonGroup group = new ButtonGroup();
        toolBar.putClientProperty("toolButtonGroup", group);
        return toolBar;
    }


    public UndoRedoManager getUndoRedoManager()
    {
        if (undoRedoManager == null)
        {
            undoRedoManager = new UndoRedoManager();
        }
        return undoRedoManager;
    }


    public void setDrawing(Drawing d)
    {
        getUndoRedoManager().discardAllEdits();
        if (getDrawingView().getDrawing() != null)
        {
            getDrawingView().getDrawing().removeUndoableEditListener(getUndoRedoManager());
        }


        getDrawingView().setDrawing(d);
        d.addUndoableEditListener(getUndoRedoManager());
        ((JComponent) getDrawingView()).setBounds(0, 0,
                getDrawingView().drawingToView(d.getDrawingArea()).width,
                getDrawingView().drawingToView(d.getDrawingArea()).height);

//        getDrawingView().setScaleFactor(Math.min(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds().getHeight() / d.getBounds().getHeight(),
//                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds().getWidth() / d.getBounds().getWidth()));
        getDrawingView().setScaleFactor(1);
    }

    public Drawing getDrawing()
    {
        return getDrawingView().getDrawing();
    }


    private JMenu getMenuZoom()
    {
        if (menuZoom == null)
        {
            menuZoom = new JMenu();
            getResourceBundleUtil().configureToolBarButton(menuZoom, "view.zoomFactor");

            JRadioButtonMenuItem rbmi;
            ButtonGroup group = new ButtonGroup();

            menuZoom.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), 0.1, null)));
            group.add(rbmi);
            menuZoom.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), 0.25, null)));
            group.add(rbmi);
            menuZoom.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), 0.5, null)));
            group.add(rbmi);
            menuZoom.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), 0.75, null)));
            group.add(rbmi);
            menuZoom.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), 1.0, null)));
            rbmi.setSelected(true);
            group.add(rbmi);
            menuZoom.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), 1.25, null)));
            group.add(rbmi);
            menuZoom.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), 1.5, null)));
            group.add(rbmi);
            menuZoom.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), 2, null)));
            group.add(rbmi);
            menuZoom.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), 3, null)));
            group.add(rbmi);
            menuZoom.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getDrawingEditor(), 4, null)));
            group.add(rbmi);
            return menuZoom;
        }
        return menuZoom;
    }


    public void setResourceBundleUtil(ResourceBundleUtil resourceBundleUtil)
    {
        this.resourceBundleUtil = resourceBundleUtil;
    }


    public ResourceBundleUtil getResourceBundleUtil()
    {
        if (resourceBundleUtil == null)
        {
            resourceBundleUtil = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        }
        return resourceBundleUtil;
    }


    protected abstract void fillToolBarFigures();

    protected void fillToolBarAtributes()
    {
        JPopupButton pb = new JPopupButton();
        pb.setItemFont(UIManager.getFont("MenuItem.font"));
        getResourceBundleUtil().configureToolBarButton(pb, "actions");
        pb.add(getMenuZoom());
        getToolBarAttributes().add(pb);
    }
}
