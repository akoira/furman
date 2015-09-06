/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.genetics.*;

import java.util.List;

/**
 * @author Peca
 */
public class ARatingFunction extends RatingFunction
{
    private GuillotineTreeCutter cutter;
    private Element[] elements;
    private Strips strips;
    private int elementsTotalArea = 0;

    public ARatingFunction(Strips strips, Element[] elements)
    {
        this.strips = strips;
        this.elements = elements;
        this.cutter = new GuillotineTreeCutter();
        this.elementsTotalArea = Utils.getElementsTotalArea(elements);
    }

    public AIndividualRating rate(Individual individual)
    {
        AIndividual ind = (AIndividual) individual;
        SChromosome sch = ind.getSChromosome();
        NChromosome nch = ind.getNChromosome();
        RChromosome rch = ind.getRChromosome();

        //narezeme
        List<Segment> segments = this.strips.getSegments();
        boolean cutSuccess = false;
        int segmentIndex = 0;
        Segment seg = null;
        while (!cutSuccess && segmentIndex < segments.size())
        {
            Segment segment = segments.get(segmentIndex);
            seg = new Segment();
            seg.setLength(segment.getWidth());
            seg.setFixedLength(true);

            CutResult result = cutter.cutSegment(seg, this.elements, sch.getElements(), rch.getElements(), nch.getElements()[0], 3);
            cutSuccess = result.isSuccess();

            if (cutSuccess)
            {
                //vytvorime znak jedince
                seg.sortByWaste();
                Element[] els = seg.getElements();
                int[] indexes = new int[els.length];
                for (int i = 0; i < els.length; i++)
                {
                    indexes[i] = els[i].hashCode();
                }
                AIndividualSign sign = new AIndividualSign(indexes);

                //spocteme hodnoceni
                long usedArea = result.getCuttedElementsArea();
                long totalArea = seg.getLength() * seg.getWidth();
                ind = ind.getSister();
                ind.setSign(sign);
                if (ind != null)
                {
                    totalArea += ((AIndividualRating) ind.getIndividualRating()).getTotalArea();
                    usedArea += ((AIndividualRating) ind.getIndividualRating()).getUsedArea();
                }
                float progress = 1 - (result.getUncuttedIndexes().length / (float) this.elements.length);
                return new AIndividualRating(usedArea, totalArea, progress);
            }
            else segmentIndex++;
        }

        return new AIndividualRating(0, 1, 0);

    }
}
