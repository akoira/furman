package by.dak.cutting.swing.dictionaries.delivery;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.Constants;
import by.dak.cutting.swing.store.helpers.AStoreNEDActions;
import by.dak.cutting.swing.store.helpers.BoardListUpdater;
import by.dak.cutting.swing.store.helpers.BorderListUpdater;
import by.dak.cutting.swing.store.helpers.FurnitureListUpdater;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.ReservedSaver;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.NewEditDeleteActions;
import by.dak.utils.GenericUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DeliveryListUpdaters
{

    public static class BoardNEDActions extends AStoreNEDActions<Board>
    {
    }

    public static class BorderNEDActions extends AStoreNEDActions<Border>
    {
    }

    public static class FurnitureActions extends AStoreNEDActions<Furniture>
    {
    }

    public abstract static class ACacheDeliveryListUpdater<E extends AStoreElement> extends AListUpdater<E>
    {
        private Delivery delivery;
        private StoreElementStatus status;

        protected ACacheDeliveryListUpdater(Delivery delivery, StoreElementStatus status)
        {
            this.delivery = delivery;
            this.status = status;
            SearchFilter filter = new SearchFilter();
            filter.eq("status", this.status);
            getList().addAll(FacadeContext.getFacadeBy(GenericUtils.getParameterClass(this.getClass(), 0)).loadAll(filter));
            for (E e : getList())
            {
                e.setProvider(delivery.getProvider());
                e.setDelivery(delivery);
                e.setStatus(StoreElementStatus.used);
                FacadeContext.getFacadeBy(GenericUtils.getParameterClass(this.getClass(), 0)).save(e);
            }
        }

        @Override
        public int getCount()
        {
            return getList().size();
        }

        @Override
        public void update()
        {
        }
    }


    public abstract static class ADeliveryListUpdater<E extends AStoreElement> extends AListUpdater<E>
    {

        private AStoreNEDActions<E> actions;
        private Delivery delivery;
        private ReservedSaver reservedSaver;
        private ResourceMap resourceMap;
        private String[] visibleProperties;


        public ADeliveryListUpdater(Delivery delivery, ReservedSaver reservedSaver)
        {
            this.delivery = delivery;
            this.reservedSaver = reservedSaver;
            actions = createActionsBy(getElementClass(), delivery, reservedSaver, getList());
            resourceMap = getResourceMapBy(getElementClass());
            visibleProperties = getVisiblePropertiesBy(getElementClass());
            setSearchFilter(SearchFilter.instanceUnbound());
            adjustFilter();
        }

        public void adjustFilter()
        {
            SearchFilter.DCriterion<Criterion> criterion = null;
            criterion = new SearchFilter.DCriterion<Criterion>("delivery", Restrictions.eq("delivery", delivery));
            getSearchFilter().addCriterion(criterion);
        }

        public void loadList()
        {
            if (this.delivery.hasId())
            {
                getList().addAll(FacadeContext.getFacadeBy(getElementClass()).loadAll(getSearchFilter()));
            }
        }

        @Override
        public void update()
        {
            ArrayList<E> list = new ArrayList<E>(getList());
            getList().clear();
            getList().addAll(list);
        }

        @Override
        public int getCount()
        {
            return getList().size();
        }

        @Override
        public NewEditDeleteActions getNewEditDeleteActions()
        {
            return actions;
        }

        @Override
        public ResourceMap getResourceMap()
        {
            return resourceMap;
        }

        @Override
        public String[] getVisibleProperties()
        {
            return visibleProperties;
        }

        public ReservedSaver getReservedSaver()
        {
            return reservedSaver;
        }
    }

    /**
     * ListUpdater для Delivery wizard для обналение таблицы boards
     *
     * @param delivery
     * @return
     */
    public static ADeliveryListUpdater<Board> createBoardListUpdater(final Delivery delivery, final ReservedSaver reservedSaver)
    {
        ADeliveryListUpdater<Board> result = new ADeliveryListUpdater<Board>(delivery, reservedSaver)
        {
        };
        return result;
    }

    /**
     * ListUpdater для Delivery wizard для обналение таблицы borders
     *
     * @param delivery
     * @return
     */
    public static ADeliveryListUpdater<Border> createBorderListUpdater(final Delivery delivery, ReservedSaver reservedSaver)
    {
        ADeliveryListUpdater<Border> result = new ADeliveryListUpdater<Border>(delivery, reservedSaver)
        {
        };

        return result;
    }


    public static ADeliveryListUpdater<Furniture> createFurnitureListUpdater(final Delivery delivery, ReservedSaver reservedSaver)
    {
        ADeliveryListUpdater<Furniture> result = new ADeliveryListUpdater<Furniture>(delivery, reservedSaver)
        {
        };

        return result;
    }


    private static String[] getVisiblePropertiesBy(Class<? extends AStoreElement> elementClass)
    {
        String[] visibleProperties;
        if (elementClass == Board.class)
            visibleProperties = Constants.BOARD_TABLE_VISIBLE_PROPERTIES;
        else if (elementClass == Border.class)
            visibleProperties = Constants.BORDER_TABLE_VISIBLE_PROPERTIES;
        else if (elementClass == Furniture.class)
            visibleProperties = Constants.FurnitureTableVisibleProperties;
        else
            throw new IllegalArgumentException();
        return visibleProperties;
    }


    private static ResourceMap getResourceMapBy(Class<? extends AStoreElement> elementClass)
    {
        ResourceMap resourceMap;
        if (elementClass == Board.class)
            resourceMap = Application.getInstance().getContext().getResourceMap(BoardListUpdater.class);
        else if (elementClass == Border.class)
            resourceMap = Application.getInstance().getContext().getResourceMap(BorderListUpdater.class);
        else if (elementClass == Furniture.class)
            resourceMap = Application.getInstance().getContext().getResourceMap(FurnitureListUpdater.class);
        else
            throw new IllegalArgumentException();
        return resourceMap;
    }


    private static AStoreNEDActions createActionsBy(Class<? extends AStoreElement> elementClass,
                                                    Delivery delivery, ReservedSaver reservedSaver,
                                                    List<? extends AStoreElement> list)
    {
        Class<? extends AStoreNEDActions> aClass;
        if (elementClass == Board.class)
            aClass = BoardNEDActions.class;
        else if (elementClass == Border.class)
            aClass = BorderNEDActions.class;
        else if (elementClass == Furniture.class)
            aClass = FurnitureActions.class;
        else
            throw new IllegalArgumentException();

        AStoreNEDActions actions;
        try
        {
            actions = aClass.newInstance();
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
        actions.setReservedSaver(reservedSaver);
        actions.setDelivery(delivery);
        actions.setList(list);
        return actions;
    }


}
