/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.report.jasper.cutoff;

import by.dak.cutting.swing.order.data.Cutoff;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.entities.OrderFurniture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author admin
 */
public class CuttoffImageCreatorTest
{

    public static void main(String[] args) throws IOException
    {
        Dimension pageSize = new Dimension(200, 100);
        Cutoff cutoff = new Cutoff();
        cutoff.setAngle(Cutoff.Angle.upLeft);
        cutoff.setVOffset(300d);
        cutoff.setHOffset(400d);

        OrderFurniture furniture = new OrderFurniture();
        furniture.setNumber(1000L);
        furniture.setLength(1000l);
        furniture.setWidth(500l);
        furniture.setCutoff(XstreamHelper.getInstance().toXML(cutoff));
        CutoffImageCreator cuttoffImageCreator = new CutoffImageCreator(furniture, pageSize);
        BufferedImage bufferedImage = cuttoffImageCreator.create();
        ImageIO.write(bufferedImage, "png", new File("test.png"));
    }
}
