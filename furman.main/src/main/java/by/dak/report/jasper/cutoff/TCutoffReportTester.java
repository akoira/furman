package by.dak.report.jasper.cutoff;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FinderException;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.Customer;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.DesignerEntity;
import by.dak.persistence.entities.Order;
import by.dak.report.ReportType;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.TAbstractReportTester;

import java.sql.Date;

/**
 * @author Denis Koyro
 * @version 0.1 13.01.2009
 * @since 1.0.0
 */
public final class TCutoffReportTester extends TAbstractReportTester
{
    private final MainFacade mainFacade;

    public TCutoffReportTester(MainFacade mainFacade)
    {
        this.mainFacade = mainFacade;
        REPORT_TYPE = ReportType.cutoff;
    }

    public static void main(String[] args)
    {
        SpringConfiguration configuration = new SpringConfiguration(false);
        new TCutoffReportTester(configuration.getMainFacade());
        try
        {
            if (args.length > 0 && args[0].equals("compile"))
            {
                String jasperPath = args.length == 2 ? args[1] : null;
                compile(TCutoffReportTester.class.getResource("CutoffReport.jrxml").getFile(), jasperPath);
            }
            else
            {
                compile(TCutoffReportTester.class.getResource("CutoffReport.jrxml").getFile(), null);
                process(createReportData(configuration.getMainFacade()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static JReportData createReportData(MainFacade mainFacade) throws FinderException
    {
        Order order = new Order();
        order.setOrderNumber(((long) (Math.random() * 1000)));
        order.setName("Test order");
        order.setDescription("description");
        order.setCost(Math.random() * 1000);
        order.setReadyDate(new Date(System.currentTimeMillis()));
        order.setCreatedDailySheet(new Dailysheet(null));
        order.setWorkedDailySheet(new Dailysheet(null));
        order.setCustomer(Customer.valueOf("Койро Андрей"));
        order.setDesigner(new DesignerEntity("Койро Денис"));
        //        OrderItem orderItem = new OrderItem("Test item");
//        orderItem.setFurnitureItems(createFurnitures());
//        order.addOrderItem(orderItem);
        return mainFacade.reportCreatorFactory.createReportDataCreator(order, REPORT_TYPE).create();
    }
}