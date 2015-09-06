/*
 * @(#)EllipseFigure.java  2.4  2006-12-23
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

import by.dak.cutting.draw.Constants;
import by.dak.cutting.draw.MillingLength;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.glueing.Gluieng;
import by.dak.persistence.convert.Gluieng2StringConverter;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ArcFigure extends AbstractAttributedFigure implements MillingLength, Arc, ShapeProvider,
        GluiengLength, TextFigureRelated
{
    static final private double[] stroke = new double[]{5};   // длинна прерывистой линии
    static final private ArrowTip arrowTip = new ArrowTip(0.35, 12, 11.3); // конец стрелки
    static final private double approximation = 0.5;                      //  приближение для сетки
    static final private double strokeWidth = 1.8d; // ширина гланой линии
    private float fontSize = 0;

    protected Arc2D.Double arc = new Arc2D.Double();
    protected LineFigure line1 = new LineFigure();
    protected LineFigure line2 = new LineFigure();
    protected LineFigure lineArrow1 = new LineFigure();
    protected LineFigure lineArrow2 = new LineFigure();
    protected TextFigure textFigure1 = new TextFigure();
    protected TextFigure textFigure2 = new TextFigure();

    public ArcFigure()
    {
        AttributeKeys.END_DECORATION.set(lineArrow1, arrowTip);
        AttributeKeys.END_DECORATION.set(lineArrow2, arrowTip);
        AttributeKeys.STROKE_DASHES.set(line1, stroke);
        AttributeKeys.STROKE_DASHES.set(line2, stroke);
        AttributeKeys.STROKE_DASHES.set(lineArrow1, stroke);
        textFigure1.setFontSize(Constants.DEFAULT_FONT_SIZE);
        AttributeKeys.STROKE_DASHES.set(lineArrow2, stroke);
        textFigure2.setFontSize(Constants.DEFAULT_FONT_SIZE);
        AttributeKeys.STROKE_WIDTH.set(this, strokeWidth);
    }

    /**
     * Creates a new instance.
     */
    public ArcFigure(double angleStart, double angleExtent)
    {
        this();
        arc.setAngleStart(angleStart);
        arc.setAngleExtent(angleExtent);
    }

    // DRAWING
    // SHAPE AND BOUNDS
    // ATTRIBUTES
    // EDITING
    // CONNECTING

    public Arc2D.Double getArc()
    {
        return arc;
    }

    @Override
    public Connector findConnector(Point2D.Double p, ConnectionFigure prototype)
    {
        return null;
    }

    @Override
    public Connector findCompatibleConnector(Connector c, boolean isStartConnector)
    {
        return null;
    }

    // COMPOSITE FIGURES
    // CLONING
    // EVENT HANDLING

    public Rectangle2D.Double getBounds()
    {
        return (Rectangle2D.Double) arc.getBounds2D();
    }

    public Rectangle2D.Double getDrawingArea()
    {
        Rectangle2D.Double r = (Rectangle2D.Double) arc.getBounds2D();

        boolean isCircule = Math.abs(arc.getHeight() - arc.getWidth()) <= approximation;
        Rectangle2D.Double union = (Rectangle2D.Double) r.getBounds2D();
        if (isCircule)
        {
            union.add(createL1().getDrawingArea());
            union.add(createL2().getDrawingArea());
            union.add(createArrow().getDrawingArea());
            union.add(createText().getDrawingArea());
        }
        else
        {
            union.add(createArrow1().getDrawingArea());
            union.add(createArrow2().getDrawingArea());
            union.add(createText1().getDrawingArea());
            union.add(createText2().getDrawingArea());
        }
        return union;
    }

    protected void drawFill(Graphics2D g)
    {
//        Arc2D.Double r = create0();
//        if (r.width > 0 && r.height > 0)
//        {
//            g.fill(r);
//        }
    }

    private Arc2D.Double create0()
    {
        Arc2D.Double r = (Arc2D.Double) arc.clone();
        double grow = AttributeKeys.getPerpendicularFillGrowth(this);
        r.x -= grow;
        r.y -= grow;
        r.width += grow * 2;
        r.height += grow * 2;
        return r;
    }

    private LineFigure createL1()
    {
        LineFigure l = (LineFigure) line1.clone();
        l.setStartPoint(new Point2D.Double(arc.getStartPoint().getX(), arc.getStartPoint().getY()));
        l.setEndPoint(new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY()));
        return l;
    }

    private LineFigure createL2()
    {
        LineFigure l = (LineFigure) line1.clone();
        l.setStartPoint(new Point2D.Double(arc.getEndPoint().getX(), arc.getEndPoint().getY()));
        l.setEndPoint(new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY()));
        return l;
    }

    private LineFigure createArrow()
    {
        LineFigure lf = (LineFigure) lineArrow1.clone();
        lf.setStartPoint(new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY()));
        lf.setEndPoint(new Point2D.Double(getMiddleArc().getX(), getMiddleArc().getY()));
        return lf;
    }

    private LineFigure createArrow1()
    {
        LineFigure lf = (LineFigure) lineArrow1.clone();
        lf.setStartPoint(new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY()));
        lf.setEndPoint(new Point2D.Double(arc.getStartPoint().getX(), arc.getStartPoint().getY()));
        return lf;
    }

    private LineFigure createArrow2()
    {
        LineFigure lf = (LineFigure) lineArrow2.clone();
        lf.setStartPoint(new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY()));
        lf.setEndPoint(new Point2D.Double(arc.getEndPoint().getX(), arc.getEndPoint().getY()));
        return lf;
    }

    private TextFigure createText()
    {
        TextFigure text = textFigure1.clone();

        if (arc.getAngleStart() >= 90 && arc.getAngleStart() < 180 ||
                arc.getAngleStart() >= 270 && arc.getAngleStart() < 360)
        {
            text.setBounds(new Point2D.Double(getTextLocate().getX(), getTextLocate().getY() - text.getFontSize()), new Point2D.Double());
        }
        else
        {
            text.setBounds(new Point2D.Double(getTextLocate().getX(), getTextLocate().getY()), new Point2D.Double());
        }


        if (arc.getHeight() <= arc.getWidth())
        {
            text.setText(String.format("%.0f", arc.getHeight() / 2).replaceAll(",", "."));
        }
        else
        {
            text.setText(String.format("%.0f", arc.getWidth() / 2).replaceAll(",", "."));
        }

        if (getGluieng() == null)
        {
            text.setText(String.format("%.0f", arc.getHeight() / 2).replaceAll(",", "."));
        }
        else
        {
            text.setText(String.format("%.0f", arc.getHeight() / 2).replaceAll(",", ".") + "  " + new Gluieng2StringConverter().convert(getGluieng()));
        }

        return text;
    }

    private TextFigure createText1()
    {
        TextFigure text = textFigure1.clone();

        if (arc.getAngleStart() >= 0 && arc.getAngleStart() < 90)
        {
            text.setBounds(new Point2D.Double(arc.getStartPoint().getX() - arc.getWidth() / 2, arc.getStartPoint().getY() - arc.getHeight() / 4), new Point2D.Double());
        }
        else if (arc.getAngleStart() >= 90 && arc.getAngleStart() < 180)
        {
            text.setBounds(new Point2D.Double(arc.getStartPoint().getX() - text.getBounds().getWidth(), arc.getStartPoint().getY() + arc.getHeight() / 4), new Point2D.Double());
        }
        else if (arc.getAngleStart() >= 180 && arc.getAngleStart() < 270)
        {
            text.setBounds(new Point2D.Double(arc.getStartPoint().getX() + arc.getWidth() / 2 - text.getBounds().getWidth(), arc.getStartPoint().getY() + arc.getHeight() / 4), new Point2D.Double());
        }
        else
        {
            text.setBounds(new Point2D.Double(arc.getStartPoint().getX(), arc.getStartPoint().getY() - arc.getHeight() / 4), new Point2D.Double());
        }
        if (getGluieng() == null)
        {
            text.setText(String.format("%.0f", arc.getHeight() / 2).replaceAll(",", "."));
        }
        else
        {
            text.setText(String.format("%.0f", arc.getHeight() / 2).replaceAll(",", ".") + "  " + new Gluieng2StringConverter().convert(getGluieng()));
        }

        return text;
    }

    private TextFigure createText2()
    {
        TextFigure text = textFigure2.clone();

        if (arc.getAngleStart() >= 0 && arc.getAngleStart() < 90)
        {
            text.setBounds(new Point2D.Double(arc.getStartPoint().getX() - arc.getWidth() / 4, arc.getStartPoint().getY() - text.getBounds().getHeight()), new Point2D.Double());
        }
        else if (arc.getAngleStart() >= 90 && arc.getAngleStart() < 180)
        {
            text.setBounds(new Point2D.Double(arc.getStartPoint().getX() - arc.getWidth() / 4, arc.getStartPoint().getY() + arc.getHeight() / 2 - text.getBounds().getHeight()), new Point2D.Double());
        }
        else if (arc.getAngleStart() >= 180 && arc.getAngleStart() < 270)
        {
            text.setBounds(new Point2D.Double(arc.getStartPoint().getX() + arc.getWidth() / 4, arc.getStartPoint().getY()), new Point2D.Double());
        }
        else
        {
            text.setBounds(new Point2D.Double(arc.getStartPoint().getX() + arc.getWidth() / 4, arc.getStartPoint().getY() - arc.getHeight() / 2), new Point2D.Double());
        }

        text.setText(String.format("%.0f", arc.getWidth() / 2).replaceAll(",", "."));

        return text;
    }

    protected void drawStroke(Graphics2D g)
    {
        Color color = g.getColor();
        Stroke stroke = g.getStroke();
        checkForChangedFontSize();
        Arc2D.Double r = create0();

        boolean isCircule = Math.abs(arc.getHeight() - arc.getWidth()) <= approximation;

        LineFigure l1 = null;
        LineFigure l2 = null;
        LineFigure a = null;
        LineFigure b = null;
        TextFigure text1 = null;
        TextFigure text2 = null;
        if (isCircule)
        {
            l1 = createL1();
            l2 = createL2();
            a = createArrow();
            text1 = createText();
        }
        else
        {
            a = createArrow1();
            b = createArrow2();
            text1 = createText1();
            text2 = createText2();
        }
        GluiengAttributeDraw gluieng = new GluiengAttributeDraw(this);
        gluieng.draw(g);
        if (r.width > 0 && r.height > 0)
        {
            g.draw(r);
            a.draw(g);
            text1.draw(g);
            if (isCircule)
            {
                l1.draw(g);
                l2.draw(g);
            }
            else
            {
                b.draw(g);
                text2.draw(g);
            }
        }
        g.setStroke(stroke);
        g.setColor(color);


    }

    private void checkForChangedFontSize()
    {
        for (TextFigure textFigure : getTextFigures())
        {
            if (fontSize != textFigure.getFontSize())
            {
                if (fontSize != 0 && textFigure.getFontSize() == Constants.DEFAULT_FONT_SIZE)
                {
                    textFigure.setFontSize(fontSize);
                }
            }
            setFontSize(textFigure.getFontSize());
        }
    }

    /**
     * Checks if a Point2D.Double is inside the figure.
     */
    public boolean contains(Point2D.Double p)
    {
        Arc2D.Double r = create0();

        boolean isCircule = Math.abs(arc.getHeight() - arc.getWidth()) <= approximation;
        LineFigure l1 = null;
        LineFigure l2 = null;
        LineFigure a = null;
        LineFigure b = null;
        TextFigure text1 = null;
        TextFigure text2 = null;

        if (isCircule)
        {
            l1 = createL1();
            l2 = createL2();
            a = createArrow();
            text1 = createText();
            if (r.contains(p) || l1.contains(p) || l2.contains(p) || a.contains(p) || text1.contains(p))
            {
                return true;
            }
        }
        else
        {
            a = createArrow1();
            b = createArrow2();
            text1 = createText1();
            text2 = createText2();
            if (r.contains(p) || a.contains(p) || b.contains(p) || text1.contains(p) || text2.contains(p))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead)
    {
        double w = Math.max(0.1, Math.abs(lead.x - anchor.x));
        double h = Math.max(0.1, Math.abs(lead.y - anchor.y));
        double x = Math.min(anchor.x, lead.x);
        double y = Math.min(anchor.y, lead.y);
        if (arc.getAngleStart() == 0)
        {
            x = x - w;
        }
        else if (arc.getAngleStart() == 180)
        {
            y = y - h;
        }
        else if (arc.getAngleStart() == 270)
        {
            x = x - w;
            y = y - h;
        }
        arc.setFrame(x, y, w * 2d, h * 2d);
    }

    /**
     * Transforms the figure.
     *
     * @param tx the transformation.
     */
    public void transform(AffineTransform tx)
    {
        if (!(hasAttribute(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.MOVEABLE) &&
                !get(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.MOVEABLE).booleanValue()))
        {
            Point2D.Double anchor = getStartPoint();
            Point2D.Double lead = getEndPoint();
            setBounds(
                    (Point2D.Double) tx.transform(anchor, anchor),
                    (Point2D.Double) tx.transform(lead, lead)
            );
        }
    }

    @Override
    public ArcFigure clone()
    {
        ArcFigure that = (ArcFigure) super.clone();
        that.arc = (Arc2D.Double) this.arc.clone();
        that.textFigure1 = (TextFigure) this.textFigure1.clone();
        that.textFigure2 = (TextFigure) this.textFigure2.clone();
        that.line1 = (LineFigure) this.line1.clone();
        that.line2 = (LineFigure) this.line2.clone();
        that.lineArrow1 = (LineFigure) this.lineArrow1.clone();
        that.lineArrow2 = (LineFigure) this.lineArrow2.clone();

        return that;
    }

    public void restoreTransformTo(Object geometry)
    {
        Arc2D.Double r = (Arc2D.Double) geometry;
        arc.x = r.x;
        arc.y = r.y;
        arc.width = r.width;
        arc.height = r.height;
        arc.start = r.start;
        arc.extent = r.extent;
    }

    public Object getTransformRestoreData()
    {
        return arc.clone();
    }

    @Override
    public void write(DOMOutput out) throws IOException
    {
        out.addAttribute("x", arc.x);
        out.addAttribute("y", arc.y);
        out.addAttribute("width", arc.width);
        out.addAttribute("height", arc.height);
        out.addAttribute("start", arc.start);
        out.addAttribute("extent", arc.extent);
        out.addAttribute("fontSize", fontSize);
        writeAttributes(out);
    }

    @Override
    public void read(DOMInput in) throws IOException
    {
        arc.x = in.getAttribute("x", 0d);
        arc.y = in.getAttribute("y", 0d);
        arc.width = in.getAttribute("width", 0d);
        arc.height = in.getAttribute("height", 0d);
        arc.start = in.getAttribute("start", 0d);
        arc.extent = in.getAttribute("extent", 0d);
        this.fontSize = in.getAttribute("fontSize", 12);
        readAttributes(in);
    }

    private Point2D getCenterPoint()
    {
        return new Point2D.Double(arc.getCenterX(), arc.getCenterY());
    }

    public Point2D.Double getMiddleArc()
    {
        return new Point2D.Double(getCenterPoint().getX() +
                Math.cos(Math.toRadians(arc.getAngleStart() + arc.getAngleExtent() / 2)) * arc.getHeight() / 2,
                getCenterPoint().getY() -
                        Math.sin(Math.toRadians(arc.getAngleStart() + arc.getAngleExtent() / 2)) * arc.getHeight() / 2);
    }

    private Point2D getTextLocate()
    {
        Point2D point2D;
        if (arc.getHeight() <= arc.getWidth())
        {
            point2D = new Point2D.Double(getCenterPoint().getX() +
                    Math.cos(Math.toRadians(arc.getAngleStart() + arc.getAngleExtent() / 2)) * arc.getHeight() / 4,
                    getCenterPoint().getY() -
                            Math.sin(Math.toRadians(arc.getAngleStart() + arc.getAngleExtent() / 2)) * arc.getHeight() / 4);
        }
        else
        {
            point2D = new Point2D.Double(getCenterPoint().getX() +
                    Math.cos(Math.toRadians(arc.getAngleStart() + arc.getAngleExtent() / 2)) * arc.getWidth() / 4,
                    getCenterPoint().getY() -
                            Math.sin(Math.toRadians(arc.getAngleStart() + arc.getAngleExtent() / 2)) * arc.getWidth() / 4);
        }
        return point2D;
    }

    @Override
    public double getMillingLength()
    {
        final double x = Math.log(2) / Math.log(Math.PI / 2);
        double a = Math.pow(arc.height / 2, x);
        double b = Math.pow(arc.width / 2, x);
        return 4 * (Math.pow(a + b, 1 / x));
    }

    @Override
    public Point2D.Double getStartPoint()
    {
        return (Point2D.Double) arc.getStartPoint();
    }

    @Override
    public Point2D.Double getEndPoint()
    {
        return (Point2D.Double) arc.getEndPoint();
    }

    public void setArc(Arc2D.Double arc)
    {
        this.arc = arc;
    }

    @Override
    public Collection<Handle> createHandles(int detailLevel)
    {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        if (hasAttribute(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.MOVEABLE) &&
                !get(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.MOVEABLE).booleanValue())
        {
            switch (detailLevel)
            {
                case -1:
                    handles.add(new BoundsOutlineHandle(this, false, true));
                    break;
                case 0:
                    handles.add(new BoundsOutlineHandle(this));
                    break;
            }
        }
        else
        {
            handles.addAll(super.createHandles(detailLevel));
        }
        return handles;
    }

    @Override
    public Shape getShape()
    {
        return arc;
    }

    public Gluieng getGluieng()
    {
        return this.get(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.GLUEING);
    }

    public void setGluieng(Gluieng gluieng)
    {
        this.set(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.GLUEING, gluieng);
    }

    @Override
    public double getGluiengLength()
    {
        return getMillingLength();
    }


    @Override
    public java.util.List<TextFigure> getTextFigures()
    {
        List<TextFigure> textFigures = new ArrayList<TextFigure>();
        textFigures.add(textFigure1);
        textFigures.add(textFigure2);

        return textFigures;
    }

    public float getFontSize()
    {
        return fontSize;
    }

    public void setFontSize(float fontSize)
    {
        this.fontSize = fontSize;
    }

    @Override
    public Shape getInvertedStartEndShape()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}