package by.dak.cutting.swing.order.cellcomponents.editors.cuttoff;

import by.dak.cutting.swing.order.cellcomponents.editors.cuttoff.CutoffPainter.CutoffLine;
import by.dak.cutting.swing.order.cellcomponents.editors.cuttoff.CutoffPainter.ElementRectangle;
import by.dak.cutting.swing.order.data.Cutoff;
import by.dak.utils.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeSupport;

/**
 * User: akoyro
 * Date: 17.04.2009
 * Time: 21:07:09
 */
public class Cutoff2DPanel extends JComponent
{

    private static final double SENSITIVITY = 5d;

    private Dimension element;

    private Cutoff cutoff;

    private CutoffAdjuster cutoffAdjuster;

    private double scale = 1.0d;

    private PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);

    public Dimension getElement()
    {
        return element;
    }

    public void setElement(Dimension element)
    {
        this.element = element;
        repaint();
    }

    public Cutoff getCutoff()
    {
        return cutoff;
    }

    public void setCutoff(Cutoff cutoff)
    {
        this.cutoff = cutoff;
        this.cutoffAdjuster = new CutoffAdjuster(cutoff);

        this.cutoffAdjuster.adjustLine();

        repaint();
    }

    public Cutoff2DPanel()
    {
        MouseController mouseController = new MouseController();
        addMouseListener(mouseController);
        addMouseMotionListener(mouseController);
        ComponentController componentController = new ComponentController();
        addComponentListener(componentController);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if (getElement() == null || getCutoff() == null)
        {
            return;
        }

        CutoffPainter painter = new CutoffPainter(element, cutoff, cutoffAdjuster.getCutoffLine(), scale);
        painter.paint((Graphics2D) g);
    }

    private ElementRectangle getElementRectangle()
    {
        return CutoffPainter.getElementRectangleBy(element, scale);
    }

    @Override
    public Dimension getPreferredSize()
    {
        if (getElement() == null)
        {
            return new Dimension(100, 100);
        }
        else
        {
            return new Dimension(getElement().width + CutoffPainter.OFFSET * 2,
                    getElement().height + CutoffPainter.OFFSET * 2);
        }
    }

    private Point2D adjustPoint(Point2D point2D)
    {

        double wW = 0;
        double wH = 0;
        Rectangle2D rect = getElementRectangle();
        if (rect.getCenterX() < point2D.getX())
        {
            wW = rect.getWidth();
        }

        if (rect.getCenterY() < point2D.getY())
        {
            wH = rect.getHeight();
        }

        if (Math.abs(wW - point2D.getX()) < Math.abs(wH - point2D.getY()))
        {
            point2D.setLocation(wW, point2D.getY());
        }
        else
        {
            point2D.setLocation(point2D.getX(), wH);
        }
        return point2D;
    }

    public PropertyChangeSupport getPropertySupport()
    {
        return propertySupport;
    }

    public class CutoffAdjuster
    {

        private Cutoff cutoff;

        private CutoffLine cutoffLine;

        private CutoffValidator cutoffValidator;

        public CutoffAdjuster(Cutoff cutoff)
        {
            this.cutoff = cutoff;
            cutoffValidator = new CutoffValidator(cutoff);
        }

        public void adjustLine()
        {
            if (!cutoffValidator.validate())
            {
                cutoffLine = null;
                return;
            }
            if (cutoffLine == null)
            {
                cutoffLine = new CutoffLine();
            }
            CutoffPainter.adjustLineBy(cutoffLine, element, cutoff, scale);
        }

        public void adjustCutoff()
        {
            ElementRectangle rect = getElementRectangle();
            cutoff.setAngle(CutoffPainter.getTypeBy(getCutoffLine()));
            double horizOffset = 0;
            double vertOffset = 0;
            switch (cutoff.getAngle())
            {
                case upLeft:
                    horizOffset = getCutoffLine().getP2().distance(rect.getUpRight());
                    vertOffset = getCutoffLine().getP1().distance(rect.getDownLeft());
                    break;
                case upRight:
                    horizOffset = getCutoffLine().getP2().getX();
                    vertOffset = getCutoffLine().getP1().distance(rect.getDownRight());
                    break;
                case downLeft:
                    horizOffset = getCutoffLine().getP2().distance(rect.getDownRight());
                    vertOffset = getCutoffLine().getP1().getY();
                    break;
                case downRight:
                    horizOffset = getCutoffLine().getP2().getX();
                    vertOffset = getCutoffLine().getP1().getY();
                    break;
            }
            cutoff.setHOffset(MathUtils.round(horizOffset * scale, 2));
            cutoff.setVOffset(MathUtils.round(vertOffset * scale, 2));
        }

        public CutoffLine getCutoffLine()
        {
            return cutoffLine;
        }

        public void setCutoffLine(CutoffLine cutoffLine)
        {
            this.cutoffLine = cutoffLine;
        }
    }

    public abstract class MouseProcess
    {

        public MouseProcess(Point point, CutoffLine line)
        {
            prevPoint = point;
            this.line = line;
        }

        private Point prevPoint;

        private CutoffLine line;

        public void process(MouseEvent event)
        {
            processLine(event);
            prevPoint = event.getPoint();
            repaint();
        }

        public abstract void processLine(MouseEvent event);

        public Point getPrevPoint()
        {
            return prevPoint;
        }

        public CutoffLine getLine()
        {
            return line;
        }
    }

    public class DragMouseProcess extends MouseProcess
    {

        private boolean first = true;

        public DragMouseProcess(Point point, CutoffLine line, Point2D termPoint)
        {
            super(point, line);
            first = line.getP1().equals(termPoint);
        }

        @Override
        public void processLine(MouseEvent event)
        {

            double dx = event.getX() - getPrevPoint().getX();
            double dy = event.getY() - getPrevPoint().getY();

            Point2D p1 = null;
            Point2D p2 = null;

            if (first)
            {

                p1 = new Point2D.Double(getLine().getX1() + dx, getLine().getY1() + dy);
                p2 = getLine().getP2();
            }
            else
            {
                p2 = new Point2D.Double(getLine().getX2() + dx, getLine().getY2() + dy);
                p1 = getLine().getP1();
            }
            getLine().setLine(p1, p2);

        }
    }

    public class PaintMouseProcess extends MouseProcess
    {

        public PaintMouseProcess(Point point, CutoffLine line)
        {
            super(point, line);
        }

        @Override
        public void processLine(MouseEvent event)
        {
            double dx = event.getX() - getPrevPoint().getX();
            double dy = event.getY() - getPrevPoint().getY();
            getLine().setLine(getLine().getP1(), new Point2D.Float(event.getX(), event.getY()));
        }
    }

    public enum State
    {

        paint,
        drag
    }

    public class LineFinder
    {

        private Point point;

        private CutoffLine cutoffLine;

        private Point2D point2D;

        public LineFinder(Point point)
        {
            this.point = point;
        }

        public void find()
        {
            Rectangle2D test = new Rectangle2D.Double(point.getX() - CutoffPainter.OFFSET - SENSITIVITY / 2,
                    point.getY() - CutoffPainter.OFFSET - SENSITIVITY / 2,
                    SENSITIVITY,
                    SENSITIVITY);

            if (cutoffAdjuster.getCutoffLine() != null)
            {
                if (test.intersects(cutoffAdjuster.getCutoffLine().getT1().getBounds2D()))
                {
                    this.cutoffLine = cutoffAdjuster.getCutoffLine();
                    this.point2D = cutoffLine.getP1();
                    return;
                }
                if (test.intersects(cutoffAdjuster.getCutoffLine().getBounds2D()))
                {
                    this.cutoffLine = cutoffAdjuster.getCutoffLine();
                    this.point2D = cutoffLine.getP2();
                }
            }

        }

        public CutoffLine getCutoffLine()
        {
            return cutoffLine;
        }

        public Point2D getPoint2D()
        {
            return point2D;
        }
    }

    public class ComponentController implements ComponentListener
    {

        @Override
        public void componentResized(ComponentEvent e)
        {
            if (getElement() != null)
            {
                scale = CutoffPainter.getScale(getElement(),
                        getSize());
//            elementRect.setRect(0, 0, element.getWidth() / scale, element.getHeight() / scale);
                cutoffAdjuster.adjustLine();
                repaint();
            }
        }

        @Override
        public void componentMoved(ComponentEvent e)
        {
        }

        @Override
        public void componentShown(ComponentEvent e)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void componentHidden(ComponentEvent e)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public class MouseController implements MouseListener, MouseMotionListener
    {

        private MouseProcess mouseProcess;

        @Override
        public void mouseClicked(MouseEvent e)
        {
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            if (getElement() == null)
            {
                return;
            }

            LineFinder finder = new LineFinder(e.getPoint());
            finder.find();
            if (finder.getCutoffLine() != null)
            {
                mouseProcess = new DragMouseProcess(e.getPoint(),
                        cutoffAdjuster.getCutoffLine(),
                        finder.getPoint2D());
            }
            else
            {
                if (cutoffAdjuster.getCutoffLine() == null)
                {
                    cutoffAdjuster.setCutoffLine(new CutoffLine(adjustPoint(e.getPoint()), e.getPoint()));
                    mouseProcess = new PaintMouseProcess(e.getPoint(), cutoffAdjuster.getCutoffLine());
                }
            }

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if (getElement() == null)
            {
                return;
            }

            Point2D p1 = null;
            Point2D p2 = null;
            if (mouseProcess instanceof PaintMouseProcess)
            {
                p1 = mouseProcess.getLine().getP1();
                p2 = adjustPoint(new Point2D.Float(e.getX() - CutoffPainter.OFFSET, e.getY() - CutoffPainter.OFFSET));
            }
            else if (mouseProcess instanceof DragMouseProcess)
            {

                p1 = adjustPoint(mouseProcess.getLine().getP1());
                p2 = adjustPoint(mouseProcess.getLine().getP2());
            }

            if (p1 != null && p2 != null)
            {
                setLine(p1, p2);
                cutoffAdjuster.adjustCutoff();
                getPropertySupport().firePropertyChange("cutoff", null, getCutoff());
            }
            mouseProcess = null;
        }

        private void setLine(Point2D p1, Point2D p2)
        {
            if (p2.getX() == 0 || p2.getX() == getElement().getWidth())
            {
                Point2D t = p1;
                p1 = p2;
                p2 = t;
            }
            mouseProcess.getLine().setLine(p1, p2);
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if (mouseProcess != null)
            {
                mouseProcess.process(e);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
        }
    }
}
