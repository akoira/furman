package by.dak.draw;

import java.awt.*;

/**
 * @author admin
 * @version 0.1 14.10.2008
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public abstract class AbstractPainter implements Painter
{
    public Color getBackgroundColor()
    {
        return PaintAttributesProvider.getInstanse().getBackgroundColor();
    }

    public Color getForegroundColor()
    {
        return PaintAttributesProvider.getInstanse().getForegroundColor();
    }

    public float getPlanScale()
    {
        return PaintAttributesProvider.getInstanse().getPlanScale();
    }

    public Stroke getSelectionOutlineStroke()
    {
        return PaintAttributesProvider.getInstanse().getSelectionOutlineStroke();
    }

    public Paint getSelectionOutlinePaint()
    {
        return PaintAttributesProvider.getInstanse().getSelectionOutlinePaint();
    }

    public Color getSelectionColor()
    {
        return PaintAttributesProvider.getInstanse().getSelectionColor();
    }

}