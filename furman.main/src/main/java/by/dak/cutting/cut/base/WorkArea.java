/*
 * WorkArea.java
 *
 * Created on 25. jen 2006, 22:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

/**
 * @author Peca
 */
public class WorkArea extends Rect
{

    // public ArrayList<WorkArea> neighbours;

    public WorkArea()
    {
        super();
        // neighbours = new ArrayList<WorkArea>();
    }

    public WorkArea(int width, int height)
    {
        super(0, 0, width, height);
        // neighbours = new ArrayList<WorkArea>();
    }

    public WorkArea(Rect rct)
    {
        super(rct);
        // neighbours = new ArrayList<WorkArea>();
    }

}
