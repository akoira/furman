package by.dak.report.jasper.milling;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.ElementDrawing;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.MillingConverter;
import by.dak.draw.Graphics2DUtils;
import by.dak.persistence.entities.OrderFurniture;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * User: akoyro
 * Date: 16.07.2009
 * Time: 10:44:53
 */
public class MillingImageCreator
{
    private OrderFurniture furniture;

    public MillingImageCreator(OrderFurniture furniture)
    {
        this.furniture = furniture;
    }

    public BufferedImage create()
    {
        ElementDrawing elementDrawing = createElementDrawing();

        Rectangle2D.Double rec = new Rectangle2D.Double(elementDrawing.getDrawingArea().getX(), elementDrawing.getDrawingArea().getY(),
                elementDrawing.getDrawingArea().getWidth(), elementDrawing.getDrawingArea().getHeight());


        BufferedImage bimage = Graphics2DUtils.createBufferedImage((int) elementDrawing.getDrawingArea().getWidth(), (int) elementDrawing.getDrawingArea().getBounds().getHeight());
        Graphics2D graphics2D = bimage.createGraphics();
        graphics2D.setTransform(AffineTransform.getTranslateInstance(Math.abs(elementDrawing.getDrawingArea().getX()),
                Math.abs(elementDrawing.getDrawingArea().getY())));
        graphics2D.setClip(rec);
        graphics2D.setColor(Color.WHITE);
        graphics2D.fill(rec);
        graphics2D.setColor(Color.BLACK);
        //ReportUtils.paintFurnitureName(this.furniture, rec, graphics2D);
        elementDrawing.draw(graphics2D);
        return bimage;
    }

    ElementDrawing createElementDrawing()
    {
        Dimension element = new Dimension(furniture.getLength().intValue(), furniture.getWidth().intValue());

        ElementDrawing elementDrawing = new ElementDrawing();
        elementDrawing.setOffset(0);
        elementDrawing.setElement(element);
        MillingConverter millingConverter = new MillingConverter();
        millingConverter.restore(furniture.getMilling(), elementDrawing);
        return elementDrawing;
    }

}
