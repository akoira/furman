package by.dak.report.jasper.milling;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.ElementDrawing;
import by.dak.persistence.entities.OrderFurniture;
import org.apache.commons.io.IOUtils;
import org.jhotdraw.draw.Figure;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: akoyro
 * Date: 16.07.2009
 * Time: 10:54:40
 */
public class TMillingImageCreator
{
    public static void main(String[] args) throws IOException
    {
        List<String> list = IOUtils.readLines(TMillingImageCreator.class.getResourceAsStream("milling.test.txt"));

        OrderFurniture furniture = new OrderFurniture();
        furniture.setLength(1000l);
        furniture.setWidth(500l);
        furniture.setNumber(100l);
        furniture.setMilling(list.get(0));
        MillingImageCreator millingImageCreator = new MillingImageCreator(furniture);

        ElementDrawing elementDrawing = millingImageCreator.createElementDrawing();
        List<Figure> figures = elementDrawing.getChildren();

        BufferedImage image = millingImageCreator.create();
        ImageIO.write(image, "png", new File("test.png"));
    }
}
