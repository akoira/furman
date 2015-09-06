package by.dak.buffer.statistic.swing;

import by.dak.buffer.entity.DilerOrder;
import by.dak.buffer.importer.production.DilerToProductionManager;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.MessageDialog;
import by.dak.cutting.swing.order.wizard.OrderWizard;
import by.dak.order.swing.IOrderWizardDelegator;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.swing.table.ListNaviTable;
import by.dak.swing.table.PopupMenuHelper;
import org.jdesktop.application.Application;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 07.12.2010
 * Time: 13:20:26
 * To change this template use File | Settings | File Templates.
 */
public class SetForProductionHelper extends PopupMenuHelper
{
    private ListNaviTable listNaviTable;
    private Customer customer;
    private IOrderWizardDelegator orderWizardDelegator;

    public SetForProductionHelper(ListNaviTable listNaviTable)
    {
        super(listNaviTable.getTable());
        this.listNaviTable = listNaviTable;
    }

    public IOrderWizardDelegator getOrderWizardDelegator()
    {
        return orderWizardDelegator;
    }

    public void setOrderWizardDelegator(IOrderWizardDelegator orderWizardDelegator)
    {
        this.orderWizardDelegator = orderWizardDelegator;
    }

    @Override
    public void init()
    {
        setActionMap(Application.getInstance().getContext().getActionMap(SetForProductionHelper.class, this));
        setActions("toProduction");
        setPopupMenuTrigger(new PopupMenuTrigger()
        {
            @Override
            public boolean showPopupMenu(MouseEvent e)
            {
                return true;
            }
        });

        super.init();
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    @org.jdesktop.application.Action
    public void toProduction()
    {
        if (listNaviTable.getSelectedId() == -1)
        {
            MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
        }
        else
        {
            SwingWorker<Order, Void> swingWorker = new SwingWorker<Order, Void>()
            {
                @Override
                protected Order doInBackground() throws Exception
                {
                    DilerOrder dilerOrder = FacadeContext.getDilerOrderFacade().findById(listNaviTable.getSelectedId(), true);
                    DilerToProductionManager manager = new DilerToProductionManager();
                    manager.setCustomer(customer);
                    return manager.manage(dilerOrder);
                }
            };

            DialogShowers.showWaitDialog(swingWorker, listNaviTable);
            try
            {
                Order order = swingWorker.get();
                final OrderWizard orderWizard = new OrderWizard(order.getNumber().
                        getStringValue());
                orderWizard.setOrder(order);
                orderWizard.setiOrderWizardDelegator(getOrderWizardDelegator());

                DialogShowers.showWizard(orderWizard, orderWizard.getWizardObserver());
            }
            catch (Exception e)
            {
                FacadeContext.getExceptionHandler().handle(e);
            }
        }
    }
}
