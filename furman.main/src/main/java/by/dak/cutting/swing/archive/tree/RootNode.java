package by.dak.cutting.swing.archive.tree;

import by.dak.buffer.exporter.OrderExporter;
import by.dak.cutting.CuttingApp;
import by.dak.cutting.CuttingView;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.MessageDialog;
import by.dak.cutting.swing.archive.OrderStatusManager;
import by.dak.cutting.swing.order.wizard.OrderWizard;
import by.dak.cutting.swing.renderer.TableEditorsRenders;
import by.dak.cutting.swing.report.ReportsPanel;
import by.dak.cutting.swing.store.helpers.AEntityNEDActions;
import by.dak.order.swing.AOrderWizard;
import by.dak.order.swing.IOrderWizardDelegator;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderStatus;
import by.dak.report.model.ReportsModel;
import by.dak.report.model.impl.ReportModelCreator;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.table.NewEditDeleteActions;
import by.dak.swing.tree.ATreeNode;
import by.dak.template.swing.action.CreateTemplateAction;
import by.dak.utils.BindingUtils;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.swingbinding.JTableBinding;

import javax.sql.DataSource;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 11:14
 */
public class RootNode extends ATreeNode implements ListUpdaterProvider<Order>
{
    private static final String[] EditableProperties = new String[]{Order.PROPERTY_orderStatus, Order.PROPERTY_id};

    private static final String[] VisibleProperties = new String[]{Order.PROPERTY_orderStatus,
            Order.PROPERTY_createdDailySheet,
            Order.PROPERTY_number,
            Order.PROPERTY_name,
            Order.PROPERTY_customer,
            Order.PROPERTY_designer,
            Order.PROPERTY_workedDailySheet,
            Order.PROPERTY_readyDate,
            Order.PROPERTY_orderGroup + "." + OrderGroup.PROPERTY_name,
            Order.PROPERTY_id
    };

    private OrderListUpdater listUpdater = new OrderListUpdater();
    private OrderStatusManager orderStatusManager;

    public RootNode(OrderStatusManager orderStatusManager)
    {
        setUserObject(getResourceMap().getString("node.name"));
        NEDActions nedActions = new NEDActions();
        nedActions.setActions(new String[]{"newValue", "openValue", "deleteValue"});
        listUpdater.setNewEditDeleteActions(nedActions);
        listUpdater.setResourceMap(getResourceMap());
        listUpdater.setVisibleProperties(VisibleProperties);
        listUpdater.setEditableProperties(EditableProperties);
        listUpdater.setActions("exportOrder", "copyOrder", "createTemplate");
        listUpdater.setActionMap(nedActions.getActionMap());


        this.orderStatusManager = orderStatusManager;

        setClosedIcon(getResourceMap().getIcon("all.orders.icon"));
        setLeafIcon(getResourceMap().getIcon("all.orders.icon"));
        setOpenIcon(getResourceMap().getIcon("all.orders.icon"));

    }


    @Override
    protected void initChildren()
    {

    }

    @Override
    public ListUpdater<Order> getListUpdater()
    {
        return listUpdater;
    }


    public class NEDActions extends NewEditDeleteActions<Order>
    {

        @Override
        public void newValue()
        {
            CuttingView.OrderCreator orderCreator = new CuttingView.OrderCreator();
            final Order order = orderCreator.create();
            showWizard(order);
        }

        private void showWizard(final Order order)
        {
            final OrderWizard orderWizard = new OrderWizard(order.getNumber().getStringValue());
            orderWizard.setOrder(order);
            DialogShowers.showWizard(orderWizard, orderWizard.getWizardObserver());
            orderWizard.setiOrderWizardDelegator(new IOrderWizardDelegator()
            {
                @Override
                public void finish(AOrderWizard wizard)
                {
                    setSelectedElement(order);
                    firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
                }

                @Override
                public void cancel(AOrderWizard wizard)
                {
                    if (order.hasId())
                    {
                        setSelectedElement(order);
                        firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
                    }
                }
            });
        }

        @Override
        public void openValue()
        {
            if (getSelectedElement() != null)
            {
                showWizard(getSelectedElement());
            }
            else
            {
                MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
            }
        }

        @Override
        public void deleteValue()
        {
            if (getSelectedElement() != null)
            {
                if (MessageDialog.showConfirmationMessage(MessageDialog.IS_DELETE_RECORD) == JOptionPane.OK_OPTION)
                {
                    FacadeContext.getFacadeBy(getEntityClass()).delete(getSelectedElement());
                    firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSelectedElement());
                }
            }
            else
            {
                MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
            }
        }

