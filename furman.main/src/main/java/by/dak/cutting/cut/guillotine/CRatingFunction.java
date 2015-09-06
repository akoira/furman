/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.common.Statistics;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.NChromosome;
import by.dak.cutting.cut.genetics.RatingFunction;
import by.dak.cutting.cut.genetics.SIndividualSign;

/**
 * @author Peca
 */
public class CRatingFunction extends RatingFunction implements IRatioConstraints
{

    private BCutter cutter;
    private Element[] elements;
    private Strips strips;
    private int elementsTotalArea = 0;
    private float ratioConstraints;
    private float forceMinimumRatio = 0.0f;
    private boolean forceBestFit = false;

    public CRatingFunction(Strips strips, Element[] elements)
    {
        this.strips = strips;
        this.elements = elements;
        this.cutter = new BCutter();
        this.elementsTotalArea = Utils.getElementsTotalArea(elements);
    }

    public CIndividualRating rate(Individual individual)
    {
        Statistics.enterRatingFx();
        try
        {
            CIndividual ind = (CIndividual) individual;
            NChromosome buildSequenceCh = ind.getBuildSequenceChromosome();
            NChromosome firstRowCountCh = ind.getFirstRowCountChromosome();

            cutter.setAllowRotation(strips.isAllowRotation());
            cutter.setSawWidth(strips.getSawWidth());

            //narezeme
            cutter.setRatio(Math.max(this.ratioConstraints, forceMinimumRatio));
            cutter.setForceBestFit(forceBestFit);

            //System.out.println(ratio);
            ind.setRatioConstraint(this.ratioConstraints);

            int sisterStripIndex = 0;
            long sisterCuttedArea = 0;
            long sisterUsedArea = 0;
            int sisterStripUsedLength = 0;
            Element[] elementsToCut = this.elements;

            CIndividual sister = ind.getSister();
            if (sister != null)
            {
                sisterStripIndex = sister.getStripIndex();
                sisterCuttedArea = sister.getCuttedArea();
                sisterUsedArea = sister.getUsedArea();
                sisterStripUsedLength = sister.getStripUsedLength();
                elementsToCut = sister.getUncuttedElements();
            }

            assert (elementsToCut.length == buildSequenceCh.getElements().length);

            CutResult result = cutter.cutRedSegment(
                    strips,
                    sisterStripIndex,
                    elementsToCut,
                    buildSequenceCh.getElements(),
                    firstRowCountCh.getElements()[0],
                    sisterCuttedArea,
                    sisterUsedArea,
                    sisterStripUsedLength,
                    true);

            float progress = 0;
            long usedArea = 0;
            long cuttedArea = 0;
            int segmentsUsed = Integer.MAX_VALUE; //устанавлеваем по умолчание очень много используемых сегментов
            if (result.isSuccess())
            {
                ind.setUncuttedElements(result.getUncuttedElements());
                segmentsUsed = result.getSegmentsUsed();
                ind.setStripIndex(result.getSegmentsUsed());
                ind.setCuttedArea(result.getCuttedElementsArea());
                ind.setUsedArea(result.getTotalUsedArea());
                ind.setStripUsedLength(strips.getSegment(result.getSegmentsUsed()).getUsedLength());
                cuttedArea = result.getCuttedElementsArea();
                usedArea = result.getTotalUsedArea();
                progress = 1 - (result.getUncuttedElements().length / (float) this.elements.length);
            }
            else
            {
                usedArea += elementsTotalArea * 100L;
            }

            CIndividualRating rating = new CIndividualRating(
                    cuttedArea,
                    usedArea,
                    progress, segmentsUsed);

            SIndividualSign sign = new SIndividualSign(strips, rating.getRating());
            ind.setSign(sign);

            return rating;
        }
        finally
        {
            Statistics.exitRatingFx();
        }
    }

    public float getRatioConstraints()
    {
        return ratioConstraints;
    }

    public void setRatioConstraints(float ratioConstraints)
    {
        this.ratioConstraints = ratioConstraints;
    }

    public void setForceBestFit(boolean forceBestFit)
    {
        this.forceBestFit = forceBestFit;
    }

    public void setForceMinimumRatio(float forceMinimumRatio)
    {
        this.forceMinimumRatio = forceMinimumRatio;
    }


}
