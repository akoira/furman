/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.PIndividual;

import javax.swing.*;

/**
 * @author Peca
 */
public class PIndividualVisualizer extends IndividualVisualizer
{
    private JFrame frame;
    private PIndividual individual;
    private PopulationParamsView populationParamsView = new PopulationParamsView();

    public PIndividualVisualizer(PIndividual individual)
    {
        this.individual = individual;
        this.frame = new JFrame();
        frame.getContentPane().add(populationParamsView);
    }

    @Override
    public void update()
    {
        populationParamsView.setPopulationParams(individual.getPopulationParams());
        frame.repaint();
    }

    @Override
    public JFrame show(int x, int y)
    {
        this.update();
        frame.pack();
        frame.setLocation(x, y);
        frame.setVisible(true);
        return frame;
    }

    @Override
    public void setIndividual(Individual individual)
    {
        this.individual = (PIndividual) individual;
        update();
    }

}
