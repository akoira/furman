package by.dak.design.draw.components;

import by.dak.design.draw.handle.DimensionEndConnectorHandler;
import by.dak.design.draw.handle.DimensionStartConnectorHandler;
import by.dak.design.entity.converter.DimensionFigureXConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.LineFigure;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.event.FigureAdapter;
import org.jhotdraw.draw.event.FigureEvent;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.geom.Geom;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/13/11
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
@XStreamConverter(value = DimensionFigureXConverter.class)
@XStreamAlias(value = "DimensionFigure")
public class DimensionFigure extends LineFigure
{
    public static int TEXT_WIDTH_OFFSET = 2;
    public static double STROKE_WIDTH = 1d;
    public static double dashes[] = {4, 4}; //первый элемент - длина линии, второй - длина отступа
    public static double fontSize = 12;
    //    private TextFigure textFigure = new TextFigure();
//    private LineFigure topRightLine = new LineFigure();
//    private LineFigure topLeftLine = new LineFigure();
    private DimensionTipFigure textFigure = new DimensionTipFigure();
    private LineFigure topLine = new LineFigure();
    private LineFigure leftLine = new LineFigure();
    private LineFigure rightLine = new LineFigure();
    private ArrowTip arrowTip = new ArrowTip();
    private double offset = 0;
    private ConnectionHandler connectionHandler = new ConnectionHandler(this);
    private DimensionConnector startConnector = null;
    private DimensionConnector endConnector = null;

    private double getFontSize(double scale)
    {
        return fontSize * (1 / scale);
    }

    @Override
    protected void drawStroke(Graphics2D g)
    {
        applyAtributes(g);
        addLines(g);
        addSizeTip(g);
    }

    private void addLines(Graphics2D g)
    {
        if (getStartPoint().distance(getEndPoint()) > 0)
        {
            double angle = findAngle();

            Point2D.Double leftLineStartPoint = getStartPoint();

            Point2D.Double leftLineEndPoint =
                    new Point2D.Double(leftLineStartPoint.x + Math.cos(Math.PI / 2 - angle) * offset,
                            leftLineStartPoint.y - Math.sin(Math.PI / 2 - angle) * offset);

            Point2D.Double rightLineStartPoint = getEndPoint();
            Point2D.Double rightLineEndPoint =
                    new Point2D.Double(rightLineStartPoint.x + Math.cos(Math.PI / 2 - angle) * offset,
                            rightLineStartPoint.y - Math.sin(Math.PI / 2 - angle) * offset);

            leftLine.setStartPoint(leftLineStartPoint);
            leftLine.setEndPoint(leftLineEndPoint);
            leftLine.draw(g);

            rightLine.setStartPoint(rightLineStartPoint);
            rightLine.setEndPoint(rightLineEndPoint);
            rightLine.draw(g);

            topLine.setStartPoint(leftLineEndPoint);
            topLine.setEndPoint(rightLineEndPoint);
            topLine.draw(g);

//            setTopLineBounds(g, angle, rightLineEndPoint, leftLineEndPoint);

//            topRightLine.draw(g);
//            topLeftLine.draw(g);
        }
    }

