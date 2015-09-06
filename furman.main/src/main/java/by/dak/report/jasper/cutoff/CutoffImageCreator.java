/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.report.jasper.cutoff;

import by.dak.cutting.swing.order.cellcomponents.editors.cuttoff.CutoffPainter;
import by.dak.cutting.swing.order.data.Cutoff;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.draw.Graphics2DUtils;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.report.jasper.ReportUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author admin
 */
public class CutoffImageCreator
{

    private OrderFurniture furniture;

    private Dimension pageSize;

    public CutoffImageCreator(OrderFurniture furniture, Dimension pageSize)
    {
        this.furniture = furniture;
        this.pageSize = pageSize;
    }

    public BufferedImage create()
    {
        Dimension element = new Dimension(furniture.getLength().intValue(), furniture.getWidth().intValue());

        double scale = CutoffPainter.getScale(element, pageSize);
        CutoffPainter.ElementRectangle rectangle = CutoffPainter.getElementRectangleBy(element, scale);

        Cutoff cutoff = (Cutoff) XstreamHelper.getInstance().fromXML(furniture.getCutoff());
        CutoffPainter.CutoffLine cutoffLine = new CutoffPainter.CutoffLine();
        CutoffPainter.adjustLineBy(cutoffLine, element, cutoff, scale);
        CutoffPainter cutoffPainter = new CutoffPainter(element, cutoff, cutoffLine, scale);
        BufferedImage bimage = Graphics2DUtils.createBufferedImage((int) (rectangle.getWidth() + CutoffPainter.OFFSET * 2), (int) (rectangle.getHeight() + CutoffPainter.OFFSET * 2));
        Graphics2D graphics2D = bimage.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, pageSize.width, pageSize.height);
        graphics2D.setColor(Color.BLACK);

        ReportUtils.paintFurnitureName(this.furniture, rectangle, graphics2D);
        cutoffPainter.paint(graphics2D);
        return bimage;
    }
}
