package by.dak.design.draw.handle;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.handle.AbstractHandle;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 6/24/11
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoardFigureRotationHandler extends AbstractHandle
{
    public static int OFFSET = 5; //handle location
    private double centerX = 0;
    private double centerY = 0;
    private CellFigure cellFigure;
    private double x = 0; //координаты относительно положения cell
    private double y = 0;

    public BoardFigureRotationHandler(Figure owner)
    {
        super(owner);
    }

    private boolean isRotatable()
    {
        if ((centerX == 0 && centerY == 0) || !getOwner().isTransformable())
        {
            return false;
        }

        cellFigure = ((FrontDesignerDrawing) getView().getDrawing()).findCellFigure(
                new Point2D.Double(centerX, centerY));


        if (cellFigure != null)
        {
            java.util.List<Figure> figures = cellFigure.getChildren();
            for (Figure f : figures)
            {
                if (f instanceof CellFigure)
                {
                    if (((CellFigure) f).getChildCount() > 0)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void trackStart(Point anchor, int modifiersEx)
    {
        Figure figure = getOwner();
        centerX = figure.getBounds().getCenterX();
        centerY = figure.getBounds().getCenterY();

        if (isRotatable())
        {
            calculateCoordinates();

            figure.willChange();
            if (((BoardFigure) figure).getOrientation() == BoardFigure.Orientation.Horizontal)
            {
                ((BoardFigure) figure).setOrientation(BoardFigure.Orientation.Vertical);
            }
            else
            {
                ((BoardFigure) figure).setOrientation(BoardFigure.Orientation.Horizontal);
            }

            Point2D.Double anchorPoint = new Point2D.Double(figure.getBounds().getMinX(),
                    figure.getBounds().getMinY());

            Point2D.Double leadPoint = new Point2D.Double(figure.getBounds().getMinX() + figure.getBounds().getHeight(),
                    figure.getBounds().getMinY() + figure.getBounds().getWidth());
            figure.setBounds(anchorPoint,
                    leadPoint);

            figure.changed();
        }

    }

    private Point getLocation()
    {
        return view.drawingToView(new Point2D.Double(getOwner().getBounds().getMaxX() + OFFSET * (1 / getView().getScaleFactor()),
                getOwner().getBounds().getMaxY() + OFFSET * (1 / getView().getScaleFactor())));
    }

    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
    }

    @Override
    public void trackEnd(Point anchor, Point lead, int modifiersEx)
    {
        if (isRotatable())
        {
            Figure currentFigure = getOwner();
            getView().removeFromSelection(currentFigure);
            getView().addToSelection(currentFigure);
            BoardSizeConstrainerHandle boardSizeConstrainerHandle = new BoardSizeConstrainerHandle((BoardFigure) currentFigure);
            boardSizeConstrainerHandle.setDrawing((FrontDesignerDrawing) getView().getDrawing());
            boardSizeConstrainerHandle.trackEnd(new Point2D.Double(x, y));

            if (cellFigure != null)
            {
                CellDelimeterHandle cellDelimeterHandle = new CellDelimeterHandle(cellFigure);
                cellDelimeterHandle.delim((BoardFigure) currentFigure);
            }
        }
        centerX = 0;
        centerY = 0;
    }

    private void calculateCoordinates()
    {
        if (cellFigure != null)
        {
            CellFigure childFigure1 = null;
            CellFigure childFigure2 = null;

            switch (((BoardFigure) getOwner()).getOrientation())
            {
                case Vertical:
                    childFigure1 = cellFigure.findChild(CellFigure.Location.Left, cellFigure);
                    childFigure2 = cellFigure.findChild(CellFigure.Location.Right, cellFigure);
                    break;
                case Horizontal:
                    childFigure1 = cellFigure.findChild(CellFigure.Location.Top, cellFigure);
                    childFigure2 = cellFigure.findChild(CellFigure.Location.Bottom, cellFigure);
                    break;
                default:
                    break;
            }

            double heightChild2 = childFigure2.getEndPoint().y - childFigure2.getStartPoint().y;
            double heightParent = cellFigure.getEndPoint().y - cellFigure.getStartPoint().y;
            double lengthParent = cellFigure.getEndPoint().x - cellFigure.getStartPoint().x;
            double lengthChild1 = childFigure1.getEndPoint().x - childFigure1.getStartPoint().x;

            switch (((BoardFigure) getOwner()).getOrientation())
            {
                case Horizontal:
                    double xProportion = heightChild2 / heightParent;
                    x = lengthParent * xProportion + cellFigure.getStartPoint().x;
                    y = centerY;
                    break;
                case Vertical:
                    double yProportion = lengthChild1 / lengthParent;
                    x = centerX;
                    y = heightParent * yProportion + cellFigure.getStartPoint().y;
                    break;
                default:
                    break;
            }
        }
        else
        {
            x = centerX;
            y = centerY;
        }
    }

    @Override
    protected Rectangle basicGetBounds()
    {
        Rectangle r = new Rectangle(getLocation());
        int h = getHandlesize();
        r.x -= h / 2;
        r.y -= h / 2;
        r.width = r.height = h;
        return r;
    }
}
