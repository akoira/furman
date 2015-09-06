/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

/**
 * @author Peca
 */
public abstract class IndividualSign
{

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    public abstract int compare(IndividualSign sign);
}
