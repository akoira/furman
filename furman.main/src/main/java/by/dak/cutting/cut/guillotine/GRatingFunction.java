/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.common.Common;
import by.dak.cutting.cut.common.Statistics;
import by.dak.cutting.cut.genetics.*;

/**
 * @author Peca
 */
public class GRatingFunction extends RatingFunction
{

    private GuillotineCutter cutter;
    private Element[] elements;
    private Strips strips;
    private int elementsTotalArea = 0;

    public GRatingFunction(Strips strips, Element[] elements)
    {
        this.strips = strips;
        this.elements = elements;
        this.cutter = new GuillotineCutter(4);
        this.elementsTotalArea = Utils.getElementsTotalArea(elements);
    }

    public GIndividualRating rate(Individual individual)
    {
        Statistics.enterRatingFx();
        try
        {

            GIndividual ind = (GIndividual) individual;
            SChromosome sch = ind.getSChromosome();
            NChromosome nch = ind.getNChromosome();
            RChromosome rch = ind.getRChromosome();
            this.cutter.setDepth(ind.getDepth());
            //narezeme
            boolean cutSuccess;
            cutter.setSawWidth(strips.getSawWidth());
            strips.clearSegments();
            CutResult result = cutter.cutElements(strips,
                    this.elements,
                    sch.getElements(),
                    rch.getElements(),
                    nch.getElements());
            cutSuccess = result.isSuccess();

            if (result.isSuccess())
            {
                assert (Common.validateSolution(strips, elements));
                assert (Common.validateSolution(strips.clone(), elements));
            }

            long usedArea = 0;
            for (int i = 0; i < result.getSegmentsUsed(); i++)
            {
                Segment seg = strips.getSegment(i);
                if (i == result.getSegmentsUsed() - 1)
                {
                    usedArea += seg.getUsedArea();
                }
                else
                {
                    usedArea += seg.getTotalArea();
                }
            }

            if (!result.isSuccess())
            {
                usedArea += elementsTotalArea * 10L;
            }

            GIndividualRating rating = new GIndividualRating(
                    elementsTotalArea,
                    usedArea,
                    1.0f);

            SIndividualSign sign = new SIndividualSign(strips, rating.getRating());
            ind.setSign(sign);


            return rating;
        }
        finally
        {
            Statistics.exitRatingFx();
        }
    }
}
