package by.dak.ordergroup.swing;

import by.dak.cutting.swing.AListTab;
import by.dak.cutting.swing.store.helpers.AEntityNEDActions;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.entities.Dailysheet;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.NewEditDeleteActions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: akoyro
 * Date: 14.01.2011
 * Time: 13:46:59
 */
public class OrderGroupsTab extends AListTab<OrderGroup, Dailysheet>
{
    public OrderGroupsTab()
    {
        OrderGroupsUpdater updater = new OrderGroupsUpdater();
        updater.setResourceMap(getResourceMap());
        getListNaviTable().setListUpdater(updater);
        getListNaviTable().setSortable(false);
        getListNaviTable().getListUpdater().getNewEditDeleteActions().addPropertyChangeListener(OrderGroupActions.PROPERTY_updateGui,
                new PropertyChangeListener()
                {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt)
                    {
                        getListNaviTable().reload();
                    }
                });
    }

    @Override
    protected void initBindingListeners()
    {
    }

    public static class OrderGroupsUpdater extends AListUpdater<OrderGroup>
    {
        public static final String[] VISIBLE_PROPERTIES = new String[]{OrderGroup.PROPERTY_name, OrderGroup.PROPERTY_dailysheet};
        private AEntityNEDActions<OrderGroup> actions = new OrderGroupActions();

        @Override
        public void adjustFilter()
        {
            getSearchFilter().addDescOrder("dailysheet.date");
        }

        @Override
        public String[] getVisibleProperties()
        {
            return VISIBLE_PROPERTIES;
        }

        @Override
        public NewEditDeleteActions getNewEditDeleteActions()
        {
            return actions;
        }
    }
}
