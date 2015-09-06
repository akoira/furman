package by.dak.persistence.dbscripts.update.t8903;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.CuttersPanel;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderStatus;
import by.dak.utils.convert.StringValueAnnotationProcessor;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 15.02.11
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
public class T8903MiscalculationUpdater
{
    private static final String CUSTOMER_name = "Директор";

    public static void main(String[] args) throws InterruptedException, InvocationTargetException
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();

        final SearchFilter sf = new SearchFilter();
        sf.eq(Order.PROPERTY_orderStatus, OrderStatus.miscalculation);
        sf.eq(Order.PROPERTY_customer +
                "." + Customer.PROPERTY_name, CUSTOMER_name);
        //sf.eq(Order.PROPERTY_orderNumber, (long) 3113);
        int count = FacadeContext.getOrderFacade().getCount(sf);
        sf.setPageSize(count);

        List<Order> orders = FacadeContext.getOrderFacade().loadAll(sf);
        for (final Order order : orders)
        {
            System.out.println(StringValueAnnotationProcessor.getProcessor().convert(order));
            CuttingModel cuttingModel = FacadeContext.getStripsFacade().loadCuttingModel(order).load();
            final CuttersPanel cuttersPanel = new CuttersPanel();
            cuttersPanel.setCuttingModel(cuttingModel);
            Runnable runnable = new Runnable()
            {
                @Override
                public void run()
                {
                    cuttersPanel.generate();
                }
            };
            SwingUtilities.invokeAndWait(runnable);
            while (cuttersPanel.getState() != CuttersPanel.State.STOPED)
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    throw new IllegalArgumentException(e);
                }
            }
//            cuttersPanel.apply();
//
//            ReportsModelImpl reportsModel = new ReportsModelImpl();
//            reportsModel.setCuttingModel(cuttingModel);
//            reportsModel.setOrder(order);
//
//            FacadeContext.getReportJCRFacade().removeAll(reportsModel.getOrder());
//            final ReportsPanel reportsPanel = new ReportsPanel();
//            reportsPanel.setReportsModel(reportsModel);
//
//            runnable = new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    reportsPanel.create();
//                }
//            };
//            SwingUtilities.invokeLater(runnable);

        }
        System.exit(0);
    }
}


