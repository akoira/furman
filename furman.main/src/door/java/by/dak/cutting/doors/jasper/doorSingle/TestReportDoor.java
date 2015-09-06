package by.dak.cutting.doors.jasper.doorSingle;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.doors.jasper.doorSingle.reportData.DoorDataReport;
import by.dak.cutting.doors.jasper.doorSingle.reportData.DoorSingleDataReport;
import by.dak.cutting.doors.jasper.doorSingle.reportData.FillingDataReport;
import by.dak.cutting.doors.jasper.doorSingle.reportData.ProfileDataReport;
import by.dak.persistence.FinderException;
import by.dak.persistence.entities.*;
import by.dak.report.ReportType;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.TAbstractReportTester;

import java.awt.image.BufferedImage;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 05.10.2009
 * Time: 13:37:42
 * To change this template use File | Settings | File Templates.
 */
public class TestReportDoor extends TAbstractReportTester
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        REPORT_TYPE = ReportType.doorsingle;
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
        DoorSingleDataReport doorDataReport = createDoorSingleDataReport();
        return new ReportDoorDataCreator(doorDataReport).create();
    }

    private static DoorSingleDataReport createDoorSingleDataReport() throws FinderException
    {
        Order order = new Order();
        order.setOrderNumber(((long) (Math.random() * 1000)));
        order.setName("Test order");
        order.setDescription("description");
        order.setCost(Math.random() * 1000);
        order.setReadyDate(new Date(System.currentTimeMillis()));
        order.setCreatedDailySheet(new Dailysheet(null));
        order.setWorkedDailySheet(new Dailysheet(null));
        order.setCustomer(new Customer("Койро Андрей", null, null, null, null, null));
        order.setDesigner(new DesignerEntity("Койро Денис"));
        OrderItem orderItem = new OrderItem();
        orderItem.setName("Test item");

        List<AOrderDetail> furnitureItems = createFurnitures();
        orderItem.setDetails(furnitureItems);
        order.addOrderItem(orderItem);

        DoorSingleDataReport doorSingleDataReport = new DoorSingleDataReport(order);
        doorSingleDataReport.setDoorDataReports(createTestDoorData());

        return doorSingleDataReport;
    }

    private static List<DoorDataReport> createTestDoorData()
    {
        ArrayList<DoorDataReport> datas = new ArrayList<DoorDataReport>();
        BufferedImage bufferedImage = null;
//                new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);

        DoorDataReport doorDataReport;

        for (int i = 1; i <= 4; i++)
        {
            doorDataReport = new DoorDataReport(new Long(1) + i - 1, new Long(300), new Long(450), "Сенатор" + i, "Золото" + i, bufferedImage);
            doorDataReport.setProfileDataReports(createTestProfile());
            doorDataReport.setFillingDataReports(createTestFilling());
            datas.add(doorDataReport);
        }

        return datas;
    }

    private static List<ProfileDataReport> createTestProfile()
    {
        ArrayList<ProfileDataReport> datas = new ArrayList<ProfileDataReport>();

        ProfileDataReport profileDataReport;

        for (int i = 1; i <= 9; i++)
        {
            profileDataReport = new ProfileDataReport("Тип" + i, new Long(5) + i);
            datas.add(profileDataReport);
        }

        return datas;
    }

    private static List<FillingDataReport> createTestFilling()
    {
        ArrayList<FillingDataReport> datas = new ArrayList<FillingDataReport>();

        FillingDataReport fillingDataReport;

        for (int i = 1; i <= 15; i++)
        {
            fillingDataReport = new FillingDataReport("Имя" + i, "Материл" + i, "ЦветТекстуры" + i, new Long(3) + i);
            datas.add(fillingDataReport);
        }

        return datas;
    }
}
