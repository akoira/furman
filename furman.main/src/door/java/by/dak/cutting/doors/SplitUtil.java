package by.dak.cutting.doors;

import by.dak.cutting.doors.profile.draw.HProfileJoin;
import by.dak.cutting.doors.profile.draw.VProfileJoin;
import by.dak.cutting.draw.Constants;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.*;
import org.jhotdraw.draw.Figure;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 11.08.2009
 * Time: 15:56:28
 * To change this template use File | Settings | File Templates.
 */
public class SplitUtil
{
    public static Point2D.Double isIntersect(Figure figure1, Figure figure2)
    {
        if (figure1 instanceof CurveFigure && figure2 instanceof CurveFigure)
        {
            CurveFigure line1 = (CurveFigure) figure1;
            CurveFigure line2 = (CurveFigure) figure2;

            return isIntersect(line1, line2);
        }
        else if (figure1 instanceof CurveFigure && figure2 instanceof Arc)
        {
            CurveFigure line = (CurveFigure) figure1;
            Arc arc = (Arc) figure2;
            return isIntersect(line, arc);
        }
        else if (figure1 instanceof Arc && figure2 instanceof CurveFigure)
        {
            CurveFigure line = (CurveFigure) figure2;
            Arc arc = (Arc) figure1;
            return isIntersect(line, arc);
        }
        else if (figure1 instanceof HProfileJoin && figure2 instanceof VProfileJoin)
        {
            HProfileJoin hProfileJoin = (HProfileJoin) figure1;
            VProfileJoin vProfileJoin = (VProfileJoin) figure2;
            return isIntersect(hProfileJoin, vProfileJoin);
        }
        else if (figure1 instanceof VProfileJoin && figure2 instanceof HProfileJoin)
        {
            HProfileJoin hProfileJoin = (HProfileJoin) figure2;
            VProfileJoin vProfileJoin = (VProfileJoin) figure1;
            return isIntersect(hProfileJoin, vProfileJoin);
        }

        return null;
    }

    private static Point2D.Double isIntersect(HProfileJoin hProfileJoin, VProfileJoin vProfileJoin)
    {
        Point2D.Double point = new Point2D.Double();
        point.setLocation(vProfileJoin.getRec().getX(), hProfileJoin.getRec().getY());

        double minX = hProfileJoin.getRec().getMinX();
        double maxX = hProfileJoin.getRec().getMaxX();
        double minY = vProfileJoin.getRec().getMinY();
        double maxY = vProfileJoin.getRec().getMaxY();

        if (point.x > minX && point.x < maxX && point.y > minY && point.y < maxY)
        {
            return point;
        }
        return null;
    }

