package by.dak.cutting.swing.order.controls;


import by.dak.cutting.MessageBox;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.order.OrderDetailsTab;
import by.dak.cutting.swing.order.OrderTable;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.order.models.OrderDetailsDTONumberer;
import by.dak.cutting.swing.order.models.OrderDetailsTableModel;
import by.dak.cutting.swing.order.models.OrderTableColumn;
import by.dak.design.swing.DesignerTab;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jidesoft.swing.JideTabbedPane;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class OrderDetailsControl extends GenericControl<OrderDetailsTab>
{
    private OrderTable orderTable;
    private OrderDetailsTableModel model;
    private ModelChangedController modelChangedController;
    private JideTabbedPane tabbedPane;

    public OrderDetailsControl(OrderDetailsTab view)
    {
        super(view);

        this.tabbedPane = getView().getTabbedPane();
        tabbedPane.addChangeListener(new DesignerComponentListener());
        this.orderTable = getView().getOrderDetailsPanel().getOrderTable();
		this.orderTable.getMillingCellEditor().setOrderDetailsControl(this);
		this.model = (OrderDetailsTableModel) getView().getOrderDetailsPanel().getOrderTable().getModel();
        this.modelChangedController = new ModelChangedController();
        addActions();

    }

    public void addTableModelListener(TableModelListener listener)
    {
        this.model.addTableModelListener(listener);
    }

    public void remoteTableModelListener(TableModelListener listener)
    {
        this.model.removeTableModelListener(listener);
    }

    protected void addActions()
    {
        this.model.addTableModelListener(this.modelChangedController);
        this.model.addTableModelListener(new BoardsInStoreInformer());
//        this.orderTable.getColumnModel().getSelectionModel().addListSelectionListener(this.modelChangedController);
//        this.orderTable.getSelectionModel().addListSelectionListener(this.modelChangedController);

        ActionMap amap = cloneActionMap();
        InputMap imap = orderTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

//        InputMap imParent = imap.getParent();
//        imParent.remove(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
//        imap.setParent(imParent);
//        imap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));

        String cut = "cut";
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), cut);
        amap.put(cut, deleteAction);

//        String selectNextColumn = "selectNextColumn";
//        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0, false), selectNextColumn);
//        amap.put(selectNextColumn, tabNextAction);

//        String selectPreviousColumnCell = "selectPreviousColumnCell";
//        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_MASK, false), selectPreviousColumnCell);
//        amap.put(selectPreviousColumnCell, tabPreviousAction);

//        String save = "save";
//        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), save);
//        amap.put(save, saveAction);

        orderTable.setInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, imap);
        orderTable.setActionMap(amap);

        //addDeleteKeyListenerForEditors();

        //todo пока единственный способ котор. нашел
        //установка фокуса при открытии панели
