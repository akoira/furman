/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.genetics.*;

/**
 * @author Peca
 */
public class AMigrator extends Migrator
{

    private GuillotineTreeCutter cutter;
    private Element[] elements;
    private Segment segment;
    private int elementsTotalArea = 0;

    public AMigrator(Segment segment, Element[] elements)
    {
        this.segment = segment;
        this.elements = elements;
        this.cutter = new GuillotineTreeCutter();
        this.elementsTotalArea = Utils.getElementsTotalArea(elements);
    }

    public Individual Migrate(Individual individual)
    {
        AIndividual ind = (AIndividual) individual;
        SChromosome sch = ind.getSChromosome();
        NChromosome nch = ind.getNChromosome();
        RChromosome rch = ind.getRChromosome();

        Segment seg = new Segment();
        seg.setLength(this.segment.getWidth());
        seg.setFixedLength(true);

        CutResult result = cutter.cutSegment(seg, this.elements, sch.getElements(), rch.getElements(), nch.getElements()[0], 3);
        AIndividual ind2 = (AIndividual) ind.clone();
        int[] indexes = result.getUncuttedIndexes();

        ind2.setSister(ind);
        ind2.getSChromosome().setElements(indexes);
        return ind2;
    }
}
