package by.dak.cutting.doors;

import by.dak.cutting.doors.profile.draw.HProfileJoin;
import by.dak.cutting.doors.profile.draw.VProfileJoin;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.*;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
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
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 10.08.2009
 * Time: 16:26:20
 * To change this template use File | Settings | File Templates.
 */
public class EditDoorPanel extends JPanel
{
    private UndoRedoManager undoManager;
    private DoorDrawing drawing;
    private Door door;
    private DrawingEditor editor;
    private boolean isCellEdit;

    /**
     * Creates new instance.
     */
    public EditDoorPanel(boolean isCellEdit, Door door)
    {
        this.isCellEdit = isCellEdit;
        this.door = door;
        initComponents();
        undoManager = new UndoRedoManager();
        editor = new DefaultDrawingEditor();
        editor.add(view);

        DoorDrawing doorDrawing = new DoorDrawing();
        doorDrawing.setDoor(door);
        drawing = doorDrawing;

        if (!isCellEdit)
        {
            view.addPropertyChangeListener(new PropertyChangeListener()
            {
                @Override
                public void propertyChange(PropertyChangeEvent evt)
                {
                    if (evt.getPropertyName().equals("activeHandle") && drawing != null && !drawing.getCells().isEmpty())
                    {
                        drawing.getCells().clear();
                        view.removePropertyChangeListener(this);
                    }
                }
            });
        }
        else
        {
            addCellButtonTo(creationToolbar, editor, door);
        }

        addCreationButtonsTo(creationToolbar, editor);

        JPopupButton pb = createPopup(AttributeKeys.labels);
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

    private JPopupButton createPopup(ResourceBundleUtil labels)
    {
        JPopupButton pb = new JPopupButton();
        pb.setItemFont(UIManager.getFont("MenuItem.font"));
        labels.configureToolBarButton(pb, "actions");
        if (!isCellEdit)
        {
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
        }
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

    public void setDoor(boolean isCellEdit, Door door)
    {
        this.isCellEdit = isCellEdit;
        this.door = door;

        if (!isCellEdit)
        {
            view.addPropertyChangeListener(new PropertyChangeListener()
            {
                @Override
                public void propertyChange(PropertyChangeEvent evt)
                {
                    if (evt.getPropertyName().equals("activeHandle") && drawing != null && !drawing.getCells().isEmpty())
                    {
                        drawing.getCells().clear();
                        view.removePropertyChangeListener(this);
                    }
                }
            });
        }
        //setDrawing(door.getDoorDrawing());
    }

    public void setDrawing(Drawing d)
    {
        if (d instanceof DoorDrawing)
        {
            drawing = (DoorDrawing) d;
        }
        undoManager.discardAllEdits();
        view.getDrawing().removeUndoableEditListener(undoManager);
        view.setDrawing(d);
        d.addUndoableEditListener(undoManager);
        view.setBounds(0, 0, view.drawingToView(d.getDrawingArea()).x, view.drawingToView(d.getDrawingArea()).y);
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

        toolButtonGroup = new ButtonGroup();
        scrollPane = new JScrollPane();
        view = new DefaultDrawingView();
        view.setBorder(BorderFactory.createEmptyBorder(0, 0, Graphics2DUtils.OFFSET, Graphics2DUtils.OFFSET));
        jPanel1 = new JPanel();
        creationToolbar = new JToolBar();
        attributesToolbar = new JToolBar();

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

        attributesToolbar.setFloatable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(attributesToolbar, gridBagConstraints);

        add(jPanel1, BorderLayout.NORTH);

    }// </draw-fold>//GEN-END:initComponents

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
        if (!isCellEdit)
        {
            ButtonFactory.addToolTo(tb, editor, new CreationTool(new ArcFigure(0, 90), attributes), "edit.createArc0", labels);
            ButtonFactory.addToolTo(tb, editor, new CreationTool(new ArcFigure(90, 90), attributes), "edit.createArc90", labels);
            ButtonFactory.addToolTo(tb, editor, new CreationTool(new ArcFigure(180, 90), attributes), "edit.createArc180", labels);
            ButtonFactory.addToolTo(tb, editor, new CreationTool(new ArcFigure(270, 90), attributes), "edit.createArc270", labels);
            ButtonFactory.addToolTo(tb, editor, new CreationTool(new ArcEveryFigure(10, 60), attributes), "edit.createArc", labels);
            ButtonFactory.addToolTo(tb, editor, new CreationTool(new VProfileJoin(20), attributes), "edit.createVProfile", labels);
            ButtonFactory.addToolTo(tb, editor, new CreationTool(new HProfileJoin(20), attributes), "edit.createHProfile", labels);

            ButtonFactory.addToolTo(tb, editor, new CreationTool(new CurveFigure(), attributes), "edit.createLine", labels);
            ButtonFactory.addToolTo(tb, editor, new CreationTool(new CurveFigure(false), attributes), "edit.createDistanceLine", labels);
            tb.addSeparator();
            ButtonFactory.addToolTo(tb, editor, new TextCreationTool(new TextFigure()), "edit.createText", labels);
            ButtonFactory.addToolTo(tb, editor, new TextAreaCreationTool(new TextAreaFigure()), "edit.createTextArea", labels);
        }
    }

    private void addCellButtonTo(final JToolBar bar, final DrawingEditor editor, final Door door)
    {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        bar.add(new DefaultAttributeAction(editor, AttributeKeys.GLUEING,
                labels.getIconProperty("attribute.texture", ButtonFactory.class))
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                java.util.List<Figure> figures = SplitUtil.closedFigures(new ArrayList<Figure>(editor.getActiveView().getSelectedFigures()));
                if (!figures.isEmpty())
                {
                    GeneralPath path = SplitUtil.closedShape(figures);

                    Window w = SwingUtilities.getWindowAncestor(getParent());
                    CellDialog dialog = null;
                    TextureBoardDefPair pair = null;

//                    for (Cell cell : door.getDoorDrawing().getCells())
//                    {
//                        if (cell.getCell().getChildren().containsAll(figures) && cell.getCell().getChildren().size() == figures.size())
//                        {
//                            pair = new TextureBoardDefPair(cell.getTextureEntity(), cell.getBoardDefEntity());
//                            break;
//                        }
//                    }

                    if (w != null)
                    {
                        if (w instanceof JFrame)
                        {
                            dialog = new CellDialog((Frame) w, true, figures, door, path, pair);
                        }
                        else if (w instanceof JDialog)
                        {
                            dialog = new CellDialog((JDialog) w, true, figures, door, path, pair);
                        }
                        else
                        {
                            throw new IllegalArgumentException();
                        }
                    }
                    if (dialog != null)
                    {
                        dialog.setVisible(true);
                        dialog.setLocationRelativeTo(w);
                    }
                }
            }
        });

        editor.getActiveView().addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                Point point = e.getPoint();
//                for (Figure figure : door.getDoorDrawing().getDoorElementsChildren())
//                {
//                    if (figure.contains(editor.getActiveView().viewToDrawing(point)))
//                    {
//                        return;
//                    }
//                }

//                for (Cell cell : door.getDoorDrawing().getCells())
//                {
//                    if (cell.getPath().contains(editor.getActiveView().viewToDrawing(point)))
//                    {
//                        editor.getActiveView().clearSelection();
//                        editor.getActiveView().addToSelection(cell.getCell().getChildren());
//                    }
//                }
            }
        });

        bar.addSeparator();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JToolBar attributesToolbar;
    private JToolBar creationToolbar;
    private JPanel jPanel1;
    private JScrollPane scrollPane;
    private ButtonGroup toolButtonGroup;
    private DefaultDrawingView view;
    // End of variables declaration//GEN-END:variables
}