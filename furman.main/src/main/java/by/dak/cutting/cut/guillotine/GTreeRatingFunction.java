/*
 * GRatingFunction.java
 *
 * Created on 5. kv�ten 2007, 11:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.genetics.*;

/**
 * @author Peca
 */
public class GTreeRatingFunction extends RatingFunction
{

    GuillotineTreeCutter cutter;
    Element[] elements;
    private int elementsTotalArea = 0;

    public GTreeRatingFunction(Element[] elements)
    {
        this.elements = elements;
        this.cutter = new GuillotineTreeCutter();
        this.elementsTotalArea = Utils.getElementsTotalArea(elements);
    }

    public IndividualRating rate(Individual individual)
    {
        GTreeIndividual ind = (GTreeIndividual) individual;
        Segment segment = ind.getSegment();
        segment.clear();
        GuillotineTreeCutter cutter = new GuillotineTreeCutter();
        GTreeChromosome gch = ind.getGTreeChromosome();
        RChromosome rch = ind.getRChromosome();
        SegmentNode node = gch.getSegmentNode();
        //if(!cutter.by.dak.cutting.cut(segment, node, rch.getElements()))return 0;
        if (!cutter.cut2(segment, node, 3, rch.getElements())) return new IndividualRating(0);

        //segment.getElementsOrder(sch.elements, 0);

        float f = segment.getLength() * segment.getWidth();
        f = elementsTotalArea / f;
        //f = f*0.9f + (segment.getUnWastedCount() / (float)segment.getItems().size())*0.1f;
        return new IndividualRating(f);
    }

    /**
     public float rate(Individual individual) {
     GIndividual ind = (GIndividual)individual;
     Segment segment = ind.getSegment();
     segment.clear();
     GuillotineCutter cutter = new GuillotineCutter();
     SChromosome sch = ind.getSChromosome();
     int[] indexes = sch.elements;
     //GChromosome gch = ind.getGChromosome();
     for(int i=0; i<sch.elements.length; i++){
     if(!cutter.cut(segment, elements[indexes[i]]))return 0;
     }
     //segment.getElementsOrder(sch.elements, 0);

     float f = segment.getLength() * segment.getWidth();
     f = elementsTotalArea / f;
     return f;
     }
     */


}
