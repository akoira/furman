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
import org.jhotdraw.draw.locator.RelativeLocator;
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

import static by.dak.cutting.draw.Constants.DEFAULT_FONT_SIZE;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 21.07.2009
 * Time: 16:51:06
 * To change this template use File | Settings | File Templates.
 */
public class ArcEveryFigure extends AbstractAttributedFigure implements MillingLength, Arc, ShapeProvider, TextFigureRelated, GluiengLength
{
    static final private double[] stroke = new double[]{5};   // длинна прерывистой линии
    static final private ArrowTip arrowTip = new ArrowTip(0.35, 12, 11.3); // конец стрелки
    static final private double indent = 8;   //  отступ от одной дуги до другой, для обозначения начального угла
    static final private double angle = 360;   //угол для рассчёта длины дуги
    private float fontSize = 0;

    protected Arc2D.Double arc = new Arc2D.Double();   // основная дуга
    protected Arc2D.Double arc1 = new Arc2D.Double();   //для обозначения угла начального
    protected Arc2D.Double arc2 = new Arc2D.Double();      //   для обозначения угла extent
    protected Arc2D.Double arc22 = new Arc2D.Double();    //   для обозначения угла extent
    protected LineFigure line1 = new LineFigure();
    protected LineFigure line2 = new LineFigure();
    protected LineFigure line3 = new LineFigure();
    protected LineFigure lineArrow1 = new LineFigure();
    protected TextFigure textFigure = new TextFigure();
    protected TextFigure textFigure1 = new TextFigure();
    protected TextFigure textFigure2 = new TextFigure();

    public ArcEveryFigure()
    {
        AttributeKeys.END_DECORATION.set(lineArrow1, arrowTip);
        AttributeKeys.STROKE_DASHES.set(line1, stroke);
        AttributeKeys.STROKE_DASHES.set(line2, stroke);
        AttributeKeys.STROKE_DASHES.set(line3, stroke);
        AttributeKeys.STROKE_DASHES.set(lineArrow1, stroke);
        textFigure.setFontSize(DEFAULT_FONT_SIZE);
        textFigure1.setFontSize(DEFAULT_FONT_SIZE);
        textFigure2.setFontSize(DEFAULT_FONT_SIZE);
    }

    public ArcEveryFigure(double angleStart, double angleExtent)
    {
        this();
        arc.setAngleStart(angleStart);
        arc.setAngleExtent(angleExtent);
    }

    @Override
    protected void drawFill(Graphics2D graphics2D)
    {
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

    private void create0()
    {
        double grow = AttributeKeys.getPerpendicularFillGrowth(this);
        arc.x -= grow;
        arc.y -= grow;
        arc.width += grow * 2;
        arc.height += grow * 2;
    }

    private void createArc1()
    {
        arc1.setAngleStart(arc.getAngleStart());
        arc1.setAngleExtent(arc.getAngleExtent());
        double h = arc.getHeight() / 4;
        arc1.setFrame(getCenterPoint().getX() - h / 2, getCenterPoint().getY() - h / 2, h, h);
    }

    private void createArc2()
    {
        arc2.setAngleStart(0);
        arc2.setAngleExtent(arc.getAngleStart() + arc.getAngleExtent() / 2);
        double h = arc.getHeight() / 2;
        arc2.setFrame(getCenterPoint().getX() - h / 2, getCenterPoint().getY() - h / 2, h, h);

        arc22.setAngleStart(0);
        arc22.setAngleExtent(arc.getAngleStart() + arc.getAngleExtent() / 2);
        h = arc.getHeight() / 2 + indent;
        arc22.setFrame(getCenterPoint().getX() - h / 2, getCenterPoint().getY() - h / 2, h, h);
    }

    private void createL1()
    {
        line1.setStartPoint(new Point2D.Double(arc.getStartPoint().getX(), arc.getStartPoint().getY()));
        line1.setEndPoint(new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY()));
    }

