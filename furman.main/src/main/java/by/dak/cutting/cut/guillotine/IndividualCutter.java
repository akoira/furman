/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.NChromosome;
import by.dak.cutting.cut.genetics.RChromosome;
import by.dak.cutting.cut.genetics.SChromosome;

import java.util.ArrayList;

/**
 * @author Peca
 */
public class IndividualCutter
{

    private static boolean sortStrips = true;

    public static Strips cutIndividual(Individual individual, Strips strips, Element[] elements)
    {
        return cutIndividual(individual, strips, elements, 0.0f, false);
    }

    public static Strips cutIndividual(Individual individual, Strips strips, Element[] elements, float forceMinimumRatio, boolean forceBestFit)
    {
        return cutIndividualInternal(individual, strips, elements, forceMinimumRatio, forceBestFit);

    }

    private static Strips cutIndividualInternal(Individual individual, Strips strips, Element[] elements, float forceMinimumRatio, boolean forceBestFit)
    {
        Strips cuttedStrips = null;
        if (individual.getClass().equals(GIndividual.class))
        {
            cuttedStrips = cutGIndividual((GIndividual) individual, strips, elements);
        }
        else if (individual.getClass().equals(BIndividual.class))
        {
            cuttedStrips = cutBIndividual((BIndividual) individual, strips, elements, forceMinimumRatio, forceBestFit);
        }
        else if (individual.getClass().equals(CIndividual.class))
        {
            cuttedStrips = cutCIndividual((CIndividual) individual, strips, elements, forceMinimumRatio, forceBestFit);
        }

        if (cuttedStrips != null && sortStrips)
        {
            cuttedStrips.sort();
        }
        return cuttedStrips;
    }

    private static Strips cutGIndividual(GIndividual individual, Strips strips, Element[] elements)
    {
        GuillotineCutter cutter = new GuillotineCutter(individual.getDepth());

        SChromosome sch = individual.getSChromosome();
        NChromosome nch = individual.getNChromosome();
        RChromosome rch = individual.getRChromosome();

        cutter.setSawWidth(strips.getSawWidth());

        strips.clearSegments();
        CutResult result = cutter.cutElements(strips,
                elements,
                sch.getElements(),
                rch.getElements(),
                nch.getElements());

        if (result.isSuccess()) return strips.clone();
        else return null;
    }

    private static Strips cutBIndividual(BIndividual individual, Strips strips, Element[] elements, float forceMinimumRatio, boolean forceBestFit)
    {
        BCutter cutter = new BCutter();

        NChromosome buildSequenceCh = individual.getBuildSequenceChromosome();
        NChromosome firstRowCountCh = individual.getFirstRowCountChromosome();

        cutter.setAllowRotation(strips.isAllowRotation());
        cutter.setSawWidth(strips.getSawWidth());
        cutter.setForceBestFit(forceBestFit);

        float ratio = Math.max(individual.getRatioConstraint(), forceMinimumRatio);
        cutter.setRatio(ratio);

        strips.clearSegments();
        CutResult result = cutter.cutElements(strips,
                elements,
                buildSequenceCh.getElements(),
                firstRowCountCh.getElements()
        );

        if (result.isSuccess()) return strips.clone();
        else return null;
    }

    private static Strips cutCIndividual(CIndividual individual, Strips strips, Element[] elements, float forceMinimumRatio, boolean forceBestFit)
    {
        CIndividual lastIndividual = individual;
        ArrayList<CIndividual> individuals = new ArrayList<CIndividual>();
        CIndividual ind = lastIndividual;
        while (ind != null)
        {
            individuals.add(ind);
            ind = ind.getSister();
        }
        ind = lastIndividual;

        BCutter cutter = new BCutter();
        cutter.setAllowRotation(strips.isAllowRotation());
        cutter.setSawWidth(strips.getSawWidth());
        cutter.setForceBestFit(forceBestFit);

        strips.clearSegments();

        for (int individualIndex = individuals.size() - 1; individualIndex >= 0; individualIndex--)
        {
            ind = individuals.get(individualIndex);
            int sisterStripIndex = 0;
            long sisterCuttedArea = 0;
            long sisterUsedArea = 0;
            int sisterStripUsedLength = 0;
            Element[] elementsToCut = elements;

            CIndividual sister = ind.getSister();
            if (sister != null)
            {
                sisterStripIndex = sister.getStripIndex();
                sisterCuttedArea = sister.getCuttedArea();
                sisterUsedArea = sister.getUsedArea();
                sisterStripUsedLength = sister.getStripUsedLength();
                elementsToCut = sister.getUncuttedElements();
            }

            NChromosome buildSequenceCh = ind.getBuildSequenceChromosome();
            NChromosome firstRowCountCh = ind.getFirstRowCountChromosome();

            cutter.setRatio(Math.max(forceMinimumRatio, ind.getRatioConstraint()));

            CutResult result = cutter.cutRedSegment(
                    strips,
                    sisterStripIndex,
                    elementsToCut,
                    buildSequenceCh.getElements(),
                    firstRowCountCh.getElements()[0],
                    sisterCuttedArea,
                    sisterUsedArea,
                    sisterStripUsedLength,
                    false);

            if (!result.isSuccess()) return null;
        }
        return strips.clone();
    }

    public static void setSortStrips(boolean sortStrips)
    {
        IndividualCutter.sortStrips = sortStrips;
    }


}
