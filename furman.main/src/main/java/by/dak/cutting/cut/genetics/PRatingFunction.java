/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.guillotine.GIndividual;
import by.dak.cutting.cut.guillotine.GRatingFunction;
import by.dak.cutting.cut.guillotine.Strips;

import java.io.IOException;

/**
 * @author Peca
 */
public class PRatingFunction extends RatingFunction
{

    private Population population;
    private CutSettings cutSettings;

    public PRatingFunction() throws IOException
    {
        cutSettings = CutSettings.loadFromFile("Board01.tocut");

        Element[] elements;
        elements = cutSettings.getElements();

        Strips strips = new Strips(cutSettings.getSheets());
        strips.setSawWidth(0);
        strips.setAllowRotation(true);
        RatingFunction rFx = new GRatingFunction(strips, elements);
        population = new Population(rFx, new GIndividual(elements.length));
    }

    public IndividualRating rate(Individual individual)
    {
        PIndividual ind = (PIndividual) individual;

        population.clear();
        population.setPopulationParams(ind.getPopulationParams());

        population.createFirstPopulation();
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + 10 * Utils.SECOND)
        {
            population.nextGeneration();
            if (Utils.shouldStop())
            {
                break;
            }
        }

        float avg = population.getAveragePopulationRating();
        float best = 0;
        if (population.getBestIndividual() != null)
        {
            best = population.getBestIndividual().getRating();
        }

        float rating = best * avg;

        return new IndividualRating(rating);
    }

}