    private static Point2D.Double isIntersect(CurveFigure line1, CurveFigure line2)
    {
        boolean isVertical1 = false;
        boolean isVertical2 = false;
        if (!line1.isCondition() || !line2.isCondition())
        {
            return null;
        }

        Point2D.Double point11 = line1.getStartPoint();
        Point2D.Double point12 = line1.getEndPoint();
        Point2D.Double point21 = line2.getStartPoint();
        Point2D.Double point22 = line2.getEndPoint();

        double b1;
        double c1 = 0;
        double b2;
        double c2 = 0;

        b1 = 360 - Math.max(ArcUtil.calcAngle(point12, point11), ArcUtil.calcAngle(point11, point12));
        b2 = 360 - Math.max(ArcUtil.calcAngle(point22, point21), ArcUtil.calcAngle(point21, point22));

        if (b1 == 90 || b1 == 270)
        {
            isVertical1 = true;
        }
        else if (b1 % 180 == 0)
        {
            b1 = 0;
            c1 = point11.getY() - b1 * point11.getX();
        }
        else
        {
            b1 = Math.tan(Math.toRadians(b1));
            c1 = point11.getY() - b1 * point11.getX();
        }

        if (b2 == 90 || b2 == 270)
        {
            isVertical2 = true;
        }
        else if (b2 % 180 == 0)
        {
            b2 = 0;
            c2 = point21.getY() - b2 * point21.getX();
        }
        else
        {
            b2 = Math.tan(Math.toRadians(b2));
            c2 = point21.getY() - b2 * point21.getX();
        }

        if ((!isVertical1 && !isVertical2 && Math.abs(b1 - b2) < 0.1) || (isVertical1 && isVertical2))
        {
            return null;
        }

        double x;
        double y;

        if (isVertical1)
        {
            if ((point11.getX() <= Math.max(point21.getX(), point22.getX())
                    && point11.getX() >= Math.min(point21.getX(), point22.getX()))
                    && ((point21.getY() <= Math.max(point11.getY(), point12.getY())
                    && point21.getY() >= Math.min(point11.getY(), point12.getY()))
                    || (point22.getY() <= Math.max(point11.getY(), point12.getY())
                    && point22.getY() >= Math.min(point11.getY(), point12.getY()))))
            {
                x = point11.getX();
            }
            else
            {
                return null;
            }
        }
        else if (isVertical2)
        {
            if (point21.getX() <= Math.max(point11.getX(), point12.getX())
                    && point21.getX() >= Math.min(point11.getX(), point12.getX())
                    && ((point11.getY() <= Math.max(point21.getY(), point22.getY())
                    && point11.getY() >= Math.min(point21.getY(), point22.getY()))
                    || (point12.getY() <= Math.max(point21.getY(), point22.getY())
                    && point12.getY() >= Math.min(point21.getY(), point22.getY()))))
            {
                x = point21.getX();
            }
            else
            {
                return null;
            }
        }
        else
        {
            x = (c2 - c1) / (b1 - b2);
        }

        if (isVertical1)
        {
            y = b2 * x + c2;
        }
        else if (isVertical2)
        {
            y = b1 * x + c1;
        }
        else
        {
            y = b1 * x + c1;
        }

        Point2D.Double point = new Point2D.Double(x, y);

        if (point.getX() - Math.max(point11.getX(), point12.getX()) <= 0.1
                && point.getX() - Math.min(point11.getX(), point12.getX()) >= -0.1
                && point.getX() - Math.max(point21.getX(), point22.getX()) <= 0.1
                && point.getX() - Math.min(point21.getX(), point22.getX()) >= -0.1
                && point.getY() - Math.max(point21.getY(), point22.getY()) <= 0.1
                && point.getY() - Math.min(point21.getY(), point22.getY()) >= -0.1
                && point.getY() - Math.max(point11.getY(), point12.getY()) <= 0.1
                && point.getY() - Math.min(point11.getY(), point12.getY()) >= -0.1)
        {
            return point;
        }
        else
        {
            return null;
        }
    }

    private static Point2D.Double isIntersect(CurveFigure line, Arc arc)
    {
        boolean isVertical = false;
        if (!line.isCondition())
        {
            return null;
        }

        Point2D.Double point1 = line.getStartPoint();
        Point2D.Double point2 = line.getEndPoint();

        double x;
        double x1 = 0;
        double x2 = 0;
        double y = 0;
        double xNew = arc.getArc().getCenterX();
        double yNew = arc.getArc().getCenterY();

        double b1;
        double c1 = 0;
        double p = arc.getArc().getWidth() / 2;
        double k = arc.getArc().getHeight() / 2;

        b1 = 360 - Math.max(ArcUtil.calcAngle(point2, point1), ArcUtil.calcAngle(point1, point2));

        if (b1 == 90 || b1 == 270)
        {
            isVertical = true;
        }
        else if (b1 % 180 == 0)
        {
            b1 = 0;
            c1 = point1.getY() - yNew - b1 * (point1.getX() - xNew);
        }
        else
        {
            b1 = Math.tan(Math.toRadians(b1));
            c1 = point1.getY() - yNew - b1 * (point1.getX() - xNew);
        }

        if (isVertical)
        {
            x = point1.getX() - xNew;
            y = Math.sqrt(1 - x * x / (p * p)) * k;
        }
        else
        {
            double m1 = k * k + b1 * b1 * p * p;
            double m2 = 2 * b1 * c1 * p * p;
            double m3 = c1 * c1 * p * p - k * k * p * p;

            x = (-m2 - Math.sqrt(m2 * m2 - 4 * m1 * m3)) / (2 * m1);
            x1 = x;
            x2 = (-m2 + Math.sqrt(m2 * m2 - 4 * m1 * m3)) / (2 * m1);
        }

        if (isVertical)
        {
            if (arc.getArc().getStartPoint().distance(x + xNew, y + yNew) < 1 ||
                    arc.getArc().getEndPoint().distance(x + xNew, y + yNew) < 1)
            {
                y *= -1;
            }
        }
        else
        {
            if (Math.abs(arc.getArc().getStartPoint().getX() - (x + xNew)) < 1 ||
                    Math.abs(arc.getArc().getEndPoint().getX() - (x + xNew)) < 1)
            {
                x = x2;
            }
            y = b1 * x + c1;
        }

        x += xNew;
        y += yNew;

        Point2D.Double point = new Point2D.Double(x, y);

        if ((arc.getArc().getBounds2D().contains(point) || arc.getMiddleArc().distance(point) < 1
                || arc.getArc().getStartPoint().distance(point) < 1 || arc.getArc().getEndPoint().distance(point) < 1)
                && point.distance(line.getStartPoint()) > 1 && point.distance(line.getEndPoint()) > 1
                && (point.getX() - Math.max(point1.getX(), point2.getX()) <= 1
                && point.getX() - Math.min(point1.getX(), point2.getX()) >= -1
                && point.getY() - Math.max(point1.getY(), point2.getY()) <= 1
                && point.getY() - Math.min(point1.getY(), point2.getY()) >= -1))
        {
            return point;
        }

        if (isVertical)
        {
            x -= xNew;
            y -= yNew;
            y *= -1;
        }
        else
        {
            if (Math.abs(x - xNew - x1) < 0.1)
            {
                x = x2;
            }
            else
            {
                x = x1;
            }
            y = b1 * x + c1;
        }

        x += xNew;
        y += yNew;

        point.setLocation(x, y);

        if ((arc.getArc().getBounds2D().contains(point) || arc.getMiddleArc().distance(point) < 1
                || arc.getArc().getStartPoint().distance(point) < 1 || arc.getArc().getEndPoint().distance(point) < 1)
                && (point.getX() - Math.max(point1.getX(), point2.getX()) <= 1
                && point.getX() - Math.min(point1.getX(), point2.getX()) >= -1
                && point.getY() - Math.max(point1.getY(), point2.getY()) <= 1
                && point.getY() - Math.min(point1.getY(), point2.getY()) >= -1))
        {
            return point;
        }
        else
        {
            return null;
        }
    }

