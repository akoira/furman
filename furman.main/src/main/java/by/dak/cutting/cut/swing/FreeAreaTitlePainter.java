package by.dak.cutting.cut.swing;

import by.dak.cutting.cut.guillotine.Segment;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 27.04.11
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class FreeAreaTitlePainter extends AbstractPainter
{
    public abstract void titleFreeAria(Graphics g, Segment segment, int x2, int y2);
}
