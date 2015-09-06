package by.dak.cutting.draw;

import org.jhotdraw.draw.GridConstrainer;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * User: akoyro
 * Date: 12.09.2009
 * Time: 13:52:52
 */
public interface Constants
{
    public static final float DEFAULT_FONT_SIZE = 12f; // размер подписи
    public static final float DEFAULT_LINE_THICKNESS = 2f; // толщина линий
    //todo зачем эта константа
    public static final Point2D.Double NEAR = new Point2D.Double(-1000, -1000);


    public static final int DEFAULT_GRID_STEP = 1;
    public static final GridConstrainer DEFAULT_GRID_CONSTRAINER = new GridConstrainer(DEFAULT_GRID_STEP, DEFAULT_GRID_STEP); //размер шага в решетке графического редакора

    public static final BasicStroke DEFAULT_DUSH_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            10.0f,
            new float[]{5.f},
            0.0f);
    Color DEFAULT_STROKE_COLOR = Color.BLACK;


    /**
     * Растаяния между двумя точками если меньше или равно значит точки соеденены вместе
     */
    public static final double DEFAULT_JOIN_OFFSET = 2;
}
