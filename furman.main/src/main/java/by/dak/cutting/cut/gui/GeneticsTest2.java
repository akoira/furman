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
import by.dak.cutting.cut.guillotine.AIndividual;
import by.dak.cutting.cut.guillotine.AMigrator;
import by.dak.cutting.cut.guillotine.ARatingFunction;
import by.dak.cutting.cut.guillotine.Strips;

import javax.swing.*;
import java.io.IOException;

/**
 * @author Peca
 */
public class GeneticsTest2
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
            cutSettings = CutSettings.loadFromFile("board01.tocut");
        }

        Element[] elements;
        elements = cutSettings.getElements();


        Strips strips = new Strips(cutSettings.getSheets());
        strips.setSawWidth(0);
        strips.setAllowRotation(true);

        ARatingFunction rFX = new ARatingFunction(strips, elements);
        AIndividual ind = new AIndividual(elements.length);


        /* population = new Population(rFX, ind);
        population.populationSize = populationCount;
        population.survive = survive;
        population.pMutation = pMutation;
        population.chromMutationCount = chromMutationCount;
        population.keep = keep;
        population.pNewIndividual = pNewIndividual;
        population.newIndividualImmortalTime = newIndividualImmortalTime;
        population.maxLifeTime = maxLifeTime;
        population.maxStagnationDuration = maxStagnationDuration;
        population.createFirstPopulation();
        population.rateAll();
        */
        //population.nextGenerations(1);

        World world = new World(rFX, ind);
        world.setMigrator(new AMigrator(strips.getSegments().get(0), elements));


        CommonGui.setElements(elements);
        CommonGui.setStrips(strips);

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {

        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        WorldView wv = new WorldView(world);

        frame.getContentPane().add(wv);
        frame.setSize(600, 500);
        frame.setVisible(true);
        //pv.showBestIndividual();


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
