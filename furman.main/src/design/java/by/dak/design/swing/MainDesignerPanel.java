package by.dak.design.swing;

import by.dak.cutting.swing.DPanel;
import by.dak.design.draw.ADesignerDrawing;
import by.dak.design.draw.components.CellFigure;
import by.dak.design.draw.components.annotation.ConvertedAnnotationProcessor;
import by.dak.design.draw.components.convert.DesignerFront2TopConverter;
import by.dak.design.settings.Settings;
import by.dak.design.settings.SettingsController;
import by.dak.design.settings.SettingsWindowProperty;
import by.dak.design.swing.action.BoardViewSelectionHandler;
import by.dak.model3d.DBoxFacade;
import by.dak.swing.infonode.RootWindowProperty;
import by.dak.view3d.View3dPanel;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.View;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;
import org.jhotdraw.draw.event.CompositeFigureEvent;
import org.jhotdraw.draw.event.CompositeFigureListener;
import org.jhotdraw.draw.event.FigureAdapter;
import org.jhotdraw.draw.event.FigureEvent;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.08.11
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public class MainDesignerPanel extends DPanel {
	private DBoxFacade boxFacade = new DBoxFacade();
	private FrontDesignerPanel frontDesignerPanel;
	private TopDesignerPanel topDesignerPanel;
	private JPanel propertiesPanel;
	private View3dPanel view3dPanel;
	private StructureTab structureTab;
	private SettingsPanel settingsPanel;

	private RootWindow rootWindow;

	private ScaleHandle scaleHandle = new ScaleHandle();

	private DrawingChangedListener drawingChangedListener = new DrawingChangedListener();
	private UpdateTopViewHandle updateTopViewHandle = new UpdateTopViewHandle();
	private BoardViewSelectionHandler boardFrontViewSelectionHandler = new BoardViewSelectionHandler();
	private BoardViewSelectionHandler boardTopViewSelectionHandler = new BoardViewSelectionHandler();

	public MainDesignerPanel() {
		//init();
	}

	private void init() {
		setLayout(new BorderLayout());

		frontDesignerPanel = new FrontDesignerPanel();
		topDesignerPanel = new TopDesignerPanel();
		propertiesPanel = new JPanel();

		view3dPanel = new View3dPanel();
		view3dPanel.getView3dController().setBoxFacade(boxFacade);
		view3dPanel.init();

		structureTab = new StructureTab();
		structureTab.getTree().setRootVisible(true);
		structureTab.getTree().setModel(new DefaultTreeModel(boxFacade.getRootTreeNode()));

		settingsPanel = new SettingsPanel();
		settingsPanel.init();

		View[] views = initViews();

		ViewMap viewMap = new ViewMap(views);

		rootWindow = DockingUtil.createRootWindow(viewMap, true);
		rootWindow.setName(MainDesignerPanel.class.getSimpleName());
		layoutRootWindow(rootWindow, viewMap);

		boardFrontViewSelectionHandler = new BoardViewSelectionHandler();
		boardFrontViewSelectionHandler.setBoardElementPropertiesView(viewMap.getView(2));
		boardFrontViewSelectionHandler.setDrawingView(frontDesignerPanel.getDrawingView());
		boardTopViewSelectionHandler = new BoardViewSelectionHandler();
		boardTopViewSelectionHandler.setBoardElementPropertiesView(viewMap.getView(2));
		boardTopViewSelectionHandler.setDrawingView(topDesignerPanel.getDrawingView());
		frontDesignerPanel.getDrawingView().addMouseListener(boardFrontViewSelectionHandler);
		frontDesignerPanel.addPropertyChangeListener("drawing", scaleHandle);
		frontDesignerPanel.addPropertyChangeListener("drawing", drawingChangedListener);
		topDesignerPanel.getDrawingView().addMouseListener(boardTopViewSelectionHandler);

		add(rootWindow);
		//the code is running when the panel is visible
		addHierarchyListener(new HierarchyListener() {
			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) > 1 && isShowing()) {
					RootWindowProperty.restore(rootWindow);
					frontDesignerPanel.setDrawing(boxFacade.getFrontDesignerDrawing());
					registerUpdateTopViewHandle();
				}
			}
		});

	}

	private void registerUpdateTopViewHandle() {
		frontDesignerPanel.getFrontDesignerDrawing().addFigureListener(updateTopViewHandle);
		frontDesignerPanel.getFrontDesignerDrawing().addCompositeFigureListener(updateTopViewHandle);
	}

	private void layoutRootWindow(RootWindow rootWindow, ViewMap viewMap) {
		rootWindow.setWindow(new SplitWindow(true, 0.1f, viewMap.getView(4),
				new SplitWindow(true, 0.6f,
						new SplitWindow(false, viewMap.getView(0), viewMap.getView(1)),
						new SplitWindow(false, viewMap.getView(2), viewMap.getView(3)))));
		rootWindow.getWindowBar(Direction.LEFT).setEnabled(true);
		rootWindow.getWindowBar(Direction.RIGHT).setEnabled(true);
		rootWindow.getWindowBar(Direction.RIGHT).addTab(viewMap.getView(2));
		rootWindow.getWindowBar(Direction.RIGHT).addTab(viewMap.getView(5));
	}


	private View[] initViews() {
		java.util.List<View> views = new ArrayList<View>();
		views.add(new View(getResourceMap().getString("top.view.title"), getResourceMap().getIcon("top.view.icon"), topDesignerPanel));
		views.add(new View(getResourceMap().getString("front.view.title"), getResourceMap().getIcon("front.view.icon"), frontDesignerPanel));
		views.add(new View(getResourceMap().getString("properties.view.title"), getResourceMap().getIcon("properties.view.icon"), propertiesPanel));
		views.add(new View(getResourceMap().getString("3d.view.title"), getResourceMap().getIcon("3d.view.icon"), view3dPanel));
		views.add(new View(getResourceMap().getString("structureTab.title"), getResourceMap().getIcon("structureTab.icon"), structureTab));
		views.add(initSettingsView());

		disableCloseButton(views);
		disableUndockButton(views);
		disableHighlighedButtons(views);


		return views.toArray(new View[views.size()]);
	}

	private View initSettingsView() {
		View settingsView = new View(getResourceMap().getString("settingsPanel.title"), getResourceMap().getIcon("settingsPanel.icon"), settingsPanel);
		settingsView.getWindowProperties().setRestoreEnabled(false);
		return settingsView;
	}

	private void disableUndockButton(List<View> views) {
		for (View view : views) {
			view.getWindowProperties().setUndockEnabled(false);
		}
	}

	private void disableHighlighedButtons(List<View> views) {
		for (View view : views) {
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

	private void disableCloseButton(List<View> views) {
		for (View view : views) {
			view.getWindowProperties().setCloseEnabled(false);
		}
	}

	public FrontDesignerPanel getFrontDesignerPanel() {
		return frontDesignerPanel;
	}

	public TopDesignerPanel getTopDesignerPanel() {
		return topDesignerPanel;
	}

	private void prepareSettings() {
		Settings settings = new Settings();
		settingsPanel.setValue(settings);
		SettingsController settingsController = new SettingsController(frontDesignerPanel.getFrontDesignerDrawing(),
				settings);
		settingsController.bindSettings();
		settingsController.bindFrontDesignerDrawing();

		SettingsWindowProperty.restore(settingsPanel);
	}

	private class ScaleHandle implements PropertyChangeListener {
		private double calcDesignerScale() {
			double scale = Math.min(frontDesignerPanel.getBounds().getWidth() /
					frontDesignerPanel.getDrawing().getBounds().getWidth(),
					frontDesignerPanel.getDrawingView().getComponent().getParent().getBounds().getHeight() /
							frontDesignerPanel.getDrawing().getBounds().getHeight());
			for (int count = 1; count < ADesignerPanel.zoomArray.length; count++) {
				if (ADesignerPanel.zoomArray[count - 1] <= scale &&
						scale <= ADesignerPanel.zoomArray[count]) {
					scale = ADesignerPanel.zoomArray[count - 1];
				}
			}


			return scale;
		}

		private void setTopDesignerScale(double scale) {
			topDesignerPanel.getDrawingView().
					setScaleFactor(scale);

			Enumeration<AbstractButton> buttons = topDesignerPanel.getZoomGroup().getElements();
			while (buttons.hasMoreElements()) {
				AbstractButton button = buttons.nextElement();
				if (button.getText().equals((int) (scale * 100) + " %")) {
					button.setSelected(true);
					break;
				}
			}
		}

		private void setFrontDesgnerScale(double scale) {
			frontDesignerPanel.getDrawingView().
					setScaleFactor(scale);

			Enumeration<AbstractButton> buttons = frontDesignerPanel.getZoomGroup().getElements();
			while (buttons.hasMoreElements()) {
				AbstractButton button = buttons.nextElement();
				if (button.getText().equals((int) (scale * 100) + " %")) {
					button.setSelected(true);
					break;
				}
			}

		}

		private void moveFrontDesignCell(double scale) {
			CellFigure topFigure = ((ADesignerDrawing) frontDesignerPanel.getDrawing()).getTopFigure();
			if (topFigure != null) {
				double drawingOffsetX = Math.abs(topFigure.getBounds().getWidth() -
						frontDesignerPanel.getBounds().getWidth() / scale) / 2;
				double drawingOffsetY = Math.abs(topFigure.getBounds().getHeight() -
						frontDesignerPanel.getBounds().getHeight() / scale) / 2;

				((ADesignerDrawing) frontDesignerPanel.getDrawing()).moveTopFigure(drawingOffsetX, drawingOffsetY);
				((ADesignerDrawing) frontDesignerPanel.getDrawing()).addDimensionsTo(topFigure,
						(int) Math.min(drawingOffsetX, drawingOffsetY) / 3);
			}

		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					double scale = calcDesignerScale();

					setFrontDesgnerScale(scale);
					setTopDesignerScale(scale);
//                    moveFrontDesignCell(scale);
				}
			});

		}

	}

	private class DrawingChangedListener implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			updateTopViewHandle.getConverter().setBoxFacade(boxFacade);
			updateTopViewHandle.getConverter().setFrontDesignerDrawing(frontDesignerPanel.getFrontDesignerDrawing());
			updateTopViewHandle.getConverter().setTopDesignerDrawing(topDesignerPanel.getTopDesignerDrawing());
			updateTopViewHandle.convert();

			prepareSettings();
		}
	}

	private class UpdateTopViewHandle extends FigureAdapter implements CompositeFigureListener {
		private DesignerFront2TopConverter converter = new DesignerFront2TopConverter();

		private void convert() {
			topDesignerPanel.getDrawingView().clearSelection();

			converter.convert();
		}

		@Override
		public void figureChanged(FigureEvent e) {
			if (ConvertedAnnotationProcessor.getProcessor().isConverted(e.getFigure().getClass())) {
				convert();
			}
		}

		@Override
		public void figureAdded(CompositeFigureEvent e) {
			if (ConvertedAnnotationProcessor.getProcessor().isConverted(e.getChildFigure().getClass())) {
				convert();
			}
		}

		@Override
		public void figureRemoved(CompositeFigureEvent e) {
			if (ConvertedAnnotationProcessor.getProcessor().isConverted(e.getChildFigure().getClass())) {
				convert();
			}
		}

		public DesignerFront2TopConverter getConverter() {
			return converter;
		}

	}

}
