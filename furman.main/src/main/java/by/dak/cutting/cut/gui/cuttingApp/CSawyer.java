/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui.cuttingApp;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.World;
import by.dak.cutting.cut.genetics.World.IWorldListener;
import by.dak.cutting.cut.genetics.WorldParams;
import by.dak.cutting.cut.guillotine.CIndividual;
import by.dak.cutting.cut.guillotine.CMigrator;
import by.dak.cutting.cut.guillotine.CRatingFunction;
import by.dak.cutting.cut.guillotine.Strips;

/**
 * @author Peca
 */
public class CSawyer extends IndividualSawyer
{

    WorldParams worldParams = new WorldParams();
    World world = null;

    @Override
    protected void compute()
    {
        Element[] elements = getCutSettings().getElements();
        Strips strips = getCutSettings().getStrips();

        CRatingFunction rFX = new CRatingFunction(strips, elements);
        rFX.setForceBestFit(this.isForceBestFit());
        rFX.setForceMinimumRatio(this.getForceMinimumRatio());
        CIndividual ind = new CIndividual(elements.length);

        //максимальное количестов Individual равно количеству элементов 
        this.worldParams.getPopulationParams().setMaxIndividualsCount(elements.length);

        world = new World(rFX, ind);
        world.setMigrator(new CMigrator());
        world.setWorldParams(this.worldParams);
        world.setForceMinimumMigrationRatio(this.getForceMigrationRatio());

        world.setWorldListener(new IWorldListener()
        {

            public void bestIndividualFound(Individual bestIndividual)
            {
                newIndividualFound(bestIndividual);
            }
        });

        while (this.getState() == Sawyer.States.RUNNING)
        {
            world.quickPass();
            incrementIterations();
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

    @Override
    public void stop()
    {
        super.stop();
        if (world != null)
        {
            world.stop();
        }
    }


}
