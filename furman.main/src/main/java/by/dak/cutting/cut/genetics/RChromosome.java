/*
 * RChromosome.java
 *
 * Created on 31. ��jen 2006, 11:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import by.dak.cutting.cut.common.Common;

/**
 * @author Peca
 */
public class RChromosome extends Chromosome
{

    private boolean[] elements;

    /**
     * Creates a new instance of SequenceChromosome
     */
    public RChromosome(int size)
    {
        elements = new boolean[size];
    }

    public void initRandom()
    {
        for (int i = 0; i < getElements().length; i++)
        {     //naplnime chromozom nahodnymi hodnotami
            elements[i] = Common.nextBoolean();
            //elements[i] = false;
        }
    }

    public void mutate()
    {
        int i1 = Common.nextInt(getElements().length);
        elements[i1] = !elements[i1];              //na nahodne pozici invertujeme hodnotu

    }

    public RChromosome clone()
    {
        RChromosome ch = new RChromosome(this.getElements().length);
        for (int i = 0; i < this.getElements().length; i++)
        {
            ch.elements[i] = this.getElements()[i];
        }
        return ch;
    }

    public Chromosome crossWith(Chromosome ch)
    {
        RChromosome ch1 = (RChromosome) ch;
        RChromosome ch2 = this;
        RChromosome ch3 = new RChromosome(ch1.getElements().length);
        int mi = getElements().length / 2;       //najdeme polovinu chromozomu
        for (int i = 0; i < mi; i++)
        {         //zkopirujeme prvni polovinu 1. chromozomu
            ch3.elements[i] = ch1.getElements()[i];
        }
        for (int i = mi; i < getElements().length; i++)
        {      //zkopirujeme druhou polovinu 2. chromozomu
            ch3.elements[i] = ch2.getElements()[i];
        }

        return ch3;
    }

    public String toString()
    {
        String s = "";
        for (int i = 0; i < getElements().length; i++)
        {
            if (getElements()[i]) s = s + "1,";
            else s = s + "0,";
        }
        return s;
    }

    public void fromString(String s)
    {
        String[] els = s.split(",");
        for (int i = 0; i < getElements().length; i++)
        {
            elements[i] = Boolean.parseBoolean(els[i]);
        }
    }

    public boolean[] getElements()
    {
        return elements;
    }


}
