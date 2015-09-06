/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.Chromosome;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.NChromosome;

/**
 * @author Peca
 */
public class CIndividual extends BIndividual implements IMigratingIndividual
{

    private CIndividual sister;
    private Element[] uncuttedElements;
    private long cuttedArea;
    private long usedArea;
    private int stripIndex;
    private int stripUsedLength;
    private float offspringsQuality;

    public CIndividual(int elementsCount)
    {
        super(elementsCount);
    }

    @Override
    protected Chromosome initChromosome(int index)
    {
        switch (index)
        {
            case 0:
                return super.initChromosome(index);
            case 1:
                return new NChromosome(1, 1, getElementsCount() + 1, 2, 2);
        }
        assert (false); //k tomu by nemelo nikdy dojit
        return null;
    }

    @Override
    public Individual clone()
    {
        CIndividual ind = (CIndividual) super.clone();
        ind.setSister(this.sister);
        ind.setUncuttedElements(this.uncuttedElements);
        ind.setCuttedArea(this.cuttedArea);
        ind.setUsedArea(this.usedArea);
        ind.setStripIndex(this.stripIndex);
        ind.setStripUsedLength(this.stripUsedLength);
        ind.setOffspringsQuality(this.offspringsQuality);
        return ind;
    }

    @Override
    protected Individual createNewInstance()
    {
        return new CIndividual(this.getElementsCount());
    }

    public CIndividual getSister()
    {
        return sister;
    }

    public void setSister(CIndividual sister)
    {
        this.sister = sister;
    }

    public long getCuttedArea()
    {
        return cuttedArea;
    }

    public void setCuttedArea(long cuttedArea)
    {
        this.cuttedArea = cuttedArea;
    }

    public int getStripIndex()
    {
        return stripIndex;
    }

    public void setStripIndex(int stripIndex)
    {
        this.stripIndex = stripIndex;
    }

    public long getUsedArea()
    {
        return usedArea;
    }

    public void setUsedArea(long usedArea)
    {
        this.usedArea = usedArea;
    }

    public int getStripUsedLength()
    {
        return stripUsedLength;
    }

    public void setStripUsedLength(int stripUsedLength)
    {
        this.stripUsedLength = stripUsedLength;
    }

    public Element[] getUncuttedElements()
    {
        return uncuttedElements;
    }

    public void setUncuttedElements(Element[] uncuttedElements)
    {
        this.uncuttedElements = uncuttedElements;
    }

    public boolean isComplete()
    {
        return this.uncuttedElements.length == 0;
    }

    public void setOffspringsQuality(float quality)
    {
        this.offspringsQuality = quality;
    }

    public float getOffspringsQuality()
    {
        return this.offspringsQuality;
    }

    @Override
    public String toString()
    {
        return super.toString() + String.format(" {%1$.2f/%2$d} ", this.offspringsQuality, this.getBornTime());
    }

    public float getProgress()
    {
        return ((CIndividualRating) this.getIndividualRating()).getProgress();
    }


}
