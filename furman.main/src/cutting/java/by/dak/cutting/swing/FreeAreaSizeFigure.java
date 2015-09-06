package by.dak.cutting.swing;

import org.jhotdraw.draw.GroupFigure;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 12.03.11
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */

/**
 * для создания тестовых фигур, которые показывают свободную область
 */
public interface FreeAreaSizeFigure
{
    public static int FREE_AREA_FIGURE_OFFSET = 2; //сдвиг, чобы не перекрывать границы сегмента
    public static int FREE_AREA_FONT_SIZE = 42; //шрифта тектовых фигур

    public void createFreeAreaSizeFigure(GroupFigure groupFigure, SegmentFigure segment);
}
