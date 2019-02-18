package by.dak.cutting.swing.renderer;

import by.dak.cutting.swing.DComboBox;
import by.dak.cutting.swing.archive.OrderStatusManager;
import by.dak.persistence.entities.OrderStatus;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.JXButton;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 18.06.2009
 * Time: 22:17:10
 * To change this template use File | Settings | File Templates.
 */
public class TableEditorsRenders
{

    private static JXButton createButton()
    {
        JXButton button = new JXButton();
        button.setPreferredSize(new Dimension(32, 32));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    private static void adjustButtonColor(JButton button, JTable table, boolean isSelected)
    {
        if (isSelected)
        {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        }
        else
        {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
    }

    public static class ItemsComboBox<I> extends DComboBox<I>
    {
        private List<I> items = ObservableCollections.observableList(new ArrayList<I>());

        public List<I> getItems()
        {
            return items;
        }

        public ItemsComboBox()
        {
            setRenderer(new EntityListRenderer());
            BindingGroup bindingGroup = new BindingGroup();
            Binding binding = SwingBindings.createJComboBoxBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                    items, this);
            bindingGroup.addBinding(binding);
            bindingGroup.bind();
        }

    }

    //popup лит зависит от текущего статуа

    public static class OrderStatusEditor extends DefaultCellEditor
    {
        private OrderStatusManager orderStatusManager;

        public OrderStatusEditor(OrderStatusManager orderStatusManager)
        {
            super(new ItemsComboBox<OrderStatus>());
            this.orderStatusManager = orderStatusManager;
        }

        @Override
        public Component getTableCellEditorComponent(JTable
                                                             table, Object value, boolean isSelected, int row, int column)
        {
            ((ItemsComboBox) getComponent()).getItems().clear();
            ((ItemsComboBox) getComponent()).getItems().addAll(orderStatusManager.allowedStatuses((OrderStatus) value));
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
        }
    }

    public static interface EditorCall<V, R> extends Callable<R>
    {
        void setValue(V value);

        V getValue();
    }

    public static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer
    {
        private JButton button;
        private Object value;
        private EditorCall editorCall;

        public ButtonEditor(String buttonText)
        {
            this(buttonText, (Icon) null);
        }

        public ButtonEditor(String buttonText, Icon icon)
        {
            button = createButton();
            button.setIcon(icon);
            if (icon == null)
            {
                button.setText(buttonText);
            }
            button.setToolTipText(buttonText);
            button.setOpaque(true);
            button.setFocusable(false);
        }

        public ButtonEditor(String buttonText, EditorCall editorCall)
        {
            this(buttonText, editorCall, null);
        }

        public ButtonEditor(String buttonText, EditorCall editorCall, Icon icon)
        {
            this(buttonText, icon);
            this.editorCall = editorCall;
            button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                ButtonEditor.this.editorCall.call();
                            }
                            catch (Exception e1)
                            {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            });
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            adjustButtonColor(button, table, isSelected);
            return button;
        }


        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column)
        {
            if (editorCall != null)
                editorCall.setValue(value);
            this.value = value;
            adjustButtonColor(button, table, isSelected);
            return button;
        }

        public Object getCellEditorValue()
        {
            return value;
        }

        public boolean stopCellEditing()
        {
            super.cancelCellEditing();
            return true;
        }

        protected void fireEditingStopped()
        {
            fireEditingCanceled();
        }
    }

    /**
     * editor для быстрой установки made статуса
     */
    public static class FastOrderStatusRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer
    {
        private JXButton button;

        private OrderStatusManager orderStatusManager;
        private OrderStatus value = OrderStatus.design;

        private ResourceMap resourceMap;


        public void init()
        {
            button = createButton();
            button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    List<OrderStatus> statuses = orderStatusManager.allowedStatuses(value);

                    switch (value)
                    {
                        case miscalculation:
                        case webMiscalculation:
                            value = OrderStatus.design;
                            break;
                        case design:
                        case webDesign:
                            value = OrderStatus.production;
                            break;
                        case production:
                            value = OrderStatus.made;
                            break;
                        case made:
                            value = OrderStatus.shipped;
                            break;
                        case shipped:
                            value = OrderStatus.archive;
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                    adjustButton(button, value);
                    stopCellEditing();
                }
            });
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, final Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            JXButton button = createButton();
            adjustButton(button, (OrderStatus) value);
            adjustButtonColor(button, table, isSelected);
            return button;
        }

        @Override
        public Object getCellEditorValue()
        {
            return value;
        }


        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            this.value = (OrderStatus) value;
            adjustButton(this.button, this.value);
            adjustButtonColor(button, table, isSelected);
            return this.button;
        }

        private Component adjustButton(JButton button, OrderStatus status)
        {
            boolean result = isEnable(status);
            //button.setEnabled(result);
            // button.setBackground(status.getColor());
            button.setIcon(status.getIcon(resourceMap));
            return button;
        }

        private boolean isEnable(OrderStatus status)
        {
            boolean result;
            switch (status)
            {
                case archive:
                case made:
                case shipped:
                    result = false;
                    break;
                case production:
                case miscalculation:
                case readyToProduction:
                case design:
                case webDesign:
                case webMiscalculation:
                    result = true;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            return result;
        }

        public ResourceMap getResourceMap()
        {
            return resourceMap;
        }

        public void setResourceMap(ResourceMap resourceMap)
        {
            this.resourceMap = resourceMap;
        }

        public void setOrderStatusManager(OrderStatusManager orderStatusManager)
        {
            this.orderStatusManager = orderStatusManager;
        }
    }

    public static FastOrderStatusRendererEditor getFastOrderStatusRendererEditor(OrderStatusManager orderStatusManager, ResourceMap resourceMap)
    {
        FastOrderStatusRendererEditor result = new FastOrderStatusRendererEditor();
        result.setOrderStatusManager(orderStatusManager);
        result.setResourceMap(resourceMap);
        result.init();
        return result;
    }
}
