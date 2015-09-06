package by.dak.report.jasper.common;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FinderException;
import by.dak.persistence.entities.*;
import by.dak.report.ReportType;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.TAbstractReportTester;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.report.jasper.common.data.CommonReportDataImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * @author Denis Koyro
 * @version 0.1 13.01.2009
 * @since 1.0.0
 */
public class TCommonReportTester extends TAbstractReportTester
{
    private static BufferedImage bufferedImage;

    public TCommonReportTester()
    {
        REPORT_TYPE = ReportType.common;
    }

    public static void main(String[] args)
    {
        new SpringConfiguration();
        new TCommonReportTester();
        try
        {
//            compile(TCommonReportTester.class.getResource("CommonReport.jrxml").getFile(), null);
//            compile(TCommonReportTester.class.getResource("CommonReportMaterialSubreport.jrxml").getFile(), null);
//            compile(TCommonReportTester.class.getResource("CommonSubreportRailing.jrxml").getFile(), null);
//            compile(TCommonReportTester.class.getResource("CommonSubreportSellotape.jrxml").getFile(), null);
//            compile(TCommonReportTester.class.getResource("CommonSubreportSheet.jrxml").getFile(), null);

            process(createReportData());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static JReportData createReportData() throws FinderException
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
        CommonReportDataImpl commonReportData = new CommonReportDataImpl(order);
        commonReportData.setCommonDatas(new CommonDatas(CommonDataType.cutting, order));
        fillMaterials(furnitureItems, commonReportData);
        return new CommonReportDataCreator(commonReportData).create();
    }

    private static void fillMaterials(List<AOrderDetail> furnitureItems, CommonReportDataImpl commonReportData)
    {
        CommonData commonData = new CommonData();
        commonData.setName("ПВХ 10мм");
        commonData.setService("Service1");
        commonData.setPrice(100d);
        commonData.increase(10d);
        commonReportData.getCommonDatas(CommonDataType.cutting).add(commonData);

        commonData = new CommonData();
        commonData.setService("Service1");
        commonData.setName("ПВХ 18мм");
        commonData.setPrice(10d);
        commonData.increase(18d);
        commonReportData.getCommonDatas(CommonDataType.cutting).add(commonData);

        commonData = new CommonData();
        commonData.setName("ПВХ 18мм");
        commonData.setService("Service2");
        commonData.setPrice(100d);
        commonData.increase(10d);
        commonReportData.getCommonDatas(CommonDataType.cutting).add(commonData);

        commonData = new CommonData();
        commonData.setName("ПВХ 10мм");
        commonData.setService("Service2");
        commonData.setPrice(70d);
        commonData.increase(2d);
        commonData.markAsLast();
        commonReportData.getCommonDatas(CommonDataType.cutting).add(commonData);

        commonData = new CommonData();
        commonData.setName("ПВХ 10x20");
        commonData.setService("Texture100D");
        commonData.setPrice(70d);
        commonData.increase(2d);
        commonReportData.getCommonDatas(CommonDataType.cutting).add(commonData);
        commonReportData.getCommonDatas(CommonDataType.cutting).add(commonData);

        commonData = new CommonData();
        commonData.setName("ПВХ 22x22");
        commonData.setService("Texture100D");
        commonData.setPrice(100d);
        commonData.increase(4d);
        commonData.markAsLast();
        commonReportData.getCommonDatas(CommonDataType.cutting).add(commonData);
        commonReportData.getCommonDatas(CommonDataType.cutting).add(commonData);

        commonData = new CommonData();
        commonData.setName("Скотч");
        commonData.setService("Скотч");
        commonData.setPrice(100d);
        commonData.increase(4d);
        commonData.markAsLast();
        commonReportData.getCommonDatas(CommonDataType.cutting).add(commonData);
    }

    private static BufferedImage getImage()
    {
        if (bufferedImage == null)
        {
            try
            {
                bufferedImage = ImageIO.read(new File(TCommonReportTester.class.getResource("cutting.sample.jpg").getFile()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return bufferedImage;
    }
}