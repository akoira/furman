/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import by.dak.cutting.cut.guillotine.CIndividual;
import by.dak.cutting.cut.guillotine.IMigratingIndividual;
import by.dak.cutting.cut.guillotine.IRatioConstraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Peca
 */
public class World
{
    public interface IWorldListener
    {
        void bestIndividualFound(Individual bestIndividual);
    }

    private WorldParams worldParams = new WorldParams();
    private ArrayList<Population> populationList;
    private Individual individualPrototype;
    private RatingFunction ratingFunction;
    private Migrator migrator;
    private Population finalPopulation;
    private Individual bestIndividual;
    private int iteration;
    private IWorldListener worldListener;
    private float forceMinimumMigrationRatio = 0.0f;
    private boolean shouldStop = false;

    public World(RatingFunction ratingFunction, Individual individualPrototype)
    {
        this.populationList = new ArrayList<Population>();
        this.ratingFunction = ratingFunction;
        this.individualPrototype = individualPrototype;
        this.finalPopulation = createNewPopulation();
        this.finalPopulation.setPopulationName("Final population");
        addPopulation(createFirstPopulation());
        /*for(int i=0; i<11; i++){
            addPopulation(createNewPopulation());
        }*/
    }

    public void reset()
    {
        this.iteration = 0;
        for (Population population : this.populationList)
        {
            population.clear();
            population.createFirstPopulation();
        }
        this.populationList.clear();
        this.finalPopulation.clear();
        this.bestIndividual = null;
        this.shouldStop = false;
        addPopulation(createFirstPopulation());
    }

    private void addPopulation(Population population)
    {
        populationList.add(population);
        population.setPopulationName(String.valueOf(populationList.size()));
    }

    private Population createNewPopulation()
    {
        Population pop = new Population(this.ratingFunction, this.individualPrototype);
        pop.setPopulationParams(worldParams.getPopulationParams());
        return pop;
    }

    private Population createFirstPopulation()
    {
        Population pop = createNewPopulation();
        pop.createFirstPopulation();
        return pop;
    }

    public Population[] getPopulations()
    {
        Population[] pop = populationList.toArray(new Population[populationList.size() + 1]);
        pop[pop.length - 1] = this.finalPopulation;
        return pop;
    }


    private boolean isGoodEnough(Individual ind)
    {
        if (ind.getRating() <= 0) return false;
        if (this.bestIndividual == null) return true;

        /**
         * Элементов должно быть всегда меньше 
         */
        if (((CIndividual) ind).getStripIndex() > ((CIndividual) bestIndividual).getStripIndex()) return false;

        if (forceMinimumMigrationRatio == 0.0f)
        {
            return IndividualRating.compare(ind.getIndividualRating(), bestIndividual.getIndividualRating()) > 0;
        }
        else
        {
            return ind.getRating() >= Math.max(forceMinimumMigrationRatio, bestIndividual.getRating());
        }
    }

    private void migrate(Population source, Population dest)
    {
        dest.clear();

        IndividualList sourceList = new IndividualList();
        for (Individual individual : source.getIndividuals())
        {
            if (isGoodEnough(individual)) sourceList.add(individual);
        }

        migrate(sourceList, dest);
    }

