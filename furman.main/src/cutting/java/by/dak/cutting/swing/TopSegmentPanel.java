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

package by.dak.cutting.swing;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.swing.action.BookBoardAction;
import by.dak.cutting.swing.action.GraySegmentReplaceAction;
import by.dak.cutting.swing.cut.SheetDimentionItem;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.MillingFigureFactory;
import by.dak.draw.Graphics2DUtils;
import by.dak.persistence.entities.Cutter;
import by.dak.persistence.entities.OrderStatus;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.GridConstrainer;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.action.ZoomAction;
import org.jhotdraw.draw.io.DOMStorableInputOutputFormat;
import org.jhotdraw.draw.io.InputFormat;
import org.jhotdraw.draw.io.OutputFormat;
import org.jhotdraw.draw.io.SerializationInputOutputFormat;
import org.jhotdraw.draw.tool.DelegationSelectionTool;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.undo.UndoRedoManager;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class TopSegmentPanel extends JPanel {
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
	private CutterPanel parentCutterPanel;

	private Cutter cutter;

	private UndoRedoManager undoManager;


	private CuttingDrawing cuttingDrawing;
	private CuttingDrawingEditor editor;

	private Segment segment;

	private boolean editable = true;

    private ArrayList<Action> actions = new ArrayList<>();


    /**
	 * Creates new instance.
	 */
	public TopSegmentPanel() {
		initComponents();
		initEnv();
	}


	public Collection<Action> createDrawingActions() {
		LinkedList<Action> list = new LinkedList<Action>();
		return list;
	}


	private void initEnv() {
		undoManager = new UndoRedoManager();
		getEditor().add(view);

		cuttingDrawing = new CuttingDrawing();
        cuttingDrawing.setDelegate(new CuttingDrawingDelegate()
        {
            @Override
            public void changed(CuttingDrawing cuttingDrawing)
            {
                updateActions(cuttingDrawing.getSegment());
            }
        });


        addSelectionTool(creationToolbar, getEditor(), createDrawingActions(),
                Collections.EMPTY_LIST);

        JPopupButton pb = createPopup(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.labels);
		creationToolbar.add(pb);

		creationToolbar.addSeparator();
        addAction(new GraySegmentReplaceAction(this));
        creationToolbar.addSeparator();
        addAction(new BookBoardAction(this));

        view.setDrawing(cuttingDrawing);
		cuttingDrawing.addUndoableEditListener(undoManager);

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


		addPropertyChangeListener("segment", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				reload();
			}
		});

		addPropertyChangeListener("enabled", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				getView().setEnabled(isEnabled());
			}
		});

		addPropertyChangeListener("editable", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				getView().setEnabled(isEditable());
			}
		});

	}

	private JPopupButton createPopup(ResourceBundleUtil labels) {
		JPopupButton pb = new JPopupButton();

		pb.setItemFont(UIManager.getFont("MenuItem.font"));
		labels.configureToolBarButton(pb, "actions");

		JRadioButtonMenuItem rbmi;
		ButtonGroup group = new ButtonGroup();

		for (Double z : zoom) {
			pb.add(rbmi = new JRadioButtonMenuItem(new ZoomAction(getEditor(), z, null)));
			rbmi.addItemListener(new ZoomButtonListener());
			if (z.equals(defaultZoom)) {
				rbmi.setSelected(true);
			}
			group.add(rbmi);
		}

		return pb;
	}

    private void addAction(Action action)
    {
        creationToolbar.add(action).setFocusable(false);
        actions.add(action);
    }

    private void updateActions(Segment segment)
    {
        for (Action action : actions)
        {
            if (action instanceof BookBoardAction)
            {
                new BookBoardActionUpdater(action, segment).invoke();
            }
        }
    }

    public void reload()
    {
        getView().clearSelection();
		getView().setEnabled(isEditable() && isEnabled());
		getCuttingDrawing().removeAllChildren();
		if (getSegment() != null) {
			Segment segment = getSegment().clone();
			getCuttingDrawing().setSegment(segment);
		} else {
			getCuttingDrawing().setSegment(null);
		}
        updateActions(segment);
    }

	public DefaultDrawingView getView() {
		return view;
	}


	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <draw-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		toolButtonGroup = new ButtonGroup();
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


	public void addSelectionTool(JToolBar tb, final DrawingEditor editor,
								 Collection<Action> drawingActions, Collection<Action> selectionActions) {
		DelegationSelectionTool selectionTool = new DelegationSelectionTool(
				drawingActions, selectionActions);
		selectionTool.setDragTracker(new CuttingDragTracker());
		ButtonFactory.addSelectionToolTo(tb, editor, selectionTool);
		tb.addSeparator();
	}


	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JToolBar creationToolbar;
	private JPanel jPanel1;
	private JScrollPane scrollPane;
	private ButtonGroup toolButtonGroup;
	private DefaultDrawingView view;
	// End of variables declaration//GEN-END:variables

	public CuttingDrawing getCuttingDrawing() {
		return cuttingDrawing;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		Segment old = this.segment;
		this.segment = segment;
		firePropertyChange("segment", old, segment);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		boolean old = editable;
		this.editable = editable;
		firePropertyChange("editable", old, editable);
	}

	public CuttingDrawingEditor getEditor() {
		if (editor == null) {
			editor = new CuttingDrawingEditor();
		}
		return editor;
	}


	public void setFigureClipboard(FigureClipboard figureClipboard) {
		getEditor().setFigureClipboard(figureClipboard);
	}

	public Segment apply() {
		if (!getCuttingDrawing().getSegment().compareTo(getSegment())) {
			return getCuttingDrawing().getSegment();
		}
		return null;
	}

	public CutterPanel getParentCutterPanel() {
		return parentCutterPanel;
	}

	public void setParentCutterPanel(CutterPanel parentCutterPanel) {
		this.parentCutterPanel = parentCutterPanel;
	}

	public Cutter getCutter() {
		return cutter;
	}

	public void setCutter(Cutter cutter) {
		this.cutter = cutter;
		cuttingDrawing.setCutter(cutter);
	}

	private class ZoomButtonListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
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

    private class BookBoardActionUpdater
    {
        private Action action;
        private Segment segment;

        public BookBoardActionUpdater(Action action, Segment segment)
        {
            this.action = action;
            this.segment = segment;
        }

        public void invoke()
        {
            boolean enabled = segment != null && segment.getDimensionItem() instanceof SheetDimentionItem &&
                    ((SheetDimentionItem) segment.getDimensionItem()).getBoard() != null &&
                    segment.getElements().length > 0 &&
                    parentCutterPanel.getCuttingModel().getOrder().getStatus() == OrderStatus.design;
            action.setEnabled(enabled);

            if (enabled)
            {
                boolean locked = segment.getDimensionItem() instanceof SheetDimentionItem &&
                        ((SheetDimentionItem) segment.getDimensionItem()).getBoard() != null &&
                        ((SheetDimentionItem) segment.getDimensionItem()).getBoard().getBookedByOrder() != null;
                ((BookBoardAction) action).setLocked(locked);
            }
        }
    }
}