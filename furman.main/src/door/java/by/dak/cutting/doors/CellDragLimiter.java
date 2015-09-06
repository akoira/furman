package by.dak.cutting.doors;

import by.dak.draw.ChildFigure;
import by.dak.draw.DragLimiter;
import by.dak.draw.Graphics2DUtils;
import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.draw.Figure;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * User: akoyro
 * Date: 16.09.2009
 * Time: 14:55:00
 */
public class CellDragLimiter implements DragLimiter
{
    private AffineTransform affineTransform;

    public CellDragLimiter(AffineTransform affineTransform)
    {
        this.affineTransform = affineTransform;
    }

    @Override
    public boolean isDragLimited(Figure figure)
    {
        //todo 7630
        if (figure instanceof ChildFigure)
        {
            CompositeFigure parent = ((ChildFigure) figure).getParent();
            if (parent != null && parent instanceof CellFigure)
            {
                Rectangle2D.Double restR = parent.getBounds();
                Rectangle2D.Double rectF = Graphics2DUtils.translateBounds(figure.getBounds(), affineTransform);
                return !restR.contains(rectF);
            }
        }

        return false;
    }
}
