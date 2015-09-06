package by.dak.cutting.swing.order;

import by.dak.cutting.swing.BaseTable;
import by.dak.cutting.swing.order.cellcomponents.editors.*;
import by.dak.cutting.swing.order.cellcomponents.renderers.ButtonCellRenderer;
import by.dak.cutting.swing.order.cellcomponents.renderers.CutoffCheckBoxProvider;
import by.dak.cutting.swing.order.cellcomponents.renderers.MillingCheckBoxProvider;
import by.dak.cutting.swing.order.models.OrderDetailsTableModel;
import by.dak.cutting.swing.order.models.OrderTableColumn;
import by.dak.cutting.swing.order.popup.CommonSideMenu;
import by.dak.cutting.swing.renderer.EntityTableRenderer;
import by.dak.cutting.swing.renderer.TableEditorsRenders;
import by.dak.cutting.swing.table.PopupEditor;
import by.dak.cutting.swing.text.NumericDocument;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.Manufacturer;
import by.dak.persistence.entities.TextureEntity;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.beans.Beans;
import java.util.EventObject;


public class OrderTable extends BaseTable
{
    private ApplicationContext context;

	private MillingCellEditor millingCellEditor;

	public OrderTable(ApplicationContext context)
    {
        super();
        this.context = context;
        if (!Beans.isDesignTime())
        {
            setModel(new OrderDetailsTableModel());
            initTableCellComponents();
            setRowSelectionAllowed(false);
            setColumnSelectionAllowed(false);
            setCellSelectionEnabled(true);
            setSortable(false);
            getTableHeader().setReorderingAllowed(false);
            getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//            setTableListeners();
        }
    }

    private void initTableCellComponents()
    {
        for (int k = 0; k < getModel().getColumnCount(); k++)
        {
            OrderTableColumn column = OrderTableColumn.valuerOf(k);
            TableColumn tableColumn = getColumnModel().getColumn(k);
            ComplexCell cellComp;
            switch (column)
            {
                case boardDef:
                    tableColumn.setCellEditor(new BoardDefCellEditor(this));
                    tableColumn.setCellRenderer(new EntityTableRenderer<BoardDef>());
                    break;
                case texture:
                    tableColumn.setCellEditor(new TextureCellEditor(this));
                    tableColumn.setCellRenderer(new EntityTableRenderer<TextureEntity>());
                    break;
                case manufacturer:
                    TableEditorsRenders.ItemsComboBox<Manufacturer> cb = new TableEditorsRenders.ItemsComboBox<Manufacturer>();
                    cb.getItems().addAll(FacadeContext.getManufacturerFacade().loadAllSortedBy("name"));
                    tableColumn.setCellEditor(new DefaultCellEditor(cb));
                    tableColumn.setCellRenderer(new EntityTableRenderer<Manufacturer>());
                    break;
                case length:
                case width:
                case count:
                    final JTextField tf = new JTextField();
                    tf.setDocument(new NumericDocument());
                    tableColumn.setCellEditor(new DefaultCellEditor(tf));
                    break;
                case glueing:
                    cellComp = new ComplexCell();
                    tableColumn.setCellEditor(cellComp);
                    tableColumn.setCellRenderer(new ButtonCellRenderer(true));
                    break;
                case milling:
					millingCellEditor = new MillingCellEditor();
					tableColumn.setCellEditor(millingCellEditor);
					tableColumn.setCellRenderer(new DefaultTableRenderer(new MillingCheckBoxProvider()));
                    break;
                case drilling:
                    DrillingCellEditor editor = new DrillingCellEditor();
                    editor.setContext(context);
                    tableColumn.setCellEditor(editor);
                    tableColumn.setCellRenderer(new ButtonCellRenderer(false));
                    break;
                case groove:
                    cellComp = new CommonCellEditor();
                    tableColumn.setCellEditor(cellComp);
                    tableColumn.setCellRenderer(new ButtonCellRenderer(true));
                    break;
                case a45:
                    cellComp = new TextSideCellEditor();
                    tableColumn.setCellEditor(cellComp);
                    tableColumn.setCellRenderer(new ButtonCellRenderer(true));
                    break;
                case cutoff:
                    tableColumn.setCellEditor(new PopupEditor(new CutoffComponentProvider()));
                    tableColumn.setCellRenderer(new DefaultTableRenderer(new CutoffCheckBoxProvider()));
                    break;
            }
            if (tableColumn.getCellEditor() instanceof DefaultCellEditor)
            {
                ((DefaultCellEditor) tableColumn.getCellEditor()).setClickCountToStart(1);
            }
        }
    }


    public void redirectFocus(int selColumn, int selRow)
    {
        if (!(selColumn >= getColumnCount()))
        {
            editOrderCellAt(selRow, selColumn);
        }
        else if (!(++selRow >= getRowCount()))
        {
            editOrderCellAt(selRow, OrderTableColumn.length.index());
        }
        else if (getRowCount() > 0)
        {
            editOrderCellAt(0, OrderTableColumn.manufacturer.index());
        }
    }

    public void redirectFocusAndBlock(int row, int col)
    {
        editOrderCellAt(row, col);
    }

    public void afterSaveColumnEdit(int selRow)
    {
        editOrderCellAt(selRow, OrderTableColumn.length.index());
    }

    @Deprecated
    public boolean editOrderCellAt(int row, int col)
    {
        if (row == -1 || col == -1)
        {
            return false;
        }
        boolean editCell = editCellAt(row, col, null);
        columnModel.getSelectionModel().setAnchorSelectionIndex(col);
        final TableCellEditor tableCellEditor = getCellEditor(row, col);
        if (tableCellEditor instanceof ComboCellEditor)
        {
            JComponent component = ((ComboCellEditor) tableCellEditor).getFocusComponent();
            if (component instanceof JComboBox)
            {
                component.requestFocus();
            }
        }
        else if (tableCellEditor instanceof DefaultCellEditor)
        {
            JTextField tf = (JTextField) ((DefaultCellEditor) tableCellEditor).getComponent();
            tf.requestFocus();
            tf.selectAll();
        }
        else if (tableCellEditor instanceof CheckBoxCellEditor)
        {
            ((CheckBoxCellEditor) tableCellEditor).getComponent().requestFocus();
        }
        getSelectionModel().setSelectionInterval(row, row);
        return editCell;
    }

    @Override
    public boolean editCellAt(int row, int column, EventObject e)
    {
        if (CommonSideMenu.currentPopup != null)
        {
            CommonSideMenu.currentPopup.hidePopupImmediately();
            CommonSideMenu.currentPopup = null;
        }
        return super.editCellAt(row, column, e);
    }

    //todo Метод должен быть вынес в какой-то листнер (Focus, Selecte) чтобы он выполнялся всегда
    @Deprecated
    public void stopEditing()
    {
        if (getRowCount() == 0)
        {
            return;
        }

        int last = getRowCount() - 1;
        for (int i = 0; i < getColumnCount(); i++)
        {
            try
            {
                getCellEditor(last, i).stopCellEditing();
            }
            catch (Exception e)
            {
                // do nothing
            }
        }
        editingStopped(new ChangeEvent(this));
    }

    public void setContext(ApplicationContext context)
    {
        this.context = context;
    }

	public MillingCellEditor getMillingCellEditor() {
		return millingCellEditor;
	}
}
