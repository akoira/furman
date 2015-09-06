package by.dak.cutting.swing.order;

import by.dak.cutting.swing.DModPanel;
import by.dak.cutting.swing.order.wizard.ClearNextStepObserver;
import by.dak.persistence.entities.Order;
import by.dak.swing.TabIterator;
import by.dak.swing.table.PopupMenuHelper;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * User: akoyro
 * Date: 16.07.2010
 * Time: 18:57:50
 */
public class OrderItemsTab extends DModPanel<Order> implements ClearNextStepObserver
{
    public OrderItemsTab()
    {
        setShowOkCancel(false);
        setShowWarningList(false);

        PopupMenuHelper popupMenuHelper = new PopupMenuHelper(this.getTabbedPane());
        popupMenuHelper.setActionMap(Application.getInstance().getContext().getActionMap(this));
        popupMenuHelper.setActions("addNewOrderItem", "deleteOrderItem");
        popupMenuHelper.init();

        addPropertyChangeListener("value", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                if (getValue() != null)
                {
                    List<Component> tabs = OrderItemTabFactory.ORDER_ITEM_TAB_FACTORY.getOrderItemTabsBy(getValue());
                    for (Component component : tabs)
                    {
                        addTab(component);
                    }
                }
            }
        });

    }

    @Override
    public void save()
    {
        TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(getTabbedPane())
        {
            @Override
            protected Boolean iterate(Component tab)
            {
                if (tab instanceof OrderDetailsTab)
                {
                    ((OrderDetailsTab) tab).getOrderDetailsControl().saveAll();
                }
                return true;
            }
        };
        tabIterator.iterate();
    }

    @Override
    public void addClearNextStepListener(final PropertyChangeListener listener)
    {
        TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(getTabbedPane())
        {
            @Override
            protected Boolean iterate(Component tab)
            {
                if (tab instanceof ClearNextStepObserver)
                {
                    ((ClearNextStepObserver) tab).addClearNextStepListener(listener);
                }
                return true;
            }
        };
        tabIterator.iterate();
    }

    @Override
    public void removeClearNextStepListener(final PropertyChangeListener listener)
    {
        TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(getTabbedPane())
        {
            @Override
            protected Boolean iterate(Component tab)
            {
                if (tab instanceof ClearNextStepObserver)
                {
                    ((ClearNextStepObserver) tab).removeClearNextStepListener(listener);
                }
                return true;
            }
        };
        tabIterator.iterate();
    }

    public boolean validateGui()
    {
        TabIterator<Boolean> tabIterator = new TabIterator<Boolean>(getTabbedPane())
        {
            @Override
            protected Boolean iterate(Component tab)
            {
                return !(tab instanceof OrderDetailsTab) || ((OrderDetailsTab) tab).validateGUI();
            }

            @Override
            public boolean canNext(Boolean previosResult)
            {
                return previosResult;
            }
        };
        return tabIterator.iterate();
    }

    @Action
    public void addNewOrderItem()
    {
//        OrderItemPanel orderItemPanel = new OrderItemPanel();
//        OrderItem orderItem = new OrderItem(null);
//        orderItem.setOrder(getValue());
//        ZFacade facade = new ZFacade();
//        orderItemPanel.setValue(facade);
//        orderItemPanel.setValueSave(new ValueSave<OrderItem>()
//        {
//            @Override
//            public void save(OrderItem value)
//            {
//                //FacadeContext.getOrderItemFacade().save(value);
//                Component detailsTab = OrderItemTabFactory.ORDER_ITEM_TAB_FACTORY.getOrderItemTabsBy(value);
//                addTab(value.getName(), detailsTab);
//            }
//        });
//        DialogShowers.showBy(orderItemPanel, this,true);
    }

    @Action
    public void deleteOrderItem()
    {
        Component component = getTabbedPane().getSelectedComponent();
//        if (component instanceof )
    }
}
