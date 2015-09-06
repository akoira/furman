/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.report.jasper.common.data;

import by.dak.cutting.swing.order.data.Glueing;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.predefined.Side;
import by.dak.report.jasper.ReportUtils;

/**
 * @author admin
 */
public class GlueingSideHelper extends by.dak.cutting.swing.order.data.GlueingSideHelper
{

    private OrderFurniture furniture;

    public GlueingSideHelper(OrderFurniture furniture, Side side)
    {
        super((Glueing)XstreamHelper.getInstance().fromXML(furniture.getGlueing()), side);
        this.furniture = furniture;
    }


    public double getSize()
    {
        long size = 0;
        switch (getSide())
        {
            case up:
            case down:
                size = furniture.getLength();
                break;
            case left:
            case right:
                size = furniture.getWidth();
                break;
            default:
                throw new RuntimeException();
        }
        double width = ReportUtils.calcLinear(size * furniture.getAmount());
        return width;
    }
}
