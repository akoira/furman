package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.dictionaries.AbstractPricesTab;
import by.dak.cutting.swing.dictionaries.delivery.DeliveryNEDActions;
import by.dak.cutting.swing.dictionaries.service.ServicePricesTab;
import by.dak.cutting.swing.order.tree.DateOrderNode;
import by.dak.cutting.swing.store.Constants;
import by.dak.cutting.swing.store.modules.ServicePanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListNaviTable;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.NewEditDeleteActions;
import by.dak.utils.BindingAdapter;
import by.dak.utils.BindingUtils;
import by.dak.utils.MathUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.swingbinding.JTableBinding;

import java.util.Calendar;
import java.util.Date;

/**
 * User: akoyro
 * Date: 01.12.2009
 * Time: 16:45:44
 */
public class ListUpdaters
{

    public static ListUpdater<Order> createOrderListUpdateBy(final Date date, final Customer customer)
    {
        ListUpdater<Order> result = new AListUpdater<Order>()
        {
            private OrderNEDActions actions = new OrderNEDActions();

            public void adjustFilter()
            {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                getSearchFilter().addCriterion(new SearchFilter.DCriterion<Criterion>("createdDailySheet.date", Restrictions.sqlRestriction("month(date)=?", calendar.get(Calendar.MONTH) + 1, IntegerType.INSTANCE)));
                getSearchFilter().addCriterion(new SearchFilter.DCriterion<Criterion>("createdDailySheet.date", Restrictions.sqlRestriction("year(date)=?", calendar.get(Calendar.YEAR), IntegerType.INSTANCE)));
                getSearchFilter().addCriterion(new SearchFilter.DCriterion<Criterion>("customer", Restrictions.eq("customer", customer)));
            }

            @Override
            public String[] getVisibleProperties()
            {
                return Constants.OrderTableVisibleProperties;
            }

            @Override
            public ResourceMap getResourceMap()
            {
                return Application.getInstance().getContext().getResourceMap(DateOrderNode.class);
            }

            @Override
            public NewEditDeleteActions getNewEditDeleteActions()
            {
                return actions;
            }
        };
        return result;
    }

    public static ListUpdater<Order> createOrderListUpdaterBy(final Customer customer)
    {
        ListUpdater<Order> result = new AListUpdater<Order>()
        {
            private OrderNEDActions actions = new OrderNEDActions();

            public void adjustFilter()
            {
                getSearchFilter().eq(Order.PROPERTY_customer, customer);
            }


            @Override
            public String[] getVisibleProperties()
            {
                return Constants.OrderTableVisibleProperties;
            }

            @Override
            public ResourceMap getResourceMap()
            {
                return Application.getInstance().getContext().getResourceMap(DateOrderNode.class);
            }

            @Override
            public NewEditDeleteActions getNewEditDeleteActions()
            {
                return actions;
            }
        };
        return result;
    }


    public final static ListUpdater<Order> OrdersListUpdater = new AListUpdater<Order>()
    {
        private OrderNEDActions actions = new OrderNEDActions();


        @Override
        public void adjustFilter()
        {
        }

        @Override
        public String[] getVisibleProperties()
        {
            return Constants.OrderTableVisibleProperties;
        }

        @Override
        public ResourceMap getResourceMap()
        {
            return Application.getInstance().getContext().getResourceMap(DateOrderNode.class);
        }

        @Override
        public NewEditDeleteActions getNewEditDeleteActions()
        {
            return actions;
        }
    };


