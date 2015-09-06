package by.dak.cutting.cut.swing;

import by.dak.cutting.cut.guillotine.Segment;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 27.04.11
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
public abstract class ElementPainter extends AbstractPainter
{
    public abstract void paintElement(Graphics g, Segment segment, int x2, int y2, int w, int h);
}
