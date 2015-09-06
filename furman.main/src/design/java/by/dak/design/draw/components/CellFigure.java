package by.dak.design.draw.components;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.annotation.Converted;
import by.dak.design.draw.handle.CellFigureSizeHandler;
import by.dak.design.draw.handle.CellNumerationHandler;
import by.dak.design.entity.converter.CellFigureXConverter;
import by.dak.draw.ChildFigure;
import by.dak.model3d.DBox;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.GraphicalCompositeFigure;
import org.jhotdraw.draw.RectangleFigure;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 6/26/11
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
@XStreamConverter(value = CellFigureXConverter.class)
@XStreamAlias(value = "CellFigure")
@Converted
public class CellFigure extends GraphicalCompositeFigure implements ChildFigure<CellFigure>, Connectable
{
    public static final double STROKE_WIDTH = 1d;
    public static final double dashes[] = {7, 7}; //первый элемент - длина линии, второй - длина отступа
    public static double fontSize = 12;
    private CellFigure parent;
    private Location location = null;
    private BoardFigure boardFigure;
    private double width;
    private Integer numeration = 1;
    private NumericTipFigure numerationTip;

    private DBox box;

    public CellFigure()
    {
        super();
        initEnv();
    }

    public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    private void initEnv()
    {
        addPropertyChangeListener("boardFigure", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                if (evt.getNewValue() != null)
                {
                    boardChanged();
                }
            }
        });
        setTransformable(false);
        setSelectable(false);
        RectangleFigure rectangleFigure = new RectangleFigure();
        rectangleFigure.set(AttributeKeys.STROKE_DASHES, dashes);
        setPresentationFigure(rectangleFigure);
        set(AttributeKeys.FILL_COLOR, null);
        numerationTip = new NumericTipFigure();
        numerationTip.setVisible(false);
    }

    private void boardChanged()
    {
        boardFigure.setParent(this);
    }

    public BoardFigure getBoardFigure()
    {
        return boardFigure;
    }

    public void setBoardFigure(BoardFigure boardFigure)
    {
        BoardFigure old = this.boardFigure;
        this.boardFigure = boardFigure;
        firePropertyChange("boardFigure", old, boardFigure);
    }

    @Override
    public CellFigure getParent()
    {
        return parent;
    }

    public Integer getNumeration()
    {
        return numeration;
    }

    public void setNumeration(Integer numeration)
    {
        this.numeration = numeration;
    }

    public NumericTipFigure getNumerationTip()
    {
        return numerationTip;
    }

    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
        numerationTip.willChange();
        numerationTip.setText(numeration.toString());
        numerationTip.setFontSize((float) (fontSize / g.getTransform().getScaleX()));
        Point2D.Double startPoint = new Point2D.Double(getBounds().getCenterX(),
                getBounds().getCenterY());
        numerationTip.setBounds(startPoint, startPoint);
        numerationTip.changed();
        if (numerationTip.isVisible() && getChildCount() == 0)
        {
            if (getBounds().getHeight() > 1 && getBounds().getWidth() > 1)
            {
                numerationTip.draw(g);
            }
        }
    }

    @Override
    public void setParent(CellFigure parent)
    {
        this.parent = parent;
    }

    @Override
    protected void drawPresentationFigure(Graphics2D g)
    {
        if (getPresentationFigure() != null)
        {
            getPresentationFigure().set(AttributeKeys.STROKE_WIDTH, STROKE_WIDTH * 1 / g.getTransform().getScaleX());
            getPresentationFigure().draw(g);
        }
    }

    /**
     * ищет чайлд по положению (может быть - слева, справа, сверху, снизу)
     *
     * @param location
     * @param parentCell
     * @return
     */
    public CellFigure findChild(Location location, CellFigure parentCell)
    {
        for (Figure figure : parentCell.getChildren())
        {
            if (figure instanceof CellFigure)
            {
                if (((CellFigure) figure).getLocation().equals(location))
                {
                    return (CellFigure) figure;
                }
            }
        }
        return null;
    }

    /**
     * поиск cell, который лежит в данной точке
     *
     * @param point
     * @param cellFigure
     * @return
     */
    public CellFigure findFrontCellFigure(Point2D.Double point, CellFigure cellFigure)
    {
        if (cellFigure.getBounds().contains(point))
        {
            for (Figure figure : cellFigure.getChildrenFrontToBack())
            {
                if (figure instanceof CellFigure)
                {
                    CellFigure result = findFrontCellFigure(point, (CellFigure) figure);
                    if (result != null)
                    {
                        return result;
                    }
                    if (figure.getBounds().contains(point) && figure.isVisible())
                    {
                        return (CellFigure) figure;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead)
    {
        Rectangle2D.Double oldBounds = getBounds();
        Rectangle2D.Double newBounds = new Rectangle2D.Double(
                Math.min(anchor.x, lead.x),
                Math.min(anchor.y, lead.y),
                Math.abs(anchor.x - lead.x),
                Math.abs(anchor.y - lead.y));


        AffineTransform tx = new AffineTransform();
        tx.translate(-oldBounds.x, -oldBounds.y);
        tx.translate(newBounds.x, newBounds.y);
        transform(tx);

        basicSetPresentationFigureBounds(anchor, lead);

        if (getDrawing() instanceof FrontDesignerDrawing)
        {
            if ((newBounds.getHeight() < 1 || newBounds.getWidth() < 1) ||
                    (oldBounds.getHeight() < 1 || oldBounds.getWidth() < 1))
            {
                CellNumerationHandler cellNumerationHandler = new CellNumerationHandler(this);
                cellNumerationHandler.recalcNumeration();
            }
        }

        if (getChildCount() > 0)
        {
            CellFigureSizeHandler cellFigureSizeHandler = new CellFigureSizeHandler(this);
            cellFigureSizeHandler.setDrawing((FrontDesignerDrawing) getDrawing());
            cellFigureSizeHandler.trackEnd(getBoardFigure());
        }
    }

    @Override
    public void transform(AffineTransform tx)
    {
        if (getPresentationFigure() != null)
        {
            getPresentationFigure().transform(tx);
            invalidate();
        }
    }

    @Override
    public boolean add(Figure figure)
    {
        boolean result = super.add(figure);
        if (result && figure instanceof ChildFigure)
        {
            ((ChildFigure) figure).setParent(this);
        }
        return result;
    }

    @Override
    public boolean remove(Figure figure)
    {
        boolean result = super.remove(figure);
        if (result && figure instanceof ChildFigure)
        {
            ((ChildFigure) figure).setParent(null);
        }
        return result;
    }

    public DimensionConnector getConnector(Point2D.Double point)
    {
        CellFigure frontCell = findFrontCellFigure(point, this);
        if (frontCell != null)
        {
            return new DimensionConnector(frontCell);
        }
        else
        {
            frontCell = findClosestFrontCellFigureTo(point, this);
            if (frontCell != null)
            {
                return new DimensionConnector(frontCell);
            }
        }
        return null;
    }

    private CellFigure findClosestFrontCellFigureTo(Point2D.Double relative, CellFigure parent)
    {
        if (parent.getChildCount() > 0)
        {
            CellFigure child1 = null;
            CellFigure child2 = null;

            if (parent.getBoardFigure().getOrientation().equals(BoardFigure.Orientation.Horizontal))
            {
                child1 = findChild(Location.Top, parent);
                child2 = findChild(Location.Bottom, parent);
            }
            else
            {
                child1 = findChild(Location.Left, parent);
                child2 = findChild(Location.Right, parent);
            }


            if (relative.distance(new DimensionConnector(child1).findStart(relative)) <
                    relative.distance(new DimensionConnector(child2).findStart(relative)))
            {
                return findClosestFrontCellFigureTo(relative, child1);
            }
            else
            {
                return findClosestFrontCellFigureTo(relative, child2);
            }
        }
        return parent;
    }

    public DBox getBox()
    {
        return box;
    }

    public void setBox(DBox box)
    {
        this.box = box;
    }

    public static enum Location
    {
        Top,
        Bottom,
        Left,
        Right
    }
}
