/*
 * SequenceChromosome.java
 *
 * Created on 27. ��jen 2006, 20:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import by.dak.cutting.cut.common.Common;

/**
 * @author Peca
 */
public class SChromosome extends Chromosome
{

    private int[] elements;

    /**
     * Creates a new instance of SequenceChromosome
     */
    public SChromosome(int size)
    {
        elements = new int[size];
        for (int i = 0; i < size; i++)
        {
            elements[i] = i;
        }
    }

    public void initRandom()
    {
        for (int i = 0; i < elements.length; i++)
        {
            elements[i] = i;
        }
        for (int i = 0; i < elements.length; i++)
        {
            int tmp = elements[i];
            int index = Common.nextInt(elements.length);
            elements[i] = elements[index];
            elements[index] = tmp;
        }
    }

    public void mutate()
    {
        if (elements.length < 2) return;
        int i1 = 0;
        int i2 = 0;
        if (elements.length > 2)
        {
            i1 = Common.nextInt(elements.length);
            while ((i2 = Common.nextInt(elements.length)) == i1) ;
        }
        int tmp = elements[i1];
        elements[i1] = elements[i2];
        elements[i2] = tmp;
    }

    public SChromosome clone()
    {
        SChromosome ch = new SChromosome(this.elements.length);
        for (int i = 0; i < this.elements.length; i++)
        {
            ch.elements[i] = this.elements[i];
        }
        return ch;
    }

    public Chromosome crossWith(Chromosome ch)
    {
        SChromosome ch1 = (SChromosome) ch;
        SChromosome ch2 = this;
        SChromosome ch3 = new SChromosome(ch1.elements.length);
        int mi = elements.length / 2;       //najdeme polovinu chromozomu
        for (int i = 0; i < mi; i++)
        {         //zkopirujeme prvni polovinu chromozomu
            ch3.elements[i] = ch1.elements[i];
        }
        int index = mi;
        for (int i = 0; i < ch2.elements.length; i++)
        {   //druhou polovinu doplnime posloupnosti jaka se objevuje u druheho chromozomu
            boolean b = false;
            for (int j = 0; j < mi; j++)
            {
                if (ch2.elements[i] == ch3.elements[j])
                {
                    b = true;
                    break;
                }
            }
            if (!b)
            {
                ch3.elements[index] = ch2.elements[i];
                index++;
            }

        }
        return ch3;
    }

    public String toString()
    {
        String s = "";
        for (int i = 0; i < elements.length; i++)
        {
            s = s + String.valueOf(elements[i]) + ",";
        }
        return s;
    }

    public void fromString(String s)
    {
        String[] els = s.split(",");
        for (int i = 0; i < elements.length; i++)
        {
            elements[i] = Integer.parseInt(els[i]);
        }
    }

    public int[] getElements()
    {
        return elements;
    }

    public void setElements(int[] elements)
    {
        this.elements = elements;
    }

}
