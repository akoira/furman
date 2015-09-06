/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui.cuttingApp;

import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.IndividualRating;
import by.dak.cutting.cut.guillotine.IndividualCutter;
import by.dak.cutting.cut.guillotine.Strips;

/**
 * @author Peca
 */
public abstract class IndividualSawyer extends Sawyer
{
    private Individual bestIndividual = null;


    protected boolean isBestIndividual(Individual individual)
    {
        if (individual == null) return false;
        if (this.bestIndividual == null) return true;

        return IndividualRating.compare(individual.getIndividualRating(), bestIndividual.getIndividualRating()) > 0;
    }

    public Individual getBestIndividual()
    {
        return bestIndividual;
    }

    protected void setBestIndividual(Individual bestIndividual)
    {
        this.bestIndividual = bestIndividual;
    }

    protected void newIndividualFound(Individual bestIndividual)
    {
        if (isBestIndividual(bestIndividual))
        {
            Strips newStrips = IndividualCutter.cutIndividual(bestIndividual, getCutSettings().getStrips(), getCutSettings().getElements(), getForceMinimumRatio(), isForceBestFit());
            if (newStrips != null)
            {
                setBestIndividual(bestIndividual.clone());
                newSolutionFound(newStrips);
            }
        }
    }

    @Override
    public void start()
    {
        super.start();
        this.bestIndividual = null;
    }

}
