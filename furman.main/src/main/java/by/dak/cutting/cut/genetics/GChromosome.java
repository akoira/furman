/*
 * GChromosome.java
 *
 * Created on 5. kv�ten 2007, 23:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import by.dak.cutting.cut.common.Common;

import java.util.ArrayList;

/**
 * @author Peca
 */
public class GChromosome extends Chromosome
{
    public ArrayList<Integer> elements;

    /**
     * Creates a new instance of GChromosome
     */
    public GChromosome()
    {
        elements = new ArrayList<Integer>();
    }

    public void initRandom()
    {
        for (int i = 0; i < 6; i++)
        {
            this.elements.add(new Integer(Common.nextInt(250) + 50));
        }
    }

    public Chromosome crossWith(Chromosome ch)
    {
        return this.clone();
    }

    public GChromosome clone()
    {
        GChromosome ch = new GChromosome();

        for (int i = 0; i < this.elements.size(); i++)
        {
            ch.elements.add(this.elements.get(i));
        }
        return ch;
    }

    public void mutate()
    {
        if (elements.size() <= 0) return;
        int i = Common.nextInt(elements.size());
        float f = elements.get(i) * (Common.nextFloat() + 0.5f);
        elements.set(i, new Integer(Math.round(f)));
    }

    public String toString()
    {
        String s = "";
        for (int i = 0; i < elements.size(); i++)
        {
            s = s + elements.get(i).toString() + ",";
        }
        return s;
    }

    public void fromString(String s)
    {
        String[] els = s.split(",");
        elements.clear();
        for (int i = 0; i < els.length; i++)
        {
            elements.add(new Integer(Integer.parseInt(els[i])));
        }
    }

}
