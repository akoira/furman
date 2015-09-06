package by.dak.buffer.importer.production;

import by.dak.buffer.entity.DilerOrder;
import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.buffer.entity.DilerOrderItem;
import by.dak.buffer.entity.migrator.DetailMigrationFactory;
import by.dak.buffer.entity.migrator.ItemMigrationFactory;
import by.dak.cutting.CuttingApp;
import by.dak.cutting.CuttingView;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrderDetail;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.11.2010
 * Time: 17:43:12
 * To change this template use File | Settings | File Templates.
 */
public class DilerToProductionManager
{
    private Customer customer;

    private List<DilerOrderItem> getDilerOrderItems(DilerOrder dilerOrder)
    {
        return FacadeContext.getDilerOrderItemFacade().findBy(dilerOrder);
    }

    private List<DilerOrderDetail> getDilerOrderDetails(DilerOrderItem dilerOrderItem)
    {
        List<DilerOrderDetail> dilerOrderDetailsList = new ArrayList<DilerOrderDetail>();
        dilerOrderDetailsList.addAll(FacadeContext.getDilerOrderDetailFacade().findBy(dilerOrderItem));
        return dilerOrderDetailsList;
    }


    /**
     * процесс копирования данных из DilerOrder в
     *
     * @param dilerOrder
     * @return
     */
    public Order manage(DilerOrder dilerOrder)
    {
        String name = CuttingApp.getApplication().getContext().getResourceMap(CuttingView.class).getString(
                "default.order.name");
        Order order = FacadeContext.getOrderFacade().initNewOrderEntity(name);
        order.setCustomer(getCustomer());
        FacadeContext.getOrderFacade().save(order);

        DetailMigrationFactory detailMigrationFactory = (DetailMigrationFactory) FacadeContext.getApplicationContext().getBean("detailMigrationFactory");
        ItemMigrationFactory itemMigrationFactory = (ItemMigrationFactory) FacadeContext.getApplicationContext().getBean("itemMigrationFactory");

        for (DilerOrderItem dilerItem : getDilerOrderItems(dilerOrder))
        {
            OrderItem orderItem = itemMigrationFactory.migrate(dilerItem);
            orderItem.setOrder(order);
            FacadeContext.getOrderItemFacade().save(orderItem);
            for (DilerOrderDetail dilerOrderDetail : getDilerOrderDetails(dilerItem))
            {
                AOrderDetail orderDetail = detailMigrationFactory.migrate(dilerOrderDetail);
                orderDetail.setOrderItem(orderItem);
                FacadeContext.getOrderDetailFacade().save(orderDetail);
            }
        }

        return order;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public Customer getCustomer()
    {
        return customer;
    }
}
