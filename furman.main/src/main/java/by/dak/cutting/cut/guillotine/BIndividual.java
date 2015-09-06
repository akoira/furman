/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.genetics.Chromosome;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.NChromosome;

/**
 * @author Peca
 */
public class BIndividual extends Individual
{
    private float ratioConstraint;
    private int elementsCount;

    public BIndividual(int elementsCount)
    {
        super();
        this.elementsCount = elementsCount;
        //initChromosomes() se vola automaticky, ale to jsme potlacili, tak to ted zavolame rucne
        //je to kvuli nastaveni elementsCount
        super.initChromosomes();

    }

    @Override
    protected Individual createNewInstance()
    {
        return new BIndividual(this.elementsCount);
    }

    public float getRatioConstraint()
    {
        return ratioConstraint;
    }

    public void setRatioConstraint(float ratioConstraint)
    {
        this.ratioConstraint = ratioConstraint;
    }

    @Override
    public Individual clone()
    {
        BIndividual ind = (BIndividual) super.clone();
        ind.setRatioConstraint(this.ratioConstraint);
        return ind;
    }

    @Override
    protected Chromosome initChromosome(int index)
    {
        switch (index)
        {
            case 0:
            {
                NChromosome nchrom = new NChromosome(this.elementsCount, 0, this.elementsCount * 2);
                nchrom.setUseGauss(false);
                return nchrom;
            }
            case 1:
                return new NChromosome(this.elementsCount, 1, this.elementsCount + 1, 2, 2);
        }
        assert (false); //k tomu by nemelo nikdy dojit
        return null;
    }

    @Override
    protected void initChromosomes()
    {
        //toto potrebujeme potlacit, volame to rucne na konci konstruktoru
        //super.initChromosomes();
    }

    @Override
    protected int getChromosomesCount()
    {
        return 2;
    }

    public NChromosome getBuildSequenceChromosome()
    {
        return (NChromosome) this.getChromosome(0);
    }

    public NChromosome getFirstRowCountChromosome()
    {
        return (NChromosome) this.getChromosome(1);
    }

    public int getElementsCount()
    {
        return elementsCount;
    }


}