        @Action
        public void exportOrder()
        {
            if (getSelectedElement() != null)
            {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle(resourceMap.getString("export.order.dialog.title"));
                jFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int resultValue = jFileChooser.showSaveDialog(getRelatedComponent());
                if (resultValue != JFileChooser.CANCEL_OPTION)
                {
                    File dir = jFileChooser.getSelectedFile();
                    try
                    {
                        OrderExporter orderExporter = new OrderExporter();
                        orderExporter.setOrder(getSelectedElement());
                        DataSource dataSource = (DataSource) FacadeContext.getApplicationContext().getBean("c3p0DataSource");
                        orderExporter.setConnection(dataSource.getConnection());
                        orderExporter.setTargetDir(dir);
                        File file = new File(dir,
                                resourceMap.getString("export.order.file.name.text") + " " +
                                        new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()) + ".zip");
                        orderExporter.setZipFile(file);
                        orderExporter.execute();
                        JOptionPane.showMessageDialog(getRelatedComponent(), file.getAbsolutePath(), "Export Order", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (Exception e)
                    {
                        JOptionPane.showMessageDialog(getRelatedComponent(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(by.dak.order.swing.tree.RootNode.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                }
            }
            else
            {
                MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
            }

        }

        @Action
        public void copyOrder()
        {
            if (!CuttingApp.getApplication().getPermissionManager().checkPermission("copyOrder"))
            {
                return;
            }
            if (getSelectedElement() != null)
            {
                Order order = getSelectedElement();
                Order newOrder = FacadeContext.getOrderFacade().copy(order, getResourceMap().getString("order.copy.suffix"));
                showWizard(newOrder);
            }
            else
            {
                MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
            }
        }

        @Action
        public void createTemplate()
        {
            CreateTemplateAction createTemplateAction = new CreateTemplateAction(getSelectedElement().getId());
            createTemplateAction.action();
        }


    }

    public class OrderListUpdater extends AListUpdater<Order>
    {

        @Override
        public void adjustFilter()
        {
            getSearchFilter().addDescOrder(Order.PROPERTY_createdDailySheet);
            getSearchFilter().addDescOrder(Order.PROPERTY_orderNumber);
        }

        @Override
        public TableCellRenderer getTableCellRenderer(String propertyName)
        {
            if (propertyName.equals(Order.PROPERTY_orderStatus))
            {
                return TableEditorsRenders.getFastOrderStatusRendererEditor(orderStatusManager,
                        Application.getInstance().getContext().getResourceMap(OrderStatus.class));
            }
            else if (propertyName.equals(Order.PROPERTY_id))
            {
                return new TableEditorsRenders.ButtonEditor(getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + Order.PROPERTY_id),
                        getResourceMap().getIcon("reports.icon"));
            }
            else
            {
                return super.getTableCellRenderer(propertyName);
            }

        }

        @Override
        public TableCellEditor getTableCellEditor(String propertyName)
        {
            if (propertyName.equals(Order.PROPERTY_orderStatus))
            {
                return TableEditorsRenders.getFastOrderStatusRendererEditor(orderStatusManager,
                        Application.getInstance().getContext().getResourceMap(OrderStatus.class));
            }
            else if (propertyName.equals(Order.PROPERTY_id))
            {
                return new ReportsEditor();
            }
            else
            {
                return super.getTableCellEditor(propertyName);
            }
        }

        @Override
        public void adjustTableColumn(TableColumn tableColumn)
        {
            if (tableColumn.getIdentifier().equals(getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + Order.PROPERTY_orderStatus)))
            {
                tableColumn.setResizable(false);
                tableColumn.setMaxWidth(32);
            }
            else if (tableColumn.getIdentifier().equals(getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + Order.PROPERTY_id)))
            {
                tableColumn.setResizable(false);
                tableColumn.setMaxWidth(48);
            }
        }

        @Override
        public void adjustColumnBinding(JTableBinding.ColumnBinding columnBinding)
        {
            if (columnBinding.getName().equals(Order.PROPERTY_orderStatus))
            {
                columnBinding.setValidator(new Validator<OrderStatus>()
                {
                    @Override
                    public Result validate(OrderStatus value)
                    {
                        Order order = getNewEditDeleteActions().getSelectedElement();
                        if (value == OrderStatus.production && orderStatusManager.canProductionOrder(order))
                        {
                            return null;
                        }
                        else if (value == OrderStatus.made && orderStatusManager.canMadeOrder(order))
                        {
                            return null;
                        }
                        else if (value == OrderStatus.design)
                        {
                            return null;
                        }
                        return new Validator.Result(null, null);
                    }
                });
                columnBinding.addBindingListener(orderStatusManager.getBindingListener());
            }

        }
    }

    public class ReportsEditor extends TableEditorsRenders.ButtonEditor
    {
        public ReportsEditor()
        {
            super(getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + Order.PROPERTY_id),
                    new TableEditorsRenders.EditorCall<Long, Long>()
                    {
                        private Long value;

                        @Override
                        public void setValue(Long value)
                        {
                            this.value = value;
                        }

                        @Override
                        public Long getValue()
                        {
                            return value;
                        }

                        @Override
                        public Long call() throws Exception
                        {
                            if (getValue() != null)
                            {
                                Order order = FacadeContext.getOrderFacade().findBy(getValue());
                                order.setOrderItems(FacadeContext.getOrderItemFacade().loadBy(order));
                                ReportModelCreator creator = new ReportModelCreator(order);
                                ReportsModel reportsModel = creator.create();
                                ReportsPanel reportsPanel = new ReportsPanel();
                                reportsPanel.setEditable(false);
                                reportsPanel.setReportsModel(reportsModel);
                                DialogShowers.showBy(reportsPanel, orderStatusManager.getRelatedComponent(), true);
                            }
                            return getValue();
                        }
                    },
                    getResourceMap().getIcon("reports.icon")
            );
        }
    }
}
