package by.dak.design.draw.handle;

import by.dak.design.draw.components.DimensionFigure;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 03.08.11
 * Time: 2:10
 * To change this template use File | Settings | File Templates.
 */
public class DimensionPositionHandler
{
    private DimensionFigure owner;

    public DimensionPositionHandler(DimensionFigure owner)
    {
        this.owner = owner;
    }


    public void trackStart(Point anchor)
    {
    }

    public void trackStep(Point2D.Double lead)
    {
        changeTipPosition(lead);
    }

    private void changeTipPosition(Point2D.Double point)
    {
        Point2D.Double figureEndPoint = owner.getEndPoint();
        Point2D.Double currentPoint = point;

        Line2D.Double line = new Line2D.Double(owner.getStartPoint(), figureEndPoint);
        double distance = line.ptLineDist(currentPoint);

        owner.willChange();

        double firstSide = owner.getStartPoint().x - owner.getEndPoint().x;
        double secondSide = owner.getStartPoint().y - owner.getEndPoint().y;
        double angle = Math.atan(secondSide / firstSide);

        if (Math.toDegrees(angle) >= 50 && Math.toDegrees(angle) <= 130)
        {

            double x = (owner.getEndPoint().x - owner.getStartPoint().x) *
                    (currentPoint.y - owner.getStartPoint().y) /
                    (owner.getEndPoint().y - owner.getStartPoint().y);

            double result = x + owner.getStartPoint().x;
            if (Math.abs(result) < currentPoint.x)
            {
                ((DimensionFigure) owner).setOffset(distance);
            }
            else
            {
                ((DimensionFigure) owner).setOffset(-distance);
            }

        }
        else if (Math.toDegrees(angle) >= -130 && Math.toDegrees(angle) <= -50)
        {

            double x = (owner.getEndPoint().x - owner.getStartPoint().x) *
                    (currentPoint.y - owner.getStartPoint().y) /
                    (owner.getEndPoint().y - owner.getStartPoint().y);

            double result = x + owner.getStartPoint().x;

            if (Math.abs(result) > currentPoint.x)
            {
                ((DimensionFigure) owner).setOffset(distance);
            }
            else
            {
                ((DimensionFigure) owner).setOffset(-distance);
            }

        }
        else
        {

            double y = (currentPoint.x - owner.getStartPoint().x) *
                    (owner.getEndPoint().y - owner.getStartPoint().y) /
                    (owner.getEndPoint().x - owner.getStartPoint().x);
            double result = y + owner.getStartPoint().y;

            if (Math.abs(result) > currentPoint.getY())
            {
                ((DimensionFigure) owner).setOffset(distance);
            }
            else
            {
                ((DimensionFigure) owner).setOffset(-distance);
            }

        }

        owner.changed();
    }


    public void trackEnd(Point anchor, Point lead)
    {
    }
}
