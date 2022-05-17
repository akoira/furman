package by.dak.report.jasper.glueing;

import by.dak.cutting.configuration.Constants;
import by.dak.cutting.swing.order.data.DTO;
import by.dak.cutting.swing.order.data.Drilling;
import by.dak.cutting.swing.order.data.Glueing;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.draw.Graphics2DUtils;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.report.jasper.ReportUtils;
import by.dak.utils.convert.Converter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * @author Denis Koyro
 * @version 0.1 05.02.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class GlueingReportConverter implements Converter<OrderFurniture, OrderDetailsData>
{

    public OrderDetailsData convert(OrderFurniture source)
    {
        String detail = source.isComplex() ? source.getNumber() + "+" + source.getSecond().getNumber() : source.getNumber().toString();
        String material = source.isComplex() ? source.getComlexBoardDef().getName() : source.getBoardDef().getName();
        String texture = ReportUtils.formatTexture(source.getTexture());

        Integer amount = source.getAmount();

        Long dirtyWidth = source.getWidth();
        Long pureWidth = dirtyWidth - Constants.COMPEXT_FURNITURE_INCREASE;
        String widthValue = source.isComplex() ? ReportUtils.formatDimension(pureWidth, dirtyWidth) : dirtyWidth.toString();

        Long dirtyLength = source.getLength();
        Long pureLength = dirtyLength - Constants.COMPEXT_FURNITURE_INCREASE;
        String lengthValue = source.isComplex() ? ReportUtils.formatDimension(pureLength, dirtyLength) : dirtyLength.toString();

        Glueing glueing = (Glueing) XstreamHelper.getInstance().fromXML(source.getGlueing());
        String milling = source.getMilling();
        Drilling drilling = (Drilling) XstreamHelper.getInstance().fromXML(source.getDrilling());


        return new OrderDetailsData(detail, material, texture, amount, widthValue, lengthValue,
                createImage(glueing, dirtyLength, dirtyWidth),
                glueing != null && glueing.isUp() ? ReportUtils.formatGlueingValue(glueing.getUpBorderDef().getName(), glueing.getUpTexture()) : EMPTY_STRING,
                glueing != null && glueing.isDown() ? ReportUtils.formatGlueingValue(glueing.getDownBorderDef().getName(), glueing.getDownTexture()) : EMPTY_STRING,
                glueing != null && glueing.isLeft() ? ReportUtils.formatGlueingValue(glueing.getLeftBorderDef().getName(), glueing.getLeftTexture()) : EMPTY_STRING,
                glueing != null && glueing.isRight() ? ReportUtils.formatGlueingValue(glueing.getRightBorderDef().getName(), glueing.getRightTexture()) : EMPTY_STRING,
                milling != null ? ReportUtils.getFurnitureName(source) : EMPTY_STRING,
                EMPTY_STRING,
                drilling != null ? drilling.getNote() : EMPTY_STRING,
                drilling != null && drilling.getNote() != null ? drilling.getNote() : EMPTY_STRING);
    }

    private Image createImage(DTO dto, long length, long width)
    {
        int iLength = 0;
        int iWidth = 0;
        if (length > width)
        {
            iLength = 40;
            iWidth = 20;
        }
        else if (width < length)
        {
            iWidth = 40;
            iLength = 20;
        }
        else
        {
            iWidth = 40;
            iLength = 40;
        }

        BufferedImage image = Graphics2DUtils.createBufferedImage(iLength,
                iWidth);

        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fill(new Rectangle(0, 0, iLength, iWidth));
        graphics2D.setColor(Color.BLACK);
        BasicStroke nBS = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                2.0f, new float[]{2.0f, 2f}, 0.0f);
        BasicStroke isBS = new BasicStroke(0.1f);


        if (dto != null)
        {

            graphics2D.setStroke(dto.isUp() ? isBS : nBS);
            graphics2D.draw(new Line2D.Float(0, 0, iLength, 0));

            graphics2D.setStroke(dto.isDown() ? isBS : nBS);
            graphics2D.draw(new Line2D.Float(0, iWidth - 1, iLength, iWidth - 1));

            graphics2D.setStroke(dto.isLeft() ? isBS : nBS);
            graphics2D.draw(new Line2D.Float(0, 0, 0, iWidth));

            graphics2D.setStroke(dto.isRight() ? isBS : nBS);
            graphics2D.draw(new Line2D.Float(iLength - 1, 0, iLength - 1, iWidth));
        }
        return image;
    }
}
