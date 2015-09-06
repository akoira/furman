/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.genetics.Chromosome;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.NChromosome;
import by.dak.cutting.cut.genetics.SRIndividual;

/**
 * @author Peca
 */
public class GIndividual extends SRIndividual
{

    public GIndividual(int elementsCount)
    {
        super(elementsCount);
    }

    public NChromosome getNChromosome()
    {
        return (NChromosome) this.getChromosome(2);
    }

    @Override
    protected Chromosome initChromosome(int index)
    {
        if (index < 2) return super.initChromosome(index);

        switch (index)
        {
            case 2:
            {
                NChromosome nchrom = new NChromosome(this.getElementsCount(), 1, this.getElementsCount() + 1, 2, 2);
                nchrom.setUseGauss(true);
                return nchrom;
            }
        }
        assert (false); //k tomu by nemelo nikdy dojit
        return null;
    }

    @Override
    protected int getChromosomesCount()
    {
        return 3;
    }

    @Override
    protected Individual createNewInstance()
    {
        return new GIndividual(this.getElementsCount());
    }


}
