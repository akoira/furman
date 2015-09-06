/*
 * @(#)MillingDrawingPanel.java  1.0  11. March 2004
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.draw.Graphics2DUtils;
import org.jhotdraw.app.action.edit.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.draw.io.DOMStorableInputOutputFormat;
import org.jhotdraw.draw.io.InputFormat;
import org.jhotdraw.draw.io.OutputFormat;
import org.jhotdraw.draw.io.SerializationInputOutputFormat;
import org.jhotdraw.draw.liner.ElbowLiner;
import org.jhotdraw.draw.tool.*;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.undo.UndoRedoManager;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;

public class MillingDrawingPanel extends JPanel
{
    private UndoRedoManager undoManager;
    private ElementDrawing drawing;
    private DrawingEditor editor;
    private OrderDetailsDTO detailsDTO;
    private JButton gluiengButton;
    /**
     * Creates new instance.
     */
    public MillingDrawingPanel()
    {
        initComponents();
        undoManager = new UndoRedoManager();
        editor = new DefaultDrawingEditor();
        editor.add(view);

        drawing = new ElementDrawing();

        addCreationButtonsTo(creationToolbar, editor);
        ButtonFactory.addAttributesButtonsTo(attributesToolbar, editor);
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
                ButtonFactory.createSelectionActions(editor)
        );
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

        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new ArcFigure(0, 90), attributes,drawing), "edit.createArc0", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new ArcFigure(90, 90), attributes,drawing), "edit.createArc90", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new ArcFigure(180, 90), attributes,drawing), "edit.createArc180", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new ArcFigure(270, 90), attributes,drawing), "edit.createArc270", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new ArcEveryFigure(10, 60), attributes,drawing), "edit.createArc", labels);

        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new CurveFigure(true), attributes,drawing), "edit.createCurve", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new CurveFigure(), attributes,drawing), "edit.createLine", labels);
        ButtonFactory.addToolTo(tb, editor, new ElementCreationTool(new CurveFigure(false), attributes,drawing), "edit.createDistanceLine", labels);
        tb.addSeparator();
        ButtonFactory.addToolTo(tb, editor, new EllipseCreationTool(new ArcFigure(), attributes, drawing), "edit.createEllipse", labels);
        tb.addSeparator();
        ButtonFactory.addToolTo(tb, editor, ct = new ElementCreationTool(new LineFigure()), "edit.createArrow", labels);
        af = (AbstractAttributedFigure) ct.getPrototype();
        //END_DECORATION.set(af, new ArrowTip(0.35, 12, 11.3));
        ButtonFactory.addToolTo(tb, editor, new ConnectionTool(new LineConnectionFigure()), "edit.createLineConnection", labels);
        ButtonFactory.addToolTo(tb, editor, cnt = new ConnectionTool(new LineConnectionFigure()), "edit.createElbowConnection", labels);
        lc = cnt.getPrototype();
        lc.setLiner(new ElbowLiner());
        ButtonFactory.addToolTo(tb, editor, new BezierTool(new BezierFigure(true)), "edit.createPolygon", labels);
        ButtonFactory.addToolTo(tb, editor, new TextCreationTool(new TextFigure()), "edit.createText", labels);
        ButtonFactory.addToolTo(tb, editor, new TextAreaCreationTool(new TextAreaFigure()), "edit.createTextArea", labels);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JToolBar attributesToolbar;
    private JToolBar creationToolbar;
    private JPanel jPanel1;
    private JScrollPane scrollPane;
    private ButtonGroup toolButtonGroup;
    private DefaultDrawingView view;
    // End of variables declaration//GEN-END:variables

    public void setDetailsDTO(OrderDetailsDTO detailsDTO)
    {
        if (gluiengButton != null)
        {
            attributesToolbar.remove(gluiengButton);
        }
        this.detailsDTO = detailsDTO;
//        gluiengButton = ButtonFactory.addGluiengButtonTo(attributesToolbar, editor, getDetailsDTO());
    }

    public OrderDetailsDTO getDetailsDTO()
    {
        return detailsDTO;
    }

    public Dimension2D getElement()
    {
        return drawing.getElement();
    }

    public void setElement(Dimension2D element)
    {
        drawing.setElement(element);
    }
}