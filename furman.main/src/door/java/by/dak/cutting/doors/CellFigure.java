package by.dak.cutting.doors;

import by.dak.cutting.draw.Constants;
import by.dak.draw.DefaultHierarchicalDrawing;
import org.jhotdraw.draw.Figure;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 13.09.2009
 * Time: 13:56:48
 */
public class CellFigure extends DefaultHierarchicalDrawing
{

    /**
     * Граничные елементы ячейки
     */
    protected List<Figure> boundsFigures = new ArrayList<Figure>();
    protected Figure dividerFigure;

    /**
     * Замкнутый shape для ячейки
     */
    private GeneralPath cellShape = new GeneralPath();


    public void setBounds(Point2D.Double anchor, Point2D.Double lead)
    {
        super.setBounds(anchor, lead);
    }

    @Override
    public void transform(AffineTransform tx)
    {
        super.transform(tx);
        //трасформируем shape ящейки.
        GeneralPath path = getCellShape();
        if (path != null)
        {
            path.transform(tx);
        }

    }

    @Override
    public boolean contains(Point2D.Double p)
    {
        return getBounds().contains(p);
    }

    @Override
    public Rectangle2D.Double getDrawingArea()
    {
        Rectangle2D.Double result = super.getDrawingArea();
        return result;
    }

    @Override
    public void draw(Graphics2D g)
    {
        drawCellShape(g);
        super.draw(g);
    }

    private void drawCellShape(Graphics2D g)
    {
        Shape shape = getCellShape();
        if (shape != null)
        {
            g.setColor(Constants.DEFAULT_STROKE_COLOR);
            g.setStroke(Constants.DEFAULT_DUSH_STROKE);
            g.draw(shape);
        }
    }


    public GeneralPath getCellShape()
    {
//        if (cellShape == null)
        {
            cellShape = SplitUtil.closedShape(SplitUtil.closedFigures(boundsFigures));
        }
        return cellShape;
    }


    @Override
    protected void invalidate()
    {
        super.invalidate();
        cellShape = null;
    }

}