    private Individual[] getQualityIndividuals(Population population)
    {
        Individual[] sourceIndividuals = population.getIndividuals();
        Arrays.sort(sourceIndividuals, new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                IMigratingIndividual ind1 = (IMigratingIndividual) o1;
                IMigratingIndividual ind2 = (IMigratingIndividual) o2;

                return -Float.compare(ind1.getOffspringsQuality(), ind2.getOffspringsQuality());
            }
        });

        int elitismCount = (int) Math.min(sourceIndividuals.length * worldParams.getElitismRatio(), sourceIndividuals.length);
        Individual[] destIndividuals = new Individual[elitismCount];
        for (int i = 0; i < destIndividuals.length; i++)
        {
            destIndividuals[i] = sourceIndividuals[i];
        }

        return destIndividuals;
    }

    private void migrateX(Population source, Population dest)
    {
        IndividualList sourceList = new IndividualList();
        for (Individual individual : source.getIndividuals())
        {
            if (isGoodEnough(individual)) sourceList.add(individual);
        }

        Individual[] destQualityIndividuals = this.getQualityIndividuals(dest);
        dest.clear();
        for (Individual individual : destQualityIndividuals)
        {
            dest.addIndividual(individual.clone());
        }

        migrate(sourceList, dest);
    }

    public void migrate(IndividualList sourceIndividuals, Population destPopulation)
    {
        if (sourceIndividuals.size() == 0) return;

        int cnt = destPopulation.getIndividualsCount();
        int size = destPopulation.getPopulationParams().getMaxIndividualsCount();
        int sourceIndex = 0;
        boolean secondPass = false;
        while (cnt < size)
        {
            Individual ind = sourceIndividuals.get(sourceIndex);
            Individual ind2 = this.migrator.Migrate(ind);

            //jedinec je komletni
            if (((IMigratingIndividual) ind).isComplete())
            {
                //pokud je lepsi nez nejlepsi jedinec, tak ho pridame do finalni populace, jinak nic
                if (isGoodEnough(ind))
                {
                    this.finalPopulation.addIndividual(ind.clone());
                    this.bestIndividual = ind.clone();
                    ((IRatioConstraints) this.ratingFunction).setRatioConstraints(ind.getRating());
                    if (worldListener != null)
                    {
                        worldListener.bestIndividualFound(this.bestIndividual);
                    }
                }
                //HACK: kvuli zacykleni
                cnt++;
            }
            else
            {
                //pokud to je druhy prubeh, tak jedince zmutujeme at je trochu jiny
                if (secondPass) ind2.mutate();
                destPopulation.addIndividual(ind2);
                cnt++;
            }

            sourceIndex++;
            sourceIndex = sourceIndex % sourceIndividuals.size();
            if (sourceIndex == 0) secondPass = true;
        }
        destPopulation.rating();
    }

    public void migrate(Population source)
    {
        if (source.getIndividualsCount() == 0) return;

        int destIndex = 1 + populationList.indexOf(source);
        if (destIndex >= populationList.size())
        {
            addPopulation(createNewPopulation());
        }

        Population dest = populationList.get(destIndex);
        this.migrateX(source, dest);

        if (destIndex == 1)
        {
            //source.clear();
            //source.createFirstPopulation();

        }
    }

    public int currentPopulationIndex = 0;

    public void nextGeneration()
    {
        /*Population[] populations = populationList.toArray(new Population[populationList.size()]);
        for(int i=populations.length-1; i>=0; i--){
            Population pop = populations[i];
            pop.nextGeneration();
            migrate(pop);
        }*/
        Population population = populationList.get(currentPopulationIndex);
        population.nextGeneration();
        boolean shouldMigrate = true;
        if ((population.getBestIndividual() != null) && (this.bestIndividual != null))
        {
            IndividualRating popBestRating = population.getBestIndividual().getIndividualRating();
            IndividualRating worldBestRating = this.bestIndividual.getIndividualRating();
            shouldMigrate = (IndividualRating.compare(popBestRating, worldBestRating) > 0);
        }
        shouldMigrate = shouldMigrate && population.getGeneration() > 10;
        if ((!shouldMigrate) && (population.getGeneration() > 20)) currentPopulationIndex = 0;


        if (shouldMigrate)
        {
            migrate(population);
            currentPopulationIndex = (currentPopulationIndex + 1) % populationList.size();
        }
        /*if(population.getGeneration() > 100) {
            
        }*/
    }

    public void quickPass()
    {
        //iteration++;
        currentPopulationIndex = 0;
        do
        {
            if (shouldStop) break;
            Population population = populationList.get(currentPopulationIndex);
            for (int i = 0; i < worldParams.getMaximumGenerationsBeforeMigration(); i++)
            {
                if (shouldStop)
                    break;
                population.nextGeneration();
                if (population.getBestIndividual() == null) continue;
                if (this.getBestIndividual() == null) break;

                IndividualRating populationBestRating = population.getBestIndividual().getIndividualRating();
                IndividualRating worldBestRating = this.bestIndividual.getIndividualRating();
                if (IndividualRating.compare(populationBestRating, worldBestRating) > 0)
                {
                    if (i > worldParams.getMinimumGenerationsBeforeMigration())
                    {
                        break;
                    }
                }
            }
            migrate(population);
            currentPopulationIndex = (currentPopulationIndex + 1) % populationList.size();
        }
        while (currentPopulationIndex != 0);

        backPropagateOffsprings();

        Population firstPopulation = populationList.get(0);
        Individual[] sourceQualityIndividuals = this.getQualityIndividuals(firstPopulation);
        firstPopulation.clear();
        for (Individual individual : sourceQualityIndividuals)
        {
            firstPopulation.addIndividual(individual.clone());
        }

        while (firstPopulation.getIndividualsCount() < firstPopulation.getPopulationParams().getMaxIndividualsCount())
        {
            Individual newIndividual = this.individualPrototype.clone();
            newIndividual.initRandom();
            newIndividual.setBornTime(this.iteration);
            firstPopulation.addIndividual(newIndividual);
        }
        firstPopulation.rating();
    }

    public Migrator getMigrator()
    {
        return migrator;
    }

    public void setMigrator(Migrator migrator)
    {
        this.migrator = migrator;
    }

    public Individual getBestIndividual()
    {
        return bestIndividual;
    }

    private void backPropagateOffsprings()
    {
        //nejprve smazat kvalitu u vsech jedincu
        for (Population population : this.populationList)
        {
            for (Individual individual : population.getIndividuals())
            {
                IMigratingIndividual migratingIndividual = (IMigratingIndividual) individual;
                migratingIndividual.setOffspringsQuality(0);
            }
        }

        ArrayList<Population> populations = new ArrayList<Population>();
        populations.add(this.finalPopulation);
        for (int i = this.populationList.size() - 1; i > 0; i--)
        {
            populations.add(this.populationList.get(i));
        }

        //zpetne propagovat kvalitu jedincu sestram
        for (Population population : populations)
        {
            for (Individual individual : population.getIndividuals())
            {
                IMigratingIndividual migratingIndividual = (IMigratingIndividual) individual;
                float quality = individual.getRating() * migratingIndividual.getProgress();
                IMigratingIndividual sister = (IMigratingIndividual) migratingIndividual.getSister();

                if (sister == null) continue;  //muze se stat ze do finalni populace se dostal nekdo z 1. populace
                quality = Math.max(sister.getOffspringsQuality(), quality);
                sister.setOffspringsQuality(quality);
            }
        }
    }

    public WorldParams getWorldParams()
    {
        return worldParams;
    }

    public void setWorldParams(WorldParams worldParams)
    {
        this.worldParams = worldParams;
    }

    public int getIteration()
    {
        return iteration;
    }

    public IWorldListener getWorldListener()
    {
        return worldListener;
    }

    public void setWorldListener(IWorldListener worldListener)
    {
        this.worldListener = worldListener;
    }

    public void setForceMinimumMigrationRatio(float forceMinimumMigrationRatio)
    {
        this.forceMinimumMigrationRatio = forceMinimumMigrationRatio;
    }

    public void stop()
    {
        this.shouldStop = true;
    }

}
