/*
 * PrimitiveCutter.java
 *
 * Created on 26. jen 2006, 11:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

/**
 * @author Peca
 */
public class PrimitiveCutter extends Cutter
{


    public CuttedElement cut(Element el, boolean rotate, AbstractWorkSpace workSpace)
    {
        Dimension dim = new Dimension(el);
        if (rotate) dim.rotate();
        WorkArea wa = workSpace.findTopLeftWorkArea(dim);
        CuttedElement rct = null;
        if (wa != null)
        {
            rct = new CuttedElement(dim.toRect().move(wa.getX(), wa.getY()), el);
            //workSpace.by.dak.cutting.cut(rct, wa, true);
            workSpace.cut(rct);
        }
        return rct;
    }


    public CuttedElement cut(Element el, AbstractWorkSpace workSpace)
    {
        return cut(el, false, workSpace);
    }


    public CuttedElement[] cut(Element[] elements, AbstractWorkSpace workSpace)
    {
        CuttedElement ce;
        CuttedElement[] cels = new CuttedElement[elements.length];
        for (int i = 0; i < elements.length; i++)
        {
            ce = cut(elements[i], workSpace);
            cels[i] = ce;
        }
        return cels;
    }

    public CuttedElement[] cut(Element[] elements, int[] order, boolean[] rotation, AbstractWorkSpace workSpace)
    {
        CuttedElement[] cels = new CuttedElement[elements.length];
        for (int i = 0; i < order.length; i++)
        {
            if (rotation == null) cels[i] = cut(elements[order[i]], workSpace);
            else cels[i] = cut(elements[order[i]], rotation[order[i]], workSpace);
        }
        return cels;
    }

    public CuttedElement[] cut(Element[] elements, int[] order, AbstractWorkSpace workSpace)
    {
        return cut(elements, order, null, workSpace);
    }


    public void cutQuick(Element el, AbstractWorkSpace workSpace)
    {
        Dimension dim = new Dimension(el);
        WorkArea wa = workSpace.findTopLeftWorkArea(dim);
        if (wa != null)
        {
            Rect rct = dim.toRect().move(wa.getX(), wa.getY());
            //workSpace.by.dak.cutting.cut(rct, wa, true);
            workSpace.cut(rct);
        }
    }

    public void cutQuick(Element[] elements, AbstractWorkSpace workSpace)
    {
        for (int i = 0; i < elements.length; i++)
        {
            cutQuick(elements[i], workSpace);
        }
    }

}
