package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 04.08.2009
 * Time: 15:00:55
 * To change this template use File | Settings | File Templates.
 */
public interface Arc
{
    public Arc2D.Double getArc();

    public void setArc(Arc2D.Double arc);

    public Point2D.Double getMiddleArc();
}