    /*  private void setTopLineBounds(Graphics2D g, double angle, Point2D.Double rightLineEndPoint, Point2D.Double leftLineEndPoint)
{
  FontMetrics fontMetrics = g.getFontMetrics(textFigure.getFont());
  double width = fontMetrics.stringWidth(textFigure.getText()) + TEXT_WIDTH_OFFSET;

  Point2D.Double centerPoint = new Point2D.Double(getCenter().x + Math.cos(Math.PI / 2 - findAngle()) * offset,
          getCenter().y - Math.sin(Math.PI / 2 - findAngle()) * offset);

  Point2D.Double endLeftTopPoint;
  Point2D.Double startRightTopPoint;

  if ((Math.toDegrees(angle) <= 0 && Math.toDegrees(angle) > -90)
          && getEndPoint().x - getStartPoint().x > 0)
  {
      endLeftTopPoint =
              new Point2D.Double(centerPoint.x - Math.sin(Math.PI / 2 - findAngle()) * width,
                      centerPoint.y - Math.cos(Math.PI / 2 - findAngle()) * width);
      startRightTopPoint =
              new Point2D.Double(centerPoint.x + Math.sin(Math.PI / 2 - findAngle()) * width,
                      centerPoint.y + Math.cos(Math.PI / 2 - findAngle()) * width);
      topRightLine.setStartPoint(rightLineEndPoint);
      topRightLine.setEndPoint(startRightTopPoint);
      topLeftLine.setStartPoint(leftLineEndPoint);
      topLeftLine.setEndPoint(endLeftTopPoint);
      if (topRightLine.getStartPoint().distance(topRightLine.getEndPoint()) < width / 2)
      {
          topRightLine.setBounds(new Point2D.Double(rightLineEndPoint.x - Math.sin(Math.PI / 2 - findAngle()),
                  rightLineEndPoint.y - Math.cos(Math.PI / 2 - findAngle())),
                  rightLineEndPoint);
      }
      if (topLeftLine.getStartPoint().distance(topLeftLine.getEndPoint()) < width / 2)
      {
          topLeftLine.setBounds(new Point2D.Double(leftLineEndPoint.x + Math.sin(Math.PI / 2 - findAngle()),
                  leftLineEndPoint.y + Math.cos(Math.PI / 2 - findAngle())),
                  leftLineEndPoint);
      }
  }
  else if ((Math.toDegrees(angle) > 0 && Math.toDegrees(angle) < 90)
          && getEndPoint().x - getStartPoint().x > 0)
  {
      endLeftTopPoint =
              new Point2D.Double(centerPoint.x - Math.sin(Math.PI / 2 - findAngle()) * width,
                      centerPoint.y - Math.cos(Math.PI / 2 - findAngle()) * width);
      startRightTopPoint =
              new Point2D.Double(centerPoint.x + Math.sin(Math.PI / 2 - findAngle()) * width,
                      centerPoint.y + Math.cos(Math.PI / 2 - findAngle()) * width);
      topRightLine.setStartPoint(rightLineEndPoint);
      topRightLine.setEndPoint(startRightTopPoint);
      topLeftLine.setStartPoint(leftLineEndPoint);
      topLeftLine.setEndPoint(endLeftTopPoint);
      if (topRightLine.getStartPoint().distance(topRightLine.getEndPoint()) < width / 2)
      {
          topRightLine.setBounds(new Point2D.Double(rightLineEndPoint.x - Math.sin(Math.PI / 2 - findAngle()),
                  rightLineEndPoint.y - Math.cos(Math.PI / 2 - findAngle())),
                  rightLineEndPoint);
      }
      if (topLeftLine.getStartPoint().distance(topLeftLine.getEndPoint()) < width / 2)
      {
          topLeftLine.setBounds(new Point2D.Double(leftLineEndPoint.x + Math.sin(Math.PI / 2 - findAngle()),
                  leftLineEndPoint.y + Math.cos(Math.PI / 2 - findAngle())),
                  leftLineEndPoint);
      }
  }
  else
  {
      endLeftTopPoint =
              new Point2D.Double(centerPoint.x + Math.sin(Math.PI / 2 - findAngle()) * width,
                      centerPoint.y + Math.cos(Math.PI / 2 - findAngle()) * width);
      startRightTopPoint =
              new Point2D.Double(centerPoint.x - Math.sin(Math.PI / 2 - findAngle()) * width,
                      centerPoint.y - Math.cos(Math.PI / 2 - findAngle()) * width);
      topRightLine.setStartPoint(rightLineEndPoint);
      topRightLine.setEndPoint(startRightTopPoint);
      topLeftLine.setStartPoint(leftLineEndPoint);
      topLeftLine.setEndPoint(endLeftTopPoint);
      if (topRightLine.getStartPoint().distance(topRightLine.getEndPoint()) < width / 2)
      {
          topRightLine.setBounds(new Point2D.Double(rightLineEndPoint.x + Math.sin(Math.PI / 2 - findAngle()),
                  rightLineEndPoint.y + Math.cos(Math.PI / 2 - findAngle())),
                  rightLineEndPoint);
      }
      if (topLeftLine.getStartPoint().distance(topLeftLine.getEndPoint()) < width / 2)
      {
          topLeftLine.setBounds(new Point2D.Double(leftLineEndPoint.x - Math.sin(Math.PI / 2 - findAngle()),
                  leftLineEndPoint.y - Math.cos(Math.PI / 2 - findAngle())),
                  leftLineEndPoint);
      }
  }


}      */

