package by.dak.cutting.cut.guillotine;

import java.awt.*;

/**
 * User: akoyro
 * Date: 27.07.2010
 * Time: 14:00:20
 */
public enum SegmentType
{
    red(Color.LIGHT_GRAY),
    blue(Color.LIGHT_GRAY),
    green(Color.LIGHT_GRAY),
    gray(Color.LIGHT_GRAY),
    element(Color.LIGHT_GRAY),
    free(new Color(194, 214, 155));


    private Color color;

    SegmentType(Color color)
    {
        this.color = color;
    }

    public Color getColor()
    {
        return color;
    }
}
