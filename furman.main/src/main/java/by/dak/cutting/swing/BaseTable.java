package by.dak.cutting.swing;

import by.dak.cutting.swing.order.cellcomponents.editors.ComboCellEditor;
import by.dak.cutting.swing.renderer.EntityTableRenderer;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * Modyfied table control
 *
 * @author Rudak Alexei
 */
public class BaseTable extends JXTable {
	private TableEventDelegator eventDelegator;
	private Boolean mouseProcessed = true;

	public Long getSelectedId() {
		if (getSelectedRow() != -1) {
			return (Long) getModel()
					.getValueAt(getSelectedRow(), 0);
		} else
			return -1L;
	}

	public BaseTable() {
		super();
		//default sorter does not work correctly.
		setSortable(false);
		addHighlighter(HighlighterFactory.createAlternateStriping());
		setColumnControlVisible(true);
		JXTableHeader jx = (JXTableHeader) getTableHeader();
		jx.setBackground(new Color(182, 209, 224));
		setTableHeader(jx);
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addMouseListener(new MouseTableHandler());
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					if (getEventDelegator() != null) {
						getEventDelegator().onKeyDelete();
					}
				}
			}
		});

		setDefaultRenderer(Object.class, new EntityTableRenderer());
		setRowHeight(24);
	}


	public void hideColumn(int n) {
		this.removeColumn(getColumn(n));
	}

	public TableEventDelegator getEventDelegator() {
		return eventDelegator;
	}

	public void setEventDelegator(TableEventDelegator eventDelegator) {
		this.eventDelegator = eventDelegator;
	}

	/**
	 * @param mouseProcessed the mouseProcessed to set
	 */
	public void setMouseProcessed(Boolean mouseProcessed) {
		this.mouseProcessed = mouseProcessed;
	}

	class MouseTableHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (mouseProcessed && getEventDelegator() != null) {
				BaseTable t = (BaseTable) e.getSource();
				TableModel tm = t.getModel();
				if (e.getClickCount() == 2) {
					getEventDelegator().onMouseDbClick();
				} else
					getEventDelegator().onMouseClick();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (mouseProcessed && getEventDelegator() != null) {
				if (SwingUtilities.isRightMouseButton(e))
					getEventDelegator().onMouseRight();
				if (SwingUtilities.isLeftMouseButton(e))
					getEventDelegator().onMouseLeft();
			}
		}
	}


	@Override
	public boolean editCellAt(int row, int column, EventObject e) {
		boolean b = super.editCellAt(row, column, e);
		if (editorComp != null) {
			final TableCellEditor tableCellEditor = getCellEditor(row, column);
			if (tableCellEditor instanceof ComboCellEditor) {
				JComponent component = ((ComboCellEditor) tableCellEditor).getFocusComponent();
				if (component instanceof JComboBox) {
					component.requestFocus();
				}
			}
			if (editorComp instanceof JTextComponent) {
				editorComp.requestFocus();
				((JTextComponent) editorComp).selectAll();
			} else if (editorComp instanceof JComboBox) {
				editorComp.requestFocus();
			}
		}
		return b;
	}
}
