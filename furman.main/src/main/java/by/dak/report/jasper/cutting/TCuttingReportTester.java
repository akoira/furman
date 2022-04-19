package by.dak.report.jasper.cutting;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.persistence.FinderException;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.*;
import by.dak.report.ReportType;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.TAbstractReportTester;
import by.dak.report.jasper.cutting.data.CuttedReportDataImpl;
import by.dak.report.jasper.cutting.data.CuttedSheetDataImpl;
import by.dak.report.jasper.cutting.data.MaterialCuttedDataImpl;

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
public class TCuttingReportTester extends TAbstractReportTester
{
    private static BufferedImage bufferedImage;

    public TCuttingReportTester()
    {
        REPORT_TYPE = ReportType.cutting;
    }

    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration(false);
        new TCuttingReportTester();
        try
        {
            if (args.length > 0 && args[0].equals("compile"))
            {
                String jasperPath = args.length > 1 ? args[1] : null;
                compile(TCuttingReportTester.class.getResource("CuttingReport.jrxml").getFile(), jasperPath);
                String subreportJasperPath = args.length > 2 ? args[2] : null;
                compile(TCuttingReportTester.class.getResource("CuttingReportDetailsSubreport.jrxml").getFile(), subreportJasperPath);
            }
            else
            {
                process(createReportData(springConfiguration.getMainFacade()));
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
        OrderItem orderItem = new OrderItem();
        orderItem.setName("Test item");

        List<AOrderDetail> furnitureItems = createFurnitures();
        orderItem.setDetails(furnitureItems);
        order.addOrderItem(orderItem);
        CuttedReportDataImpl cuttedReportData = new CuttedReportDataImpl();
        cuttedReportData.setOrder(order);
        fillMaterials(furnitureItems, cuttedReportData);
        return mainFacade.reportCreatorFactory.createReportDataCreator(cuttedReportData, REPORT_TYPE).create();
    }

    private static void fillMaterials(List<AOrderDetail> furnitureItems, CuttedReportDataImpl cuttedReportData)
    {
        for (AOrderDetail furniture : furnitureItems)
        {
            MaterialCuttedDataImpl materialCuttedData = new MaterialCuttedDataImpl();
            materialCuttedData.setTextureFurniturePair(((OrderFurniture) furniture).getPair());
            CuttedSheetDataImpl cuttedSheetData = new CuttedSheetDataImpl();
            cuttedSheetData.setBufferedImage(getImage());
            cuttedSheetData.setSheetSegment(new Segment(100, 350));
            Board board = new Board();
            board.setPair(((OrderFurniture) furniture).getPair());
            cuttedSheetData.setBoard(board);
            cuttedSheetData.addFurniture(((OrderFurniture) furniture));
            OrderFurniture entity = (OrderFurniture) ((OrderFurniture) furniture).clone();
            entity.setNumber(2L);
            cuttedSheetData.addFurniture(entity);
            materialCuttedData.addCuttedSheetData(cuttedSheetData);
            cuttedReportData.add(materialCuttedData);
        }
    }

    private static BufferedImage getImage()
    {
        if (bufferedImage == null)
        {
            try
            {
                bufferedImage = ImageIO.read(new File(TCuttingReportTester.class.getResource("cutting.sample.jpg").getFile()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return bufferedImage;
    }
}