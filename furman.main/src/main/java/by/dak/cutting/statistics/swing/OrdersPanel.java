package by.dak.cutting.statistics.swing;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.cutting.swing.AListTab;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.swing.table.AListUpdater;
import org.hibernate.Criteria;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 12.10.11
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
public class OrdersPanel extends AListTab<Order, StatisticFilter>
{
    private OrdersListUpdater listUpdater = new OrdersListUpdater();

    @Override
    public void init()
    {
        getListNaviTable().setShowNaviControls(false);
        getListNaviTable().setListUpdater(listUpdater);
        listUpdater.setSearchFilter(SearchFilter.instanceUnbound());
        super.init();
    }

    @Override
    protected void initBindingListeners()
    {
    }

    @Override
    protected void valueChanged()
    {
        getListNaviTable().reload();
    }

    private class OrdersListUpdater extends AListUpdater<Order>
    {

        @Override
        public String getName()
        {
            return new StringBuffer("OrdersListUpdater").append(".").append(getElementClass().getSimpleName()).toString();
        }

        @Override
        public void update()
        {
            getList().clear();
            if (getValue() != null)
            {
                adjustFilter();
                getList().addAll(FacadeContext.getOrderFacade().loadAll(getSearchFilter()));
            }
        }

        @Override
        public String[] getVisibleProperties()
        {
            return new String[]
                    {
                            Order.PROPERTY_number,
                            Order.PROPERTY_name
                    };
        }


        @Override
        public void adjustFilter()
        {
            SearchFilter searchFilter = SearchFilter.instanceUnbound();
            if (getValue().getStart() != null)
                searchFilter.ge(Order.PROPERTY_readyDate, getValue().getStart());
            if (getValue().getEnd() != null)
                searchFilter.le(Order.PROPERTY_readyDate, getValue().getEnd());
            if (!Order.isNull(getValue().getOrder()))
                searchFilter.eq(Order.PROPERTY_id, getValue().getOrder().getId());
            else
            {
                if (!Customer.isNull(getValue().getCustomer()))
                    searchFilter.eq(Order.PROPERTY_customer, getValue().getCustomer());
                MaterialType materialType = getValue().getType();
                if (materialType != null)
                {
                    searchFilter.eq(materialType, Order.PROPERTY_orderItems, OrderItem.PROPERTY_details,
                            AOrderDetail.PROPERTY_priceAware, PriceAware.PROPERTY_type);
                    searchFilter.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                }
            }
            searchFilter.in(Order.PROPERTY_orderStatus, Arrays.asList(OrderStatus.notEditables()));
            searchFilter.addAscOrder(AOrder.PROPERTY_createdDailySheet + "." + PersistenceEntity.PROPERTY_id);
            searchFilter.addAscOrder(Order.PROPERTY_orderNumber);
            setSearchFilter(searchFilter);
        }

        @Override
        public ResourceMap getResourceMap()
        {
            return Application.getInstance().getContext().getResourceMap(OrdersPanel.class);
        }

        @Override
        public int getCount()
        {
            return getList().size();
        }
    }
}
