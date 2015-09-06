/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui.cuttingApp;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.Population;
import by.dak.cutting.cut.genetics.PopulationParams;
import by.dak.cutting.cut.guillotine.GIndividual;
import by.dak.cutting.cut.guillotine.GRatingFunction;
import by.dak.cutting.cut.guillotine.Strips;

/**
 * @author Peca
 */
public class GSawyer extends IndividualSawyer implements IPopulationSawyer
{

    private PopulationParams populationParams = new PopulationParams();

    public GSawyer()
    {
        populationParams = new PopulationParams();
    }

    @Override
    protected void compute()
    {
        Element[] elements = getCutSettings().getElements();
        Strips strips = getCutSettings().getStrips();

        GRatingFunction rFX = new GRatingFunction(strips, elements);
        GIndividual ind = new GIndividual(elements.length);
        Population population = new Population(rFX, ind);
        population.setPopulationParams(this.populationParams);

        population.createFirstPopulation();

        while (this.getState() == Sawyer.States.RUNNING)
        {
            incrementIterations();
            population.nextGeneration();

            Individual popBestIndividual = population.getBestIndividual();
            newIndividualFound(popBestIndividual);
        }
    }

    public PopulationParams getPopulationParams()
    {
        return populationParams;
    }

    public void setPopulationParams(PopulationParams populationParams)
    {
        this.populationParams = populationParams;
    }


}
