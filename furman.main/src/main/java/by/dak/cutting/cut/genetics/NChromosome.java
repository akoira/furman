/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

/**
 *
 * @author Peca
 */

import by.dak.cutting.cut.common.Common;

/**
 * @author Peca
 */
public class NChromosome extends Chromosome
{

    private int[] elements;
    private int minValue;
    private int maxValue;
    private int prefferedValue;
    private int sigma;
    private boolean useGauss = true;

    /**
     * Creates a new instance of SequenceChromosome
     */
    public NChromosome(int size, int minValue, int maxValue)
    {
        this(size, minValue, maxValue, (minValue + maxValue) / 2, maxValue - minValue);
    }

    public NChromosome(int size, int minValue, int maxValue, int prefferedValue, int sigma)
    {
        this.elements = new int[size];
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.prefferedValue = prefferedValue;
        this.sigma = sigma;
    }

    public void initRandom()
    {
        for (int i = 0; i < elements.length; i++)
        {
            if (useGauss)
            {
                int value = Common.nextIntGauss(this.minValue, this.maxValue, this.prefferedValue, this.sigma);
                elements[i] = value;
            }
            else
            {
                elements[i] = Common.nextInt(this.minValue, this.maxValue);
            }
            assert (elements[i] >= minValue);
            assert (elements[i] <= maxValue);
        }
    }

    public void mutate()
    {
        //prohozeni
        if (Common.luck(0.0f))
        {
            int i1 = Common.nextInt(this.elements.length);
            int i2 = Common.nextInt(this.elements.length);
            int tmp = this.elements[i1];
            this.elements[i1] = this.elements[i2];
            this.elements[i2] = tmp;
        }
        //nahrazeni novym cislem
        else
        {
            int i1 = Common.nextInt(this.elements.length);
            this.elements[i1] = Common.nextInt(this.maxValue - this.minValue + 1) + this.minValue;
        }
    }

    public NChromosome clone()
    {
        NChromosome ch = new NChromosome(this.getElements().length, this.minValue, this.maxValue, this.prefferedValue, this.sigma);
        ch.useGauss = this.useGauss;
        for (int i = 0; i < this.elements.length; i++)
        {
            ch.elements[i] = this.elements[i];
        }
        return ch;
    }

    public Chromosome crossWith(Chromosome ch)
    {
        NChromosome ch2 = (NChromosome) ch;
        NChromosome ch3 = this.clone();


        //zkopirujeme druhou polovinu 2. chromozomu
        int mi = ch2.elements.length / 2;
        for (int i = mi; i < Math.min(ch2.elements.length, ch3.elements.length); i++)
        {
            ch3.elements[i] = ch2.elements[i];
        }

        return ch3;
    }

    public String toString()
    {
        String s = "";
        for (int i = 0; i < getElements().length; i++)
        {
            s = s + String.valueOf(getElements()[i]) + ",";
        }
        return s;
    }

    public void fromString(String s)
    {
        String[] els = s.split(",");
        for (int i = 0; i < getElements().length; i++)
        {
            elements[i] = Integer.parseInt(els[i]);
        }
    }

    public int[] getElements()
    {
        return elements;
    }

    public boolean isUseGauss()
    {
        return useGauss;
    }

    public void setUseGauss(boolean useGauss)
    {
        this.useGauss = useGauss;
    }


}
