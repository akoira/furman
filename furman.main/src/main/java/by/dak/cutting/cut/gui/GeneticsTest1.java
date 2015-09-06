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
import by.dak.cutting.cut.guillotine.GIndividual;
import by.dak.cutting.cut.guillotine.GRatingFunction;
import by.dak.cutting.cut.guillotine.Strips;

import javax.swing.*;
import java.io.IOException;

/**
 * @author Peca
 */
public class GeneticsTest1
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
            cutSettings = CutSettings.loadFromFile("Board01.tocut");
        }

//        Element[] elements = Common.createRandomeElements(100, 10, 10, 150, 1);
        //      Strips strips = new Strips(new Dimension[]{ new Dimension(1000, 200)});

        Element[] elements;
        elements = cutSettings.getElements();


        Strips strips = new Strips(cutSettings.getSheets());
        strips.setSawWidth(0);
        strips.setAllowRotation(true);

        GRatingFunction rFX = new GRatingFunction(strips, elements);
        GIndividual ind = new GIndividual(elements.length);

        Population population;
        population = new Population(rFX, ind);
        PopulationParams params = new PopulationParams();
        population.setPopulationParams(params);
        population.createFirstPopulation();

        //population.nextGenerations(1);

        //if(true)return;

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

        PopulationView pv = new PopulationView(population);

        frame.getContentPane().add(pv);
        frame.setSize(400, 500);
        frame.setVisible(true);
        pv.showBestIndividual();


    }


}
