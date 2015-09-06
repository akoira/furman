/*
 * CuttedElement.java
 *
 * Created on 28. jen 2006, 16:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

/**
 * @author Peca
 */
public class CuttedElement extends Rect
{

    public Element element;
    //public boolean rotated;

    /**
     * Creates a new instance of CuttedElement
     */

    public CuttedElement(Rect cuttedRect, Element element)
    {
        super(cuttedRect);
        this.element = element;
    }

}
