/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.common;

import by.dak.cutting.cut.genetics.Individual;

/**
 * @author Peca
 */
public interface IReproductionListener
{
    void crossing(Individual parent1, Individual parent2, Individual child1, Individual child2);

    void mutation(Individual parent, Individual child);
}
