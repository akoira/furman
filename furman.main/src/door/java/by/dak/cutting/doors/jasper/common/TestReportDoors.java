package by.dak.cutting.doors.jasper.common;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.doors.jasper.common.reportData.DoorsDataReport;
import by.dak.persistence.FinderException;
import by.dak.persistence.entities.*;
import by.dak.report.ReportType;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.TAbstractReportTester;

import java.awt.image.BufferedImage;
import java.sql.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 28.09.2009
 * Time: 16:26:29
 * To change this template use File | Settings | File Templates.
 */
public class TestReportDoors extends TAbstractReportTester
{

    public static void main(String[] args)
    {
        new SpringConfiguration();
        REPORT_TYPE = ReportType.doorscommon;
        try
        {
            process(createReportData());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static JReportData createReportData() throws FinderException
    {
        DoorsDataReport doorsDataReport = createDoorDataReport();
        return new CommonReportDoorsDataCreator(doorsDataReport).create();
    }

    private static DoorsDataReport createDoorDataReport() throws FinderException
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
        OrderItem orderItem = new OrderItem();
        orderItem.setName("Test item");
        List<AOrderDetail> furnitureItems = createFurnitures();
        orderItem.setDetails(furnitureItems);
        order.addOrderItem(orderItem);

        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);

        return new DoorsDataReport(order, "Сенатор", "Открытый", "Золото", new Long(2500), new Long(3), bufferedImage);
    }
}