    private double findAngle()
    {
        if (getStartPoint().distance(getEndPoint()) > 0)
        {
            double firstSide = getStartPoint().x - getEndPoint().x;
            double secondSide = getStartPoint().y - getEndPoint().y;
            return Math.atan(secondSide / firstSide);
        }

        return 0;
    }

    public double getOffset()
    {
        return offset;
    }

    public void setOffset(double offset)
    {
        this.offset = offset;
    }

    private void applyAtributes(Graphics2D g)
    {

        leftLine.set(AttributeKeys.STROKE_DASHES, dashes);
        rightLine.set(AttributeKeys.STROKE_DASHES, dashes);
        leftLine.set(AttributeKeys.STROKE_WIDTH, STROKE_WIDTH * 1 / g.getTransform().getScaleX());
        rightLine.set(AttributeKeys.STROKE_WIDTH, STROKE_WIDTH * 1 / g.getTransform().getScaleX());
        topLine.set(AttributeKeys.STROKE_DASHES, dashes);
        topLine.set(AttributeKeys.STROKE_WIDTH, STROKE_WIDTH * 1 / g.getTransform().getScaleX());
        topLine.set(AttributeKeys.START_DECORATION, arrowTip);
        topLine.set(AttributeKeys.END_DECORATION, arrowTip);
        /*   textFigure.set(AttributeKeys.STROKE_WIDTH, 0d);
       textFigure.set(AttributeKeys.FILL_COLOR, null);
       topRightLine.set(AttributeKeys.STROKE_WIDTH, STROKE_WIDTH * 1 / g.getTransform().getScaleX());
       topRightLine.set(AttributeKeys.START_DECORATION, arrowTip);
       topRightLine.set(AttributeKeys.STROKE_DASHES, dashes);
       topLeftLine.set(AttributeKeys.STROKE_WIDTH, STROKE_WIDTH * 1 / g.getTransform().getScaleX());
       topLeftLine.set(AttributeKeys.START_DECORATION, arrowTip);
       topLeftLine.set(AttributeKeys.STROKE_DASHES, dashes);*/

    }

    @Override
    public Rectangle2D.Double getBounds()
    {
        Rectangle2D.Double rectangle = new Rectangle2D.Double();
        rectangle.add(super.getBounds());
        Rectangle2D.Double rectangleText = textFigure.getBounds();
        Geom.grow(rectangleText, textFigure.getInsets().bottom, textFigure.getInsets().right);
        rectangle.add(rectangleText);
        rectangle.add(leftLine.getBounds());
        rectangle.add(rightLine.getBounds());
        return rectangle;
    }

    private void addSizeTip(Graphics2D g)
    {
        AffineTransform oldTransform = g.getTransform();

//        textFigure.willChange();
        String text = String.valueOf((int) getStartPoint().distance(getEndPoint()));
        textFigure.setText(text);
        textFigure.setSelectable(true);
        textFigure.setEditable(true);
        textFigure.setTransformable(true);
        textFigure.setConnectable(false);
        textFigure.set(AttributeKeys.FONT_SIZE, getFontSize(g.getTransform().getScaleX()));

        FontMetrics fontMetrics = g.getFontMetrics(textFigure.getFont());
        double height = fontMetrics.getHeight() + textFigure.getInsets().bottom * 2;
        double width = fontMetrics.stringWidth(text) + textFigure.getInsets().right * 2;

        Point2D.Double centerPoint = new Point2D.Double(getCenter().x + Math.cos(Math.PI / 2 - findAngle()) * offset,
                getCenter().y - Math.sin(Math.PI / 2 - findAngle()) * offset);

        Point2D.Double startPoint = new Point2D.Double(centerPoint.x - width / 2,
                centerPoint.y - height / 2);
        Point2D.Double endPoint = new Point2D.Double(startPoint.x + width, startPoint.y + height);
        textFigure.setBounds(startPoint, endPoint);

        AffineTransform newTransform = new AffineTransform();

        newTransform.concatenate(oldTransform);
        newTransform.translate(textFigure.getBounds().getCenterX(), textFigure.getBounds().getCenterY());
        newTransform.rotate(findAngle());
        newTransform.translate(-textFigure.getBounds().getCenterX(), -textFigure.getBounds().getCenterY());

//        textFigure.changed();
        g.setTransform(newTransform);
        textFigure.draw(g);
        g.setTransform(oldTransform);
    }

