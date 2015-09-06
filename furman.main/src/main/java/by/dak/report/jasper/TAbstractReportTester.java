package by.dak.report.jasper;

import by.dak.cutting.swing.order.data.A45;
import by.dak.cutting.swing.order.data.Drilling;
import by.dak.cutting.swing.order.data.Glueing;
import by.dak.cutting.swing.order.data.Groove;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.FinderException;
import by.dak.persistence.entities.*;
import by.dak.report.ReportType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Denis Koyro
 * @version 0.1 18.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class TAbstractReportTester
{
    protected static ReportType REPORT_TYPE;
    protected static Object REPORT_OBJECT;

    protected static void process(JReportData reportData) throws JRException, FinderException
    {
        System.out.println("Report generating...");
        JasperPrint jasperPrint = ReportGeneratorImpl.getInstance().generate(reportData);

        OrderItem orderItem = new OrderItem()
        {
            private Long identity = (long) (Math.random() * 1000);

            @Override
            public Long getId()
            {
                return identity;
            }
        };
        orderItem.setName("Test item");
        FacadeContext.<OrderItem, JasperPrint>getReportJCRFacade().saveReport(orderItem, REPORT_TYPE, jasperPrint);
        FacadeContext.<OrderItem, JasperPrint>getReportJCRFacade().saveReport(orderItem, REPORT_TYPE, jasperPrint);
        jasperPrint = FacadeContext.<OrderItem, JasperPrint>getReportJCRFacade().loadReport(orderItem, REPORT_TYPE);

        System.out.println("Report is generated.");
        new ReportFrame("Report viewer", new JRViewer(jasperPrint, new Locale("ru")));

        FacadeContext.<OrderItem, JasperPrint>getReportJCRFacade().removeReport(orderItem, REPORT_TYPE);
        try
        {
            FacadeContext.<OrderItem, JasperPrint>getReportJCRFacade().loadReport(orderItem, REPORT_TYPE);
            assert false;
        }
        catch (Throwable th)
        {
            assert true;
        }
    }

    protected static void compile(String jrxmlReportPath, String jasperPath) throws JRException
    {
        System.out.println("Report compilation...");
        ReportGeneratorImpl.getInstance().compile(jrxmlReportPath, jasperPath);
    }

    protected static List<AOrderDetail> createFurnitures() throws FinderException
    {
        List<AOrderDetail> furnitures = new ArrayList<AOrderDetail>();
        List<TextureEntity> textures = FacadeContext.getTextureFacade().loadAll();
        assert !textures.isEmpty() && textures.size() > 1;
        TextureEntity texture1 = textures.get(0);
        TextureEntity texture2 = textures.get(1);

        List<BoardDef> boards = FacadeContext.getBoardDefFacade().loadAll();
        assert !boards.isEmpty() && boards.size() > 1;
        BoardDef board1 = boards.get(0);
        BoardDef board2 = boards.get(1);
        BoardDef board3 = boards.get(2);

        List<BorderDefEntity> borders = FacadeContext.getBorderDefFacade().loadAll();
        assert !borders.isEmpty() && borders.size() > 1;
        BorderDefEntity border1 = borders.get(0);
        BorderDefEntity border2 = borders.get(1);
        BorderDefEntity border3 = borders.get(2);
        BorderDefEntity border4 = borders.get(3);

        Glueing glueing = new Glueing(false, false, true, true, texture1, texture1, texture1, texture1);
        glueing.setUpBorderDef(border1);
        glueing.setDownBorderDef(border1);
        glueing.setLeftBorderDef(border1);
        glueing.setRightBorderDef(border1);
        furnitures.add(new TestOrderFurnitureEntity(1L, "1", board1, texture1, 600L, 2370L, 1, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(false, false, true, true)),
                XstreamHelper.getInstance().toXML(new A45(false, false, true, true, "", "", "45", "45")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D1", "Комментарий Комментарий Комментарий Комментарий Комментарий"))));
        furnitures.add(new TestOrderFurnitureEntity(2L, "1", board1, texture1, 600L, 2370L, 1, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(false, false, true, true)),
                XstreamHelper.getInstance().toXML(new A45(false, false, true, true, "", "", "45", "45")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D1", "Комментарий Комментарий Комментарий Комментарий Комментарий"))));

        glueing = new Glueing(true, false, true, false, texture2, texture2, texture2, texture2);
        glueing.setUpBorderDef(border2);
        glueing.setDownBorderDef(border2);
        glueing.setLeftBorderDef(border2);
        glueing.setRightBorderDef(border2);
        furnitures.add(new TestOrderFurnitureEntity(3L, "2", board2, texture2, 600L, 1100L, 1, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(true, false, true, false)),
                XstreamHelper.getInstance().toXML(new A45(true, false, true, false, "50", "", "50", "")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D2", "Комментарий Комментарий Комментарий Комментарий Комментарий"))));

        glueing = new Glueing(true, true, true, true, texture2, texture2, texture2, texture2);
        glueing.setUpBorderDef(border3);
        glueing.setDownBorderDef(border3);
        glueing.setLeftBorderDef(border3);
        glueing.setRightBorderDef(border3);
        furnitures.add(new TestOrderFurnitureEntity(4L, "3", board3, texture2, 1200L, 1200L, 1, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(true, true, true, true)),
                XstreamHelper.getInstance().toXML(new A45(true, true, true, true, "35", "35", "35", "35")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D3", "Комментарий Комментарий Комментарий Комментарий Комментарий"))));

        glueing = new Glueing(true, false, true, true, texture2, texture2, texture2, texture2);
        glueing.setUpBorderDef(border4);
        glueing.setDownBorderDef(border4);
        glueing.setLeftBorderDef(border4);
        glueing.setRightBorderDef(border4);
        furnitures.add(new TestOrderFurnitureEntity(5L, "4", board1, texture2, 300L, 400L, 2, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(true, false, true, true)),
                XstreamHelper.getInstance().toXML(new A45(true, false, true, true, "60", "", "45", "25")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D4", "Комментарий Комментарий Комментарий Комментарий Комментарий"))));

        glueing = new Glueing(true, false, true, true, texture1, texture1, texture1, texture1);
        glueing.setUpBorderDef(border1);
        glueing.setDownBorderDef(border1);
        glueing.setLeftBorderDef(border1);
        glueing.setRightBorderDef(border1);
        TestOrderFurnitureEntity first = new TestOrderFurnitureEntity(6L, "5", board3.getSimpleType1(), texture1, 600L, 2370L, 1, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(true, false, true, true)),
                XstreamHelper.getInstance().toXML(new A45(true, false, true, true, "60", "", "45", "25")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D5", "Комментарий Комментарий Комментарий Комментарий Комментарий")));
        TestOrderFurnitureEntity second = new TestOrderFurnitureEntity(7L, "6", board3.getSimpleType2(), texture1, 600L, 2370L, 1, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(true, false, true, true)),
                XstreamHelper.getInstance().toXML(new A45(true, false, true, true, "60", "", "45", "25")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D5", "Комментарий Комментарий Комментарий Комментарий Комментарий")));
        first.setSecond(second);
        second.setSecond(first);
        first.setPrimary(true);
        first.setComlexBoardDef(board3);
        second.setComlexBoardDef(board3);
        second.setPrimary(false);
        furnitures.add(first);
        furnitures.add(second);

        glueing = new Glueing(true, false, true, false, texture1, texture1, texture1, texture1);
        glueing.setUpBorderDef(border2);
        glueing.setDownBorderDef(border2);
        glueing.setLeftBorderDef(border2);
        glueing.setRightBorderDef(border2);
        furnitures.add(new TestOrderFurnitureEntity(8L, "7", board3, texture1, 600L, 1100L, 1, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(true, false, true, false)),
                XstreamHelper.getInstance().toXML(new A45(true, false, true, false, "50", "", "50", "")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D7", "Комментарий Комментарий Комментарий Комментарий Комментарий"))));

        glueing = new Glueing(true, true, true, true, texture2, texture2, texture2, texture2);
        glueing.setUpBorderDef(border3);
        glueing.setDownBorderDef(border3);
        glueing.setLeftBorderDef(border3);
        glueing.setRightBorderDef(border3);

        furnitures.add(new TestOrderFurnitureEntity(9L, "8", board1, texture2, 1200L, 1200L, 1, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(true, true, true, true)),
                XstreamHelper.getInstance().toXML(new A45(true, true, true, true, "35", "35", "35", "35")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D8", "Комментарий Комментарий Комментарий Комментарий Комментарий"))));

        glueing = new Glueing(true, false, true, true, texture2, texture2, texture2, texture2);
        glueing.setUpBorderDef(border4);
        glueing.setDownBorderDef(border4);
        glueing.setLeftBorderDef(border4);
        glueing.setRightBorderDef(border4);
        furnitures.add(new TestOrderFurnitureEntity(10L, "9", board2, texture2, 300L, 400L, 2, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(true, false, true, true)),
                XstreamHelper.getInstance().toXML(new A45(true, false, true, true, "60", "", "45", "25")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D9", "Комментарий Комментарий Комментарий Комментарий Комментарий"))));

        furnitures.add(new TestOrderFurnitureEntity(11L, "10", board2, texture2, 700L, 350L, 2, false,
                XstreamHelper.getInstance().toXML(glueing),
                XstreamHelper.getInstance().toXML(new Groove(true, false, true, true)),
                XstreamHelper.getInstance().toXML(new A45(true, false, true, true, "60", "", "45", "25")),
                XstreamHelper.getInstance().toXML(new Drilling("Рисунок D9", "Комментарий Комментарий Комментарий Комментарий Комментарий"))));

        return furnitures;
    }

    private static class ReportFrame extends JFrame
    {
        public ReportFrame(String title, JRViewer viewer) throws HeadlessException
        {
            super(title);
            setLocation(0, 0);

            Dimension screenSize = getToolkit().getScreenSize();
            Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration());
            screenSize.width -= screenInsets.left + screenInsets.right;
            screenSize.height -= screenInsets.top + screenInsets.bottom;

            Dimension size = viewer.getPreferredSize();
            setSize(Math.min(screenSize.width, size.width), Math.min(screenSize.height, size.height));

            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(BorderLayout.CENTER, viewer);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setVisible(true);
        }
    }

    private static class TestOrderFurnitureEntity extends OrderFurniture
    {
        Long id = 0L;

        public TestOrderFurnitureEntity(Long number, String name, BoardDef boardDef, TextureEntity texture, Long length, Long width, Integer amount, boolean rotatable, String glueing, String groove, String angle45, String drilling)
        {
            this.setNumber(number);
            this.setName(name);
            this.setBoardDef(boardDef);
            this.setTexture(texture);
            this.setLength(length);
            this.setWidth(width);
            this.setAmount(amount);
            this.setRotatable(rotatable);
            this.setGlueing(glueing);
            this.setGroove(groove);
            this.setAngle45(angle45);
            this.setDrilling(drilling);
            this.id = number;
        }

        @Override
        public Long getId()
        {
            return id;
        }
    }
}