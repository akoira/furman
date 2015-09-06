package by.dak.cutting.swing.archive;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.linear.LinearCuttingModel;
import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.StripsEntity;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.report.ReportType;
import by.dak.utils.BindingAdapter;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.application.Application;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingListener;

import javax.swing.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 09.08.2009
 * Time: 12:02:12
 */
public class OrderStatusManager
{

    private JComponent relatedComponent;

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private BindingListener bindingListener;

    public OrderStatusManager(JComponent relatedComponent)
    {
        this.relatedComponent = relatedComponent;
    }

    /**
     * Список order status доступных после установки этого статуса
     *
     * @param currentStatus
     * @return
     */
    public List<OrderStatus> allowedStatuses(OrderStatus currentStatus)
    {
        ArrayList<OrderStatus> statuses = new ArrayList<OrderStatus>();
        switch (currentStatus)
        {
            case miscalculation:
                statuses.add(OrderStatus.miscalculation);
                statuses.add(OrderStatus.design);
                break;
            case design:
                statuses.add(OrderStatus.design);
                statuses.add(OrderStatus.production);
                break;
            case production:
                statuses.add(OrderStatus.production);
                statuses.add(OrderStatus.made);
                break;
            case made:
                statuses.add(OrderStatus.made);
                statuses.add(OrderStatus.shipped);
                break;
            case shipped:
                statuses.add(OrderStatus.shipped);
                statuses.add(OrderStatus.archive);
                break;
            case archive:
                statuses.add(OrderStatus.archive);
                statuses.add(OrderStatus.shipped);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return statuses;
    }

    public void setOrderStatus(Order order, OrderStatus orderStatus)
    {
        OrderStatus oldValue = order.getStatus();
        switch (orderStatus)
        {
            case miscalculation:
            case design:
                processOrderStatusDesign(order);
                break;
            case production:
                processOrderStatusProduction(order);
                break;
            case made:
                processOrderStatusMade(order);
                break;
            case shipped:
                break;
            case archive:
                break;
            default:
                throw new IllegalArgumentException();
        }
        order.setStatus(orderStatus);
        propertyChangeSupport.firePropertyChange("orderStatus", oldValue, orderStatus);
    }

    private void processOrderStatusDesign(Order order)
    {
        FacadeContext.getStripsFacade().deleteAll(order);
        FacadeContext.getReportJCRFacade().removeAll(order);
    }


    private void processOrderStatusMade(Order order)
    {
        //FacadeContext.getBoardFacade().setExistStatusForOrderBoards(order);
        order.setOrderItems(FacadeContext.getOrderItemFacade().loadBy(order));

        FacadeContext.getBoardFacade().putRestsToStore(order);

        List<Board> boards = FacadeContext.getBoardFacade().findAllByField("order", order);
        for (Board board : boards)
        {
            board.setStatus(StoreElementStatus.used);
            FacadeContext.getBoardFacade().save(board);
        }

        processOrderStatusMadeForLinear(order);
    }

    private void processOrderStatusMadeForLinear(Order order)
    {
        //в группе остался последний заказ который не переведён в продактион
        SearchFilter filter = SearchFilter.instanceSingle();
        filter.eq(Order.PROPERTY_orderGroup, order.getOrderGroup());
        filter.eq(Order.PROPERTY_orderStatus, OrderStatus.production);
        if (FacadeContext.getOrderFacade().getCount(filter) == 1)
        {
            LinearCuttingModel cuttingModel = FacadeContext.getLinearStripsFacade().loadLinearCuttingModelBy(order.getOrderGroup());
            if (cuttingModel != null)
            {
                FacadeContext.getFurnitureFacade().putRestToStore(cuttingModel);
                FacadeContext.getFurnitureFacade().attachLinearTo(order.getOrderGroup());
                FacadeContext.getLinearStripsFacade().deleteAllBy(order.getOrderGroup());
            }
        }
    }

    private void processOrderStatusProduction(Order order)
    {
        FacadeContext.getBoardFacade().attachTo(order);
        FacadeContext.getBorderFacade().attachTo(order);
        FacadeContext.getFurnitureFacade().attachTo(order);
        order.setWorkedDailySheet(FacadeContext.getDailysheetFacade().loadCurrentDailysheet());
    }

    public BindingListener getBindingListener()
    {
        if (bindingListener == null)
        {
            bindingListener = new BindingAdapter()
            {
                @Override
                public void synced(Binding binding)
                {
                    if (!binding.getTargetValueForSource().failed())
                    {
                        Order order = (Order) binding.getSourceObject();
                        OrderStatus orderStatus = (OrderStatus) binding.getTargetValueForSource().getValue();
                        setOrderStatus(order, orderStatus);
                        FacadeContext.getOrderFacade().save(order);
                    }
                }

            };
        }
        return bindingListener;
    }

    public boolean canProductionOrder(Order order)
    {
        SearchFilter filter = new SearchFilter();
        filter.eq(StripsEntity.PROPERTY_order, order);

        List<OrderFurniture> list = FacadeContext.getOrderFurnitureFacade().loadOrderedByNumber(order);
        if (list.size() > 0 &&
                FacadeContext.getStripsFacade().getCount(filter) < 1)
        {
            String message = Application.getInstance().getContext().getResourceMap(OrderStatusManager.class).getString("message.warn.order.not.cutted");
            JOptionPane.showMessageDialog(relatedComponent, message, message, JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else if (FacadeContext.getReportJCRFacade().loadReport(order, ReportType.production_common) == null)
        {
            String message = Application.getInstance().getContext().getResourceMap(OrderStatusManager.class).getString("message.warn.order.not.reports");
            JOptionPane.showMessageDialog(relatedComponent, message, message, JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isLinearCuttingDone(Order order)
    {
        OrderGroup orderGroup = order.getOrderGroup();
        //у этой группы раскроена фурнитура
        SearchFilter searchFilterCutted = SearchFilter.instanceSingle();
        searchFilterCutted.eq(LinearStripsEntity.PROPERTY_ORDER_GROUP, orderGroup);

        //в заказе есть фурнитура
        SearchFilter searchFilterFurniture = SearchFilter.instanceSingle();
        searchFilterFurniture.eq(FurnitureLink.PROPERTY_orderItem + "." + OrderItem.PROPERTY_order, order);
        searchFilterFurniture.eq(FurnitureLink.PROPERTY_priceAware + "." + FurnitureType.PROPERTY_unit, Unit.linearMetre);

        if (FacadeContext.getLinearStripsFacade().getCount(searchFilterCutted) < 1 &&
                FacadeContext.getFurnitureLinkFacade().getCount(searchFilterFurniture) > 0)
        {
            String message = Application.getInstance().getContext().getResourceMap(OrderStatusManager.class).getString("message.warn.order.group.not.linear.cutted");
            JOptionPane.showMessageDialog(relatedComponent, message, message, JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean canMadeOrder(Order order)
    {
        if (!isLinearCuttingDone(order))
        {
            return false;
        }
        SearchFilter filter = new SearchFilter();
        filter.setResultSize(1);
        filter.addCriterion(new SearchFilter.DCriterion<Criterion>("order", Restrictions.eq("order", order)));
        filter.addCriterion(new SearchFilter.DCriterion<Criterion>("status", Restrictions.eq("status", StoreElementStatus.order)));
        if (FacadeContext.getBoardFacade().getCount(filter) > 0 ||
                FacadeContext.getBorderFacade().getCount(filter) > 0 ||
                FacadeContext.getFurnitureFacade().getCount(filter) > 0
                )
        {
            String message = Application.getInstance().getContext().getResourceMap(OrderStatusManager.class).getString("message.warn.order.has.order.board");
            JOptionPane.showMessageDialog(relatedComponent, message, message, JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public JComponent getRelatedComponent()
    {
        return relatedComponent;
    }

}
