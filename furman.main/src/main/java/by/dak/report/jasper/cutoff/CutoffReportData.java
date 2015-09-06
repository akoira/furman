/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.report.jasper.cutoff;

import by.dak.persistence.entities.OrderFurniture;

import java.awt.*;

/**
 * @author admin
 */
public class CutoffReportData
{

    private static final Dimension PAGE_SIZE = new Dimension(278, 249);

    private OrderFurniture furniture1;

    private OrderFurniture furniture2;

    private Image image1;

    private Image image2;

    public CutoffReportData(OrderFurniture furniture1, OrderFurniture furniture2)
    {
        this.furniture1 = furniture1;
        this.furniture2 = furniture2;
    }

    public Image getImage1()
    {
        if (image1 == null)
        {
            if (furniture1 != null)
            {
                image1 = new CutoffImageCreator(furniture1, PAGE_SIZE).create();
            }
        }
        return image1;
    }

    public Image getImage2()
    {
        if (image2 == null)
        {
            if (furniture2 != null)
            {
                image2 = new CutoffImageCreator(furniture2, PAGE_SIZE).create();
            }
        }
        return image2;
    }
}
