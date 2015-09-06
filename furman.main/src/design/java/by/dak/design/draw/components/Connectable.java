package by.dak.design.draw.components;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/24/11
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Connectable
{
    public DimensionConnector getConnector(Point2D.Double point);
}
