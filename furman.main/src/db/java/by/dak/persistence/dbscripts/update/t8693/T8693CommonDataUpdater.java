package by.dak.persistence.dbscripts.update.t8693;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.report.jasper.common.data.CommonDataCreator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: akoyro
 * Date: 19.11.2010
 * Time: 18:48:35
 */
public class T8693CommonDataUpdater
{
    private static final int SIZE = 10;
    private static ExecutorService service = Executors.newFixedThreadPool(SIZE);

    public static void main(String[] args) throws InterruptedException
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.addDescOrder("id");
        searchFilter.setPageSize(SIZE);
        int count = FacadeContext.getOrderFacade().getCount(searchFilter);
        int current = 0;
        while (current + SIZE < count)
        {
            final SearchFilter sf = new SearchFilter();
            sf.setPageSize(SIZE);
            sf.setStartIndex(current);
            sf.addDescOrder("id");
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    try
                    {
                        List<Order> list = FacadeContext.getOrderFacade().loadAll(sf);
                        System.out.println("Start = " + sf.getStartIndex());
                        for (Order order : list)
                        {
                            try
                            {
                                CuttingModel cuttingModel = FacadeContext.getStripsFacade().loadCuttingModel(order).load();
                                CommonDataCreator commonDataCreator = new CommonDataCreator(cuttingModel, springConfiguration.getMainFacade());
                                commonDataCreator.create();
                            }
                            catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            service.submit(runnable);
            current += SIZE;
        }

        while (FacadeContext.getOrderFacade().getCount(searchFilter) > 0)
        {
            Thread.sleep(1000);
        }
    }
}
