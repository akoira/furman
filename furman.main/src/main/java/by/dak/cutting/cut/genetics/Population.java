/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import by.dak.cutting.cut.common.Common;
import by.dak.cutting.cut.common.Statistics;

import java.util.Comparator;
import java.util.HashSet;


/**
 * @author Peca
 */
public class Population
{

    public interface IPopulationListener
    {
        void bestIndividualFound(Individual bestIndividual);
    }

    private PopulationParams populationParams = new PopulationParams();
    private IndividualList individuals = new IndividualList();
    private IndividualList individualsToReproduce = new IndividualList();
    private IndividualList eliteIndividuals = new IndividualList();
    private HashSet<IndividualSign> twinsTable = new HashSet<IndividualSign>();
    private RatingFunction ratingFunction;
    private Individual individualPrototype;
    private Individual bestIndividual = null;
    private float bestRatingInActualGeneration;
    private float worstRatingInActualGeneration;
    private int generation;
    private String populationName;
    private IPopulationListener populationListener;
    private float averagePopulationRating;

    public Population(RatingFunction ratingFunction, Individual individualPrototype)
    {
        this.ratingFunction = ratingFunction;
        this.individualPrototype = individualPrototype;
    }

    public void createFirstPopulation()
    {
        this.clear();

        while (individuals.size() < populationParams.getMaxIndividualsCount())
        {

            Individual individual = this.individualPrototype.clone();
            individual.initRandom();

            addIndividual(individual);
        }

        this.rating();
    }

    public void clear()
    {
        this.individuals.clear();
        this.generation = 0;
        this.bestIndividual = null;
        this.bestRatingInActualGeneration = 1;
        this.worstRatingInActualGeneration = 0;
        this.twinsTable.clear();
        this.individualsToReproduce.clear();
        this.eliteIndividuals.clear();
    }

    public void nextGeneration()
    {
        this.selection();
        this.reproduction();
        this.rating();

        this.generation++;
    }

    private void selection()
    {
        SelectionParams selectionParams = populationParams.getSelectionParams();

        this.eliteIndividuals.clear();
        this.individualsToReproduce.clear();
        this.twinsTable.clear();

        //setridime jedince podle hodnoceni sestupne
        this.individuals.sort(new RatingComparator());

        for (int individualIndex = 0; individualIndex < this.individuals.size(); individualIndex++)
        {
            Individual individual = this.individuals.get(individualIndex);

            //odstranime dvojcata
            if (selectionParams.isKillTwins())
            {
                if (isTwin(individual)) continue;
            }

            float rating = individual.getRating();
            if (selectionParams.isShouldNormalize()) rating = normalizeRating(rating);

            //jedinec je moc stary
            if (getIndividualAge(individual) > selectionParams.getMaximumAge()) continue;

            //jedinec ma moc nizke hodnoceni
            if (rating < selectionParams.getMinimumRatioToSurvive())
            {
                //pokud uz je to tolikaty jedinec v rade, ktery muze byt zabit
                if (individualIndex / (float) this.individuals.size() > selectionParams.getMinimumThatWillSurvive())
                {
                    continue;
                }
            }

            //jedinec je elitni
            int maximumEliteIndividuals = (int) (selectionParams.getMaximumEliteIndividuals() * this.individuals.size());
            if (rating >= selectionParams.getElitismRatio())
            {
                if (eliteIndividuals.size() < maximumEliteIndividuals)
                {
                    eliteIndividuals.add(individual);
                }
            }

            individualsToReproduce.add(individual);
        }
    }

