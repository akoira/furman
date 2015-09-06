package by.dak.cutting.statistics.swing;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.cutting.swing.ItemSelector;
import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderStatus;
import by.dak.swing.FilterPanel;
import by.dak.utils.BindingAdapter;
import org.jdesktop.application.Application;
import org.jdesktop.beansbinding.Binding;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * User: akoyro
 * Date: 16.05.2010
 * Time: 17:49:16
 */
public class PeriodFilterPanel extends FilterPanel<StatisticFilter>
{

    public PeriodFilterPanel()
    {
//        setVisibleProperties("type", "start", "end");
        setVisibleProperties("type", "serviceType", "customer", "order", "start", "end");
        setCacheEditorCreator(Constants.getEntityEditorCreators(StatisticFilter.class));
        setResourceMap(Application.getInstance().getContext().getResourceMap(PeriodFilterPanel.class));
    }


    @Override
    public void init()
    {
        super.init();
        getBindingGroup().addBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                Binding.ValueResult valueResult = binding.getTargetValueForSource();
                if (getValue() == null || valueResult.failed())
                {
                    return;
                }

                if (binding.getName().equals("customer"))
                {
                    List<Order> orders = new ArrayList<Order>();
                    orders.add(Order.NULL_Order);
                    if (!Customer.isNull(getValue().getCustomer()))
                    {
                        SearchFilter searchFilter = SearchFilter.instanceUnbound();
                        searchFilter.in(Order.PROPERTY_orderStatus, Arrays.asList(OrderStatus.notEditables()));
                        searchFilter.eq(Order.PROPERTY_customer, getValue().getCustomer());
                        searchFilter.ge(Order.PROPERTY_readyDate, getValue().getStart());
                        searchFilter.le(Order.PROPERTY_readyDate, getValue().getEnd());
                        searchFilter.addDescOrder(Order.PROPERTY_readyDate);
                        orders.addAll(FacadeContext.getOrderFacade().loadAll(searchFilter));
                    }
                    Component component = getEditors().get("order");
                    if (component != null && component instanceof Component)
                    {
                        ItemSelector selector = (ItemSelector) component;
                        selector.setItems(orders);
                        selector.getComboBoxItem().setSelectedItem(null);
                    }
                }
            }
        });
    }
}
;