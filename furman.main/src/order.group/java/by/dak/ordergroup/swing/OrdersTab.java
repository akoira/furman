package by.dak.ordergroup.swing;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.impl.OrderFacadeImpl;
import by.dak.cutting.swing.AListTab;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.Order;
import by.dak.swing.table.AListUpdater;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.swingx.table.TableColumnExt;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * User: akoyro
 * Date: 15.01.2011
 * Time: 18:14:23
 */
public class OrdersTab extends AListTab<Order, OrderGroup>
{
    public static final String[] VISIBLE_PROPERTIES = new String[]{AOrder.PROPERTY_orderGroup,
            Order.PROPERTY_number,
            Order.PROPERTY_name,
            Order.PROPERTY_workedDailySheet,
            Order.PROPERTY_readyDate};
    private static final String[] EDITABLE_PROPERTIES = new String[]{Order.PROPERTY_orderGroup};


    public OrdersTab()
    {
        OrdersUpdater updater = new OrdersUpdater();
        updater.setResourceMap(getResourceMap());
        getListNaviTable().setListUpdater(updater);
        getListNaviTable().setSortable(false);
    }

    @Override
    protected void initBindingListeners()
    {
        BindingAdapter bindingAdapter = new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                if (binding.getName().equals(Order.PROPERTY_orderGroup))
                {
                    Order order = (Order) binding.getSourceObject();
                    order.setOrderGroup(getValue());
                    FacadeContext.getOrderFacade().save(order);
                }
            }
        };
        getListNaviTable().getBindingGroup().addBindingListener(bindingAdapter);
    }

    @Override
    public void init()
    {
        super.init();

        TableColumnExt tableColumnExt = (TableColumnExt) getListNaviTable().getTable().getColumnModel().getColumn(0);
        tableColumnExt.setResizable(false);
        tableColumnExt.setMaxWidth(24);
    }

    @Override
    protected void valueChanged()
    {

        getListNaviTable().reload();
    }

    public class OrdersUpdater extends AListUpdater<Order>
    {
        public OrdersUpdater()
        {
            getSearchFilter().setPageSize(Integer.MAX_VALUE);
        }


        @Override
        public String[] getVisibleProperties()
        {
            return VISIBLE_PROPERTIES;
        }

        @Override
        public String[] getEditableProperties()
        {
            return EDITABLE_PROPERTIES;
        }

        @Override
        public void update()
        {
            getList().clear();
            getList().addAll(FacadeContext.getOrderFacade().loadAllBy(getValue()));
            if (getList().size() < 1)
            {
                SearchFilter searchFilter = OrderFacadeImpl.getNotGroupedFilter();
				searchFilter.addAscOrder(Order.PROPERTY_createdDailySheet + '.' + Dailysheet.PROPERTY_date);
				searchFilter.addAscOrder(Order.PROPERTY_orderNumber);
				getList().addAll(FacadeContext.getOrderFacade().loadAll(searchFilter));
                getListNaviTable().getTable().setEditable(true);
            }
            else
            {
                getListNaviTable().getTable().setEditable(false);
            }
        }


        @Override
        public int getCount()
        {
            return getList().size();
        }

        @Override
        public TableCellEditor getTableCellEditor(String propertyName)
        {
            if (propertyName.equals(Order.PROPERTY_orderGroup))
            {
                return new OrderGroupEditor();
            }
            return super.getTableCellEditor(propertyName);
        }

        @Override
        public TableCellRenderer getTableCellRenderer(String propertyName)
        {
            if (propertyName.equals(Order.PROPERTY_orderGroup))
            {
                return new OrderGroupRender();
            }
            return super.getTableCellRenderer(propertyName);
        }
    }

    public class OrderGroupRender extends JCheckBox implements TableCellRenderer
    {

        public OrderGroupRender()
        {
            super();
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column)
        {

            setSelected(value != null);
            if (isSelected)
            {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }
            else
            {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            return this;
        }
    }

    public class OrderGroupEditor extends DefaultCellEditor
    {
        public OrderGroupEditor()
        {
            super(new JCheckBox());
            ((JCheckBox) editorComponent).removeActionListener(delegate);
            delegate = new EditorDelegate()
            {
                public void setValue(Object value)
                {
                    boolean selected = value != null;
                    ((JCheckBox) editorComponent).setSelected(selected);
                }

                public Object getCellEditorValue()
                {
                    return ((JCheckBox) editorComponent).isSelected() ? OrdersTab.this.getValue() : null;
                }
            };

            ((JCheckBox) editorComponent).addActionListener(delegate);
        }
    }
}
