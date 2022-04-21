/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.report;

import by.dak.draw.DimensionLine;
import by.dak.persistence.entities.OrderFurniture;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.*;
import net.sf.jasperreports.view.JasperDesignViewer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

/**
 * @author admin
 */
public class TestCreateReport
{

    private float scale = 1f;

    private int pageWidth = 595;

    private int pageHeight = 842;

    private int segmentRowCount = 3;

    private int segmentColumnCount = 2;

    private int segmentMargin = 20;

    private JasperDesign jasperDesign;

    public TestCreateReport() throws JRException
    {
        jasperDesign = new JasperDesign();
        jasperDesign.setName("Test");
        JRDesignBand details = new JRDesignBand();
        details.setHeight(pageHeight);
//        jasperDesign.setDetail(details);
        jasperDesign.setBottomMargin(0);
        jasperDesign.setTopMargin(0);
        jasperDesign.setBottomMargin(0);
        jasperDesign.setLeftMargin(0);
        jasperDesign.setRightMargin(0);
        jasperDesign.setColumnHeader(null);
        jasperDesign.setPageWidth(pageWidth);
        jasperDesign.setPageHeight(pageHeight);
        JRDesignParameter parameter = new JRDesignParameter();
        parameter.setName("text");
        parameter.setValueClass(String.class);
        jasperDesign.addParameter(parameter);

    }

    public static void main(String[] args) throws JRException
    {

        TestCreateReport createReport = new TestCreateReport();

        OrderFurniture furniture = new OrderFurniture();
        furniture.setLength(1200L);
        furniture.setWidth(600L);
        JRDesignRectangle rectangle = createReport.getRectangle(furniture);

//        ((JRDesignBand) createReport.jasperDesign.getDetail()).addElement(rectangle);
//        ((JRDesignBand) createReport.jasperDesign.getDetail()).addElementGroup(createReport.getDimentionLine(new DimensionLine(rectangle.getX(), rectangle.getY(), rectangle.getX() + rectangle.getWidth(), rectangle.getY(), 10), 100L));


//        JRDesignRectangle rectangle = new JRDesignRectangle();
//        rectangle.setX(10);
//        rectangle.setY(20);
//        rectangle.setWidth(300);
//        rectangle.setHeight(200);

        JRDesignTextField field = new JRDesignTextField();
        field.setX(100);
        field.setY(80);
        field.setHeight(20);
        field.setWidth(100);

        JRDesignExpression expression = new JRDesignExpression();
        expression.addTextChunk("\"Test\"");
        expression.setValueClass(String.class);
        field.setExpression(expression);
        //((JRDesignBand) createReport.jasperDesign.getDetail()).addElement(field);

//        JRDesignLine designLine = new JRDesignLine();
//        designLine.setForecolor(Color.BLACK);
//        designLine.setWidth(500);
//        designLine.setX(10);
//        designLine.setY(10);
//        
//        details.addElement(designLine);
//        details.addElement(rectangle);
//        jasperDesign.setDetail(details);
//        jasperDesign.setName("Test");
        JasperReport jasperReport = JasperCompileManager.compileReport(createReport.jasperDesign);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("text", "10000");
        JasperFillManager.fillReport(jasperReport, hashMap);
        JasperDesignViewer designViewer = new JasperDesignViewer(jasperReport);
        designViewer.setVisible(true);
    }

    public void cutoff(OrderFurniture furniture)
    {
        furniture.getCutoff();
    }

    public JRDesignRectangle getRectangle(OrderFurniture furniture)
    {
        JRDesignRectangle rectangle = new JRDesignRectangle();
        rectangle.setForecolor(Color.BLACK);
        rectangle.setX(segmentMargin);
        rectangle.setY(segmentMargin);
        int width = furniture.getLength().intValue();
        int height = furniture.getWidth().intValue();
        rectangle.setWidth((int) (width * getScale(width, height)));
        rectangle.setHeight((int) (height * getScale(width, height)));
        return rectangle;
    }

    public JRDesignElementGroup getDimentionLine(DimensionLine dimensionLine, long value)
    {
        JRDesignElementGroup elementGroup = new JRDesignElementGroup();

        double angle = Math.atan2(dimensionLine.getYEnd() - dimensionLine.getYStart(),
                dimensionLine.getXEnd() - dimensionLine.getXStart());
        double dimensionLineLength = Point2D.distance(dimensionLine.getXStart(), dimensionLine.getYStart(),
                dimensionLine.getXEnd(), dimensionLine.getYEnd());
        JRDesignLine line = new JRDesignLine();
        line.getLinePen().setLineStyle(LineStyleEnum.DASHED);

        JRDesignLine offsetLine1 = new JRDesignLine();
        offsetLine1.getLinePen().setLineStyle(LineStyleEnum.DASHED);

        JRDesignLine offsetLine2 = new JRDesignLine();
        offsetLine2.getLinePen().setLineStyle(LineStyleEnum.DASHED);

        JRDesignTextField field = new JRDesignTextField();
        JRDesignExpression expression = new JRDesignExpression();
        expression.addParameterChunk("text");
        expression.setValueClass(String.class);
        field.setExpression(expression);

        if (angle == 0.0)
        {
            field.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            field.setVerticalTextAlign(VerticalTextAlignEnum.BOTTOM);
            field.setX((int) (dimensionLine.getXStart()));
            field.setY((int) (dimensionLine.getYStart() - dimensionLine.getOffset() - 20));
            field.setWidth((int) (dimensionLineLength - 10));
            field.setHeight(20);


            offsetLine1.setX((int) dimensionLine.getXStart());
            offsetLine1.setY((int) (dimensionLine.getYStart() - dimensionLine.getOffset()));
            offsetLine1.setWidth(0);
            offsetLine1.setHeight((int) Point2D.distance(dimensionLine.getXStart(),
                    dimensionLine.getYStart() - dimensionLine.getOffset(),
                    dimensionLine.getXStart(),
                    dimensionLine.getYStart()));


            offsetLine2.setX((int) (dimensionLine.getXEnd()));
            offsetLine2.setY((int) (dimensionLine.getYEnd() - dimensionLine.getOffset()));
            offsetLine2.setWidth(0);
            offsetLine2.setHeight((int) Point2D.distance(dimensionLine.getXEnd(),
                    dimensionLine.getYEnd() - dimensionLine.getOffset(),
                    dimensionLine.getXEnd(),
                    dimensionLine.getYEnd()));

            line.setX((int) dimensionLine.getXStart());
            line.setY((int) (dimensionLine.getYStart() - dimensionLine.getOffset()));
            line.setWidth((int) dimensionLineLength);
            line.setHeight(0);
        }
        else
        {
        }

        elementGroup.addElement(offsetLine2);
        elementGroup.addElement(offsetLine1);
        elementGroup.addElement(line);
        elementGroup.addElement(field);
        return elementGroup;
    }

    private float getScale(int width, int heigth)
    {
        double segmentWidth = pageWidth / segmentColumnCount - segmentMargin * 2;
        double segmentHigth = pageHeight / segmentRowCount - segmentMargin * 2;

        return (float) Math.min(segmentWidth / width, segmentHigth / heigth);
    }
}
