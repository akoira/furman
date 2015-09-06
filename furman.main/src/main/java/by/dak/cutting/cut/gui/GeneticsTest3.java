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
import by.dak.cutting.cut.genetics.Population;
import by.dak.cutting.cut.genetics.PopulationParams;
import by.dak.cutting.cut.gui.population.PopulationView;
import by.dak.cutting.cut.guillotine.BIndividual;
import by.dak.cutting.cut.guillotine.BRatingFunction;
import by.dak.cutting.cut.guillotine.IndividualCutter;
import by.dak.cutting.cut.guillotine.Strips;

import javax.swing.*;
import java.io.IOException;

/**
 * @author Peca
 */
public class GeneticsTest3
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

        float forceMinimumRatio = 1.00f;
        boolean forceBestFit = true;

        BRatingFunction rFX = new BRatingFunction(strips, elements);
        BIndividual ind = new BIndividual(elements.length);
        rFX.setForceMinimumRatio(forceMinimumRatio);
        rFX.setForceBestFit(forceBestFit);

        Population population = new Population(rFX, ind);
        PopulationParams params = new PopulationParams();
        population.setPopulationParams(params);
        population.createFirstPopulation();

        //population.nextGenerations(1);

        //World world = new World(rFX, ind);
        //world.setMigrator(new AMigrator(segment, elements));


        CommonGui.setElements(elements);
        CommonGui.setStrips(strips);
        CommonGui.setForceMinimumRatio(forceMinimumRatio);
        CommonGui.setForceBestFit(forceBestFit);
        IndividualCutter.setSortStrips(true);

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

        //WorldView wv = new WorldView(world);
        PopulationView pv = new PopulationView(population);

        //frame.getContentPane().add(wv);
        frame.getContentPane().add(pv);
        frame.setSize(350, 500);
        frame.setVisible(true);
        //pv.showBestIndividual();


    }


}