    private void createL2()
    {
        line2.setStartPoint(new Point2D.Double(arc.getEndPoint().getX(), arc.getEndPoint().getY()));
        line2.setEndPoint(new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY()));
    }

    private void createArrow()
    {
        lineArrow1.setStartPoint(new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY()));
        lineArrow1.setEndPoint(new Point2D.Double(getMiddleArc().getX(), getMiddleArc().getY()));
    }

    private void createL3()
    {
        line3.setStartPoint(new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY()));
        line3.setEndPoint(new Point2D.Double(getCenterPoint().getX() + arc.getHeight() / 2 * 13 / 20, getCenterPoint().getY()));
    }

    private void createText()
    {
        if (arc.getAngleStart() >= 90 && arc.getAngleStart() < 180 ||
                arc.getAngleStart() >= 270 && arc.getAngleStart() < 360)
        {
            textFigure.setBounds(new Point2D.Double(getTextLocate().getX(), getTextLocate().getY() - textFigure.getFontSize()), new Point2D.Double());
        }
        else
        {
            textFigure.setBounds(new Point2D.Double(getTextLocate().getX(), getTextLocate().getY()), new Point2D.Double());
        }


        if (arc.getHeight() <= arc.getWidth())
        {
            if (getGluieng() == null)
            {
                textFigure.setText(String.format("%.0f", arc.getHeight() / 2).replaceAll(",", "."));
            }
            else
            {
                textFigure.setText(String.format("%.0f", arc.getHeight() / 2).replaceAll(",", ".") + "  " + new Gluieng2StringConverter().convert(getGluieng()));
            }
        }
        else
        {
            if (getGluieng() == null)
            {
                textFigure.setText(String.format("%.0f", arc.getWidth() / 2).replaceAll(",", "."));
            }
            else
            {
                textFigure.setText(String.format("%.0f", arc.getWidth() / 2).replaceAll(",", ".") + "  " + new Gluieng2StringConverter().convert(getGluieng()));
            }
        }
    }

    private void createText1()
    {
        textFigure1.setBounds(new Point2D.Double(getCenterPoint().getX() +
                Math.cos(Math.toRadians(arc.getAngleStart() + arc.getAngleExtent() * 3 / 4)) * arc.getHeight() / 2 * 7 / 20,
                getCenterPoint().getY() -
                        Math.sin(Math.toRadians(arc.getAngleStart() + arc.getAngleExtent() * 3 / 4)) * arc.getHeight() / 2 * 7 / 20), new Point2D.Double());

        textFigure1.setText(String.format("%.0f", arc.getAngleExtent()).replaceAll(",", "."));
    }

    private void createText2()
    {
        textFigure2.setBounds(new Point2D.Double(getCenterPoint().getX() +
                Math.cos(Math.toRadians((arc.getAngleStart() + arc.getAngleExtent() / 2) * 3 / 4)) * arc.getHeight() / 2 * 3 / 5,
                getCenterPoint().getY() -
                        Math.sin(Math.toRadians((arc.getAngleStart() + arc.getAngleExtent() / 2) * 3 / 4)) * arc.getHeight() / 2 * 3 / 5), new Point2D.Double());

        textFigure2.setText(String.format("%.0f", arc.getAngleStart() + arc.getAngleExtent() / 2).replaceAll(",", "."));
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

    @Override
    protected void drawStroke(Graphics2D g)
    {
        Color color = g.getColor();
        Stroke stroke = g.getStroke();
        checkForChangedFontSize();
        create0();

        createArc1();
        createArc2();

        createL1();
        createL2();
        createL3();

        createArrow();
        createText();
        createText1();
        createText2();

        GluiengAttributeDraw gluieng = new GluiengAttributeDraw(this);
        gluieng.draw(g);

        if (arc.width > 0 && arc.height > 0)
        {

            g.draw(arc);

            g.setStroke(Constants.DEFAULT_DUSH_STROKE);
            g.draw(arc1);

            g.setStroke(new BasicStroke(1f));
//            g.draw(arc2);
//            g.draw(arc22);

            lineArrow1.draw(g);
            textFigure.draw(g);
            textFigure1.draw(g);
//            textFigure2.draw(g);

            line1.draw(g);
            line2.draw(g);
//            line3.draw(g);
        }
        g.setStroke(stroke);
        g.setColor(color);
    }

    /**
     * Checks if a Point2D.Double is inside the figure.
     */
    public boolean contains(Point2D.Double p)
    {
        if (arc.contains(p) || arc1.contains(p) || line1.contains(p)
                || line2.contains(p) || lineArrow1.contains(p)
                || textFigure.contains(p) || textFigure1.contains(p))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public Rectangle2D.Double getBounds()
    {
        return (Rectangle2D.Double) arc.getBounds2D();
    }


    public void setBounds(Point2D.Double anchor, Point2D.Double lead)
    {
        double x1 = anchor.x;
        double y1 = anchor.y;
        double x2 = lead.x;
        double y2 = lead.y;

        double xc, yc, x11 = 0, y11 = 0;


        double d = anchor.distance(lead);
        double l = Math.sin(Math.PI / 3) * d;

        xc = x1 + (x2 - x1) / 2;
        yc = y1 + (y2 - y1) / 2;

        double angle = Math.atan(Math.abs(y1 - y2) / Math.abs(x1 - x2));
        double angleStart = 0;
        if (x2 >= x1 && y2 >= y1)
        {
            x11 = xc - Math.sin(angle) * l;
            y11 = yc + Math.cos(angle) * l;
            angleStart = Math.atan(Math.abs(y2 - y11) / Math.abs(x2 - x11));
            if (y2 > y11)
            {
                angleStart *= -1;
            }
        }
        else if (x2 <= x1 && y2 <= y1)
        {
            x11 = xc + Math.sin(angle) * l;
            y11 = yc - Math.cos(angle) * l;

            angleStart = Math.PI + Math.atan(Math.abs(y2 - y11) / Math.abs(x2 - x11));
            if (y2 < y11)
            {
                angleStart = Math.PI - Math.atan(Math.abs(y2 - y11) / Math.abs(x2 - x11));
            }
        }
        else if (x2 < x1 && y2 >= y1)
        {
            x11 = xc + Math.sin(angle) * l;
            y11 = yc + Math.cos(angle) * l;
            angleStart = Math.PI / 6 + Math.atan(Math.abs(x2 - x11) / Math.abs(y2 - y11));
            if (y2 > y11)
            {
                angleStart = 2 * Math.PI / 3 + Math.atan(Math.abs(y2 - y11) / Math.abs(x2 - x11));
            }
        }
        else if (x2 > x1 && y2 <= y1)
        {
            x11 = xc - Math.sin(angle) * l;
            y11 = yc - Math.cos(angle) * l;

            angleStart = -Math.PI / 3 - Math.atan(Math.abs(y2 - y11) / Math.abs(x2 - x11));
            if (y2 < y11)
            {
                angleStart = -Math.PI / 3 + Math.atan(Math.abs(y2 - y11) / Math.abs(x2 - x11));
            }
        }

        arc.setAngleStart(Math.toDegrees(angleStart));

        double x = x11 - d;
        double y = y11 - d;

        arc.setFrame(x, y, d * 2, d * 2);
    }

    public Rectangle2D.Double getDrawingArea()
    {

        if (arc.getWidth() != 0 || arc.getHeight() != 0)
        {
            createL1();
            createL2();
            createL3();
            createArrow();
        }

        Rectangle2D.Double r = (Rectangle2D.Double) arc.getBounds2D();

        Rectangle2D.Double union = (Rectangle2D.Double) r.getBounds2D();

        union.add(arc1.getBounds2D());
        union.add(arc2.getBounds2D());
        union.add(arc22.getBounds2D());
        union.add(lineArrow1.getBounds());
        union.add(textFigure.getDrawingArea());
        union.add(textFigure1.getDrawingArea());
        union.add(textFigure2.getDrawingArea());
        union.add(line1.getBounds());
        union.add(line2.getBounds());
        union.add(line3.getBounds());

        return union;
    }

    @Override
    public ArcEveryFigure clone()
    {
        ArcEveryFigure that = (ArcEveryFigure) super.clone();
        that.arc = (Arc2D.Double) this.arc.clone();
        that.line1 = (LineFigure) this.line1.clone();
        that.line2 = (LineFigure) this.line2.clone();
        that.line3 = (LineFigure) this.line3.clone();
        that.lineArrow1 = (LineFigure) this.lineArrow1.clone();
        that.arc1 = (Arc2D.Double) this.arc1.clone();
        that.arc2 = (Arc2D.Double) this.arc2.clone();
        that.arc22 = (Arc2D.Double) this.arc22.clone();
        that.textFigure = (TextFigure) this.textFigure.clone();
        that.textFigure1 = (TextFigure) this.textFigure1.clone();
        that.textFigure2 = (TextFigure) this.textFigure2.clone();

        return that;
    }


    @Override
    public Object getTransformRestoreData()
    {
        return arc.clone();
    }

    @Override
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

    @Override
    public void transform(AffineTransform tx)
    {
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


    public Point2D.Double getCenterPoint()
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

        return new Point2D.Double(getCenterPoint().getX() +
                Math.cos(Math.toRadians(arc.getAngleStart() + arc.getAngleExtent() / 2)) * arc.getHeight() / 2 * 7 / 10,
                getCenterPoint().getY() -
                        Math.sin(Math.toRadians(arc.getAngleStart() + arc.getAngleExtent() / 2)) * arc.getHeight() / 2 * 7 / 10);
    }

    @Override
    public Collection<Handle> createHandles(int detailLevel)
    {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        if ((hasAttribute(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.MOVEABLE) &&
                !get(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.MOVEABLE).booleanValue()))
        {
            switch (detailLevel)
            {
                case -1:
                    handles.add(new OutlineHandle(this, false, true));
                    break;
                case 0:
                    handles.add(new OutlineHandle(this));
                    break;
            }
        }
        else
        {
            handles.add(new RotateArcHandle(this));
            handles.add(new DragArcHandle(this));
            handles.add(new RotateArcAngleStartHandle(this));
            handles.add(new RotateArcAngleExtentHandle(this));
            handles.add(new ResizeArcHandle(this, new RelativeLocator(0.5f, 0.5f)));
        }
        return handles;
    }


    @Override
    public double getMillingLength()
    {
        double r = this.line1.getStartPoint().distance(this.line1.getEndPoint());
        return 2 * Math.PI * r * this.getArc().extent / angle;
    }

    @Override
    public double getGluiengLength()
    {
        return getMillingLength();
    }

    public void setArc(Arc2D.Double arc)
    {
        this.arc = arc;
    }


    public Arc2D.Double getArc()
    {
        return arc;
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

    public Gluieng getGluieng()
    {
        return this.get(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.GLUEING);
    }

    public void setGluieng(Gluieng gluieng)
    {
        this.set(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.GLUEING, gluieng);
    }

    @Override
    public Shape getShape()
    {
        return arc;
    }

    @Override
    public Shape getInvertedStartEndShape()
    {
        Arc2D.Double inverted = new Arc2D.Double(arc.getX(), arc.getY(), arc.getWidth(), arc.getHeight(),
                arc.getAngleStart() + arc.getAngleExtent(), -arc.getAngleExtent(), Arc2D.OPEN);
        return inverted;
    }

    @Override
    public java.util.List<TextFigure> getTextFigures()
    {
        List<TextFigure> textFigures = new ArrayList<TextFigure>();
        textFigures.add(textFigure1);
        textFigures.add(textFigure2);
        textFigures.add(textFigure);

        return textFigures;
    }

    public class OutlineHandle extends BoundsOutlineHandle
    {
        public OutlineHandle(Figure owner)
        {
            super(owner);
        }

        public OutlineHandle(Figure owner, boolean isTransformHandle, boolean isHoverHandle)
        {
            super(owner, isTransformHandle, isHoverHandle);
        }

        public OutlineHandle(Figure owner, AttributeKey<Stroke> stroke1Enabled, AttributeKey<Color> strokeColor1Enabled, AttributeKey<Stroke> stroke2Enabled, AttributeKey<Color> strokeColor2Enabled, AttributeKey<Stroke> stroke1Disabled, AttributeKey<Color> strokeColor1Disabled, AttributeKey<Stroke> stroke2Disabled, AttributeKey<Color> strokeColor2Disabled)
        {
            super(owner, stroke1Enabled, strokeColor1Enabled, stroke2Enabled, strokeColor2Enabled, stroke1Disabled, strokeColor1Disabled, stroke2Disabled, strokeColor2Disabled);
        }

        @Override
        public void draw(Graphics2D g)
        {
            //drawShapeBounds(g, getShape());
        }
    }

    public float getFontSize()
    {
        return fontSize;
    }

    public void setFontSize(float fontSize)
    {
        this.fontSize = fontSize;
    }

}