    public static List<Figure> split(Figure figure, Point2D.Double point)
    {
        List<Figure> figures = new ArrayList<Figure>();
        double x = point.getX();
        double y = point.getY();

        if (x % ((int) x) >= 0.5)
        {
            x = (int) (x + 1);
        }
        else
        {
            x = (int) x;
        }

        if (y % ((int) y) >= 0.5)
        {
            y = (int) (y + 1);
        }
        else
        {
            y = (int) y;
        }
        point.setLocation(x, y);

        if (figure instanceof CurveFigure)
        {
            figures.addAll(splitLine((CurveFigure) figure, point));
        }
        else if (figure instanceof ArcEveryFigure)
        {
            figures.addAll(splitArc(figure, point));
        }
        else if (figure instanceof VProfileJoin)
        {
            figures.addAll(splitVProfile(figure, point));
        }
        return figures;
    }

    public static List<Figure> splitVProfile(Figure figure, Point2D.Double point)
    {
        List<Figure> figures = new ArrayList<Figure>();
        VProfileJoin vProfileJoin = (VProfileJoin) figure;
        double indent = vProfileJoin.getProfileWidth();

        Point2D.Double anchor = new Point2D.Double();
        Point2D.Double lead = new Point2D.Double();

        VProfileJoin vProfileJoinNew = new VProfileJoin(vProfileJoin.getProfileWidth());
        anchor.setLocation(vProfileJoin.getRec().getMinX(), vProfileJoin.getRec().getMinY());
        lead.setLocation(vProfileJoin.getRec().getMinX(), point.getY());
        vProfileJoinNew.setBounds(anchor, lead);
        figures.add(vProfileJoinNew);

        vProfileJoinNew = new VProfileJoin(vProfileJoin.getProfileWidth());
        anchor.setLocation(vProfileJoin.getRec().getMinX(), point.getY() + indent);
        lead.setLocation(vProfileJoin.getRec().getMinX(), vProfileJoin.getRec().getMaxY());
        vProfileJoinNew.setBounds(anchor, lead);
        figures.add(vProfileJoinNew);

        return figures;
    }

