package by.dak.design.draw.components;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.annotation.Converted;
import by.dak.design.draw.handle.*;
import by.dak.design.entity.converter.BoardFigureXConverter;
import by.dak.design.swing.action.BoardLocationHandle;
import by.dak.draw.ChildFigure;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.event.FigureAdapter;
import org.jhotdraw.draw.event.FigureEvent;
import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.jhotdraw.draw.handle.Handle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 6/22/11
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */

@XStreamAlias(value = "BoardFigure")
@XStreamConverter(value = BoardFigureXConverter.class)
@Converted
public class BoardFigure extends GraphicalCompositeFigure implements ChildFigure<CellFigure>, Connectable
{
    public static double STROKE_WIDTH = 1d;
    public static double fontSize = 12;
    public static String PROPERTY_boardElement = "boardElement";

    private RectangleFigure rectangleFigure;
    private NumericTipFigure numerationTip = new NumericTipFigure();

    private Orientation orientation = Orientation.Horizontal;

    private BoardElement boardElement;
    private CellFigure parent;

    public BoardFigure()
    {
        super();
        initFigures();
    }

    private void initFigures()
    {
        rectangleFigure = new RectangleFigure();
        setPresentationFigure(rectangleFigure);
    }

    public Orientation getOrientation()
    {
        return orientation;
    }

    public void setOrientation(Orientation orientation)
    {
        this.orientation = orientation;
    }

    private List<Handle> initHandles()
    {
        List<Handle> handles = new ArrayList<Handle>();
        handles.add(new BoardFigureRotationHandler(this));
        switch (orientation)
        {
            case Horizontal:
                handles.add(BoardFigureResizeHandler.east(this));
                handles.add(BoardFigureResizeHandler.west(this));
                break;
            case Vertical:
                handles.add(BoardFigureResizeHandler.north(this));
                handles.add(BoardFigureResizeHandler.south(this));
                break;
            default:
                break;
        }

        return handles;
    }

    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
        numerationTip.willChange();
        numerationTip.setText(boardElement.getNumeration().toString());
        numerationTip.setFontSize((float) (fontSize / g.getTransform().getScaleX()));
        Point2D.Double startPoint = new Point2D.Double(getBounds().getCenterX(),
                getBounds().getCenterY());
        numerationTip.setBounds(startPoint, startPoint);
        numerationTip.changed();
        if (numerationTip.isVisible())
        {
            numerationTip.draw(g);
        }
    }

    public NumericTipFigure getNumerationTip()
    {
        return numerationTip;
    }

    @Override
    protected void drawPresentationFigure(Graphics2D g)
    {
        if (rectangleFigure != null)
        {
            rectangleFigure.set(AttributeKeys.STROKE_WIDTH, STROKE_WIDTH * 1 / g.getTransform().getScaleX());
            rectangleFigure.draw(g);
        }
    }

    @Override
    public Collection<Handle> createHandles(int detailLevel)
    {
        List<Handle> handles = new ArrayList<Handle>();
        switch (detailLevel)
        {
            case -1:
                handles.add(new BoundsOutlineHandle(this, true, true));
                break;
            case 0:
                handles.addAll(initHandles());
                break;
            default:
                break;
        }
        return handles;
    }


    public BoardElement getBoardElement()
    {
        return boardElement;
    }

    public void setBoardElement(BoardElement boardElement)
    {
        this.boardElement = boardElement;
        firePropertyChange(PROPERTY_boardElement, null, boardElement);
    }

    @Override
    public GraphicalCompositeFigure clone()
    {
        BoardFigure that = new BoardFigure();
        return that;
    }

    @Override
    public String getToolTipText(Point2D.Double p)
    {
        return new StringBuilder().append(boardElement.getLength()).append("x").append(boardElement.getWidth()).
                append("x").append(boardElement.getBoardDef().getThickness()).toString();
    }


    @Override
    public CellFigure getParent()
    {
        return parent;
    }

    @Override
    public void setParent(CellFigure parent)
    {
        this.parent = parent;
    }

    @Override
    public DimensionConnector getConnector(Point2D.Double point)
    {
        return new DimensionConnector(this);
    }

    public static enum Orientation
    {
        Vertical,
        Horizontal
    }

    @Override
    public boolean handleMouseClick(Point2D.Double p, MouseEvent evt, DrawingView view)
    {
        return false;
    }

    private class ElementChangeHandler extends FigureAdapter implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            if (getDrawing() instanceof FrontDesignerDrawing)
            {
                if (evt.getPropertyName().equals(BoardElement.PROPERTY_boardDef))
                {
                    calcBoardBounds();
                }
                else if (evt.getPropertyName().equals(BoardElement.PROPERTY_width))
                {
                    if (boardElement.getType() == BoardElement.Type.common)
                    {
                        calcCellWidth(getParent(), boardElement.getWidth());
                    }
                }
                else if (evt.getPropertyName().equals(BoardElement.PROPERTY_location))
                {
                    BoardLocationHandle boardLocationHandle = new BoardLocationHandle(BoardFigure.this,
                            (BoardElement.Location) evt.getNewValue(), (FrontDesignerDrawing) BoardFigure.this.getDrawing());
                    boardLocationHandle.magnetize();
                }
            }
        }

        private void calcCellWidth(CellFigure cellFigure, double width)
        {
            if (cellFigure.getParent() != null)
            {
                cellFigure.willChange();
                cellFigure.setWidth(width);
                cellFigure.changed();

            }
            for (Figure figure : cellFigure.getChildren())
            {
                if (((CellFigure) figure).getBoardFigure() != null)
                {
                    ((CellFigure) figure).getBoardFigure().getBoardElement().setWidth(width);
                }
                else
                {
                    calcCellWidth((CellFigure) figure, width);
                }
            }
        }

        private void calcBoardBounds()
        {
            double length = boardElement.getLength();
            double height = boardElement.getBoardDef().getThickness();

            willChange();
            setBounds(getStartPoint(),
                    new Point2D.Double(getStartPoint().getX() + length, getStartPoint().getY() + height));
            changed();

            CellFigure cellFigure = getParent();
            if (cellFigure != null)
            {
                BoardSizeConstrainerHandle boardSizeConstrainerHandle = new BoardSizeConstrainerHandle(BoardFigure.this);
                boardSizeConstrainerHandle.setDrawing((FrontDesignerDrawing) getDrawing());
                boardSizeConstrainerHandle.trackStep();

                BoardLocationConstrainerHandle boardLocationConstrainerHandle = new BoardLocationConstrainerHandle(cellFigure);
                boardLocationConstrainerHandle.constrainDraggedFigure(BoardFigure.this);

                CellFigureSizeHandler cellFigureSizeHandler = new CellFigureSizeHandler(cellFigure);
                cellFigureSizeHandler.setDrawing((FrontDesignerDrawing) getDrawing());
                cellFigureSizeHandler.trackEnd(BoardFigure.this);
            }
        }

        @Override
        public void figureChanged(FigureEvent e)
        {
            switch (orientation)
            {
                case Horizontal:
                    boardElement.setLength(rectangleFigure.getBounds().getWidth());
                    break;
                case Vertical:
                    boardElement.setLength(rectangleFigure.getBounds().getHeight());
                    break;
                default:
                    break;
            }
        }

    }


}
