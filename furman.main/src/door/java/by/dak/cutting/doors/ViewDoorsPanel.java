package by.dak.cutting.doors;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.*;
import by.dak.draw.Graphics2DUtils;
import org.jhotdraw.app.action.edit.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.draw.io.DOMStorableInputOutputFormat;
import org.jhotdraw.draw.io.InputFormat;
import org.jhotdraw.draw.io.OutputFormat;
import org.jhotdraw.draw.io.SerializationInputOutputFormat;
import org.jhotdraw.draw.tool.ConnectionTool;
import org.jhotdraw.draw.tool.CreationTool;
import org.jhotdraw.draw.tool.TextAreaCreationTool;
import org.jhotdraw.draw.tool.TextCreationTool;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.undo.UndoRedoManager;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 07.08.2009
 * Time: 17:03:48
 * To change this template use File | Settings | File Templates.
 */
public class ViewDoorsPanel extends JPanel
{
    private UndoRedoManager undoManager;
    private DoorsDrawing drawing;
    private DrawingEditor editor;
    private Doors doors;
    java.util.List<DoorDrawing> drawings;

    private static final ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

    /**
     * Creates new instance.
     */
    public ViewDoorsPanel()
    {
        initComponents();
        editor = new DefaultDrawingEditor();
        undoManager = new UndoRedoManager();
        editor.add(view);

        drawing = new DoorsDrawing();

        addCreationButtonsTo(creationToolbar, editor);

        JPopupButton pb = createPopup(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.labels);
        creationToolbar.addSeparator();
        creationToolbar.add(pb);
        creationToolbar.addSeparator();

        view.setDrawing(drawing);
        drawing.addUndoableEditListener(undoManager);

        DOMStorableInputOutputFormat ioFormat = new DOMStorableInputOutputFormat(new MillingFigureFactory());
        SerializationInputOutputFormat sFormat = new SerializationInputOutputFormat();
        LinkedList<InputFormat> inputFormats = new LinkedList<InputFormat>();
        LinkedList<OutputFormat> outputFormats = new LinkedList<OutputFormat>();
        inputFormats.add(ioFormat);
        inputFormats.add(sFormat);
        outputFormats.add(ioFormat);
        outputFormats.add(sFormat);

        drawing.setInputFormats(inputFormats);
        drawing.setOutputFormats(outputFormats);

        GridConstrainer constrainer = new GridConstrainer(10, 10);
        view.setVisibleConstrainer(constrainer);
        view.setConstrainerVisible(true);
        view.setAutoscrolls(true);
    }

    private void addCreationButtonsTo(JToolBar tb, DrawingEditor editor)
    {
        addDefaultCreationButtonsTo(tb, editor,
                ButtonFactory.createDrawingActions(editor),
                ButtonFactory.createSelectionActions(editor));
    }

    public void addDefaultCreationButtonsTo(JToolBar tb, final DrawingEditor editor,
                                            Collection<Action> drawingActions, Collection<Action> selectionActions)
    {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        ButtonFactory.addSelectionToolTo(tb, editor, drawingActions, selectionActions);
        tb.addSeparator();

        AbstractAttributedFigure af;
        CreationTool ct;
        ConnectionTool cnt;
        ConnectionFigure lc;
        HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>();
        attributes.put(STROKE_WIDTH, 2d);

        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new ArcFigure(0, 90), attributes), "edit.createArc0", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new ArcFigure(90, 90), attributes), "edit.createArc90", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new ArcFigure(180, 90), attributes), "edit.createArc180", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new ArcFigure(270, 90), attributes), "edit.createArc270", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new ArcEveryFigure(10, 60), attributes), "edit.createArc", labels);
