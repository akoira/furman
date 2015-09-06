/*
 * @(#)RectangleFigure.java  2.3  2006-12-23
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */


package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.cutting.draw.MillingLength;
import by.dak.draw.Graphics2DUtils;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.GroupFigure;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ElementDrawing extends DefaultDrawing
{
    private Point2D.Double start = new Point2D.Double(0, 0); //начало отрисовки елемента

    private double offset = Graphics2DUtils.OFFSET;
    private Rectangle2D.Double elementRec = new Rectangle2D.Double();
    private Dimension2D element;
    private boolean cut = true;

    private List<CurveFigure> lines = null;
    private List<CurveFigure> defaultLines = null;
    private List<CurveFigure> allLines = null;

    @Override
    public void draw(Graphics2D g, Collection<Figure> children)
    {
        Color color = g.getColor();
        Stroke stroke = g.getStroke();

        g.setStroke(Graphics2DUtils.DIMENTION_BASIC_STROKE);
        g.setColor(Graphics2DUtils.DIMENTION_LINE_COLOR);
        g.draw(elementRec);

        Point2D.Double start = null;
        Point2D.Double end = null;

        if (this.children.size() == 0 && lines.size() != 0)
        {
            super.addAll(lines);
        }

        addToAllLines();
        if (cut)
        {
            for (Figure figure : this.children)
            {
                start = null;
                end = null;
                if (figure.getClass() == ArcFigure.class)
                {
                    ArcFigure arc = (ArcFigure) figure;
                    start = (Point2D.Double) arc.getArc().getStartPoint();
                    end = (Point2D.Double) arc.getArc().getEndPoint();
                }
                else if (figure.getClass() == ArcEveryFigure.class)
                {
                    ArcEveryFigure arc = (ArcEveryFigure) figure;
                    start = (Point2D.Double) arc.getArc().getStartPoint();
                    end = (Point2D.Double) arc.getArc().getEndPoint();
                }
                else if (figure.getClass() == CurveFigure.class)
                {
                    boolean out = false;
                    for (CurveFigure line : lines)
                    {
                        if (line == figure)
                        {
                            out = true;
                            break;

                        }
                        else if (((CurveFigure) figure).isCondition())
                        {
                            out = true;
                            break;

                        }

                    }
                    if (!out)
                    {
                        CurveFigure curve = (CurveFigure) figure;
                        start = (Point2D.Double) curve.getStartPoint().clone();
                        end = (Point2D.Double) curve.getEndPoint().clone();


                        if (start.equals(end))
                        {
                            start = null;
                        }
                    }
                }
                if (start != null)
                {
                    int i = isContainLines(start, figure);
                    int j = isContainLines(end, figure);

                    if (i != -1 && j != -1)

                    {
                        CutElement cut = new CutElement();
                        cut.setLine1(allLines.get(i));
                        cut.setPoint1(start);
                        cut.setLine2(allLines.get(j));
                        cut.setPoint2(end);
                        cut.cut();
                    }
                }
            }
        }
        g.setStroke(stroke);
        g.setColor(color);

        super.draw(g, this.children);
    }

    private void addToAllLines()
    {
        if (allLines == null)
        {
            return;
        }
        for (Figure figure : this.children)
        {
            if (figure instanceof CurveFigure)
            {
                if (allLines.contains(figure))
                {
                    defaultLines.set(allLines.indexOf(figure), createDefaultLine((CurveFigure) figure));
                }
                else
                {
                    allLines.add((CurveFigure) figure);
                    defaultLines.add(createDefaultLine((CurveFigure) figure));
                }
            }
        }
    }

    private int isContainLines(Point2D.Double start, Figure figure)
    {
        int j = allLines.indexOf(figure);
        for (int i = 0; i < defaultLines.size(); i++)
        {
            if (defaultLines.get(i).isContains(start) && i != j)
            {
                if (figure instanceof CurveFigure && i >= lines.size())
                {
                    continue;
                }
                return i;
            }
        }
        return -1;
    }

    public Rectangle2D.Double getDrawingArea()
    {
        Rectangle2D.Double d = super.getDrawingArea();
        d.add(elementRec);
        return d;
    }

    public void setElement(Dimension2D element)
    {
        Dimension2D d = element;
        this.element = element;
        elementRec.setFrame(start.getX(), start.getY(), element.getWidth(), element.getHeight());

        lines = new ArrayList<CurveFigure>();
        defaultLines = new ArrayList<CurveFigure>();
        allLines = new ArrayList<CurveFigure>();

        for (int i = 0; i < 4; i++)
        {
            lines.add(createDefaultLine(i));
        }
        firePropertyChange("element", d, element);
    }

    public CurveFigure createDefaultLine(int i)
    {
        CurveFigure line = new CurveFigure();
        switch (i)
        {
            case 0:
                line.setStartPoint(new Point2D.Double(start.x, start.y));
                line.setEndPoint(new Point2D.Double(element.getWidth() + start.x, start.y));
                line.setDirection(-1);
                break;
            case 1:
                line.setStartPoint(new Point2D.Double(element.getWidth() + start.x, start.y));
                line.setEndPoint(new Point2D.Double(element.getWidth() + start.x, element.getHeight() + start.y));
                line.setDirection(-1);
                break;
            case 2:
                line.setStartPoint(new Point2D.Double(element.getWidth() + start.x, element.getHeight() + start.y));
                line.setEndPoint(new Point2D.Double(start.x, element.getHeight() + start.y));
                line.setDirection(1);
                break;
            case 3:
                line.setStartPoint(new Point2D.Double(start.x, element.getHeight() + start.y));
                line.setEndPoint(new Point2D.Double(start.x, start.y));
                line.setDirection(-1);
                break;
        }
        return line;
    }

    private CurveFigure createDefaultLine(CurveFigure line)
    {
        Point2D.Double[] points = reCalcPoint(line);
        CurveFigure curve = new CurveFigure();
        curve.setStartPoint(points[0]);
        curve.setEndPoint(points[1]);
        return curve;
    }

    private Point2D.Double[] reCalcPoint(CurveFigure curve)
    {
        Point2D.Double point1 = curve.getStartPoint();
        Point2D.Double point2 = curve.getEndPoint();
        if (point1.getX() == point2.getX())
        {
            point1 = new Point2D.Double(point1.getX(), start.getY());
            point2 = new Point2D.Double(point2.getX(), start.getY() + element.getHeight());
        }
        else if (point1.getY() == point2.getY())
        {
            point1 = new Point2D.Double(start.getX(), point1.getY());
            point2 = new Point2D.Double(start.getX() + element.getWidth(), point1.getY());
        }

        return new Point2D.Double[]{point1, point2};
    }

    public Dimension2D getElement()
    {
        return this.element;
    }

    public double getOffset()
    {
        return offset;
    }

    public void setOffset(double offset)
    {
        this.offset = offset;
    }

    public double getMillingLength()
    {
        Calc calc = new Calc()
        {
            @Override
            public double getValue(Figure figure)
            {
                if (figure instanceof MillingLength)
                {
                    return ((MillingLength) figure).getMillingLength();
                }
                return 0D;
            }
        };
        return calc.processLength(getChildren());
    }

    public double getGlueingLength()
    {
        Calc calc = new Calc()
        {
            @Override
            public double getValue(Figure figure)
            {
                if (figure instanceof GluiengLength)
                {
                    return ((GluiengLength) figure).getGluiengLength();
                }
                return 0D;
            }
        };
        return calc.processLength(getChildren());
    }

    public List<CurveFigure> getLines()
    {
        return lines;
    }

    @Override
    public List<Figure> getChildren()
    {
        return this.children;
    }

    public void setCut(boolean cut)
    {
        this.cut = cut;
    }

    public boolean isMilling()
    {
        return !(getChildren().containsAll(lines) && getChildren().size() == lines.size());
    }


    public static abstract class Calc
    {
        public double processLength(java.util.List<Figure> figures)
        {
            double result = 0;
            for (Figure figure : figures)
            {
                if (figure instanceof GroupFigure)
                {
                    result += processLength(((GroupFigure) figure).getChildren());
                }
                else
                {
                    result += getValue(figure);
                }
            }
            return result;
        }

        public abstract double getValue(Figure figure);
    }

}