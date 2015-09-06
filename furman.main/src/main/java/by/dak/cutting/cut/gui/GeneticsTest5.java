/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.genetics.*;
import by.dak.cutting.cut.gui.population.PopulationView;

import javax.swing.*;
import java.io.IOException;

/**
 * @author Peca
 */
public class GeneticsTest5
{


    public static void main(String args[]) throws IOException
    {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);


        RatingFunction rFX = new PRatingFunction();
        Individual ind = new PIndividual();

        PopulationParams params = new PopulationParams();
        params.setMaxIndividualsCount(50);

        Population population;
        population = new Population(rFX, ind);
        population.setPopulationParams(params);
        population.createFirstPopulation();

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