    public static List<Figure> splitArc(Figure arc, Point2D.Double point)
    {
        List<Figure> figures = new ArrayList<Figure>();
        Arc2D.Double arc2D = ((Arc) arc).getArc();
        Point2D.Double center = new Point2D.Double(arc2D.getCenterX(), arc2D.getCenterY());

        if (point.distance(arc2D.getStartPoint()) > 1 && point.distance(arc2D.getEndPoint()) > 1)
        {
            Arc2D.Double arc2D1;
            Arc2D.Double arc2D2;

            double angle = ArcUtil.calcAngle(center, point);

            if (arc2D.getAngleStart() + arc2D.getAngleExtent() > 360 && arc2D.getAngleStart() > angle)
            {
                arc2D1 = new Arc2D.Double(arc2D.getX(), arc2D.getY(), arc2D.getWidth(), arc2D.getHeight(),
                        arc2D.getAngleStart(), 360 - arc2D.getAngleStart() + angle, Arc2D.OPEN);

                arc2D2 = new Arc2D.Double(arc2D.getX(), arc2D.getY(), arc2D.getWidth(), arc2D.getHeight(),
                        angle, ArcUtil.calcAngle(center, (Point2D.Double) arc2D.getEndPoint()) - angle, Arc2D.OPEN);
            }
            else
            {
                arc2D1 = new Arc2D.Double(arc2D.getX(), arc2D.getY(), arc2D.getWidth(), arc2D.getHeight(),
                        arc2D.getAngleStart(), angle - arc2D.getAngleStart(), Arc2D.OPEN);

                arc2D2 = new Arc2D.Double(arc2D.getX(), arc2D.getY(), arc2D.getWidth(), arc2D.getHeight(),
                        angle, arc2D.getAngleExtent() - (angle - arc2D.getAngleStart()), Arc2D.OPEN);
            }

            ArcEveryFigure arcF1 = (ArcEveryFigure) arc.clone();
            arcF1.setArc(arc2D1);

            ArcEveryFigure arcF2 = (ArcEveryFigure) arc.clone();
            arcF2.setArc(arc2D2);

            figures.add(arcF1);
            figures.add(arcF2);
        }

        return figures;
    }

    public static List<Figure> splitLine(CurveFigure line, Point2D.Double point)
    {
        List<Figure> figures = new ArrayList<Figure>();
        if (point.distance(line.getStartPoint()) > 1 && point.distance(line.getEndPoint()) > 1)
        {
            CurveFigure newLine = new CurveFigure();
            newLine.setStartPoint(line.getStartPoint());
            newLine.setEndPoint(point);
            newLine.setDirection(line.getDirection());
            figures.add(newLine);

            newLine = new CurveFigure();
            newLine.setStartPoint(point);
            newLine.setEndPoint(line.getEndPoint());
            newLine.setDirection(line.getDirection());
            figures.add(newLine);
        }

        return figures;
    }

    private static Figure findMinX(List<Figure> figures)
    {
        Figure min = figures.get(0);
        double minX = Math.min(min.getStartPoint().getX(), min.getEndPoint().getX());

        for (Figure figure : figures)
        {
            double figX = Math.min(figure.getStartPoint().getX(), figure.getEndPoint().getX());
            if (minX > figX)
            {
                min = figure;
                minX = figX;
            }
        }
        return min;
    }

    public static List<Figure> closedFigures(List<Figure> figures)
    {
        List<Figure> shape = new ArrayList<Figure>();
        if (figures.size() <= 1)
        {
            return shape;
        }

        Point2D.Double start;
        Point2D.Double end;

        start = figures.get(0).getStartPoint();
        end = figures.get(0).getEndPoint();
        shape.add(figures.get(0));


        int count = 0;
        while (true)
        {
            List<Figure> list;
            if (shape.get(count).getStartPoint().distance(end) < 2)
            {
                list = SplitUtil.startFigure(shape.get(count), shape.get(count).getStartPoint(), figures);
            }
            else
            {
                list = SplitUtil.startFigure(shape.get(count), shape.get(count).getEndPoint(), figures);
            }

            if (list.size() != 1)
            {
                shape.clear();
                break;
            }
            else
            {
                shape.add(list.get(0));
                if (shape.get(count + 1).getStartPoint().distance(end) < 2)
                {
                    end = shape.get(count + 1).getEndPoint();
                }
                else
                {
                    end = shape.get(count + 1).getStartPoint();
                }
            }

            count++;

            if (end.distance(start) < 1 && count + 1 == figures.size())
            {
                break;
            }
            else if (count + 1 == figures.size() || end.distance(start) < 1)
            {
                shape.clear();
                break;
            }
        }
        return shape;
    }

    public static ElementDrawing findMinSq(List<ElementDrawing> drawings)
    {
        if (drawings.size() == 0)
        {
            return null;
        }
        ElementDrawing min = drawings.get(0);
        double minSq = findSq(min);

        for (ElementDrawing drawing : drawings)
        {
            double sq = findSq(drawing);
            if (sq < minSq)
            {
                minSq = sq;
                min = drawing;
            }
        }

        return min;
    }

