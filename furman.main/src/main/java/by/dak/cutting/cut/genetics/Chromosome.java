/*
 * Chromosome.java
 *
 * Created on 27. ��jen 2006, 20:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

/**
 * @author Peca
 */
public abstract class Chromosome
{

    /**
     * Creates a new instance of Chromosome
     */
    public abstract void initRandom();

    public abstract Chromosome crossWith(Chromosome ch);

    public abstract Chromosome clone();

    public abstract void mutate();

    public abstract String toString();

    public abstract void fromString(String s);
    // public abstract Chromosome createNewInstance();

}
