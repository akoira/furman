/*
 * AbstractWorkSpace.java
 *
 * Created on 1. prosinec 2006, 17:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

/**
 * @author Peca
 */
public abstract class AbstractWorkSpace
{

    // protected abstract AbstractWorkSpace();
    public int minElementSize = 0;
    public int minMaxElementSize = 0;
    public int minMinElementSize = 0;

    protected abstract boolean isWorkAreaOverlapped(WorkArea wa);

    public abstract Dimension getPlateSize();

    public abstract int getFreeArea();

    public abstract void cut(Rect rct);

    public abstract int getTotalArea();

    public abstract int getRightEmptyArea();

    public abstract int getRightEmptyArea(WorkArea workSheet);

    public abstract void clear();

    public abstract WorkArea findTopLeftWorkArea(Dimension elementSize);

    public abstract WorkArea[] getWorkAreas();
}