    @Override
    public BezierFigure clone()
    {
        DimensionFigure that = new DimensionFigure();
        return that;
    }

    @Override
    public Rectangle2D.Double getDrawingArea()
    {
        Rectangle2D.Double rectangle = new Rectangle2D.Double();
        rectangle.add(super.getDrawingArea());
        Rectangle2D.Double rectangleText = textFigure.getDrawingArea();
        Geom.grow(rectangleText, textFigure.getInsets().bottom, textFigure.getInsets().right);
        rectangle.add(rectangleText);
        rectangle.add(leftLine.getDrawingArea());
        rectangle.add(rightLine.getDrawingArea());
        /*rectangle.add(topLeftLine.getDrawingArea());
        rectangle.add(topRightLine.getDrawingArea());*/

        return rectangle;
    }

    @Override
    public boolean contains(Point2D.Double p)
    {
        if (textFigure.contains(p))
        {
            return true;
        }
        return false;
    }

    @Override
    public Collection<Handle> createHandles(int detailLevel)
    {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        switch (detailLevel)
        {
            case -1: // Mouse hover handles
                break;
            case 0:
                handles.add(new DimensionStartConnectorHandler(this));
                handles.add(new DimensionEndConnectorHandler(this));
                break;
        }
        return handles;
    }

    @Override
    public boolean handleMouseClick(Point2D.Double p, MouseEvent evt, DrawingView view)
    {
        return true;
    }

    public DimensionConnector getStartConnector()
    {
        return startConnector;
    }

    public void setStartConnector(DimensionConnector startConnector)
    {
        if (this.startConnector != null)
        {
            this.startConnector.getOwner().removeFigureListener(connectionHandler);
        }
        this.startConnector = startConnector;
        if (startConnector != null)
        {
            startConnector.getOwner().addFigureListener(connectionHandler);
        }
    }

    public DimensionConnector getEndConnector()
    {
        return endConnector;
    }

    public void setEndConnector(DimensionConnector endConnector)
    {
        if (this.endConnector != null)
        {
            this.endConnector.getOwner().removeFigureListener(connectionHandler);
        }
        this.endConnector = endConnector;
        if (endConnector != null)
        {
            endConnector.getOwner().addFigureListener(connectionHandler);
        }
    }

    private void updateConnection()
    {
        if (startConnector != null)
        {
            Point2D.Double startPoint = startConnector.findStart(getStartPoint());
            setStartPoint(startPoint);
        }
        if (endConnector != null)
        {
            Point2D.Double endPoint = endConnector.findEnd(getEndPoint(), getStartPoint());
            setEndPoint(endPoint);
        }
    }

    private class ConnectionHandler extends FigureAdapter implements Serializable
    {
        private DimensionFigure owner;

        private ConnectionHandler(DimensionFigure owner)
        {
            this.owner = owner;
        }

        @Override
        public void figureRemoved(FigureEvent evt)
        {
            owner.fireFigureRequestRemove();
        }

        @Override
        public void figureChanged(FigureEvent e)
        {
            if (!owner.isChanging())
            {
                owner.willChange();
                owner.updateConnection();
                owner.changed();
            }
        }
    }

}
