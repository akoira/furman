/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.genetics.Individual;

/**
 * @author Peca
 */
public interface IMigratingIndividual
{
    public boolean isComplete();

    public void setOffspringsQuality(float quality);

    public float getOffspringsQuality();

    public Individual getSister();

    public float getProgress();
}
