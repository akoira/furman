/*
 * Cutter.java
 *
 * Created on 26. jen 2006, 11:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

/**
 * @author Peca
 */
public abstract class Cutter
{

    //  protected WorkSpace workSpace;
//    protected ArrayList<Element> elements;
    //protected int lastCuttedIndex;

    public Cutter()
    {

    }
    
/*    public Cutter(ArrayList<Element> elements, WorkSpace ws){
        this.workSpace = ws;
        this.elements = elements;
    }*/

    // public abstract Element by.dak.cutting.cut(int index);
    public abstract CuttedElement cut(Element el, AbstractWorkSpace workSpace);

    public abstract CuttedElement[] cut(Element[] elements, AbstractWorkSpace workSpace);

    public abstract CuttedElement[] cut(Element[] elements, int[] order, AbstractWorkSpace workSpace);
    //  public abstract Element cutFirst();
    // public abstract Element cutNext();
    // public abstract int cutAll();
}