    public static double findSq(ElementDrawing element)
    {
        double sq = 0;
        Figure startFigure1 = element.getChildren().get(0);
        Figure startFigure2 = element.getChildren().get(0);
        Point2D.Double start1 = startFigure1.getStartPoint();
        Point2D.Double start2 = startFigure1.getEndPoint();
        Point2D.Double end1;
        Point2D.Double end2;

        List<Figure> list1 = startFigure(startFigure1, start1, element.getChildren());
        List<Figure> list2 = startFigure(startFigure2, start2, element.getChildren());

        if (list1.get(0).getStartPoint().distance(start1) < 0.1)
        {
            end1 = list1.get(0).getEndPoint();
        }
        else
        {
            end1 = list1.get(0).getStartPoint();
        }

        if (list2.get(0).getStartPoint().distance(start2) < 0.1)
        {
            end2 = list2.get(0).getEndPoint();
        }
        else
        {
            end2 = list2.get(0).getStartPoint();
        }

        if (end1.distance(end2) < 0.1)
        {
            sq += findSq(start1, start2, end1);
            return sq;
        }
        else
        {
            sq += findSq(start1, start2, end1);
            sq += findSq(start2, end2, end1);
        }

        while (true)
        {
            startFigure1 = list1.get(0);
            startFigure2 = list2.get(0);

            start1 = end1;
            start2 = end2;

            list1 = startFigure(startFigure1, start1, element.getChildren());
            list2 = startFigure(startFigure2, start2, element.getChildren());

            if (list1.get(0) == list2.get(0))
            {
                break;
            }

            if (list1.get(0).getStartPoint().distance(start1) < 0.1)
            {
                end1 = list1.get(0).getEndPoint();
            }
            else
            {
                end1 = list1.get(0).getStartPoint();
            }

            if (list2.get(0).getStartPoint().distance(start2) < 0.1)
            {
                end2 = list2.get(0).getEndPoint();
            }
            else
            {
                end2 = list2.get(0).getStartPoint();
            }

            if (end1.distance(end2) < 0.1)
            {
                sq += findSq(start1, start2, end1);
                break;
            }
            else
            {
                sq += findSq(start1, start2, end1);
                sq += findSq(start2, end2, end1);
            }
        }

        return sq;
    }

    public static double findSq(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3)
    {
        return Math.abs(((p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) -
                (p2.getX() - p3.getX()) * (p1.getY() - p3.getY())) / 2);
    }

    public static boolean isIntersect(Figure figure, List<Figure> shape)
    {
        if (shape.contains(figure))
        {
            return true;
        }
        else if (shape.isEmpty())
        {
            return false;
        }

        for (Figure f : shape.subList(0, shape.size() - 1))
        {
            if (f.getStartPoint().distance(figure.getStartPoint()) < 0.1
                    || f.getStartPoint().distance(figure.getEndPoint()) < 0.1
                    || f.getEndPoint().distance(figure.getStartPoint()) < 0.1
                    || f.getEndPoint().distance(figure.getEndPoint()) < 0.1)
            {
                return true;
            }
        }
        return false;
    }

    public static List<Figure> startFigure(Figure f, Point2D.Double point, List<Figure> children)
    {
        List<Figure> list = new ArrayList<Figure>();

        for (Figure figure : children)
        {
            if ((figure.getStartPoint().distance(point) < 2 || figure.getEndPoint().distance(point) < 2)
                    && figure != f)
            {
                list.add(figure);
            }
        }
        return list;
    }

    /**
     * Все figures должны реализовывать ShapeProvider
     *
     * @param closedFigures
     * @return
     */
    public static GeneralPath closedShape(List<Figure> closedFigures)
    {
        if (closedFigures.size() < 1)
        {
            return null;
        }
        GeneralPath path = new GeneralPath();

        Figure f1 = closedFigures.get(0);
        path.append(((ShapeProvider) f1).getShape(), true);

        Point2D.Double end = f1.getEndPoint();
        for (int i = 1; i < closedFigures.size(); i++)
        {
            Figure f2 = closedFigures.get(i);
            Shape s2;
            if (end.distance(f2.getStartPoint()) > Constants.DEFAULT_JOIN_OFFSET)
            {
                s2 = ((ShapeProvider) f2).getInvertedStartEndShape();
                end = f1.getStartPoint();
            }
            else
            {
                s2 = ((ShapeProvider) f2).getShape();
                end = f1.getEndPoint();
            }
            path.append(s2, true);
        }
        return path;
    }
}
