/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.common.Common;
import by.dak.cutting.cut.common.Statistics;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.NChromosome;
import by.dak.cutting.cut.genetics.RatingFunction;
import by.dak.cutting.cut.genetics.SIndividualSign;

/**
 * @author Peca
 */
public class BRatingFunction extends RatingFunction
{

    private BCutter cutter;
    private Element[] elements;
    private Strips strips;
    private int elementsTotalArea = 0;
    private float forceMinimumRatio = 0.0f;
    private boolean forceBestFit = false;

    public BRatingFunction(Strips strips, Element[] elements)
    {
        this.strips = strips;
        this.elements = elements;
        this.cutter = new BCutter();
        this.elementsTotalArea = Utils.getElementsTotalArea(elements);
    }

    public BIndividualRating rate(Individual individual)
    {
        Statistics.enterRatingFx();
        try
        {
            BIndividual ind = (BIndividual) individual;
            NChromosome buildSequenceCh = ind.getBuildSequenceChromosome();
            NChromosome firstRowCountCh = ind.getFirstRowCountChromosome();

            cutter.setAllowRotation(strips.isAllowRotation());
            cutter.setSawWidth(strips.getSawWidth());
            cutter.setForceBestFit(this.forceBestFit);

            //narezeme
            Individual bestIndividual = null;
            if (individual.getPopulation() != null)
            {
                bestIndividual = individual.getPopulation().getBestIndividual();
            }
            float ratio = 0;
            if (bestIndividual != null)
            {
                ratio = bestIndividual.getRating();
            }
            ratio = Math.max(ratio, forceMinimumRatio);
            cutter.setRatio(ratio);
            //System.out.println(ratio);
            ind.setRatioConstraint(ratio);

            CutResult result = cutter.cutElements(strips,
                    this.elements,
                    buildSequenceCh.getElements(),
                    firstRowCountCh.getElements());
            //return result;

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
                usedArea += elementsTotalArea * 100L;
            }

            BIndividualRating rating = new BIndividualRating(elementsTotalArea,
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

    public void setForceMinimumRatio(float forceMinimumRatio)
    {
        this.forceMinimumRatio = forceMinimumRatio;
    }

    public void setForceBestFit(boolean forceBestFit)
    {
        this.forceBestFit = forceBestFit;
    }


}