//        orderTable.addComponentListener(new ComponentAdapter()
//        {
//            @Override
//            public void componentResized(ComponentEvent e)
//            {
//                super.componentResized(e);
//                orderTable.grabFocus();
//                orderTable.focus2LastRow();
//                orderTable.flushFocusBlock();
//            }
//        });
    }

    private void addKeyListenerToCell(Component component)
    {
        component.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_DELETE)
                {
                    onDeleteAction();
                }
            }
        });
    }

    private ActionMap cloneActionMap()
    {
        ActionMap amap = new ActionMap();
        ActionMap orig = orderTable.getActionMap();
        Object[] keys = orig.keys();
        if (keys != null)
        {
            for (Object key : keys)
            {
                amap.put(key, orig.get(key));
            }
        }
        keys = orig.getParent().keys();
        for (Object key : keys)
        {
            amap.put(key, orig.getParent().get(key));
        }
        return amap;
    }

    public void onDeleteAction()
    {
        int selRow = orderTable.getSelectedRow();
        if (selRow == -1)
        {
            return;
        }
        OrderDetailsDTO selectedDTO = model.getRowBy(selRow);
        if (selectedDTO != model.getEmptyDto())
        {
            if (MessageBox.confirmYesNoDeletion())
            {
                deleteOrderFurniture(selectedDTO);
                ((OrderDetailsTableModel) orderTable.getModel()).delete(selRow);
            }
        }
    }

    private void deleteOrderFurniture(final OrderDetailsDTO selectedDTO)
    {
        final OrderFurniture orderFurniture = selectedDTO.getOrderFurnitureEntity();
        if (orderFurniture == null)
        {
            return;
        }
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                FacadeContext.getOrderFurnitureFacade().delete(orderFurniture);
            }
        });
    }

	private void save(final OrderFurniture orderFurniture) {
        getView().cleanWarnList();
		FacadeContext.getOrderFurnitureFacade().save(orderFurniture);
	}


	public void saveOrderFurniture(final OrderDetailsDTO selectedDTO) {
        OrderItem orderItem = model.getOrderItem();
        OrderFurniture furniture = new OrderFurnitureConverter(selectedDTO, orderItem).getOrderFurniture();

		save(furniture);

		selectedDTO.setOrderFurnitureEntity(furniture);
        selectedDTO.updateSavedOrderDetails();
    }

    public void saveAll()
    {
        java.util.List<OrderDetailsDTO> data = model.getData();
        OrderDetailsDTONumberer orderDetailsDTONumberer = new OrderDetailsDTONumberer();

        for (OrderDetailsDTO orderDetails : data)
        {
            orderDetailsDTONumberer.number(orderDetails);
			saveOrderFurniture(orderDetails);
		}

//        saveDesign();
    }

    private void saveDesign()
    {
//        FrontDesignerPanel frontDesignerPanel = (FrontDesignerPanel) getView().getDesignerTab().getDesignerPanel();
//        if (frontDesignerPanel != null)
//        {
//            DesignXmlSerializer designXmlSerializer = DesignXmlSerializer.getInstance();
//            String design = designXmlSerializer.serialize(frontDesignerPanel.getDrawing());
//            OrderItem orderItem = model.getOrderItem();
//            orderItem.setDesign(design);
//            FacadeContext.getOrderItemFacade().save(orderItem);
//        }
    }

    private Action deleteAction = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        {
            onDeleteAction();
        }
    };


    private class ModelChangedController implements TableModelListener
    {
        @Override
        public void tableChanged(TableModelEvent e)
        {
            int row = e.getFirstRow();
            if (row == -1)
            {
                return;
            }
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == -1)
            {
                rowInserted(model.getRowCount() - 1);
                return;
            }
            removePlasticDSP();
            if (e.getType() == TableModelEvent.INSERT)
            {
                rowInserted(row);
                return;
            }
            if (e.getType() == TableModelEvent.UPDATE)
            {
                rowUpdated(row);
                return;
            }
            if (e.getType() == TableModelEvent.DELETE)
            {
                rowDeleted(row);
                return;
            }
            gluingMillingSwitch(e);
        }

        private void removePlasticDSP()
        {
            SearchFilter searchFilter = SearchFilter.instanceSingle();
            searchFilter.eq(OrderItem.PROPERTY_order, model.getOrderItem().getOrder());
            searchFilter.eq(OrderItem.PROPERTY_type, OrderItemType.plastic);
            OrderItem orderItem = FacadeContext.getOrderItemFacade().getFirstBy(searchFilter);
            if (orderItem != null)
            {
                FacadeContext.getDSPPlasticDetailFacade().deleteAllBy(orderItem);
                FacadeContext.getOrderItemFacade().delete(orderItem);
            }

        }

        /**
         * Bug 	7700
         *
         * @param e
         * @deprecated
         */
        private void gluingMillingSwitch(TableModelEvent e)
        {
            int row = e.getFirstRow();
            if (e.getColumn() == OrderTableColumn.glueing.index())
            {
                model.setValueAt(null, row, OrderTableColumn.milling.index());
                return;
            }
            if (e.getColumn() == OrderTableColumn.milling.index())
            {
                Object value = model.getValueAt(row, OrderTableColumn.milling.index());
                if (value != null)
                {
                    model.getRowBy(row).setGlueing(null);
                }
                return;
            }
        }

        private void rowInserted(final int row)
        {
            DetailsErrorData errorData = new DetailsErrorData(orderTable);
            if (!errorData.validate(row))
            {

                final int column = errorData.getWrongColumn();
                //final int column = OrderTableColumn.length.index();
                Runnable runnable = new Runnable()
                {
                    @Override
                    public void run()
                    {
//                        orderTable.editCellAt(row, column);
                        orderTable.setRowSelectionInterval(row, row);
                        orderTable.setColumnSelectionInterval(column, column);
                        orderTable.scrollCellToVisible(row, column);
                    }

                };
                SwingUtilities.invokeLater(runnable);
            }
            else
            {
                updateWarningList(errorData);
            }
        }

        private void rowUpdated(int row)
        {
            OrderDetailsDTO dto = model.getRowBy(row);
            if (dto.isNew())
            {
                newRowUpdated(row, dto);
            }
            else
            {
                rowUpdated(row, dto);
            }
        }

        private void rowUpdated(int row, OrderDetailsDTO dto)
        {
            DetailsErrorData errorData = new DetailsErrorData(orderTable);

            if (errorData.validate(row))
            {
				saveOrderFurniture(dto);
			}
            else
            {
                updateWarningList(errorData);
            }
        }

        private void newRowUpdated(int row, OrderDetailsDTO dto)
        {
            DetailsErrorData errorData = new DetailsErrorData(orderTable);
            if (errorData.validate(row))
            {
                dto.setNumber((long) row);
                if (dto.getBoardDef().getComposite())
                {
                    dto.setSecondNumber((long) row);
                }
				saveOrderFurniture(dto);
				model.addRow(dto);
                model.setEmptyRow(model.createNewRow(dto));
            }
            else
            {
                updateWarningList(errorData);
            }
        }

        private void updateWarningList(DetailsErrorData errorData)
        {
            ValidationResultModel validationResultModel = getView().getWarningList().getModel();
            ValidationResult validationResult = new ValidationResult();
            validationResult.addAllFrom(errorData.getValidationResult());
            validationResult.addAllFrom(clearErrorResult(validationResultModel.getResult()));
            validationResultModel.setResult(validationResult);
            getView().getWarningList().setModel(validationResultModel);
        }

        /**
         * message which are errors have to clean, to refresh from old messages
         *
         * @param oldResult
         */
        private ValidationResult clearErrorResult(ValidationResult oldResult)
        {
            ValidationResult validationResult = new ValidationResult();
            for (ValidationMessage message : oldResult.getMessages())
            {
                if (!message.severity().equals(Severity.ERROR))
                {
                    validationResult.add(message);
                }
            }

            return validationResult;
        }

        private void rowDeleted(int row)
        {
            int selectRow = model.getRowCount() - 1;
            rowInserted(selectRow);
        }
    }


    private class BoardsInStoreInformer implements TableModelListener
    {
        @Override
        public void tableChanged(TableModelEvent e)
        {
            if (e.getColumn() < 0)
                return;
            OrderTableColumn column = OrderTableColumn.valuerOf(e.getColumn());
            switch (column)
            {
                case texture:
                case boardDef:
                    OrderDetailsDTO dto = model.getRowBy(e.getFirstRow());
                    SearchFilter filter = SearchFilter.instanceUnbound();
                    filter.eq(Board.PROPERTY_priceAware, dto.getBoardDef());
                    filter.eq(Board.PROPERTY_priced, dto.getTexture());
                    filter.eq("status", StoreElementStatus.exist);
                    java.util.List<Board> list = FacadeContext.getBoardFacade().loadAll(filter);
                    ValidationResultModel resultModel = getView().getWarningList().getModel();
                    ValidationResult validationResult = new ValidationResult();
                    for (Board board : list)
                    {
                        validationResult.addWarning(StringValueAnnotationProcessor.getProcessor().convert(board));
                    }
                    resultModel.setResult(validationResult);
                    getView().getWarningList().setModel(resultModel);
            }
        }
    }

    private class DesignerComponentListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            JideTabbedPane tabbedPane = (JideTabbedPane) e.getSource();
            Component component = tabbedPane.getSelectedComponent();
            if (component instanceof DesignerTab)
            {
                ((DesignerTab) component).updateComponent();
            }
        }

    }

}