//        ButtonFactory.addToolTo(tb, editor, new CreationTool(new VProfileJoin(20), attributes), "edit.createVProfile", labels);
//        ButtonFactory.addToolTo(tb, editor, new CreationTool(new HProfileJoin(20), attributes), "edit.createHProfile", labels);

        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new CurveFigure(), attributes), "edit.createLine", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new CurveFigure(false), attributes), "edit.createDistanceLine", labels);
        tb.addSeparator();
        ButtonFactory.addToolTo(tb, editor, new TextCreationTool(new TextFigure()), "edit.createText", labels);
        ButtonFactory.addToolTo(tb, editor, new TextAreaCreationTool(new TextAreaFigure()), "edit.createTextArea", labels);
    }

    public Doors getDoors()
    {
        return doors;
    }

    public void setDoors(Doors doors)
    {
        this.doors = doors;
        drawings = new ArrayList<DoorDrawing>();
        for (Door door : doors.getDoors())
        {
            DoorDrawing doorDrawing;
//            if (door.getDoorDrawing() == null)
//            {
//                doorDrawing = new DoorDrawing();
//                doorDrawing.setDoor(door);
//            }
//            else
//            {
//                doorDrawing = door.getDoorDrawing();
//            }

//            drawings.add(doorDrawing);
        }
        setDrawings(drawings);
    }

    public List<DoorDrawing> getDrawings()
    {
        return drawings;
    }

    public void setDrawings(List<DoorDrawing> doorDrawings)
    {
        view.clearSelection();
        this.drawings = new ArrayList<DoorDrawing>();
        for (DoorDrawing drawing : doorDrawings)
        {
            this.drawings.add((DoorDrawing) drawing.clone());
        }
        drawing.setDrawings(this.drawings);

        view.addPropertyChangeListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                if (evt.getPropertyName().equals("activeHandle") && drawing != null)
                {
                    for (DoorDrawing doorDrawing : drawings)
                    {
                        if (doorDrawing != null && !doorDrawing.getCells().isEmpty())
                        {
                            doorDrawing.getCells().clear();
                        }
                    }
                    view.removePropertyChangeListener(this);
                }
            }
        });
    }


    private JPopupButton createPopup(ResourceBundleUtil labels)
    {
        JPopupButton pb = new JPopupButton();
        pb.setItemFont(UIManager.getFont("MenuItem.font"));
        labels.configureToolBarButton(pb, "actions");

        pb.add(new DuplicateAction());
        pb.addSeparator();
        pb.add(new GroupAction(editor));
        pb.add(new UngroupAction(editor));
        pb.addSeparator();
        pb.add(new BringToFrontAction(editor));
        pb.add(new SendToBackAction(editor));
        pb.addSeparator();
        pb.add(new CutAction());
        pb.add(new CopyAction());
        pb.add(new PasteAction());
        pb.add(new SelectAllAction());
        pb.add(new SelectSameAction(editor));
        pb.addSeparator();
        pb.add(undoManager.getUndoAction());
        pb.add(undoManager.getRedoAction());
        pb.addSeparator();

        pb.add(new ToggleGridAction(editor));
        pb.setFocusable(false);
        pb.addSeparator();
        pb.add(createZoomMenu(labels));
        return pb;
    }

    private JMenu createZoomMenu(ResourceBundleUtil labels)
    {
        JMenu m = new JMenu();
        labels.configureToolBarButton(m, "view.zoomFactor");

        JRadioButtonMenuItem rbmi;
        ButtonGroup group = new ButtonGroup();

        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 0.1, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 0.25, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 0.5, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 0.75, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 1.0, null)));
        rbmi.setSelected(true);
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 1.25, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 1.5, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 2, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 3, null)));
        group.add(rbmi);
        m.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(editor, 4, null)));
        group.add(rbmi);
        return m;
    }

    public DefaultDrawingView getView()
    {
        return view;
    }

    public void setDrawing(Drawing d)
    {
        undoManager.discardAllEdits();
        view.getDrawing().removeUndoableEditListener(undoManager);
        view.setDrawing(d);
        d.addUndoableEditListener(undoManager);
    }

    public DoorsDrawing getDoorsDrawing()
    {
        return drawing;
    }

    public Drawing getDrawing()
    {
        return view.getDrawing();
    }


    public DrawingEditor getEditor()
    {
        return editor;
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
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        jPanel1.add(creationToolbar, gridBagConstraints);

        add(jPanel1, BorderLayout.NORTH);

    }// </draw-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JToolBar creationToolbar;
    private JPanel jPanel1;
    private JScrollPane scrollPane;
    private DefaultDrawingView view;
    // End of variables declaration//GEN-END:variables
}

