/*
 * WorkArea_O1.java
 *
 * Created on 1. prosinec 2006, 17:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

/**
 * @author Peca
 */
public class WorkArea_O1 extends WorkArea
{

    public boolean isNew;

    public WorkArea_O1()
    {
        super();
        isNew = true;
    }

    public WorkArea_O1(int width, int height)
    {
        super(width, height);
        isNew = true;
    }

    public WorkArea_O1(Rect rct)
    {
        super(rct);
        isNew = true;
    }

}
