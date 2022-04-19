package by.dak.spec.gui;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.order.data.Glueing;
import by.dak.cutting.swing.report.ReportsPanel;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.*;
import by.dak.report.ReportType;
import by.dak.report.jasper.DefaultReportCreatorFactory;
import by.dak.report.jasper.ReportGeneratorImpl;
import by.dak.report.model.ReportsModel;
import by.dak.report.model.impl.ReportsModelImpl;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.jdesktop.application.FrameView;

import javax.swing.*;
import java.sql.Date;

/**
 * User: akoyro
 * Date: 16.03.2009
 * Time: 16:28:28
 */
public class TestReportsPanel
{
    private SpringConfiguration springConfiguration = new SpringConfiguration();
    private  MainFacade mainFacade = springConfiguration.getMainFacade();
    private ReportsPanel reportTab = new ReportsPanel();

    public static void main(String[] args) throws JRException
    {

        final TestReportsPanel testReportsPanel = new TestReportsPanel();

        Dailysheet dailysheet = new Dailysheet();
        dailysheet.setDate(new Date(System.currentTimeMillis()));

        TextureEntity texture = FacadeContext.getTextureFacade().loadAll().get(0);
//                new TextureEntity();
//        texture.setCode(100L);
//        texture.setGroupIdentifier("test");
//        texture.setNameEnglish("test");
//        texture.setNameRussian("test");
//        texture.setSurface("test");

        BoardDef boardDef = FacadeContext.getBoardDefFacade().loadAll().get(0);
//                new BoardDef();
//        boardDef.setDefaultLength(Constants.DEFAULT_SHEET_LENGTH);
//        boardDef.setDefaultWidth(Constants.DEFAULT_SHEET_WIDTH);
//        boardDef.setThickness(18L);

        BorderDefEntity borderDef = FacadeContext.getBorderDefFacade().loadAll().get(0);
//                new BorderDefEntity();
//        borderDef.setName("test");
//        borderDef.setHeight(22L);
//        borderDef.setThickness(2L);
        Glueing glueing = new Glueing(true, true, true, true, texture, texture, texture, texture);
        glueing.setDownBorderDef(borderDef);
        glueing.setUpBorderDef(borderDef);
        glueing.setLeftBorderDef(borderDef);
        glueing.setRightBorderDef(borderDef);


        Order order = new Order();
        order.setOrderNumber(1000l);
        Customer customer = new Customer();
        customer.setName("Test");
        order.setCustomer(customer);
        order.setCreatedDailySheet(dailysheet);
        order.setReadyDate(new Date(System.currentTimeMillis()));

        OrderItem orderItem = new OrderItem();
        orderItem.setName("Test item");
        order.addOrderItem(orderItem);


        OrderFurniture orderFurniture = new OrderFurniture();
        orderFurniture.setGlueing(XstreamHelper.getInstance().toXML(glueing));
        orderFurniture.setAmount(1);
        orderFurniture.setPrimary(Boolean.TRUE);
        orderFurniture.setOrderItem(orderItem);
        orderFurniture.setWidth(500l);
        orderFurniture.setLength(400l);
        orderFurniture.setBoardDef(boardDef);
        orderFurniture.setNumber(1l);
        orderFurniture.setTexture(texture);
        orderItem.addDetail(orderFurniture);

        orderFurniture = new OrderFurniture();
        orderFurniture.setGlueing(XstreamHelper.getInstance().toXML(glueing));
        orderFurniture.setAmount(2);
        orderFurniture.setPrimary(Boolean.TRUE);
        orderFurniture.setOrderItem(orderItem);
        orderFurniture.setWidth(300l);
        orderFurniture.setLength(600l);
        orderFurniture.setBoardDef(boardDef);
        orderFurniture.setNumber(2l);
        orderFurniture.setTexture(texture);
        orderItem.addDetail(orderFurniture);

        orderFurniture = new OrderFurniture();
        orderFurniture.setGlueing(XstreamHelper.getInstance().toXML(glueing));
        orderFurniture.setAmount(2);
        orderFurniture.setPrimary(Boolean.TRUE);
        orderFurniture.setOrderItem(orderItem);
        orderFurniture.setWidth(100l);
        orderFurniture.setLength(2500l);
        orderFurniture.setBoardDef(boardDef);
        orderFurniture.setNumber(2l);
        orderFurniture.setTexture(texture);
        orderItem.addDetail(orderFurniture);

        orderFurniture = new OrderFurniture();
        orderFurniture.setGlueing(XstreamHelper.getInstance().toXML(glueing));
        orderFurniture.setAmount(2);
        orderFurniture.setPrimary(Boolean.TRUE);
        orderFurniture.setOrderItem(orderItem);
        orderFurniture.setWidth(500l);
        orderFurniture.setLength(500l);
        orderFurniture.setBoardDef(boardDef);
        orderFurniture.setNumber(2l);
        orderFurniture.setTexture(texture);
        orderItem.addDetail(orderFurniture);


        final JasperPrint print = ReportGeneratorImpl.getInstance().generate(testReportsPanel.mainFacade.reportCreatorFactory.createReportDataCreator(order, ReportType.glueing).create());
        FrameView frameView = new FrameView(CuttingApp.getApplication());
        frameView.setComponent(testReportsPanel.reportTab);
        CuttingApp.getApplication().show(frameView);
        final ReportsModel model = new ReportsModelImpl();
        model.setJasperPrint(ReportType.common, print);
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                testReportsPanel.reportTab.setReportsModel(model);

            }
        };
        SwingUtilities.invokeLater(runnable);

    }
}