    private void reproduction()
    {
        ReproductionParams reproductionParams = populationParams.getReproductionParams();

        individuals.clear();

        //pridame vsechni elitni jedince
        for (Individual individual : this.eliteIndividuals)
        {
            addIndividual(individual);
        }

        //pokud neni co reprodukovat tak konec
        if (individualsToReproduce.size() == 0) return;

        while (individuals.size() < populationParams.getMaxIndividualsCount())
        {

            boolean shouldCross = Common.luck(reproductionParams.getCrossingProbability());
            boolean shouldMutate = Common.luck(reproductionParams.getMutationProbability());

            if (shouldCross)
            {
                Individual originalIndividual1 = individualsToReproduce.get(Common.nextInt(individualsToReproduce.size()));
                Individual originalIndividual2 = individualsToReproduce.get(Common.nextInt(individualsToReproduce.size()));

                Individual newIndividual1 = originalIndividual1.crossWith(originalIndividual2);
                Individual newIndividual2 = originalIndividual2.crossWith(originalIndividual1);

                addIndividual(newIndividual1);
                addIndividual(newIndividual2);

                Statistics.crossing(originalIndividual1, originalIndividual2, newIndividual1, newIndividual2);
            }

            if (shouldMutate)
            {
                Individual originalIndividual = individualsToReproduce.get(Common.nextInt(individualsToReproduce.size()));
                Individual clonedIndividual = originalIndividual.clone();
                clonedIndividual.mutate();
                addIndividual(clonedIndividual);

                Statistics.mutation(originalIndividual, clonedIndividual);
            }

            if (!shouldCross && !shouldMutate)
            {
                Individual originalIndividual = individualsToReproduce.get(Common.nextInt(individualsToReproduce.size()));
                addIndividual(originalIndividual);
            }
        }
    }

    public void rating()
    {
        this.bestRatingInActualGeneration = 0;
        this.worstRatingInActualGeneration = Float.MAX_VALUE;

        double sumRating = 0;
        for (Individual individual : this.individuals)
        {
            IndividualRating rating = this.rate(individual);
            individual.setRating(rating);
            sumRating += rating.getRating();

            if (rating.getRating() > this.bestRatingInActualGeneration)
                this.bestRatingInActualGeneration = rating.getRating();
            if (rating.getRating() < this.worstRatingInActualGeneration)
                this.worstRatingInActualGeneration = rating.getRating();

            if (this.bestIndividual == null) this.setBestIndividual(individual.clone());
            if (IndividualRating.compare(rating, this.bestIndividual.getIndividualRating()) > 0)
            {
                setBestIndividual(individual.clone());
            }
        }
        if (this.individuals.size() == 0) averagePopulationRating = 0;
        else averagePopulationRating = (float) (sumRating / (float) this.individuals.size());
    }

    private void setBestIndividual(Individual individual)
    {
        this.bestIndividual = individual;
        if (populationListener != null)
        {
            populationListener.bestIndividualFound(individual);
        }
    }

    private boolean isTwin(Individual individual)
    {
        IndividualSign sign = individual.getSign();
        if (sign == null) return false;

        if (this.twinsTable.contains(sign)) return true;

        this.twinsTable.add(sign);
        return false;
    }

    public IndividualRating rate(Individual individual)
    {
        return this.ratingFunction.rate(individual);
    }

    public void addIndividual(Individual individual)
    {
        this.individuals.add(individual);
        individual.setPopulation(this);
    }

    private int getIndividualAge(Individual individual)
    {
        return this.generation - individual.getBornTime();
    }

    public float normalizeRating(float rating)
    {
        float k = 1 / (bestRatingInActualGeneration - worstRatingInActualGeneration);
        float q = -k * worstRatingInActualGeneration;
        return k * rating + q;
    }

    public Individual getBestIndividual()
    {
        return bestIndividual;
    }

    public int getGeneration()
    {
        return generation;
    }

    public Individual[] getIndividuals()
    {
        return individuals.toArray();
    }

    public String getPopulationName()
    {
        return populationName;
    }

    public void setPopulationName(String populationName)
    {
        this.populationName = populationName;
    }

    public int getIndividualsCount()
    {
        return this.individuals.size();
    }

    public PopulationParams getPopulationParams()
    {
        return populationParams;
    }

    public void setPopulationParams(PopulationParams populationParams)
    {
        this.populationParams = populationParams;
    }

    public Individual getIndividual(int index)
    {
        return this.individuals.get(index);
    }

    private class RatingComparator implements Comparator
    {
        public int compare(Object o1, Object o2)
        {
            Individual i1 = (Individual) o1;
            Individual i2 = (Individual) o2;
            IndividualRating r1 = i1.getIndividualRating();
            IndividualRating r2 = i2.getIndividualRating();
            if ((r1 == null) || (r2 == null)) return 0;
            else return IndividualRating.compare(r2, r1);
        }
    }

    @Override
    public String toString()
    {
        return this.populationName;
    }

    public IPopulationListener getPopulationListener()
    {
        return populationListener;
    }

    public void setPopulationListener(IPopulationListener populationListener)
    {
        this.populationListener = populationListener;
    }

    public float getAveragePopulationRating()
    {
        return averagePopulationRating;
    }

}
