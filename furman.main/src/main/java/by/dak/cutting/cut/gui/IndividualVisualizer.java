/*
 * IndividualVisualizer.java
 *
 * Created on 8. kv�ten 2007, 14:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.genetics.Individual;

import javax.swing.*;

/**
 * @author Peca
 */
public abstract class IndividualVisualizer
{

    public abstract void update();

    public abstract JFrame show(int x, int y);

    public abstract void setIndividual(Individual individual);
    //public abstract void setLocation(int x, int y);
}
