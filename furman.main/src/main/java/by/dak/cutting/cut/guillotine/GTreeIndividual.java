/*
 * GIndividual.java
 *
 * Created on 5. kv�ten 2007, 11:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.Chromosome;
import by.dak.cutting.cut.genetics.GTreeChromosome;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.SRIndividual;

/**
 * @author Peca
 */
public class GTreeIndividual extends SRIndividual
{

    Segment segment;
    Element[] elements;
    int maxLevel;
    //GChromosome gchromosome;

    /**
     * @param elementsCount
     * @param maxSegmentWidth
     */
    public GTreeIndividual(Segment segment, Element[] elements, int maxLevel)
    {
        super(elements.length);
        this.segment = segment;
        this.elements = elements;
        this.maxLevel = maxLevel;
    }


    @Override
    protected Chromosome initChromosome(int index)
    {
        if (index < 2) return super.initChromosome(index);

        switch (index)
        {
            case 2:
                return new GTreeChromosome(segment, elements, maxLevel);
        }
        assert (false); //k tomu by nemelo nikdy dojit
        return null;
    }

    @Override
    protected int getChromosomesCount()
    {
        return 3;
    }

    public GTreeChromosome getGTreeChromosome()
    {
        return (GTreeChromosome) this.getChromosome(2);
    }

    @Override
    protected Individual createNewInstance()
    {
        return new GTreeIndividual(segment, elements, maxLevel);
    }

    public Segment getSegment()
    {
        return segment;
    }

    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

}
