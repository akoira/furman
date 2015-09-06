/*
 * GeneticsTest1.java
 *
 * Created on 27. ��jen 2006, 22:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.World;
import by.dak.cutting.cut.guillotine.CIndividual;
import by.dak.cutting.cut.guillotine.CMigrator;
import by.dak.cutting.cut.guillotine.CRatingFunction;
import by.dak.cutting.cut.guillotine.Strips;

import javax.swing.*;
import java.io.IOException;

/**
 * @author Peca
 */
public class GeneticsTest4
{


    public static void main(String args[]) throws IOException
    {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        CutSettings cutSettings;

        if (args.length > 0)
        {
            String fileName = args[0];
            cutSettings = CutSettings.loadFromFile(fileName);
        }
        else
        {
            cutSettings = CutSettings.loadFromFile("RandomBoard100.tocut");
        }

        Element[] elements;
        elements = cutSettings.getElements();


        Strips strips = new Strips(cutSettings.getSheets());
        strips.setSawWidth(0);
        strips.setAllowRotation(true);

        float forceMinimumRatio = 0.94f;
        boolean forceBestFit = true;

        CRatingFunction rFX = new CRatingFunction(strips, elements);
        CIndividual ind = new CIndividual(elements.length);
        rFX.setForceMinimumRatio(forceMinimumRatio);
        rFX.setForceBestFit(forceBestFit);

        World world = new World(rFX, ind);
        world.setMigrator(new CMigrator());
        world.setForceMinimumMigrationRatio(forceMinimumRatio);


        /* population = new Population(rFX, ind);
        PopulationParams params = new PopulationParams();
        params.setPopulationSize(populationCount);
        params.setSurvive(survive);
        params.setPMutation(pMutation);
        params.setChromMutationCount(chromMutationCount);
        params.setKeep(keep);
        params.setPNewIndividual(pNewIndividual);
        params.setNewIndividualImmortalTime(newIndividualImmortalTime);
        params.setMaxLifeTime(maxLifeTime);
        params.setMaxStagnationDuration(maxStagnationDuration);
        population.setPopulationParams(params);
        population.createFirstPopulation();
        population.rateAll();*/

        //population.nextGenerations(1);


        CommonGui.setElements(elements);
        CommonGui.setStrips(strips);
        CommonGui.setForceMinimumRatio(forceMinimumRatio);
        CommonGui.setForceBestFit(forceBestFit);

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {

        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        WorldView wv = new WorldView(world);
        //PopulationView pv = new PopulationView(population);

        frame.getContentPane().add(wv);
        frame.setSize(600, 500);
        frame.setVisible(true);
        //pv.showBestIndividual();
/*        int iteration = 0;
        while (System.in.available() == 0) {
            world.reset();
            boolean b = (iteration % 2) == 0;
            if(b) world.qualityCountTemp = 100;
            else world.qualityCountTemp = 0;
            for (int i = 0; i < 10; i++) {
                world.quickPass();
            }

            if (world.getBestIndividual() != null) {
                System.out.print(b);
                System.out.print(" ");
                System.out.println(world.getBestIndividual().getRating());
            }
            iteration++;
        }
        */
        //saveToXmlFile("12a.xml");
        /*
        long ticks=0;
        try{
            for(int j=0; j < 20; j++){
                System.out.println("POPULATION: "+String.valueOf(j));
                float bestRating = 0;
                population.clear();
                population.createFirstPopulation();
                FileOutputStream os = new FileOutputStream(new File(String.format("data%1d.txt", j)));
                OutputStreamWriter osw = new OutputStreamWriter(os);
                while(population.generation < 5000){
                    if(population.getBestIndividual().getRating() > bestRating){
                        bestRating = population.getBestIndividual().getRating();
                        osw.write(String.valueOf(population.generation)+"\t");
                        osw.write(String.valueOf(bestRating)+"\n");
                    }
                    if(System.currentTimeMillis() > ticks+5000){
                        ticks = System.currentTimeMillis();
                        System.out.println(population.generation);
                        System.out.flush();
                    }
                    population.nextGeneration();
                }
                osw.close();
                os.close();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        */
    }


}
