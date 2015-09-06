/*
 * RatingFunction.java
 *
 * Created on 27. ��jen 2006, 21:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import by.dak.cutting.cut.base.*;
//import by.dak.cutting.cut.base.util;

/**
 * @author Peca
 */
public class SRRatingFunction extends RatingFunction
{

    // public Factory factory;
    private PrimitiveCutter cutter;
    private AbstractWorkSpace workSpace;
    private WorkArea[] workSheets;
    private Element[] elements;
    private int elementsTotalArea = 0;
    private int workSpaceTotalArea = 0;


    public SRRatingFunction(WorkArea[] workSheets, Element[] elements)
    {
        this.workSheets = workSheets;
        this.cutter = new PrimitiveCutter();
        this.workSpace = new WorkSpace_O1(workSheets);
        this.workSpace.minElementSize = Utils.getMinElementSize(elements);
        this.workSpace.minMaxElementSize = Utils.getMinMaxElementSize(elements);
        this.workSpace.minMinElementSize = Utils.getMinMinElementSize(elements);
        this.elements = elements;
        this.elementsTotalArea = Utils.getElementsTotalArea(elements);
        this.workSpaceTotalArea = workSpace.getTotalArea();
    }


    public int getIdealRightEmptyArea()
    {
        int area = 0;
        int area2 = 0;
        for (Element el : elements)
        {     //plocha zaplnena vsemi obdelniky
            area += el.getArea();
        }
        for (WorkArea wa : workSheets)
        {  //plocha vsech sheetu co se maji rezat
            area2 += wa.getArea();
        }
        return area2 - area;
    }

    public IndividualRating rate(Individual individual)
    {
        workSpace.clear();
        SRIndividual ind = (SRIndividual) individual;
        SChromosome sch = ind.getSChromosome();
        RChromosome rch = ind.getRChromosome();
        for (int i = 0; i < sch.getElements().length; i++)
        {
            CuttedElement cel = cutter.cut(elements[sch.getElements()[i]], rch.getElements()[sch.getElements()[i]], workSpace);
            if (cel == null)
            {    //nepovedlo se to vsechno narezat - uspesnost je 0
                return new IndividualRating(0);
            }
        }
        //return (float)workSpace.getRightEmptyArea() / (float)getIdealRightEmptyArea();
//        float r1 = 1.0f - ((float)workSpace.getLeftEmptyArea() / (float)workSpace.getTotalArea());
//        float r2 = workSpace.getTotalArea() - ReportUtils.getElementsTotalArea(elements) - workSpace.getRightEmptyArea();
//        r2 = 1.0f - (r2 / (float)workSpace.getTotalArea());         
        //float r3 = (workSpace.getTotalArea() - workSpace.getRightEmptyArea() - ReportUtils.getElementsTotalArea(elements));
        // r3 = 1.0f - (r3 / (float)workSpace.getTotalArea());
        float r4 = (float) workSpace.getRightEmptyArea() / (workSpaceTotalArea - elementsTotalArea);
        return new IndividualRating(r4);//r1*r2;  
    }
}
