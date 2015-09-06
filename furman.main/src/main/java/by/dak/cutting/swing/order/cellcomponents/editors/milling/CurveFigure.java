package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.cutting.draw.Constants;
import by.dak.cutting.draw.MillingLength;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.glueing.Gluieng;
import by.dak.draw.ShowDimention;
import by.dak.persistence.convert.Gluieng2StringConverter;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.LineFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.handle.BezierOutlineHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.locator.BezierPointLocator;
import org.jhotdraw.draw.locator.RelativeLocator;
import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: akoyro
 * Date: 15.07.2009
 * Time: 18:11:23
 */
public class CurveFigure extends LineFigure implements MillingLength, ShapeProvider,
        GluiengLength, TextFigureRelated, ShowDimention
{
    private LineFigure line1 = new LineFigure(); //штрихованная линия
    private LineFigure line2 = new LineFigure(); //дополнитнльная линия к штриховой
    private LineFigure line3 = new LineFigure(); //вторая дополнитнльная линия к штриховой
    private TextFigure textFigure = new TextFigure();
    private double angle;  //угол для построения штриховой линии
    private final double shift = 20; //сдвиг линии относительно главной линии(super)

    private int direction = -1; //расположенность штриховой линии
    private boolean isGeneralLine = true; //с главной (super) линиией (просто прямая линия)
    private final double strokeWidth = 1.8d; // ширина гланой линии
    private double moving = 1;
    private float fontSize = 0;
    private boolean isShowDimention = false;

    public CurveFigure()
    {
        applyAttributes();
        AttributeKeys.STROKE_WIDTH.set(this, strokeWidth);
    }

    public CurveFigure(boolean curve)
    {
        if (curve)                   // если true отрисовываем кривую, а если false отрисовываем разметку
        {
            removeNode(0);
            removeNode(0);

            BezierPath.Node node = new BezierPath.Node(new Point2D.Double(0, 0));
            node.setControlPoint(0, new Point2D.Double(0, 0));
            node.setMask(BezierPath.C2_MASK);
            addNode(0, node);

            node = new BezierPath.Node(new Point2D.Double(0, 0));
            node.setControlPoint(0, new Point2D.Double(0, 0));
            node.setMask(BezierPath.C1C2_MASK);
            addNode(1, node);
        }
        else
        {
            isGeneralLine = false;
        }
        applyAttributes();
    }

    @Override
    public double getMillingLength()
    {
        //todo для кривой
        double result = 0;
        int count = getNodeCount();

        if (count > 2)            // если ломанная
        {
            for (int i = 1; i < count; i++)
            {
                result += getNode(i - 1).getControlPoint(0).distance(getNode(i).getControlPoint(0));
            }
        }
        else if (count == 2)       // если прямая
        {
            result = super.getStartPoint().distance(super.getEndPoint());
        }
        return result;
    }

    @Override
    protected void drawStroke(Graphics2D g)
    {
        Color color = g.getColor();
        Stroke stroke = g.getStroke();

        GluiengAttributeDraw gluieng = new GluiengAttributeDraw(this);
        gluieng.draw(g);

        if (isGeneralLine == true)
        {
            super.drawStroke(g);
        }

        drawDimension(g);
        g.setStroke(stroke);
        g.setColor(color);
    }

    private void drawDimension(Graphics2D g)
    {
        if (isShowDimention())
        {
            calcElements();
            line1.draw(g);
            line2.draw(g);
            line3.draw(g);
            textFigure.draw(g);
        }
    }

    @Override
    public BezierFigure clone()
    {
        CurveFigure that = (CurveFigure) super.clone();
        that.line1 = (LineFigure) this.line1.clone();
        that.line2 = (LineFigure) this.line2.clone();
        that.line3 = (LineFigure) this.line3.clone();
        that.textFigure = (TextFigure) this.textFigure.clone();
        return that;
    }


    private void calcElements()
    {
        if (getNodeCount() > 1)
        {

            Point2D.Double startPoint = super.getStartPoint();
            Point2D.Double endPoint = super.getEndPoint();
            if (endPoint.distance(startPoint) > 0)
            {
                angle = Math.atan((startPoint.getY() - endPoint.getY()) / (endPoint.getX() - startPoint.getX()));

                Point2D.Double p0 = new Point2D.Double(startPoint.getX() + direction * Math.cos(Math.PI / 2 - angle) * shift,
                        startPoint.getY() + direction * Math.sin(Math.PI / 2 - angle) * shift);
                Point2D.Double p1 = new Point2D.Double(endPoint.getX() + direction * Math.cos(Math.PI / 2 - angle) * shift,
                        endPoint.getY() + direction * Math.sin(Math.PI / 2 - angle) * shift);
                Point2D.Double pC = new Point2D.Double(super.getCenter().getX() + direction * Math.cos(Math.PI / 2 - angle) * 2 * shift,
                        super.getCenter().getY() + direction * Math.sin(Math.PI / 2 - angle) * 2 * shift);
                Point2D.Double p4 = new Point2D.Double(endPoint.getX() + direction * Math.cos(Math.PI / 2 - angle) * 2 * shift,
                        endPoint.getY() + direction * Math.sin(Math.PI / 2 - angle) * 2 * shift);

                if (line1 != null)
                {
                    removeNodes(line1);
                    line1.setStartPoint(p0);
                    line1.setEndPoint(p1);
                }


                if (line2 != null)
                {
                    removeNodes(line2);
                    line2.setStartPoint(super.getStartPoint());
                    line2.setEndPoint(p0);
                }

                if (line3 != null)
                {
                    removeNodes(line3);
                    line3.setStartPoint(super.getEndPoint());
                    line3.setEndPoint(p1);
                }

                calcText(pC, p4, p0, p1);
            }
            else
            {
                Point2D.Double p1 = super.getStartPoint();
                line1.setStartPoint(p1);
                line1.setEndPoint(p1);
                line2.setStartPoint(p1);
                line2.setEndPoint(p1);
                line3.setStartPoint(p1);
                line3.setEndPoint(p1);
                textFigure.setText(null);
            }
        }
    }


    @Override
    public boolean contains(Point2D.Double p)
    {
        if ((super.contains(p) || line1.contains(p) || line2.contains(p) || line3.contains(p) ||
                textFigure.contains(p)) && (super.getEndPoint().distance(super.getStartPoint()) > 0))
        {
            return true;
        }
        return false;
    }

    public boolean isContains(Point2D.Double p) //содержит ли главную линию
    {
        return super.contains(p);
    }

    private void removeNodes(LineFigure figure)
    {
        int count = figure.getNodeCount();
        for (int i = 0; i < count; i++)
        {
            figure.removeNode(figure.getNodeCount() - 1);
        }
    }

    private void applyAttributes()
    {
        AttributeKeys.STROKE_DASHES.set(line1, new double[]{10});
        AttributeKeys.END_DECORATION.set(line1, new ArrowTip());
        AttributeKeys.START_DECORATION.set(line1, new ArrowTip());

    }

    private void calcText(Point2D.Double pC, Point2D.Double p4, Point2D.Double p0, Point2D.Double p1)
    {
        double length = getMillingLength();
        if (getGluieng() != null)
        {
            textFigure.setText(String.valueOf((int) length) + "  " + new Gluieng2StringConverter().convert(getGluieng()));
        }
        else
        {
            textFigure.setText(String.valueOf((int) length));
        }

        textFigure.setBounds(pC, p4);
        while (textFigure.getBounds().intersectsLine(new Line2D.Double(p0, p1)) ||                  //если textfigure пересекается с приямой
                textFigure.getBounds().intersectsLine(new Line2D.Double(super.getStartPoint(), super.getEndPoint()))) // меняем расположение textfigure
        {
            moving += 0.3;
            Point2D.Double pC1 = new Point2D.Double(super.getCenter().getX() + direction * Math.cos(Math.PI / 2 - angle) * moving * shift,
                    super.getCenter().getY() + direction * Math.sin(Math.PI / 2 - angle) * moving * shift);
            textFigure.setBounds(new Point2D.Double(pC1.getX(), pC1.getY()), p4);

            if (!textFigure.getBounds().intersectsLine(new Line2D.Double(p0, p1)) &&
                    !textFigure.getBounds().intersectsLine(new Line2D.Double(super.getStartPoint(), super.getEndPoint())))
            {
                moving -= 0.3;
                break;
            }
        }
        checkForChangedFontSize();

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
    public Collection<Handle> createHandles(int detailLevel)
    {
        List<Handle> handles = new ArrayList<Handle>();
        if (hasAttribute(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.MOVEABLE) &&
                !get(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.MOVEABLE).booleanValue())
        {
            switch (detailLevel)
            {
                case -1: // Mouse hover handles
                    handles.add(new BezierOutlineHandle(this, true));
                    break;
                case 0:
                    handles.add(new BezierOutlineHandle(this));
                    break;
            }
        }
        else
        {
            RelativeLocator locator = new RelativeLocator(0.5f, 0.5f); //середина главной(super) линии
            locator.locate(this);
            MoveDirectionHandle moveHandler = new MoveDirectionHandle(this, locator);
            if (isGeneralLine) //для прямой
            {
                handles.addAll(super.createHandles(detailLevel));
            }
            else //handles для кривой
            {
                handles.add(new DragLineHandle(this));
                BezierPointLocator locatorEnd = new BezierPointLocator(1);
                BezierPointLocator locatorStart = new BezierPointLocator(0);
                locatorStart.locate(this);
                locatorEnd.locate(this);
                handles.add(new ResizeLineFigureHandle(this, locatorEnd, false));
                handles.add(new ResizeLineFigureHandle(this, locatorStart, true));
            }

            handles.add(moveHandler);
        }

        return handles;
    }

    @Override
    public void transform(AffineTransform tx)
    {
        if (!(hasAttribute(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.MOVEABLE) &&
                !get(by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.MOVEABLE).booleanValue()))
        {
            super.transform(tx);
        }
    }

    @Override
    public Rectangle2D.Double getDrawingArea()
    {
        Rectangle2D.Double rectangle2D = super.getDrawingArea();
        calcElements();
        Rectangle2D.Double union = (Rectangle2D.Double) rectangle2D.getBounds2D();
        union.add(line1.getDrawingArea());
        union.add(line2.getDrawingArea());
        union.add(line3.getDrawingArea());
        union.add(textFigure.getDrawingArea());
        return union;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    public double getAngle()
    {
        return Math.toDegrees(angle);
    }

    @Override
    public void write(DOMOutput out) throws IOException
    {
        out.addAttribute("direction", this.direction);
        out.addAttribute("angle", this.angle);
        out.addAttribute("isGeneralLine", this.isGeneralLine);
        out.addAttribute("fontSize", this.fontSize);
        writePoints(out);
        writeAttributes(out);
    }

    @Override
    public void read(DOMInput in) throws IOException
    {
        this.direction = in.getAttribute("direction", -1);
        this.angle = in.getAttribute("angle", 0d);
        this.isGeneralLine = in.getAttribute("isGeneralLine", false);
        this.fontSize = in.getAttribute("fontSize", 12);
        readPoints(in);
        readAttributes(in);
    }


    public boolean isCondition()
    {
        return isGeneralLine;
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
        return super.getBezierPath();
    }

    @Override
    public Shape getInvertedStartEndShape()
    {
        BezierPath origP = super.getBezierPath();
        BezierPath newP = new BezierPath();
        for (int i = origP.size() - 1; i > -1; i--)
        {
            BezierPath.Node node = origP.get(i);
            newP.add(node);

        }
        return newP;
    }

    @Override
    public double getGluiengLength()
    {
        return getMillingLength();
    }


    @Override
    public List<TextFigure> getTextFigures()
    {
        List<TextFigure> textFigures = new ArrayList<TextFigure>();
        textFigures.add(textFigure);

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
    public boolean equals(Object obj)
    {
        if (obj instanceof CurveFigure)
        {
            return ((getStartPoint().distance(((CurveFigure) obj).getStartPoint()) < 1 || getStartPoint().distance(((CurveFigure) obj).getEndPoint()) < 1)
                    && (getEndPoint().distance(((CurveFigure) obj).getStartPoint()) < 1 || getEndPoint().distance(((CurveFigure) obj).getEndPoint()) < 1));
        }
        else
        {
            return super.equals(obj);
        }
    }

    @Override
    public boolean isShowDimention()
    {
        return isShowDimention;
    }

    @Override
    public void setShowDimention(boolean showDimention)
    {
        boolean old = this.isShowDimention;
        this.isShowDimention = showDimention;
        firePropertyChange("showDimention", old, showDimention);
    }
}
