/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.genetics.Individual;

/**
 * @author Peca
 */

public class AIndividual extends GIndividual implements IMigratingIndividual
{

    private AIndividual sister;

    public AIndividual(int elementsCount)
    {
        super(elementsCount);
    }

    @Override
    public Individual clone()
    {
        AIndividual ind = (AIndividual) super.clone();
        ind.sister = this.sister;
        return ind;
    }

    public AIndividual getSister()
    {
        return sister;
    }

    public void setSister(AIndividual sister)
    {
        this.sister = sister;
    }

    @Override
    protected Individual createNewInstance()
    {
        return new AIndividual(this.getElementsCount());
    }

    public boolean isComplete()
    {
        return this.getSChromosome().getElements().length == 0;
    }

    public void setOffspringsQuality(float quality)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public float getOffspringsQuality()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public float getProgress()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