    public static BoardListUpdater createBoardListUpdaterBy(final StoreElementStatus status)
    {
        BoardListUpdater result;
        switch (status)
        {
            case exist:
            case used:
            case order:
                result = new BoardListUpdater(status);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return result;
    }


    public static ListUpdater<Furniture> createFurnitureListUpdaterBy(final StoreElementStatus status)
    {
        return new FurnitureListUpdater(status);
    }

    public static ListUpdater<Border> createBorderListUpdaterBy(final StoreElementStatus status)
    {
        return new BorderListUpdater(status);
    }

    public static ListUpdater<Delivery> getDeliveryListUpdater()
    {
        return new AListUpdater<Delivery>()
        {
            private DeliveryNEDActions actions;

            @Override
            public NewEditDeleteActions getNewEditDeleteActions()
            {
                if (actions == null)
                {
                    actions = new DeliveryNEDActions();
                }
                return actions;
            }

            @Override
            public void adjustFilter()
            {
                getSearchFilter().addColumnOrder(new SearchFilter.DCriterion<org.hibernate.criterion.Order>("deliveryDate", org.hibernate.criterion.Order.desc("deliveryDate")));
            }

            @Override
            public String[] getVisibleProperties()
            {
                return Constants.DeliveryTableVisibleProperties;
            }
        };
    }


    public final static ListUpdater<FurnitureType> FurnitureTypeListUpdater = new AFurnitureTypeListUpdater<FurnitureType>()
    {
    };

    public final static AListUpdater<BoardDef> BOARD_DEF_LIST_UPDATER = new BoardDefListUpdater();


    public final static BorderDefListUpdater BORDER_DEF_LIST_UPDATER = new BorderDefListUpdater();

    public final static ListUpdater<Service> SERVICE_LIST_UPDATER = new AListUpdater<Service>()
    {
        private ServiceNEDActions serviceNEDActions = new ServiceNEDActions();


        @Override
        public void adjustFilter()
        {
            getSearchFilter().addAscOrder(Service.PROPERTY_serviceType);
        }

        @Override
        public String[] getVisibleProperties()
        {
            return Constants.SERVICE_TABLE_VISIBLE_PROPERTIES;
        }

        @Override
        public NewEditDeleteActions getNewEditDeleteActions()
        {
            return serviceNEDActions;
        }

        @Override
        public ResourceMap getResourceMap()
        {
            return Application.getInstance().getContext().getResourceMap(ServicePanel.class);
        }
    };


    public static ListUpdater<PriceEntity> createListUpdaterBy
            (
                    final PriceAware priceAware)
    {
        ListUpdater<PriceEntity> result = new AListUpdater<PriceEntity>()
        {
            public void adjustFilter()
            {
                getSearchFilter().eq(PriceEntity.PROPERTY_priceAware, priceAware);
                getSearchFilter().eq(PriceEntity.PROPERTY_priced + "." + "pricedType", priceAware.getType().pricedClass());
                getSearchFilter().addAscOrder(PriceEntity.PROPERTY_priced + "." + "name");
            }

            @Override
            public String[] getVisibleProperties()
            {
                return Constants.PRICEAWARE_TABLE_VISIBLE_PROPERTIES;
            }

            @Override
            public void initAdditionalTableControls(ListNaviTable listNaviTable)
            {
                SetForAllHelper helper = new SetForAllHelper(listNaviTable);
                helper.setActions("setValueForAll");
                helper.init();

                initPriceChangedBinding(listNaviTable);
            }

            @Override
            public ResourceMap getResourceMap()
            {
                return Application.getInstance().getContext().getResourceMap(AbstractPricesTab.class);
            }

            @Override
            public String[] getEditableProperties()
            {
                return Constants.PRICE_TABLE_EDITABLE_PROPERTIES;
            }
        };
        return result;
    }

    public static ListUpdater<PriceEntity> createListUpdaterBy
            (
                    final Service service)
    {
        ListUpdater<PriceEntity> result = new AListUpdater<PriceEntity>()
        {
            public void adjustFilter()
            {
                getSearchFilter().eq(PriceEntity.PROPERTY_priced, service);
                getSearchFilter().eq(PriceEntity.PROPERTY_priced + "." + "pricedType", "Service");
            }


            @Override
            public String[] getVisibleProperties()
            {
                return Constants.PRICED_TABLE_VISIBLE_PROPERTIES;
            }

            @Override
            public String[] getEditableProperties()
            {
                return Constants.PRICE_TABLE_EDITABLE_PROPERTIES;
            }


            @Override
            public void initAdditionalTableControls(final ListNaviTable listNaviTable)
            {
                SetForAllHelper helper = new SetForAllHelper(listNaviTable);
                helper.init();

                initPriceChangedBinding(listNaviTable);
            }

            @Override
            public ResourceMap getResourceMap()
            {
                return Application.getInstance().getContext().getResourceMap(ServicePricesTab.class);
            }
        };
        return result;
    }


    private static void initPriceChangedBinding(final ListNaviTable listNaviTable)
    {
        BindingAdapter bindingAdapter = new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                if (binding instanceof JTableBinding.ColumnBinding)
                {
                    JTableBinding.ColumnBinding columnBinding = (JTableBinding.ColumnBinding) binding;
                    PriceEntity priceEntity = (PriceEntity) columnBinding.getSourceObject();
                    if (columnBinding.getColumnName().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_price)) ||
                            columnBinding.getColumnName().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_priceFaktor)))
                    {
                        if (priceEntity.getPriceFaktor() != null || priceEntity.getPriceFaktor() > 0)
                            priceEntity.setPriceDealer(MathUtils.round(priceEntity.getPrice() * priceEntity.getPriceFaktor(), 4));
                    }

                    if (columnBinding.getColumnName().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_price)) ||
                            columnBinding.getColumnName().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_priceSaleFaktor)))
                    {
                        if (priceEntity.getPriceSaleFaktor() != null || priceEntity.getPriceSaleFaktor() > 0)
                            priceEntity.setPriceSale(MathUtils.round(priceEntity.getPrice() * priceEntity.getPriceSaleFaktor(), 4));
                    }
                    FacadeContext.getPriceFacade().save(priceEntity);


                }
            }
        };

        listNaviTable.getBindingGroup().addBindingListener(bindingAdapter);
    }


    public abstract static class AStoreElementListUpdater<E extends AStoreElement> extends AListUpdater<E>
    {

        private StoreElementStatus status;

        public AStoreElementListUpdater(StoreElementStatus status)
        {
            this.status = status;
        }

        public StoreElementStatus getStatus()
        {
            return status;
        }


        @Override
        public void initAdditionalTableControls(ListNaviTable listNaviTable)
        {
            listNaviTable.getBindingGroup().addBindingListener(new BindingAdapter()
            {
                @Override
                public void synced(Binding binding)
                {
                    if (status == StoreElementStatus.order &&
                            binding != null &&
                            binding.getName() != null &&
                            binding.getName().equals(AStoreElement.PROPERTY_ordered))
                    {
                        PersistenceEntity entity = (PersistenceEntity) binding.getSourceObject();
                        FacadeContext.getFacadeBy(entity.getClass()).save(entity);
                    }
                }
            });
        }

        @Override
        public String[] getEditableProperties()
        {
            if (status == StoreElementStatus.order)
            {
                return new String[]{"ordered"};
            }
            else
            {
                return super.getEditableProperties();
            }
        }

        @Override
        public String getName()
        {
            return super.getName() + "." + status.name();
        }

    }
}
